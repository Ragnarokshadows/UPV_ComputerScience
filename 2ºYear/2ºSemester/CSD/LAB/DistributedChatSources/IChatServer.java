// CSD 2013, Pablo Galdámez
import java.rmi.*;

//
// ChatServer object interface. ChatServers have a set of channels and a set of connected users.
//
public interface IChatServer extends Remote {
   public IChatChannel [] listChannels () throws RemoteException;
   public IChatChannel getChannel (String name) throws RemoteException;
   public IChatChannel createChannel (String name) throws RemoteException;

   public IChatUser getUser (String nick) throws RemoteException;
   public boolean connectUser (IChatUser usr) throws RemoteException;
   public boolean disconnectUser (IChatUser usr) throws RemoteException;
}
