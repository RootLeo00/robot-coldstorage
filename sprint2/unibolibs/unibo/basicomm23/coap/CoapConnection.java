package unibo.basicomm23.coap;

 
import org.eclipse.californium.core.CoapClient;
import org.eclipse.californium.core.CoapHandler;
import org.eclipse.californium.core.CoapObserveRelation;
import org.eclipse.californium.core.CoapResponse;
import org.eclipse.californium.core.coap.MediaTypeRegistry;
import unibo.basicomm23.interfaces.Interaction2021;
import unibo.basicomm23.utils.CommUtils;
 
  
public class CoapConnection implements Interaction2021  {
private CoapClient client;
private String url;
 
private String answer = "unknown";

	public static Interaction2021 create(String host, String path) throws Exception {
	 	return new CoapConnection(host,path);
	}

	public CoapConnection( String address, String path) { //"coap://localhost:5683/" + path
 		setCoapClient(address,path);
	}
	
	protected void setCoapClient(String addressWithPort, String path) {
		//url            = "coap://"+address + ":5683/"+ path;
		url            = "coap://"+addressWithPort + "/"+ path;
		CommUtils.outyellow(  "    +++ CoapConn | setCoapClient url=" +  url  );  
		client          = new CoapClient( url );
 		client.useExecutor(); //To be shutdown
 		CommUtils.outyellow("    +++ CoapConn | STARTS client url=" +  url ); //+ " client=" + client );
		client.setTimeout( 1000L );		 		
	}
 	
	public void removeObserve(CoapObserveRelation relation) {
		relation.proactiveCancel();	
		CommUtils.outyellow("    +++ CoapConn | removeObserve !!!!!!!!!!!!!!!" + relation   );
	}
	public CoapObserveRelation observeResource( CoapHandler handler  ) {
		CoapObserveRelation relation = client.observe( handler ); 
		//CommUtils.outyellow("    +++ CoapConn |  added " + handler + " relation=" + relation + relation );
 		return relation;
	}


	
//From Interaction2021 
	@Override
	public void forward(String msg)   {
		CommUtils.outyellow(  "    +++ CoapConn | forward " + url + " msg=" + msg ); 
		if( client != null ) {
			CoapResponse resp = client.put(msg, MediaTypeRegistry.TEXT_PLAIN); //Blocking!
 			if( resp != null )
 				CommUtils.outyellow("    +++ CoapConn | forward " + msg + " resp=" + resp.getCode()   );
		    else { CommUtils.outred("    +++ CoapConn | forward - resp null "   ); }  //?????
		} 
	}

	
	@Override
	public String request(String query)   {
  		CommUtils.outyellow(  "    +++ CoapConn | request query=" + query + " url="+url  );
		String param = query.isEmpty() ? "" :  "?q="+query;
  		//CommUtils.outyellow(  "    +++ CoapConn | param=" + param, ColorsOut.ANSI_YELLOW );
		client.setURI(url+param);
		//CoapResponse response = client.get(  );
		CoapResponse response = client.put(query, MediaTypeRegistry.TEXT_PLAIN);
		if( response != null ) {
 	 		CommUtils.outyellow(  "    +++ CoapConn | request=" + query
 	 				+" RESPONSE CODE: " + response.getCode() + " answer=" + response.getResponseText()  );
			answer = response.getResponseText();
 	 		return answer;
		}else {
	 		CommUtils.outred(  "    +++ CoapConn | request=" + query +" RESPONSE NULL " );
			return null;
		}
	}
	
	//https://phoenixnap.com/kb/install-java-raspberry-pi
	
	@Override
	public void reply(String reqid) throws Exception {
		throw new Exception( "   +++ CoapConn | reply not allowed");
	} 

	@Override
	public String receiveMsg() throws Exception {
		//throw new Exception(name + " | receiveMsg not allowed");
		CommUtils.outyellow(  "    +++ CoapConn | receiveMsg" );
		return answer;
	}

	@Override
	public void close()  {
		CommUtils.outyellow(  "    +++ CoapConn | client shutdown=" + client);		
		client.shutdown();	
	}

}
/*
Log4j by default looks for a file called log4j.properties or log4j.xml on the classpath
*/