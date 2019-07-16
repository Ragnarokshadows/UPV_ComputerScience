package messageBodies;

import java.io.Serializable;

/**
 * @author Agustín Espinosa Minguet (aespinos@upvnet.upv.es)
 * 
 *         Tras el envío de un mensaje de A a B, B envía este mensaje para
 *         confirmar la llegada
 */
public class AckChatMessage implements Serializable {

	private static final long serialVersionUID = 1L;

	private String senderName;
	private long timestamp;

	/**
	 * @param senderName
	 *            El usuario (B) que envía este mensaje
	 * @param timestamp
	 *            La marca de tiempo del mensaje enviado por A a B que B quiere
	 *            confirmar
	 */
	public AckChatMessage(String senderName, long timestamp) {
		super();
		this.senderName = senderName;
		this.timestamp = timestamp;
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