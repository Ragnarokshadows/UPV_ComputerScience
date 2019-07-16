
package ui;

import java.io.Serializable;

/**
 * Esta clase representa los mensajes de chat que se muestran en la UI,
 * y que son por tanto parte del modelo subyacente.
 * 
 * Ten en cuenta que no son los mensajes que se intercambian utilizando JMS.
 * 
 * @author Agustín Espinosa Minguet (aespinos@upvnet.upv.es)
 *
 */
class ChatMessage implements Serializable {

	private static final long serialVersionUID = 1L;

	static enum State {
		CREATED, SENDED, DELIVERED, READED
	}

	/**
	 * El texto del mensaje.
	 */
	private String text = "";
		
	/**
	 * Originado por el usuario que ha lanzado la aplicación cliente (se muestra a la derecha) o recibido
	 * desde otro usuario (se muestra a la izquierda)
	 */
	private Boolean mine = true;
	
	/**
	 * El estado del mensaje.
	 * Según dicho estado la visualización de los dos círculos que se muestran en cada mensaje cambia.
	 */
	private State state = State.CREATED;

	/**
	 * La marca de tiempo del mensaje.
	 * Corresponde con la hora local en el momento en que el usuario pulsa el botón de envío o Entrar
	 */
	private long timeStamp = 0L;

	// El resto de la implementación es auto descriptiva
	
	ChatMessage() {
		super();
	}


	ChatMessage(String text, State state, long t, Boolean mine) {
		super();
		this.text = text;
		this.state = state;
		this.timeStamp = t;
		this.mine = mine;
	}

	String getText() {
		return text;
	}

	void setText(String text) {
		this.text = text;
	}

	Boolean getMine() {
		return mine;
	}

	void setMine(Boolean mine) {
		this.mine = mine;
	}

	long getTimeStamp() {
		return timeStamp;
	}

	void setTimeStamp(long t) {
		timeStamp = t;
	}

	State getState() {
		return state;
	}

	void setState(State state) {
		this.state = state;
	}

}
