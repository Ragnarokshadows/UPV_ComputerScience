// This file must be implemented when completing activity 2
//

import java.rmi.*;
import java.rmi.registry.*;
import java.rmi.server.*;
import java.util.*;

//
// ChatRobot implementation
//
public class ChatRobot implements MessageListener
{
   private ChatConfiguration conf;
   private IChatServer srv = null;   // We just connect to one single server
   private IChatUser myUser = null;  // Our own user    
   private ChatUI ui = null;         // User interface for fancy UI-driven chatting.
   private boolean bConnected = false;
   private boolean prueba=true;
   
   public ChatRobot (ChatConfiguration conf) {
      this.conf = conf;
   }
   
   public void doConnect (String serverName, String nick) throws Exception {
        try {
            Registry reg = LocateRegistry.getRegistry(conf.getNameServiceHost(), 
                            conf.getNameServicePort());

            srv = (IChatServer) reg.lookup(serverName);
        } catch (java.rmi.ConnectException e) {
            throw new Exception ("rmiregistry not found at '" + 
            conf.getNameServiceHost() + ":" + conf.getNameServicePort() + "'");
        } catch (java.rmi.NotBoundException e) {
            throw new Exception ("Server '" + serverName + "' not found.");
        }   

        myUser = new ChatUser(nick, this);

        if(!srv.connectUser(myUser)) throw new Exception ("The nickname is already used");
 
        IChatChannel [] channels = srv.listChannels();  // <---- 2.c Substitute "null" with the right value 

        if (channels == null || channels.length == 0) throw new Exception ("Server has no channels");

        // Connected to server. Things went fine :)
        bConnected = true;
   }
     
   public void doJoinChannel (String channelName) throws Exception {
      
      IChatChannel ch = srv.getChannel (channelName);
      if (ch == null) {throw new Exception ("Channel not found");}
      
      ch.join(myUser);
   }
   
   public void messageArrived (IChatMessage msg) {
      try {
         IChatUser src = msg.getSender();
         Remote dst = msg.getDestination();
         String str = msg.getText();
    
         IChatChannel c_dst = (IChatChannel) dst;
         if (src == null) { // Control message from the channel itself
             String nick = null;
             if (str.startsWith (ChatChannel.JOIN)) {
                  nick = str.substring (ChatChannel.JOIN.length() + 1);
                  IChatMessage c_msg = new ChatMessage(myUser, c_dst, "Hello " + nick);
                  c_dst.sendMessage(c_msg);
             }
         }
      } catch (Exception e) {
      }
   }
    
   public static void main (String args [] )
   {
      ChatRobot cr = new ChatRobot(ChatConfiguration.parse (args));
      try{
          cr.doConnect("TestServer", "ChatRoBOT");
          cr.doJoinChannel("#Spain");
      } catch (Exception e) {}
   }
}
