package comm;

import java.util.ArrayList;

import javax.jms.ConnectionFactory;
import javax.jms.JMSConsumer;
import javax.jms.JMSContext;
import javax.jms.JMSException;
import javax.jms.JMSProducer;
import javax.jms.ObjectMessage;
import javax.jms.Queue;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import messageBodies.AckChatMessage;
import messageBodies.NewChatMessage;
import messageBodies.NewUser;
import messageBodies.ReadedChatMessage;
import messageBodies.UserStart;
import messageBodies.UsersList;
import ui.Model;

import javax.jms.TemporaryQueue;
import javax.jms.*;

/**
 * 
 * @author Agustín Espinosa Minguet (aespinos@upvnet.upv.es)
 *
 */
public class Communication implements Runnable {

    public static Communication theCommunication = new Communication();
    
    private InitialContext ic;
    private JMSContext jmsContext;
    private JMSProducer producer;

    private Communication() {
    }

    static Communication getCommunication() {
        return theCommunication;
    }



    /**
     * Este método es llamado cuando se completa la inicialización de la
     * interfaz de usuario. Su función consiste en inicializar el componente de
     * comunicación.
     * 
     */
    void initialize() {
        // ACTIVIDAD 3
        try{
            ic = new InitialContext();
            
            ConnectionFactory cfac = (ConnectionFactory) ic.lookup("ConnectionFactory");
            
            jmsContext = cfac.createContext();
            
            producer = jmsContext.createProducer();
            
            UserStart us = new UserStart(ui.API.getMyName());
            
            ObjectMessage replyMsg = jmsContext.createObjectMessage(us);
            
            TemporaryQueue tq = jmsContext.createTemporaryQueue();
            
            replyMsg.setJMSReplyTo(tq);
            
            Queue queue = (Queue) ic.lookup("dynamicQueues/csd");
    
            producer.send(queue, replyMsg);
            
            JMSConsumer consumer = jmsContext.createConsumer(tq);
            
            UsersList ul =(UsersList) ((ObjectMessage) consumer.receive()).getObject();
            
            ui.API.updateUserList(ul.getUsers());
        } catch (Exception e) {}
        
    }

    /*
     * Hilo receptor de mensajes
     */
    public void run() {

        // ACTIVIDAD 6
        
        try{
            JMSContext jmscontext = jmsContext.createContext(JMSContext.AUTO_ACKNOWLEDGE);
            
            JMSProducer produc = jmscontext.createProducer();
            
            Queue queue = (Queue) ic.lookup("dynamicQueues/users-" + ui.API.getMyName());
            
            JMSConsumer consumer = jmscontext.createConsumer(queue);
            
            while(true){
                ObjectMessage ncm = (ObjectMessage) consumer.receive();
                Object aux2 = ncm.getObject();
                
                if(aux2 instanceof NewChatMessage){
                    NewChatMessage aux = (NewChatMessage) aux2;
                    ui.API.chatMessageReceived(aux.getSenderName(), 
                        aux.getText(), aux.getTimestamp());
                } else {
                    ui.API.addToLog(0, ncm.getClass().getSimpleName());
                }
            }
        } catch (Exception e) {}
        
    }

    /**
     * Envía un mensaje de chat al usuario especificado.
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
    void sendChatMessage(String destUser, String text, long timestamp) throws NamingException, JMSException {

        // ACTIVIDAD 5 
        Queue queue = (Queue) ic.lookup("dynamicQueues/users-" + destUser);
        
        NewChatMessage ncm = new NewChatMessage(text, ui.API.getMyName(), timestamp);
        
        ObjectMessage JMSMessage = jmsContext.createObjectMessage(ncm);
        
        producer.send(queue, JMSMessage);
    }

    /**
     * Notifica al usuario especificado que un mensaje en particular ha sido
     * leído.
     * 
     * @param user
     *            El usuario que envió el mensaje que se ha leído
     * @param timestamp
     *            La hora que fue establecida en origen del mensaje que se ha
     *            leído
     * @throws NamingException
     * @throws JMSException
     */
    void sendMessageReaded(String user, long timestamp) throws NamingException, JMSException {


    }

}
