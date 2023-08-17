package it.unibo.ctxcoldstorageservice;



import it.unibo.kactor.ActorBasic;
import it.unibo.kactor.QakContext;
import org.eclipse.californium.core.CoapClient;
import org.eclipse.californium.core.CoapObserveRelation;
import org.eclipse.californium.elements.exception.ConnectorException;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import unibo.basicomm23.interfaces.IApplMessage;
import unibo.basicomm23.msg.ApplMessage;
import unibo.basicomm23.utils.ColorsOut;
import unibo.basicomm23.utils.CommUtils;


import java.io.IOException;
import java.util.concurrent.atomic.AtomicReference;

import static org.junit.Assert.*;


public class CtxColdStorageServiceTestJUnit {

	private final CoapObserveRelation service_relation = null;

	private static CoapClient service_client = null;

	private final CoapObserveRelation trolley_relation = null;
	private static CoapClient trolley_client = null;

	static ConnTcp connTcp;
	static int MAXPB = 10;
	static int MAXGB = 20;


	@BeforeClass
	public static void up() {
		new Thread(MainCtxcoldstorageserviceKt::main).start();
		waitForApplStarted();
		//5683 default
		String service_ipaddr = "localhost:8038";
		String service_context = "ctxcoldstorageservice";
		String service_destactor = "ctxcoldstorageservice";
		service_client = new CoapClient("coap://" + service_ipaddr + "/" + service_context + "/" + service_destactor);
		//5683 default
		String trolley_ipaddr = "localhost:8038";
		String trolley_context = "ctxcoldstorageservice";
		String trolley_destactor = "transporttrolley";
		trolley_client = new CoapClient("coap://" + trolley_ipaddr + "/" + trolley_context + "/" + trolley_destactor);

		try {
			connTcp = new ConnTcp("localhost", 8038);

		} catch (Exception e) {
			ColorsOut.outerr("initial ERROR:" + e.getMessage());
		}
/*		String initContainersStr = CommUtils.buildDispatch("test","init_capacity","values("+MAXGB+","+MAXPB+")","ctxcoldstorageservice").toString();


		//Dispatch all_position : coordinates(HOMEX,HOMEY,INDOORX,INDOORY,PLASTICX,PLASTICY,GLASSX,GLASSY)

		String initPositionsStr = CommUtils.buildDispatch("test","all_position", "coordinates(0,0,1,4,6,3,5,0)","transporttrolley").toString();
		try{
			connTcp = new ConnTcp("localhost", 8038);
			connTcp.forward(new ApplMessage(initContainersStr));
			connTcp.forward(new ApplMessage(initPositionsStr));

		}catch(Exception e){
			ColorsOut.outerr("initial ERROR:" + e.getMessage());

		}*/

	}


	protected static void waitForApplStarted() {
		ActorBasic service = QakContext.Companion.getActor("coldstorageservice");
		while (service == null) {
			ColorsOut.outappl("TestCoreRequisiti waits for appl ... ", ColorsOut.GREEN);
			CommUtils.delay(400);
			service = QakContext.Companion.getActor("coldstorageservice");
		}
	}

	@AfterClass
	public static void down() {

		try {
			connTcp.close();
		} catch (Exception e) {
			ColorsOut.outerr("close ERROR:" + e.getMessage());
		}
		ColorsOut.outappl("TestCoreRequisiti ENDS", ColorsOut.BLUE);
	}
/*
	@After
	public void resetWeight() throws Exception {

		String resetDispatch = CommUtils.buildDispatch("test","reset", "info(reset)", "ctxcoldstorageservice").toString();
		connTcp.forward(resetDispatch);
	}*/

	@Test
	public void testSingleLoadAccepted() {
		ColorsOut.outappl("testLoadok STARTS", ColorsOut.BLUE);
		String truckRequestStr = CommUtils.buildRequest("tester", "storefood", "storefood( 50 )", "coldstorageservice").toString();


		try {
			IApplMessage reply = connTcp.request(new ApplMessage(truckRequestStr));
			ColorsOut.outappl("reply: " + reply.msgContent(), ColorsOut.BLUE);
			String ticket = reply.msgContent().split(",")[0].split("[\\\\(]")[1];
			ColorsOut.outappl("ticket:  " + ticket, ColorsOut.ANSI_PURPLE);
			String secret = reply.msgContent().split(",")[1];
			ColorsOut.outappl("secret:  " + secret, ColorsOut.ANSI_PURPLE);

			assertTrue(reply.msgContent().contains("ticketaccepted"));
			assertTrue(!ticket.isEmpty() || !ticket.isBlank());
			assertTrue(!secret.isEmpty() || !secret.isBlank());


			String response = trolley_client.get().getResponseText();
			ColorsOut.outappl("response: " + response, ColorsOut.ANSI_PURPLE);
			assertTrue(response.contains("created"));

			truckRequestStr = CommUtils.buildRequest("tester", "sendticket", "sendticket( " + ticket + "," + secret + " )", "coldstorageservice").toString();
			reply = connTcp.request(new ApplMessage(truckRequestStr));
			ColorsOut.outappl("reply: " + reply.msgContent(), ColorsOut.BLUE);
			assertTrue(reply.msgContent().contains("chargetaken"));

		} catch (Exception e) {
			ColorsOut.outerr("testLoadAccept ERROR:" + e.getMessage());
		}

	}

	@Test
	public void testConsecutiveLoadAccepted() {
		ColorsOut.outappl("testLoadok_double STARTS", ColorsOut.BLUE);
		String truckRequestStr_1 = CommUtils.buildRequest("tester", "storefood", "storefood( 50 )", "coldstorageservice").toString();
		String truckRequestStr_2 = CommUtils.buildRequest("tester", "storefood", "storefood( 50 )", "coldstorageservice").toString();

/*


		try {

			String finalTruckRequestStr_2 = truckRequestStr_1;
			String finalTruckRequestStr_3 = truckRequestStr_2;
			String ticket_1=null;
			String secret_1=null;
			String ticket_2=null;
			String secret_2=null;
			new Thread((() -> {

				IApplMessage reply_1 = null;
				IApplMessage reply_2 = null;
				try {
					reply_1 = connTcp.request(new ApplMessage(finalTruckRequestStr_2));
					reply_2 = connTcp.request(new ApplMessage(finalTruckRequestStr_3));
				} catch (Exception e) {
					throw new RuntimeException(e);
				}

				assertTrue(reply_1.msgContent().contains("ticketaccepted"));
				ticket_1 = reply_1.msgContent().split(",")[0].split("[\\\\(]")[1];
				ColorsOut.outappl("ticket:  " + ticket_1, ColorsOut.ANSI_PURPLE);
				secret_1 = reply_1.msgContent().split(",")[1];
				ColorsOut.outappl("secret:  " + secret_1, ColorsOut.ANSI_PURPLE);

				assertTrue(!ticket_1.get().isEmpty() || !ticket_1.get().isBlank());
				assertTrue(!secret_1.isEmpty() || !secret_1.isBlank());

				assertTrue(reply_2.msgContent().contains("ticketaccepted"));
				ticket_2 = reply_2.msgContent().split(",")[0].split("[\\\\(]")[1];
				ColorsOut.outappl("ticket:  " + ticket_2, ColorsOut.ANSI_PURPLE);
				secret_2 = reply_2.msgContent().split(",")[1];
				ColorsOut.outappl("secret:  " + secret_2, ColorsOut.ANSI_PURPLE);

				assertTrue(!ticket_2.isEmpty() || !ticket_2.isBlank());
				assertTrue(!secret_2.isEmpty() || !secret_2.isBlank());

			})).start();


			String response = null;
			try {
				response = trolley_client.get().getResponseText();
			} catch (ConnectorException | IOException e) {
				throw new RuntimeException(e);
			}

			ColorsOut.outappl("response: " + response, ColorsOut.ANSI_PURPLE);
			assertTrue(response.contains("created"));
			CommUtils.delay(600);


			truckRequestStr_1 = CommUtils.buildRequest("tester", "sendticket", "sendticket( " + ticket_1 + "," + secret_1 + " )", "coldstorageservice").toString();
			truckRequestStr_2 = CommUtils.buildRequest("tester", "sendticket", "sendticket( " + ticket_2 + "," + secret_2 + " )", "coldstorageservice").toString();

			String finalTruckRequestStr_ = truckRequestStr_1;
			String finalTruckRequestStr_1 = truckRequestStr_2;
			new Thread((() -> {

				IApplMessage reply_1 = null;
				IApplMessage reply_2 = null;
				try {
					reply_1 = connTcp.request(new ApplMessage(finalTruckRequestStr_));
					reply_2 = connTcp.request(new ApplMessage(finalTruckRequestStr_1));
				} catch (Exception e) {
					throw new RuntimeException(e);
				}


				ColorsOut.outappl("reply: " + reply_1.msgContent(), ColorsOut.BLUE);
				assertTrue(reply_1.msgContent().contains("chargetaken"));
				ColorsOut.outappl("reply: " + reply_2.msgContent(), ColorsOut.BLUE);
				assertTrue(reply_2.msgContent().contains("chargetaken"));

			})).start();

			} catch (Exception e) {
			ColorsOut.outerr("testLoadAccept ERROR:" + e.getMessage());

		}
*/

	}
}
