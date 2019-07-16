package comm;

import javax.jms.JMSException;
import javax.naming.NamingException;

/**
 * 
 * @author Agustín Espinosa Minguet (aespinos@upvnet.upv.es)
 *
 */
public class API {

	/**
	 * Este método es llamado cuando se completa la inicialización de la
	 * interfaz de usuario. Su función consiste en inicializar el componente de
	 * comunicación.
	 */
	public static void initialize() {
		Communication.getCommunication().initialize();
		(new Thread(Communication.getCommunication())).start();
	}

	/**
	 * Envía un mensaje de chat al usuario especificado. Es llamado desde la
	 * interfaz de usuario cuando se pulsa el botón de envío o la tecla Entrar
	 * 
	 * @param destUser
	 *            El usuario destinatario
	 * @param text
	 *            El texto del mensaje
	 * @param timestamp
	 *            La hora local del sistema
	 * @throws NamingException
	 * @throws JMSException
	 */
	public static void sendChatMessage(String destUser, String text, long timestamp)
			throws NamingException, JMSException {
		Communication.getCommunication().sendChatMessage(destUser, text, timestamp);
	}

	/**
	 * Notifica al usuario especificado que un mensaje en particular ha sido
	 * leído. Es llamado desde la interfaz de usuario cuando el mensaje se
	 * muestra al estar seleccionado el usuario especificado en el panel
	 * derecho.
	 * 
	 * @param user
	 *            El usuario que envió el mensaje que se ha leído
	 * @param timestamp
	 *            La hora que fue establecida en origen del mensaje que se ha
	 *            leído
	 * @throws NamingException
	 * @throws JMSException
	 */
	public static void sendMessageReaded(String user, long timestamp) throws NamingException, JMSException {
		Communication.getCommunication().sendMessageReaded(user, timestamp);
	}
}
