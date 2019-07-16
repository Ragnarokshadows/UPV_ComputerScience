package ui;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Properties;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

/**
 * Programa principal del cliente CSD Messenger
 * 
 * @author Agustín Espinosa Minguet (aespinos@upvnet.upv.es)
 *
 */
public class CsdMessengerClient extends Application {

	private static Scene globalScene;

	static Scene getGlobalScene() {
		return globalScene;
	};

	@Override
	public void start(Stage stage) throws Exception {

		// Cargamos la vista definida en el fichero main.fxml

		GridPane root = FXMLLoader.load(getClass().getResource("/main.fxml"));

		// Creamos la escena donde se incluirán los elementos de la interfaz

		Scene scene = new Scene(root, 800, 600);

		// Asociamos la hoja de estilo donde hemos definido el aspecto visual de
		// los elementos
		scene.getStylesheets().add("/stylesheet.css");

		globalScene = scene;

		// Hacemos que el nombre del usuario aparezca en el título de la ventana
		stage.setTitle(Model.getMe());

		// Colocamos la escena en el "escenario"

		stage.setScene(scene);

		// y acción!

		stage.show();

		// Capturamos la acción de cierre de la ventana para finalizar la
		// aplicación

		stage.setOnCloseRequest(e -> {

			// Terminamos la interfaz de usuario
			Platform.exit();

			// Guardamos los chats con sus mensajes para poder recuperarlos la
			// siguiente vez que se inicie la aplicación
			String dirName = Globals.getDir();
			String fileName = Globals.getDatafile();
			try {
				File dir = new File(dirName);
				dir.mkdir();
				FileOutputStream fos;
				fos = new FileOutputStream(fileName);
				ObjectOutputStream oos;
				oos = new ObjectOutputStream(fos);
				oos.writeObject(new ArrayList<String>(Model.getModel().chats));

				HashMap<String, ArrayList<ChatMessage>> messagesToSave = new HashMap<String, ArrayList<ChatMessage>>();

				for (String u : Model.getModel().getChatMessages().keySet()) {

					ArrayList<ChatMessage> temp = new ArrayList<ChatMessage>();
					for (ChatMessage m : Model.getModel().getChatMessages().get(u)) {
						temp.add(m);
					}
					messagesToSave.put(u, temp);
				}

				oos.writeObject(messagesToSave);

				oos.close();
			} catch (IOException e1) {
				e1.printStackTrace();
			}

			// Y finalizamos completamente la aplicación
			System.exit(0);
		});

	};

	public static void main(final String[] args) throws Exception {

		
		if (args.length != 1) {
		 System.out.println("Se requiere un argumento que sea el nombre del usuario");
		 System.exit(1);
		}
		
		// Anotamos el nombre del usuario que se ha conectado
		
		Model.setMe(args[0]);

		// Lanzamos el toolkit JavaFX que tras iniciarse llamará al método start
		// codificado arriba

		javafx.application.Application.launch(CsdMessengerClient.class);

	}

}
