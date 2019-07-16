// CSD 2013, Pablo Galdámez
import java.rmi.*;

//
// Users are objects. They receive messages when they are invoked their "sendMessage()" 
//
public interface IChatUser extends Remote {
   public String getNick() throws RemoteException;
   public void sendMessage (IChatMessage msg) throws RemoteException;
}
