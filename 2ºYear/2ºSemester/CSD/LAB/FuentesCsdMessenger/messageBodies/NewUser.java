package messageBodies;

import java.io.Serializable;

/**
 * Mensaje enviado por CsdMessengerServer a los usuarios conocidos informando
 * que un nuevo usuario se ha conectado por primera vez
 * 
 * @author Agustín Espinosa Minguet (aespinos@upvnet.upv.es)
 *
 */
public class NewUser implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String name;

	/**
	 * @param name El nombre del nuevo usuario que acaba de conectarse por primera vez
	 */
	public NewUser(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}