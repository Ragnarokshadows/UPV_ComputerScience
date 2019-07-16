package messageBodies;

import java.io.Serializable;

/**
 * @author Agustín Espinosa Minguet (aespinos@upvnet.upv.es)
 * 
 *         B notifica a A que ha leído su mensaje
 */
public class ReadedChatMessage implements Serializable {

	private static final long serialVersionUID = 1L;

	private String senderName;
	private long timestamp;

	/**
	 * @param senderName
	 *            El usuario que envía este mensaje (B)
	 * @param timestamp
	 *            La marca de tiempo del mensaje enviado por A que B quiere
	 *            notificarle que ha leído
	 */
	public ReadedChatMessage(String senderName, long timestamp) {
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