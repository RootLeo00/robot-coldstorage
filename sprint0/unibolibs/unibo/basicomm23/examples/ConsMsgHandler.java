package unibo.basicomm23.examples;

import unibo.basicomm23.interfaces.IApplMessage;
import unibo.basicomm23.interfaces.Interaction2021;
import unibo.basicomm23.msg.ApplMsgHandler;
import unibo.basicomm23.utils.CommUtils;

public class ConsMsgHandler extends ApplMsgHandler{

	public ConsMsgHandler(String name) {
		super(name);
		 
	}

	@Override
	public void elaborate(IApplMessage message, Interaction2021 conn) {
		CommUtils.outgreen(name + " elaborate " + message );
		
		try {
			IApplMessage reply = CommUtils.buildReply("consumer", "date", "today", message.msgSender());
			conn.reply(reply.toString());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
