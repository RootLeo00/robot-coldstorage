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
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicReference;

import static org.junit.Assert.*;


public class CtxColdStorageServiceTestJUnit {

	private final CoapObserveRelation service_relation = null;

	private static CoapClient service_client = null;

	private final CoapObserveRelation trolley_relation = null;

	private static CoapClient trolley_client = null;
	static int MAXREQ = 20;
	static ConnTcp[] connTcp=new ConnTcp[MAXREQ];



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
			for (int i = 0; i<connTcp.length;i++) {
				connTcp[i] = new ConnTcp("localhost", 8038);
			}


		} catch (Exception e) {
			ColorsOut.outerr("initial ERROR:" + e.getMessage());
		}
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
			for (int i = 0; i<connTcp.length;i++) {
				connTcp[i].close();
			}

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
	public void testMultipleLoad() throws InterruptedException {
		ColorsOut.outappl("testLoadok_double STARTS", ColorsOut.BLUE);
		int numberOfThreads = 10;
		ExecutorService service = Executors.newFixedThreadPool(10);
		CountDownLatch latch = new CountDownLatch(numberOfThreads);
		int i=0;

		for (i = 0; i < numberOfThreads; i++) {
			int finalI = i;
			service.submit(() -> {
				try {
					CommUtils.delay(1000);
					String truckRequestStr = CommUtils.buildRequest("tester", "storefood", "storefood(50)", "coldstorageservice").toString();
					ColorsOut.outappl(truckRequestStr+"\n", ColorsOut.ANSI_YELLOW);
					CommUtils.delay(1000);
					IApplMessage reply = connTcp[finalI].request(new ApplMessage(truckRequestStr));
					ColorsOut.outappl("reply: " + reply.msgContent()+"\n", ColorsOut.BLUE);
					assertTrue(reply.msgContent().contains("ticketaccepted") || reply.msgContent().contains("ticketdenied"));

					if(reply.msgContent().contains("ticketaccepted")){
						String ticket = reply.msgContent().split(",")[0].split("[\\\\(]")[1];
						ColorsOut.outappl("ticket:  " + ticket, ColorsOut.ANSI_PURPLE);
						String secret = reply.msgContent().split(",")[1];
						ColorsOut.outappl("secret:  " + secret, ColorsOut.ANSI_PURPLE);
						assertTrue(!ticket.isEmpty() || !ticket.isBlank());
						assertTrue(!secret.isEmpty() || !secret.isBlank());
					}
					CommUtils.delay(1000);
				} catch (InterruptedException e) {
					// Handle exception
				} catch (Exception e) {
					throw new RuntimeException(e);
				}
				latch.countDown();
			});
		}
		latch.await();
		assertEquals(numberOfThreads, i);
	}


	@Test
	public void testSingleLoadExpirationTime() {
		ColorsOut.outappl("testLoadok STARTS", ColorsOut.BLUE);
		String truckRequestStr = CommUtils.buildRequest("tester", "storefood", "storefood( 50 )", "coldstorageservice").toString();


		try {
			IApplMessage reply = connTcp[0].request(new ApplMessage(truckRequestStr));
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

			CommUtils.delay(11000);
			truckRequestStr = CommUtils.buildRequest("tester", "sendticket", "sendticket( " + ticket + "," + secret + " )", "coldstorageservice").toString();
			reply = connTcp[0].request(new ApplMessage(truckRequestStr));
			ColorsOut.outappl("reply: " + reply.msgContent(), ColorsOut.BLUE);
			assertTrue(reply.msgContent().contains("ticketexpired"));

		} catch (Exception e) {
			ColorsOut.outerr("testLoadAccept ERROR:" + e.getMessage());
		}


	}

}
