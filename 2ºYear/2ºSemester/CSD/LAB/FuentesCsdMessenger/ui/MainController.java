package ui;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.HashMap;

import javax.jms.JMSException;
import javax.naming.NamingException;

import comm.Communication;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.input.KeyCode;
import javafx.util.Callback;
import ui.ChatMessage.State;

/**
 * Control de la vista definida en Main.fxml
 * 
 * @author Agustín Espinosa Minguet (aespinos@upvnet.upv.es)
 *
 */
public class MainController {

	// Asociación de elementos definidos en Main.fxml con objetos Java

	@FXML
	private TextArea messageField;

	@FXML
	private Button btnSendMessage;

	@FXML
	private ListView<String> chatsList;

	@FXML
	private ListView<ChatMessage> messagesList;

	@FXML
	private TextArea logView;

	/**
	 * Este método se llama automáticamente cuando el toolkit de JavaFX ya ha
	 * sido inicializado, es decir, cuando termina el método Start de
	 * CsdMessengerClient.
	 * 
	 * @throws ClassNotFoundException
	 */
	@SuppressWarnings("unchecked")
	@FXML
	private void initialize() throws ClassNotFoundException {

		try {

			// Cargamos los mensajes guardados de conversaciones previas

			String fileName = Globals.getDatafile();
			FileInputStream fis;
			fis = new FileInputStream(fileName);
			ObjectInputStream ois = new ObjectInputStream(fis);
			Model.getModel().chats.setAll((ArrayList<String>) ois.readObject());

			HashMap<String, ArrayList<ChatMessage>> messagesSaved = (HashMap<String, ArrayList<ChatMessage>>) ois
					.readObject();

			for (String u : messagesSaved.keySet()) {

				ObservableList<ChatMessage> temp = FXCollections.observableArrayList();
				for (ChatMessage m : messagesSaved.get(u)) {
					temp.add(m);
				}
				Model.getModel().getChatMessages().put(u, temp);
			}
			ois.close();
		} catch (IOException e1) {
			// El fichero de datos no existe. Se creará cuando termine el
			// programa.
		}

		// Asociamos a la vista de la lista de mensajes las celdas a medida
		// (ChatMessagesListCell) para visualizar los mensajes de chat

		messagesList.setCellFactory(new Callback<ListView<ChatMessage>, ListCell<ChatMessage>>() {
			@Override
			public ListCell<ChatMessage> call(ListView<ChatMessage> list) {
				return new ChatMessagesListCell();
			}
		});

		// Preparamos la lista de chats que se muestra a la izquierda con los
		// usuarios conocidos
		// por el sistema.
		chatsList.setId("chatsList");
		chatsList.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
			/*
			 * (non-Javadoc)
			 * 
			 * @see
			 * javafx.beans.value.ChangeListener#changed(javafx.beans.value.
			 * ObservableValue, java.lang.Object, java.lang.Object)
			 */
			public void changed(ObservableValue<? extends String> ov, String old_val, String new_val) {

				// Al seleccionar uno de los usuarios de la lista izquierda
				// realizamos los cambios necesarios

				// Cambiamos la asociación del area de escritura de mensajes al
				// nuevo usuario

				if (old_val != null) {
					Model.getNewMessageMap().put(old_val, Model.getMessageFieldValue().getValue());
				}
				if (new_val != null) {
					Model.getMessageFieldValue().setValue(Model.getNewMessageMap().get(new_val));
				}

				// Anotamos cuál es el usuario actualmente seleccionado

				Model.setCurrentChatUser(new_val);

				// Notificamos al emisor los mensajes que estaban pendientes de
				// leer, para que este actualiza su estado en su UI

				for (ChatMessage m : Model.getModel().getChatMessages().get(new_val)) {
					if (m.getMine() == false && m.getState() != State.READED) {
						m.setState(State.READED);
						try {
							comm.API.sendMessageReaded(new_val, m.getTimeStamp());
						} catch (NamingException | JMSException e) {
							e.printStackTrace();
						}
					}
				}

				// Cambiamos la lista subyacente de la vista de mensajes del
				// panel derecho

				messagesList.setItems(Model.getModel().getChatMessages().get(new_val));

				// Forzamos a que se visualice el último mensaje moviendo el
				// scroll
				messagesList.scrollTo(messagesList.getItems().size() - 1);

				// Activamos el botón y el área de texto por si no lo estaban,
				// tal como sucede inicialmente
				btnSendMessage.setDisable(false);
				messageField.setEditable(true);

			}
		});

		// Asociamos el área de texto donde se escriben mensajes con el string
		// subyacente
		messageField.textProperty().bindBidirectional(Model.getMessageFieldValue());

		// Inicialmente no podemos editar mensajes hasta que no se seleccione un
		// usuario en el panel izquierdo

		messageField.setEditable(false);

		// Asociamos la lista de chats del panel derecho con la lista de
		// usuarios conocidos.
		chatsList.setItems(Model.getChats());

		// Preparamos el botón de envío para que envíe el mensaje escrito al
		// pulsarlo

		btnSendMessage.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {

				if (Model.getMessageFieldValue().getValue() != null
						&& Model.getMessageFieldValue().getValue().length() != 0
						&& Model.getCurrentChatUser() != null) {

					ChatMessage chatMessage = new ChatMessage(Model.getMessageFieldValue().getValue(), State.SENDED,
							System.currentTimeMillis(), true);
					Model.getModel().getChatMessages().get(Model.getCurrentChatUser()).add(chatMessage);
					try {
						comm.API.sendChatMessage(Model.getCurrentChatUser(), Model.getMessageFieldValue().getValue(),
								chatMessage.getTimeStamp());
					} catch (NamingException | JMSException e) {
						e.printStackTrace();
					}

					Model.getMessageFieldValue().setValue("");

					messagesList.scrollTo(messagesList.getItems().size() - 1);
				}
			}
		});

		// El botón de envío está inicialmente desactivado hasta que se
		// seleccione un usuario de la lista de chats

		btnSendMessage.setDisable(true);

		// Hacemos que pulsar ENTER sea equivalente a pulsar el botón de envío

		messageField.setOnKeyPressed(event -> {
			if (event.getCode() == KeyCode.ENTER) {
				btnSendMessage.fire();
				messageField.setText("");
				event.consume();
			}
		});

		// Asociamos la vista del log con los contenidos del log

		logView.textProperty().bindBidirectional(Log.getLog().logContents);

		Log.getLog().add(0, "Inicialización completa");

		// Con esto la UI está lista para funcionar. Llamamos al módulo de
		// comunicación para que se inicialice la parte de mensajería.
		comm.API.initialize();

	}

}
