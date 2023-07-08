package unibo.basicomm23.msg;

import unibo.basicomm23.interfaces.IApplMessage;
import unibo.basicomm23.interfaces.IApplMsgHandler;
import unibo.basicomm23.interfaces.Interaction2021;
import unibo.basicomm23.utils.ColorsOut;
import unibo.basicomm23.utils.CommSystemConfig;

public abstract class ApplMsgHandler  implements IApplMsgHandler {  
protected String name;
   
 	public ApplMsgHandler( String name  ) {  
		this.name = name;
	}
 	
 	public String getName() {
		return name;
	}	 
   	
 	public void sendMsgToClient( String message, Interaction2021 conn  ) {
		try {
  			ColorsOut.out(name + " | ApplMsgHandler sendMsgToClient conn=" + conn, ColorsOut.BLUE);
			conn.forward( message );
		} catch (Exception e) {
 			ColorsOut.outerr(name + " | ApplMsgHandler sendMsgToClient ERROR " + e.getMessage());;
		}
 	} 
 	
 	
 	@Override
 	public void sendAnswerToClient( String reply, Interaction2021 conn   ) {
  		try {
  			ColorsOut.out(name + " | ApplMsgHandler sendAnswerToClient conn=" + conn, ColorsOut.BLUE);
			conn.reply(reply);
		} catch (Exception e) {
			ColorsOut.outerr(name + " | ApplMsgHandler sendAnswerToClient ERROR " + e.getMessage() );
		}
 	}
 	
 	//@Override
 	public void sendAnswerToClient( String reply  ) {
		ColorsOut.out(name + " | ApplMsgHandler sendAnswerToClient reply=" + reply, ColorsOut.BLUE);
		try {
			if( CommSystemConfig.protcolType == ProtocolType.mqtt) {
				//TODO
			}else {
				ColorsOut.outerr(name + " | ApplMsgHandler sendAnswerToClient not implemented for  " 
							+ CommSystemConfig.protcolType);
			}
		} catch (Exception e) {
			ColorsOut.outerr(name + " | ApplMsgHandler sendAnswerToClient ERROR " + e.getMessage());
 		}
  	}
 	
 	public abstract void elaborate(IApplMessage message, Interaction2021 conn) ;
 	
}
