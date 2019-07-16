package messageBodies;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Mensaje enviado por CsdMessengerServer al usuario que acaba de conectarse (A)
 * 
 * CsdMessenger notifica a A la lista de usuarios conocidos
 * 
 * @author Agustín Espinosa Minguet (aespinos@upvnet.upv.es)
 *
 */
public class UsersList implements Serializable {

	private static final long serialVersionUID = 1L;

	private ArrayList<String> users = new ArrayList<String>();

	/**
	 * @param users
	 *            La lista de usuarios conocidos
	 */
	public UsersList(ArrayList<String> users) {
		this.setUsers(users);

	}

	public ArrayList<String> getUsers() {
		return users;
	}

	public void setUsers(ArrayList<String> users) {
		this.users = users;
	}

}