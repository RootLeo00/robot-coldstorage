package unibo.basicomm23.tcp;

import java.net.ServerSocket;
import java.net.Socket;
import unibo.basicomm23.interfaces.IApplMsgHandler;
import unibo.basicomm23.interfaces.Interaction2021;
import unibo.basicomm23.utils.ColorsOut;
import unibo.basicomm23.utils.CommSystemConfig;
import unibo.basicomm23.utils.CommUtils;


public class TcpServer extends Thread{
private ServerSocket serversock;
protected IApplMsgHandler userDefHandler;
protected String name;
protected boolean stopped = true;

 	public TcpServer( String name, int port,  IApplMsgHandler userDefHandler   ) {
		super(name);
	      try {
	  		this.userDefHandler   = userDefHandler;
	  		//CommUtils.outyellow(getName() + " | costructor port=" + port   );
			this.name             = getName();
		    serversock            = new ServerSocket( port );
		    serversock.setSoTimeout(CommSystemConfig.serverTimeOut);
	     }catch (Exception e) { 
	    	 ColorsOut.outerr(getName() + " | costruct ERROR: " + e.getMessage());
	     }
	}
	
	@Override
	public void run() {
	      try {
	      	//CommUtils.outyellow(getName() + " | STARTING ... "   );
			while( ! stopped ) {
				//Accept a connection				 
				CommUtils.outyellow(getName() + " | TcpServer waiting ..."  );
		 		Socket sock          = serversock.accept();
				CommUtils.outyellow(getName() + " | TcpServer accepted connection on "+ sock.getPort() + " with userDefHandler=" + userDefHandler.getName()   );
		 		Interaction2021 conn = new TcpConnection(sock);
		 		//Create a message handler on the connection
		 		new TcpApplMessageHandler( userDefHandler, conn );		//thread
			}//while
		  }catch (Exception e) {  //Scatta quando la deactive esegue: serversock.close();
			  CommUtils.outred(getName() + " | probably socket closed: " + e.getMessage() );
		  }
	}
	
	public void activate() {
		if( stopped ) {
			stopped = false;
			this.start();
		}//else already activated
	}
 
	public void deactivate() {
		try {
			CommUtils.outyellow(getName()+" |  DEACTIVATE serversock=" +  serversock);
			stopped = true;
            serversock.close();
		} catch (Exception e) {
			CommUtils.outred(getName() + " | deactivate ERROR: " + e.getMessage());
		}
	}
 
}
