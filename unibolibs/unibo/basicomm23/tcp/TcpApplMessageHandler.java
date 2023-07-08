package unibo.basicomm23.tcp;

import unibo.basicomm23.interfaces.IApplMessage;
import unibo.basicomm23.interfaces.IApplMsgHandler;
import unibo.basicomm23.interfaces.Interaction2021;
import unibo.basicomm23.msg.ApplMessage;
import unibo.basicomm23.utils.ColorsOut;
import unibo.basicomm23.utils.CommUtils;

/*
 * Ente attivo per la ricezione di messaggi su una connessione Interaction2021
 */
public class TcpApplMessageHandler extends Thread{
private IApplMsgHandler handler ;
private Interaction2021 conn;

public TcpApplMessageHandler(  IApplMsgHandler handler, Interaction2021 conn ) {
		this.handler = handler;
		this.conn    = conn;
	    //CommUtils.outblue("TcpApplMessageHandler | STARTING with user handler:" + handler.getName()  );
 		this.start();
}
 	
	@Override 
	public void run() {
		String name = handler.getName();
			CommUtils.outyellow(getName() + " | TcpApplMessageHandler  STARTS with user-handler=" + name + " conn=" + conn );
			while( true ) {
			try {
				//CommUtils.outblue(name + " | waits for message  ...");
			    String msg = conn.receiveMsg();
				//CommUtils.outblue(name + "  | TcpApplMessageHandler received:" + msg   );
			    if( msg == null ) {
			    	//conn.close();	//Feb23
			    	break;
			    } else{ 
			    	IApplMessage m = new ApplMessage(msg);
			    	handler.elaborate( m, conn ); 
			    }
			}catch( Exception e) {
				CommUtils.outred( getName() + "  | TcpApplMessageHandler: " + e.getMessage()  );
			}
        }
		//CommUtils.outblue(getName() + " | TcpApplMessageHandler BYE"   );
	}
}
