package ui;

import java.util.ArrayList;

import javafx.application.Platform;
import javafx.collections.FXCollections;

import ui.ChatMessage.State;

/**
 * 
 * 	Esta es la API del paquete "ui" y define los únicos métodos que pueden
 *	ser utilizados desde el paquete "comm"
 *	Son todos métodos de clase (static), y por tanto no es una clase pensada
 *	para instanciar objetos
 *
 *  @author Agustín Espinosa Minguet (aespinos@upvnet.upv.es)
 *
 */
public class API {

	
	public static String getMyName() {
		return Model.getMe();
	}	
	
	/**
	 * @return El usuario que está seleccionado en el panel izquierdo de la
	 *         interfaz
	 */
	public static String getCurrentChatUser() {
		return Model.getCurrentChatUser();
	}

	/**
	 * Notificamos a la UI que ha llegado un nuevo mensaje de chat
	 * 
	 * @param user
	 *            El usuario que ha enviado el mensaje
	 * @param text
	 *            El texto del mensaje
	 * @param timestamp
	 *            La marca de tiempo según el reloj del usuario que ha enviado
	 *            el mensaje
	 */
	public static void chatMessageReceived(String user, String text, long timestamp) {

		// El uso de runLater que hacemos aquí y en los métodos siguientes se
		// debe a que en JavaFX solo el hilo de la UI debe manipular elementos
		// gráficos u objetos que estén siendo observados por la UI. Con
		// runLater se encola un fragmento de código que la UI ejecutará en el
		// momento que tenga disponibilidad
		Platform.runLater(new Runnable() {
			public void run() {

				if (!Model.getChats().contains(user)) {
					newUser(user);
				}

				Model.getModel().getChatMessages().get(user)
						.add(new ChatMessage(text, State.CREATED, timestamp, false));
			}
		});
	}

	/**
	 * Notificamos a la UI la lista de usuarios conocidos para que los muestre
	 * en el panel izquierdo
	 * 
	 * @param users
	 *            La lista de usuarios conocidos
	 */
	public static void updateUserList(ArrayList<String> users) {

		Platform.runLater(new Runnable() {
			public void run() {

				for (String user : users) {
					if (!Model.getChats().contains(user) && !user.equals(Model.getMe())) {
						newUser(user);
					}
				}
			}
		});

	}

	/**
	 * Notificamos a la UI que aparece un nuevo usuario conocido para que lo
	 * muestre en el panel izquierdo
	 * 
	 * @param user
	 *            El nuevo usuario
	 */
	public static void addToUserList(String user) {

		Platform.runLater(new Runnable() {
			public void run() {

				if (!Model.getChats().contains(user)) {
					newUser(user);
				}

			}
		});

	}



	/**
	 * Notificamos a la UI que un mensaje ha sido recibido por el usuario
	 * destinatario, para que muestre los dos círculos indicadores
	 * 
	 * @param userName
	 *            El usuario destinatario del mensaje
	 * @param timestamp
	 *            La marca de tiempo del mensaje que fue enviado, utilizada para
	 *            identificar en qué mensaje deben activarse los indicadores
	 */
	public static void changeMessageStatusToDelivered(String userName, long timestamp) {
		Platform.runLater(new Runnable() {
			public void run() {
				Log.getLog().add(0, "Cambio estado a delivered. Usuario = " + userName + " timestamp = " + timestamp);

				int i = 0;
				for (ChatMessage m : Model.getModel().getChatMessages().get(userName)) {
					if (m.getMine() == true && m.getTimeStamp() == timestamp) {
						m.setState(State.DELIVERED);
						Model.getModel().getChatMessages().get(userName).set(i, m);
						break;
					}
					i++;
				}

			}

		});
	}

	/**
	 * Notificamos a la UI que un mensaje ha sido leido por el usuario
	 * destinatario, para que muestre en color resaltado los dos círculos
	 * indicadores
	 * 
	 * @param userName
	 *            El usuario destinatario del mensaje
	 * @param timestamp
	 *            La marca de tiempo del mensaje que fue enviado, utilizada para
	 *            identificar en qué mensaje deben activarse los indicadores
	 */
	public static void changeMessageStatusToReaded(String userName, long timestamp) {
		Platform.runLater(new Runnable() {
			public void run() {
				Log.getLog().add(0, "Cambio estado a readed. Usuario = " + userName + " timestamp = " + timestamp);

				int i = 0;
				for (ChatMessage m : Model.getModel().getChatMessages().get(userName)) {
					if (m.getMine() == true && m.getTimeStamp() == timestamp) {
						m.setState(State.READED);
						Model.getModel().getChatMessages().get(userName).set(i, m);
						break;
					}
					i++;
				}

			}

		});
	}

	/**
	 * Solicitamos a la UI que muestre una información en el panel de Log
	 * 
	 * @param level
	 *            La importancia de la información. A menor número más
	 *            importante.
	 * @param text
	 *            El texto de la información
	 */
	public static void addToLog(int level, String text) {
		Log.getLog().add(level, text);
	}

	// Método privado que notifica a la UI que se ha detectado un usuario nuevo,
	// usado por otros métodos codificados arriba
	private static void newUser(String user) {
		Model.getChats().add(user);
		Model.getNewMessageMap().put(user, new String());
		Model.getModel().getChatMessages().put(user, FXCollections.observableArrayList());
	}
}
