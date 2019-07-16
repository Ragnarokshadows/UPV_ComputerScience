package messageBodies;

import java.io.Serializable;

/**
 * @author Agustín Espinosa Minguet (aespinos@upvnet.upv.es)
 * 
 *         Mensaje que A envía a otro usuario
 */
public class NewChatMessage implements Serializable {

	private static final long serialVersionUID = 1L;

	private String text;
	private String senderName;
	private long timestamp;

	/**
	 * @param text
	 *            El texto del mensaje
	 * @param senderName
	 *            El usuario que envía el mensaje (A)
	 * @param timestamp
	 *            La marca de tiempo en la que A pulsa el botón de envío
	 */
	public NewChatMessage(String text, String senderName, long timestamp) {
		super();
		this.text = text;
		this.senderName = senderName;
		this.timestamp = timestamp;
	}

	public String getText() {
		return text;
	}

	public void setText(String name) {
		this.text = name;
	}

	public String getSenderName() {
		return senderName;
	}

	public void setSenderName(String senderName) {
		this.senderName = senderName;
	}

	public long getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(long timestamp) {
		this.timestamp = timestamp;
	}

}