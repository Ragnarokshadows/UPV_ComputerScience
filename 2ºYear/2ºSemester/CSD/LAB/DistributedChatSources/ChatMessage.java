// CSD 2013, Pablo Galdámez
import java.rmi.*;
import java.rmi.server.*;

//
// Simple ChatMessage implementation
//
public class ChatMessage 
   extends UnicastRemoteObject 
   implements IChatMessage 
{
   private String str = "";
   private IChatUser src = null; // sender
   private Remote dst = null; // Actual class depends on wheter the message is private or public
   
   public ChatMessage (IChatUser src, IChatChannel dst, String str) throws RemoteException
   {
      super (ChatConfiguration.the().getMyPort());
      this.src = src; this.dst = dst; this.str = str; 
   }

   public ChatMessage (IChatUser src, IChatUser dst, String str) throws RemoteException {
      super (ChatConfiguration.the().getMyPort());
      this.src = src; this.dst = dst; this.str = str; 
   }
   
   //
   // ISA IChatMessage
   //
   public String getText () throws RemoteException {return str;}
   public IChatUser getSender () throws RemoteException {return src;}
   public Remote getDestination () throws RemoteException {return dst;}
   public boolean isPrivate() {return dst instanceof IChatUser;}
}
