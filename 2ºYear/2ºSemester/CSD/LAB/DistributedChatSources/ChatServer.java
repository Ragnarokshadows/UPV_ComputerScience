// CSD 2013, Pablo Galdámez
//

import java.rmi.*;
import java.rmi.registry.*;
import java.rmi.server.*;
import java.util.*;

//
// This file contains both the ChatServer object and the main ChatServer process.
// The ChatServer process creates a ChatServer object instance, binds it to the name server and
// it creates some default channels.
// After that initialization, it just waits incoming invocations.
//
public class ChatServer extends UnicastRemoteObject
   implements IChatServer 
{
   private String name;
   private ChatConfiguration conf;
   private Hashtable <String, IChatUser> users = new Hashtable<String, IChatUser> ();
   private Hashtable <String, IChatChannel> channels = new Hashtable<String, IChatChannel> ();
   
   public ChatServer (ChatConfiguration conf) throws RemoteException {
      super (ChatConfiguration.the().getMyPort());
      this.conf = conf;
   }

   //
   // ISA IChatServer
   //
   public synchronized IChatChannel [] listChannels () throws RemoteException
   {
      purgeChannels ();      
      return channels.values().toArray(new IChatChannel[channels.size()]);
   }

   //
   // ISA IChatServer
   // Returns a named channel. If not found or stale, returns null. If stale, removes it.
   //
   public synchronized IChatChannel getChannel (String name) throws RemoteException
   {
      IChatChannel ch = null;
      String key = name.trim().toLowerCase();
      try {
     ch = channels.get (key);
      } catch (Exception e) {}

      // if Channel found, check if channel is stale or alive
      if (ch != null) {
     // Check if stale
     try {
        ch.getName();
     } catch (Exception e) {
        channels.remove (key);
        ch = null; // Do not return stale channels
     }
      }
      return ch;
   }

   //
   // ISA IChatServer
   //
   public synchronized IChatChannel createChannel (String name) throws RemoteException
   {
      if (getChannel (name) != null) return null; // Channel with same name exists
      IChatChannel ch = new ChatChannel (name);
      channels.put (name.trim().toLowerCase(), ch);
      System.out.println ("Channel '" + name + "' created.");
      return ch; // return self on success
   }

   //
   // ISA IChatServer
   // Returns a named user. If not found or stale, returns null. If stale, removes it.
   //
   public synchronized IChatUser getUser (String nick) throws RemoteException
   {
      String key = nick.trim().toLowerCase();
      IChatUser usr = null;
      try { usr = users.get (key);} catch (Exception e) {}
      if (usr == null) return null; // Return null if not found
      
      // if User found, check if user is stale or alive
      try {
          usr.getNick();
      } catch (Exception e) {
         users.remove (key);
         usr = null; // Do not return stale users
      }
      return usr;
   }

   //
   // ISA IChatServer
   //
   public synchronized boolean connectUser (IChatUser usr) throws RemoteException
   {
      String nick = usr.getNick();
      String keyNick = nick.trim().toLowerCase();
      if (getUser(keyNick) != null) return false; // User with same nick already connected
      users.put (keyNick, usr);      
      System.out.println ("User '" + nick + "' connected.");
      return true; // success
   }

   //
   // ISA IChatServer
   //
   public synchronized boolean disconnectUser (IChatUser usr) throws RemoteException
   {
      if (usr == null) return false; // Avoid rt error
      String nick = null;
      try {
          nick = usr.getNick();
      } catch (Exception e) {return false;} // Stale, so nothing to do
      System.out.println ("User '" + nick + "' disconnected.");

      IChatUser found = getUser (nick);
      if (found == null) return false;
      if (!found.equals (usr)) return false;

      // User found and it was the same as the argument.... so remove it from server and channels
      users.remove (nick);
      for (IChatChannel ch: channels.values()) {
          ch.leave (usr);
      }
      return true;
   }
   
   //
   // Utility function to remove stale users
   //
   private void purgeUsers (){
      String [] keys = users.keySet().toArray(new String [users.size()]);
      for (int i=0; i<keys.length; i++) {
     try {
        users.get (keys[i]).getNick(); // Remote invo to check if stale or alive
     } catch (Exception e){
        users.remove (keys[i]); // Remove stale users
     }
      }
   }

   //
   // Utility function to remove stale channels
   //
   private void purgeChannels (){
      String [] keys = channels.keySet().toArray(new String [channels.size()]);
      for (int i=0; i<keys.length; i++) {
     try {
        channels.get (keys[i]).getName(); // Remote invo to check if stale or alive
     } catch (Exception e){
        channels.remove (keys[i]); // Remove stale channels
     }
      }
   }

   //
   // work() is invoked by main() to startup a basic server
   // It creates some channels, and registers the server into the name service
   //
   private void work () {
      // Register at name service
      try {   
     Registry reg = LocateRegistry.getRegistry (conf.getNameServiceHost(), 
                            conf.getNameServicePort());
                    
     reg.rebind (conf.getServerName(), this);
      } catch (java.rmi.ConnectException e) {
     System.out.println ("rmiregistry not found at '" + 
                 conf.getNameServiceHost() + ":" + conf.getNameServicePort() + "'");
     System.exit(-1);
      } catch (Exception e) {
     System.out.println("Error connecting to rmiregistry: " + e); 
     System.exit(-1);
      }

      // Create default channels
      try {
     createChannel ("#Spain");
     createChannel ("#Linux");
     createChannel ("#Friends");
      } catch (Exception e) {}
      
      // Reference sent to the name service, prevents us from exiting.
      System.out.println("OK ==> Server '" + conf.getServerName() + "' Running ....");
   }

   public static void main (String args [] ) throws Exception
   {
      ChatServer cs = new ChatServer (ChatConfiguration.parse (args));
      cs.work ();
   }
}
