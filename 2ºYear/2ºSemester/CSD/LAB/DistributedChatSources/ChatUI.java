 // CSD 2013, Pablo Galdámez
import java.awt.*;
import java.awt.event.*;

//
// Client graphical user interface
// This UI implements the "V" in the MVC design pattern. It does not know a word about RMI or 
// distribution.
//
// Students need not modify nor fully understand this file.
//

public class ChatUI 
{
   private final String NOCHANNEL = "--                    ";

   private ChatClientCSD cc;  // Main client. We use it as "C" from rhe "MCV"
   private Frame mainFrame;

   //
   // Many fields are used to store the Chat State as Strings
   //
   private TextField txtMessage = new TextField (50);  // Message to send
   private TextField txtServer = new TextField (20);   // Server to connect to
   private TextField txtNick = new TextField (10);     // Our own nick
   private Button btnConnect = new Button ("Connect"); // Button to trigger connection
   private List lstChannel =new  List(20,false);       // Main area where chat is received
   private List lstChannelList =new  List(20,false);   // Server list of channels
   private List lstUserList =new  List(20, false);     // Channel list of users
   private Label lblCurrentChannel = new Label (NOCHANNEL);  // Current channel
   private Label lblDestination = new Label (NOCHANNEL);  // User or Channel, where our messages go
   private boolean bDestinationIsChannel = true;       
   private boolean bConnected = false;
   
   public ChatUI (ChatClientCSD cc) {
      this.cc = cc;
      createUI ();
   }

   public void show () {mainFrame.setVisible(true);}


   //
   // UI is just a AWT Panel with some simple components.
   //
   private void createUI () {
      this.mainFrame = new Frame("Chat Client");
      mainFrame.setLayout (new BorderLayout());
      Panel pnlBorder = new Panel (new CardLayout (10,10));
      Panel pnlMain = new Panel (new BorderLayout(0,5));

      Panel pnlTop = new Panel(new FlowLayout(FlowLayout.LEFT, 20, 0)); 
      Panel pnlServer = new Panel(new BorderLayout()); 
      pnlServer.add(new Label("Server: "), BorderLayout.WEST); 
      txtServer.setText (cc.getDefaultServerName());
      pnlServer.add(txtServer, BorderLayout.CENTER);
      Panel pnlNick = new Panel(new BorderLayout()); 
      pnlNick.add(new Label("My nick: "), BorderLayout.WEST); 
      pnlNick.add(txtNick, BorderLayout.CENTER); 
      pnlTop.add(pnlServer);
      pnlTop.add(pnlNick);
      pnlTop.add(btnConnect);
            

      Panel pnlLists = new Panel (new BorderLayout(10, 0));
      Panel pnlChannelList = new Panel (new BorderLayout());
      pnlChannelList.add (new Label ("Channels"), BorderLayout.NORTH);
      pnlChannelList.add (lstChannelList, BorderLayout.SOUTH);

      Panel pnlChannel = new Panel (new BorderLayout());
      Panel pnlChannelH = new Panel (new BorderLayout());
      pnlChannelH.add (new Label ("Current Channel: "), BorderLayout.WEST);
      pnlChannelH.add (lblCurrentChannel, BorderLayout.CENTER);
      pnlChannel.add (pnlChannelH, BorderLayout.NORTH);
      pnlChannel.add (lstChannel, BorderLayout.SOUTH);

      Panel pnlUserList = new Panel (new BorderLayout());
      pnlUserList.add (new Label ("Users"), BorderLayout.NORTH);
      pnlUserList.add (lstUserList, BorderLayout.SOUTH);

      pnlLists.add(pnlChannelList, BorderLayout.WEST);
      pnlLists.add(pnlChannel, BorderLayout.CENTER);
      pnlLists.add(pnlUserList, BorderLayout.EAST);

      Panel pnlBottom = new Panel(new BorderLayout(20, 0)); 
      Panel pnlDestination = new Panel(new BorderLayout()); 
      pnlDestination.add(new Label("Destination:"), BorderLayout.WEST); 
      pnlDestination.add(lblDestination, BorderLayout.CENTER); 

      Panel pnlMessage= new Panel(new BorderLayout()); 
      pnlMessage.add(new Label("Message:"), BorderLayout.WEST); 
      pnlMessage.add(txtMessage, BorderLayout.CENTER); 

      pnlBottom.add (pnlDestination, BorderLayout.WEST);
      pnlBottom.add (pnlMessage, BorderLayout.CENTER);


      pnlMain.add(pnlTop, BorderLayout.NORTH); 
      pnlMain.add(pnlLists, BorderLayout.CENTER); 
      pnlMain.add(pnlBottom, BorderLayout.SOUTH);
      
      pnlBorder.add(pnlMain, "main");
      mainFrame.add(pnlBorder, BorderLayout.CENTER);

      mainFrame.addWindowListener(new WindowAdapter(){
	    public void windowClosing(WindowEvent e) {onWindowClose();}
	 });

      txtMessage.addActionListener(new ActionListener(){
	    public void actionPerformed(ActionEvent e) {onMessageTyped();}
	 });
      btnConnect.addActionListener(new ActionListener(){
	    public void actionPerformed(ActionEvent e) {onConnectButtonPressed();}
	 });
      txtServer.addActionListener(new ActionListener(){
	    public void actionPerformed(ActionEvent e) {onConnectionParamsChanged();}
	 });
      txtNick.addActionListener(new ActionListener(){
	    public void actionPerformed(ActionEvent e) {onConnectionParamsChanged();}
	 });

      lstChannelList.addActionListener(new ActionListener(){
	    public void actionPerformed(ActionEvent e) {onChannelSelected();}
	 });

      lstUserList.addActionListener(new ActionListener(){
	    public void actionPerformed(ActionEvent e) {onUserSelected();}
	 });

      mainFrame.pack();
      mainFrame.setSize(800,465);
      mainFrame.pack();
   }

   //
   // Main chat area where incomming messages are shown is "lstChannel".
   // We show error messages, normal messages, private or channel messages in it.
   //

   public void showErrorMessage (String msg) {showMessage("==> ERROR: " + msg);}
   public void showMessage (String msg) {
      lstChannel.add (msg);
      lstChannel.makeVisible(lstChannel.getItemCount()-1); // Automatic scroll
   }


   //
   // UI ActionListener
   // User types [Enter] on the Message chat box.... send a message to the appropriate destination
   //
   public void onMessageTyped () {
      String msg = txtMessage.getText();
      String src = txtNick.getText();
      String dst = lblDestination.getText();
      if (!bConnected) return;
      
      try {
	 if (bDestinationIsChannel) {
	    cc.doSendChannelMessage (dst, msg); // ChatClient does the real send
	 } else {
	    cc.doSendPrivateMessage (dst, msg);
	    showPrivateMessage (src, dst, msg);
	 }
      } catch (Exception e) {
	 showErrorMessage (e.getMessage());
      }

      txtMessage.setText("");
   }


   //
   // UI ActionListener
   // Our simple UI just shows one single channel, so if we click onto a channel, leave the 
   // previous one.
   //
   public void onChannelSelected () {
      String prevChannel = lblCurrentChannel.getText();
      String newChannel = lstChannelList.getSelectedItem();
      String []users = null;
      try {
	 users = cc.doJoinChannel (newChannel);
	 cc.doLeaveChannel (prevChannel);  // Leave if joining worked
      } catch (Exception e) {
	 showErrorMessage (e + "");
	 return;
      }
      
      // Show user list
      lstUserList.removeAll();
      for (int i=0; i< users.length; i++) {
	 lstUserList.add (users[i]);
      }

      // Show channel and set destination to channel too.
      lblDestination.setText (newChannel);
      bDestinationIsChannel = true;
      lblCurrentChannel.setText (newChannel);
   }

   //
   // UI ActionListener
   // Clicking on a user, sets it as our default destination
   //
   public void onUserSelected () {
      String userName = lstUserList.getSelectedItem();

      bDestinationIsChannel = false;
      lblDestination.setText (userName);
   }

   //
   // UI ActionListener
   // If [Enter] pressed on server or nick, try to connect... only if not connected yet.
   //
   public void onConnectionParamsChanged () {
      if (bConnected) return;
      connect();
   }

   //
   // UI ActionListener
   // Pressing the connect button, means to connect or to disconnect. It acts as a toggle.
   //
   public void onConnectButtonPressed () {
      if (!bConnected) {
	 connect();
      } else {
	 disconnect ();
      }
   }

   //
   // UI ActionListener
   // User closes window, we try to disconnect from server
   //
   public void onWindowClose () {
      cc.doTerminate();
   }

   //
   // show methods. Called by ChatClient to show whatever in the graphical UI
   //


   //
   // Private message comming from a remote user
   //
   public void showPrivateMessage (String src, String dst, String str)
   {
      String txt = "[ " + src + " --> " + dst + " ]: " + str;
      showMessage (txt);
   }
   
   //
   // Channel message 
   //
   public void showChannelMessage (String src, String dst, String str)
   {
      String txt = "[ " + src + " ]: " + str;
      showMessage (txt);
   }

   //
   // Some user leaves the channel where we are chatting.
   //
   public void showUserLeavesChannel (String channel, String nick) {
      String [] items = lstUserList.getItems();
      for (int i=0; i<items.length; i++) {
	 if (nick.equalsIgnoreCase (items[i])) {
	    lstUserList.remove (i);
	    break;
	 }
      }
   }

   //
   // Some user joins our channel
   //
   public void showUserEntersChannel (String channel, String nick) {
      lstUserList.add (nick);
   }

   //
   // Connect and disconnect code.
   // It tries to keep in sync the UI with the chat logic.
   //


   private void connect () {
      String srv = txtServer.getText();
      String nick = txtNick.getText();
      String strError = null;
      if (srv == null || srv.trim().equals ("")) {
	 strError = "Server name cannot be empty";
      }
      if (nick == null || nick.trim().equals ("")) {
	 strError = "Nick cannot be empty";
      }
   
      String [] channels = null;
      if (strError == null) 
	 try {
	    channels = cc.doConnect (srv, nick); // Actual connection is done by ChatClient
	    bConnected = true;
	    btnConnect.setLabel ("Disconnect");
	 } catch (Exception e) {
	    strError = e + "";
	 }

      String msg = null;
      if (strError != null) {
	 showErrorMessage (strError);
      } else {
	 showMessage ("==> OK: " + "connected to '" + srv + "' as '" + nick + "'");
      }

      
      // Show list of channels
      lstChannelList.removeAll();      
      lstUserList.removeAll();      
      for (int i=0; channels != null && i< channels.length; i++) {
	 lstChannelList.add (channels[i]);
      }

   }

   private void disconnect () {
      cc.doDisconnect ();
      btnConnect.setLabel ("Connect");
      bConnected = false;
      lstChannel.add ("==> OK: " + "disconnected");
      lstChannelList.removeAll();
      lstUserList.removeAll();
      lblDestination.setText("");
      lblCurrentChannel.setText("");
   }


}
