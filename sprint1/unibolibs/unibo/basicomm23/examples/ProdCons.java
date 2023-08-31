package unibo.basicomm23.examples;

import unibo.basicomm23.coap.CoapApplServer;
import unibo.basicomm23.enablers.EnablerAsServer;
import unibo.basicomm23.interfaces.IApplMessage;
import unibo.basicomm23.interfaces.Interaction2021;
import unibo.basicomm23.msg.ProtocolType;
import unibo.basicomm23.utils.CommUtils;
import unibo.basicomm23.utils.ConnectionFactory;

public class ProdCons {
private static int port = 9010;
private EnablerAsServer server;
private Interaction2021 client;
 

    public ProdCons(){
    }

    public void doJob( ProtocolType protocol ) throws Exception {
    	if( protocol == ProtocolType.coap ) {
    		port = 5683;
    		new CoapApplServer(port);
    		CommUtils.delay(2000);
    	}else {
            server = new EnablerAsServer("server", port, protocol,  new ConsMsgHandler("consumer") );
            server.start();   		
    	}
    	IApplMessage msg = CommUtils.buildRequest("producer", "date", "today", "consumer");
        client = ConnectionFactory.createClientSupport(protocol, "localhost", ""+port);
        CommUtils.outblue("ProdCons request");
        String answer = client.request( msg.toString() );
        CommUtils.outblue("ProdCons answer=" + answer);
    }

 
    public static void main(String args[]) throws Exception{
        CommUtils.outblue("ProdCons starts");
        ProdCons appl = new ProdCons();
        appl.doJob( ProtocolType.tcp  );
 
    }
}
