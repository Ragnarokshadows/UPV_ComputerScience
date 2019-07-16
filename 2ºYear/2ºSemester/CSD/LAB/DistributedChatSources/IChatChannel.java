// CSD 2013, Pablo Galdámez
import java.rmi.*;

//
// Channels have a set of connected users. Sending a message to a channel means to broadcast it to 
// the channel users.
//
public interface IChatChannel extends Remote {
   public boolean join (IChatUser usr) throws RemoteException;
   public boolean leave (IChatUser usr) throws RemoteException;
   public void sendMessage (IChatMessage msg) throws RemoteException;
   public IChatUser [] listUsers () throws RemoteException;
   public String getName () throws RemoteException;
}
