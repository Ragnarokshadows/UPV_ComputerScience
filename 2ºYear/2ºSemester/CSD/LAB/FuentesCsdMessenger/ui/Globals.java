package ui;

/**
 * Métodos de clase que devuelven valores usados globalmente en la UI
 * 
 * @author Agustín Espinosa Minguet (aespinos@upvnet.upv.es)
 *
 */
class Globals {

	/**
	 * @return El directorio donde se guardan los chats de todos los usuarios. Un fichero por usuario.
	 */
	static String getDir() {
		return System.getProperty("user.home") + "/csdmessenger/";
	}
	
	/**
	 * @return El fichero donde se guardan los chats de un usuario concreto
	 */
	static String getDatafile() {
		return getDir() + Model.getMe();
	}
	
}
