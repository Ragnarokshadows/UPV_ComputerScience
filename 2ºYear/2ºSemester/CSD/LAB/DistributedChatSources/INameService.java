// CSD 2013, Pablo Galdámez
// This file must be studied when completing activity 3.
//
import java.rmi.*;
import java.rmi.registry.*;

//
// Name Service interface similar to that of RMI (rmiregistry)
//
public interface INameService extends Remote {
   // Tries to bind an object to a name. If name was already present, 
   // throws an "AlreadyBoundException"
   public void bind (String name, Remote obj) throws RemoteException,
						     AlreadyBoundException;

   // Similar to sequentially invoke unbind() followed by bind.
   public void rebind (String name, Remote obj) throws RemoteException;

   // If not found, this method does nothing and does not throw any exception
   public void unbind (String name) throws RemoteException;

   // Returns the bound object or null of not found.
   public Remote resolve (String name) throws RemoteException;
}