package server;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;

import javax.jms.ConnectionFactory;
import javax.jms.JMSConsumer;
import javax.jms.JMSContext;
import javax.jms.JMSProducer;
import javax.jms.ObjectMessage;
import javax.jms.Queue;
import javax.management.MalformedObjectNameException;
import javax.naming.InitialContext;
import org.jolokia.client.J4pClient;
import org.jolokia.client.exception.J4pException;
import org.jolokia.client.request.J4pExecRequest;
import org.jolokia.client.request.J4pReadRequest;
import org.jolokia.client.request.J4pReadResponse;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import messageBodies.NewUser;
import messageBodies.UserStart;
import messageBodies.UsersList;

/**
 * @author Agustín Espinosa Minguet (aespinos@upvnet.upv.es)
 * 
 *         Clase para un único objeto que actúa como servidor del servicio de
 *         mensajería. Sus funciones son:
 * 
 *         1.- Mantener y difundir la lista de usuarios participantes
 * 
 *         2.- Crear, la primera vez que un usuario participa, su cola JMS
 */
public class CsdMessengerServer {

	public static void main(final String[] args) throws Exception {

		// Iniciamos el contexto JNDI. Se requiere que exista el fichero
		// jndi.properties correctamente configurado.
					
		InitialContext ic = new InitialContext();
		
		// Establecemos la conexión con el broker
		
		
		ConnectionFactory cfac = (ConnectionFactory) ic.lookup("ConnectionFactory");
		
		// Creamos el contexto JMS

		JMSContext jmsContext = cfac.createContext();

		// Accedemos a la cola donde esperamos las peticiones

		Queue queue = (Queue) ic.lookup("dynamicQueues/csd");

		// Creamos el consumidor para dicha cola

		JMSConsumer consumer = jmsContext.createConsumer(queue);

		// Y el productor para enviar los mensajes a los usuarios

		JMSProducer producer = jmsContext.createProducer();

		// Obtenemos la lista de usuarios ya registrados

		ArrayList<String> currentUsers = registeredUsers();

		System.out.println("Usuarios ya registrados: " + currentUsers);

		// Accedemos a sus colas y las guardamos para futuras referencias
		Map<String, Queue> userQueues = new HashMap<String, Queue>();

		for (String s : currentUsers) {
			Queue userQueue = (Queue) ic.lookup("dynamicQueues/" + "users-" + s);
			userQueues.put(s, userQueue);
		}

		// Nos preparamos para atender peticiones

		Queue replyQueue;

		ObjectMessage replyMsg = jmsContext.createObjectMessage();

		// Hasta que finalize la aplicación, esperamos peticiones y las
		// atendemos

		while (true) {

			// Esperamos la llegada de una petición. Será un mensaje JMS que
			// contenga un objeto

			System.out.println("Esperando petición:");
			ObjectMessage msg = (ObjectMessage) consumer.receive();

			// Averiguamos la clase del objeto que se envía en el mensaje, para
			// determinar qué hacer
			// *en este caso solo se esperan objetos de una única clase, pero
			// mejor hacerlo así en previsión de incorporar nueva funcionalidad

			String msgBodyClass = msg.getObject().getClass().toString();
			System.out.println("Recibido un mensaje de la clase " + msgBodyClass);

			switch (msgBodyClass) {

			case "class messageBodies.UserStart": {
				// Un usuario ha notificado que se ha conectado. Esto lo hace la
				// aplicación cliente cuando se inicia.

				// Averiguamos qué usuario es
				String userName = ((UserStart) msg.getObject()).getName();
				if (userName.equals("")) {
					System.out.println("He recibido una petición UserStart anónima. No hago nada");
				} else {
					System.out.println("El usuario " + userName + " ha enviado la petición");

					if (!currentUsers.contains(userName)) {

						// El usuario es la primera vez que se conecta
						// Lo añadimos a la lista de usuarios conocidos

						currentUsers.add(userName);

						// Creamos la cola JMS para dicho usuario.

						createJMSQueue(userName);

						// Avisamos a los demás usuarios que hay un usuario
						// nuevo

						ObjectMessage newUserNotificationMsg = jmsContext.createObjectMessage();

						for (Queue q : userQueues.values()) {
							newUserNotificationMsg.setObject(new NewUser(userName));
							producer.send(q, newUserNotificationMsg);
						}

						// Anotamos la cola del nuevo usuario para futuras
						// comunicaciones

						Queue userQueue = (Queue) ic.lookup("dynamicQueues/users-" + userName);
						userQueues.put(userName, userQueue);

					}

					// Sea o no un usuario nuevo, le contestamos con la lista de
					// usuarios conocidos, para que construya la lista de
					// usuarios
					// que se muestra en el panel izquierdo de la aplicación
					// cliente

					replyQueue = (Queue) msg.getJMSReplyTo();

					replyMsg.setObject(new UsersList(currentUsers));

					producer.send(replyQueue, replyMsg);
				}
				break;
			}
			default: {

				// Clase del objeto del mensaje desconocida.

				System.out.println("Clase del cuerpo del mensaje inesperada: " + msgBodyClass);
				break;
			}
			}

		}

	}

	private static J4pClient j4pClient = new J4pClient("http://localhost:" + getJolokiaPort()+ "/jolokia");

	private static ArrayList<String> registeredUsers() throws MalformedObjectNameException, J4pException {

		ArrayList<String> result = new ArrayList<String>();
		// Vía jolokia (acceso HTTP a la interfaz de gestión de Java JMX),
		// obtenemos la lista de colas JMS que ya han sido creadas.

		J4pReadRequest req = new J4pReadRequest(
				"org.apache.activemq.artemis:type=Broker,brokerName=\"0.0.0.0\",module=JMS,serviceType=Server",
				"QueueNames");
		J4pReadResponse resp = j4pClient.execute(req);

		// La respuesta es un objeto JSON, y el campo "value" es la lista de
		// colas JMS del broker

		JSONObject jo = resp.asJSONObject();

		JSONArray ja = (JSONArray) jo.get("value");

		// Construimos un HashMap asociando a cada nombre de usuario el objeto
		// administrado cola correspondiente y una lista con los nombres de los
		// usuarios.
		//
		// Las colas se denominan "users-NOMBREDELUSUARIO" y pueden localizarse
		// vía JNDI usando como clave de búsqueda
		// "dynamicQueues/users-NOMBREDELUSUARIO

		@SuppressWarnings("unchecked")
		Iterator<String> iterator = ja.iterator();
		while (iterator.hasNext()) {
			String queueName = iterator.next();
			if (queueName.startsWith("users-")) {
				String userName = queueName.substring(queueName.lastIndexOf("-") + 1);
				result.add(userName);
			}
		}

		return result;
	}

	private static void createJMSQueue(String userName) throws MalformedObjectNameException, J4pException {
		// Creamos la cola JMS para dicho usuario. De nuevo, al
		// tratarse de una acción administrativa la realizamos
		// vía
		// la interfaz JMX jolokia

		J4pExecRequest execReq = new J4pExecRequest(
				"org.apache.activemq.artemis:type=Broker,brokerName=\"0.0.0.0\",module=JMS,serviceType=Server",
				"createQueue(java.lang.String)", "users-" + userName);
		j4pClient.execute(execReq);
	}
	
	private static String getJolokiaPort() {
    	Properties prop = new Properties();
    	InputStream input = null;
		String portString = "";

    	try {

    		String filename = "jndi.properties";
    		input = CsdMessengerServer.class.getClassLoader().getResourceAsStream(filename);
    		if(input==null){
    	            System.out.println("No existe el fichero " + filename);
    		    return "";
    		}

    		prop.load(input);
    		
    		String cfName = prop.getProperty("connectionFactory.ConnectionFactory");
			
			portString = (cfName.split(":"))[2];
			
			int portNumber = Integer.parseInt(portString) + 1;

			portString = String.valueOf(portNumber);
			

    	} catch (IOException ex) {
    		ex.printStackTrace();
        } 
				
		return portString;
	}
}
