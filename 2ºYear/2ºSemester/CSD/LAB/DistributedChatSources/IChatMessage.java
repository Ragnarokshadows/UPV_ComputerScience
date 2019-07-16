// CSD 2013, Pablo Galdámez
import java.rmi.*;

//
// For our Chat, messages are objects. We could simplify the chat so that messages are simple 
// string, but having them as objects allows us to explore interesting invocation patterns.
//
public interface IChatMessage extends Remote{
   public String getText () throws RemoteException;    // The message itself
   public IChatUser getSender () throws RemoteException; // allways the user who sends
   public Remote getDestination () throws RemoteException; // actual class depends on private/pub
   public boolean isPrivate() throws RemoteException; // actual class depends on private/pub
}
