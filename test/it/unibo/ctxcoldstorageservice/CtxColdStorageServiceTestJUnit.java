package it.unibo.ctxcoldstorageservice;



import it.unibo.kactor.ActorBasic;
import it.unibo.kactor.QakContext;
import org.eclipse.californium.core.CoapClient;
import org.eclipse.californium.core.CoapObserveRelation;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import unibo.basicomm23.interfaces.IApplMessage;
import unibo.basicomm23.msg.ApplMessage;
import unibo.basicomm23.utils.ColorsOut;
import unibo.basicomm23.utils.CommUtils;


import static org.junit.Assert.*;


public class CtxColdStorageServiceTestJUnit {

	private final CoapObserveRelation service_relation = null;

	private static CoapClient 	service_client = null;

	private final CoapObserveRelation trolley_relation = null;
	private static CoapClient 	trolley_client = null;

	static ConnTcp connTcp;
	static int MAXPB = 10;
	static int MAXGB = 20;


	@BeforeClass
	public  static void up() {
		new Thread(MainCtxcoldstorageserviceKt::main).start();
		waitForApplStarted();
		//5683 default
		String service_ipaddr = "localhost:8038";
		String service_context = "ctxcoldstorageservice";
		String service_destactor = "ctxcoldstorageservice";
		service_client = new CoapClient("coap://"+ service_ipaddr +"/"+ service_context +"/"+ service_destactor);
		//5683 default
		String trolley_ipaddr = "localhost:8038";
		String trolley_context = "ctxcoldstorageservice";
		String trolley_destactor = "transporttrolley";
		trolley_client = new CoapClient("coap://"+ trolley_ipaddr +"/"+ trolley_context +"/"+ trolley_destactor);

		try{
			connTcp = new ConnTcp("localhost", 8038);

		}catch(Exception e){
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


	protected  static void waitForApplStarted(){
		ActorBasic service = QakContext.Companion.getActor("coldstorageservice");
		while( service == null ){
			ColorsOut.outappl("TestCoreRequisiti waits for appl ... " , ColorsOut.GREEN);
			CommUtils.delay(400);
			service = QakContext.Companion.getActor("coldstorageservice");
		}
	}

	@AfterClass
	public static void down() {

		try {
			connTcp.close();
		}catch(Exception e){
			ColorsOut.outerr("close ERROR:" + e.getMessage());
		}
		ColorsOut.outappl("TestCoreRequisiti ENDS" , ColorsOut.BLUE);
	}
/*
	@After
	public void resetWeight() throws Exception {

		String resetDispatch = CommUtils.buildDispatch("test","reset", "info(reset)", "ctxcoldstorageservice").toString();
		connTcp.forward(resetDispatch);
	}*/

	@Test
	public void testSingleLoadAccepted() {
		ColorsOut.outappl("testLoadok STARTS" , ColorsOut.BLUE);
		String truckRequestStr = CommUtils.buildRequest("tester","storefood","storefood( 10 )","coldstorageservice").toString();


			try {
				IApplMessage reply = connTcp.request(new ApplMessage(truckRequestStr));
				ColorsOut.outappl("reply: "+reply.msgContent() , ColorsOut.BLUE);
				assertTrue(reply.msgContent().contains("ticketaccepted"));

			String pathStr = trolley_client.get().getResponseText();
			CommUtils.delay(600);
			//while (pathStr.split(" ").length < 4){ 	// controllo che ci siano almeno 4 elementi, amplio
															// per percorsi futuri
				//pathStr = trolley_client.get().getResponseText();
				//CommUtils.delay(600);
			//}

			ColorsOut.outappl("response:  " +pathStr, ColorsOut.ANSI_PURPLE );


		}
		catch (Exception e){
			ColorsOut.outerr("testLoadAccept ERROR:" + e.getMessage());
		}

	}
/*
	@Test
	public void testConsecutiveLoadAccepted() {
		ColorsOut.outappl("testLoadok_double STARTS" , ColorsOut.BLUE);
		String truckRequestStr_1 = CommUtils.buildRequest("test","waste","details(Glass,6)","ctxcoldstorageservice").toString();
		String truckRequestStr_2 = CommUtils.buildRequest("test","waste","details(Plastic,4)","ctxcoldstorageservice").toString();

		try {

		new Thread( (() ->{

			String reply_1 = null;
			String reply_2 = null;
			try {
				reply_1 = connTcp.request(truckRequestStr_1);
				reply_2 = connTcp.request(truckRequestStr_2);
			} catch (Exception e) {
				throw new RuntimeException(e);
			}

			assertTrue(reply_1.contains("loadAccept"));
			assertTrue(reply_2.contains("loadAccept"));
		} )).start();


			String pathStr = trolley_client.get().getResponseText();

			while (pathStr.split(" ").length < 6 *//*&& (pathStr.contains("Glass") || pathStr.contains(("Plastic")))*//*){ 	// controllo che ci siano almeno 4 elementi, amplio
				// per percorsi futuri
				pathStr = trolley_client.get().getResponseText();
				CommUtils.delay(600);
			}

			ColorsOut.outappl("position " +pathStr, ColorsOut.ANSI_PURPLE );
			assertTrue(pathStr.contains("ACCEPTED"));
			pathStr = pathStr.substring(pathStr.indexOf("ACCEPTED"));

			assertTrue(pathStr.contains("INDOOR"));
			pathStr = pathStr.substring(pathStr.indexOf("INDOOR"));

			assertTrue(pathStr.contains("Glass"));
			pathStr = pathStr.substring(pathStr.indexOf("Glass"));



			assertTrue(pathStr.contains("ACCEPTED"));

			var oldPathStr = pathStr.substring(0,pathStr.indexOf("ACCEPTED"));

			assertFalse(oldPathStr.contains("HOME"));
			pathStr = pathStr.substring(pathStr.indexOf("ACCEPTED"));

			assertTrue(pathStr.contains("INDOOR"));
			pathStr = pathStr.substring(pathStr.indexOf("INDOOR"));

			assertTrue(pathStr.contains("Plastic"));
			pathStr = pathStr.substring(pathStr.indexOf("Plastic"));


		}
		catch (Exception e){
			ColorsOut.outerr("testLoadAccept ERROR:" + e.getMessage());
		}

	}*/



}
