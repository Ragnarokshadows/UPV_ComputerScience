package messageBodies;

import java.io.Serializable;

/**
 * Mensaje enviado por el usuario A al iniciarse la aplicación cliente
 * 
 * Notifica a CsdMessengerServer que el usuario A se ha conectado
 * 
 * @author Agustín Espinosa Minguet (aespinos@upvnet.upv.es)
 *
 */
public class UserStart implements Serializable {

	private static final long serialVersionUID = 1L;
	private String name;

	/**
	 * @param name
	 *            El nombre del usuario que ha iniciado la aplicación cliente
	 */
	public UserStart(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}