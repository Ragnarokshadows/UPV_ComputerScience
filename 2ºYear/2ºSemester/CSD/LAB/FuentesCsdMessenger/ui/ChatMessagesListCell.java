
package ui;

import java.util.Date;

import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

/**
 * Esta clase representa las celdas de la vista de lista que muestra los mensajes de un chat
 * 
 * @author Agustín Espinosa Minguet (aespinos@upvnet.upv.es)
 *
 */
class ChatMessagesListCell extends ListCell<ChatMessage> {

	/**
	 * La celda es un grid con dos columnas, de las que una está vacía y la otra
	 * contiene un mensaje del chat. La columna izquierda se utiliza para los
	 * mensajes recibidos y la derecha para los mensajes enviados. La columna
	 * vacía se emplea para crear el efecto de encolumnado superpuesto entre
	 * mensajes enviados y recibidos.
	 */
	private GridPane main = new GridPane();
	/**
	 * Panel vertical: el primer elemento contiene el texto y el segundo fecha+
	 * hora y estado. Se asocia a la columna que contiene el mensaje
	 */
	private VBox vbox = new VBox();
	/**
	 * Panel horizontal: primer elemento feha, segundo primer círculo y tercero
	 * el segundo círculo. Se asocia como el segundo elemento del panel vbox
	 */
	private HBox hbox = new HBox();

	/**
	 * Los elementos anteriormente citados: texto, fecha y círculos
	 */
	private Label messageTextLabel = new Label();
	private Label dateLabel = new Label();
	private Circle leftCircle = new Circle(4.0);
	private Circle rightCircle = new Circle(4.0);

	/**
	 * Restricciones de las columnas. Usadas para establecer el ancho relativo
	 * de cada una de ellas.
	 */
	private ColumnConstraints col1 = new ColumnConstraints();
	private ColumnConstraints col2 = new ColumnConstraints();

	/**
	 * Constructor.
	 */
	ChatMessagesListCell() {

		col1.setPercentWidth(25);
		col2.setPercentWidth(75);

		main.setPadding(new Insets(6, 6, 6, 6));
		main.getColumnConstraints().addAll(col1, col2);

		vbox.getChildren().addAll(messageTextLabel, hbox);
		vbox.setPadding(new Insets(6, 6, 6, 6));

		hbox.setAlignment(Pos.CENTER_RIGHT);
		hbox.getChildren().addAll(dateLabel, leftCircle, rightCircle);

		dateLabel.setPadding(new Insets(0, 6, 0, 0));

		messageTextLabel.setWrapText(true);
		messageTextLabel.setMaxWidth(Double.MAX_VALUE);
		messageTextLabel.maxWidthProperty().bind(CsdMessengerClient.getGlobalScene().widthProperty().multiply(0.5));
		messageTextLabel.getStyleClass().add("messageTextLabel");

		leftCircle.setFill(Color.TRANSPARENT);
		rightCircle.setFill(Color.TRANSPARENT);

		main.add(vbox, 1, 0);

		// Anulamos la selección de la celda en la lista
		this.addEventFilter(MouseEvent.MOUSE_PRESSED, new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				event.consume();
			}
		});

	}

	/* 
	 * @see javafx.scene.control.Cell#updateItem(java.lang.Object, boolean)
	 * Este método se ejecuta automáticamente cada vez que hay un cambio en el objeto asociado
	 * a una celda, es decir, un cambio en algún mensaje de chat.
	 * 
	 *  En función del origen del mensaje, se muestra en una u otra columna.
	 *  Según el estado, los círculos se muestran con diferente aspecto 
	 */
	@Override
	protected void updateItem(ChatMessage item, boolean empty) {
		super.updateItem(item, empty);
		if (empty) {
			setText(null);
			setGraphic(null);
		} else {
			setText(null);
			messageTextLabel.setText(item.getText());
			dateLabel.setText((new Date(item.getTimeStamp())).toString());

			if (item.getMine()) {
				col1.setPercentWidth(25);
				col2.setPercentWidth(75);
				vbox.setStyle(
						"-fx-background-color: #B2D8D6; -fx-border-style: solid; -fx-border-color:rgb(90,142,139)");
				main.getChildren().remove(vbox);
				main.add(vbox, 1, 0);

				switch (item.getState()) {
				case CREATED:
					leftCircle.setFill(Color.TRANSPARENT);
					rightCircle.setFill(Color.TRANSPARENT);
					break;
				case SENDED:
					leftCircle.setFill(Color.GRAY);
					rightCircle.setFill(Color.TRANSPARENT);
					break;
				case DELIVERED:
					leftCircle.setFill(Color.GRAY);
					rightCircle.setFill(Color.GRAY);
					break;
				case READED:
					leftCircle.setFill(Color.rgb(139, 255, 255));
					rightCircle.setFill(Color.rgb(139, 255, 255));
					break;
				default:
					break;
				}
			} else {
				col1.setPercentWidth(75);
				col2.setPercentWidth(25);
				vbox.setStyle(
						"-fx-background-color: rgb(245,226,219); -fx-border-style: solid; -fx-border-color:rgb(224,158,153)");
				main.getChildren().remove(vbox);
				main.add(vbox, 0, 0);
				leftCircle.setFill(Color.TRANSPARENT);
				rightCircle.setFill(Color.TRANSPARENT);
			}

			setGraphic(main);
		}
	}

}
