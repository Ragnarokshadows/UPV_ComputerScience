package ui;

import java.util.HashMap;
import java.util.Map;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * Clase con objeto único que representa el modelo subyacente de la vista.
 * Cualquier cambio en el modelo se repercute automáticamente en la vista,
 * gracias a las asociaciones realizadas en el controlador (MainController)
 * 
 * @author Agustín Espinosa Minguet (aespinos@upvnet.upv.es)
 *
 */
public class Model {

	private static Model theModel = new Model();

	private Model() {
	};

	static Model getModel() {
		return theModel;

	}

	// La lista de chats. Se registra el nombre del contacto
	
	ObservableList<String> chats = FXCollections.observableArrayList();

	static ObservableList<String> getChats() {
		return theModel.chats;
	}

	// Los mensajes de todos los chats.

	private Map<String, ObservableList<ChatMessage>> chatMessages = new HashMap<String, ObservableList<ChatMessage>>();

	Map<String, ObservableList<ChatMessage>> getChatMessages() {
		return theModel.chatMessages;
	}

	void setChatMessages(Map<String, ObservableList<ChatMessage>> chatMessages) {
		this.chatMessages = chatMessages;
	}

	// El contenido del área de edición de nuevos mensajes, uno por cada chat,

	private Map<String, String> newMessageMap = new HashMap<String, String>();

	static Map<String, String> getNewMessageMap() {
		return theModel.newMessageMap;
	}

	private StringProperty messageFieldValue = new SimpleStringProperty();

	static StringProperty getMessageFieldValue() {
		return theModel.messageFieldValue;
	}

	// El usuario actual
	private StringProperty currentChatUser = new SimpleStringProperty();

	static String getCurrentChatUser() {
		return theModel.currentChatUser.get();
	}

	static void setCurrentChatUser(String user) {
		theModel.currentChatUser.set(user);
	}

	// El nombre del usuario que ha iniciado la aplicación cliente
	
	static String getMe() {
		return theModel.me;
	}

	static void setMe(String me) {
		theModel.me = me;
	}

	private String me = new String();

}
