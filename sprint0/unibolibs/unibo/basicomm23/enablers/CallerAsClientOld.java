package unibo.basicomm23.enablers;

import unibo.basicomm23.interfaces.Interaction2021;
import unibo.basicomm23.msg.ProtocolType;
import unibo.basicomm23.tcp.TcpClientSupport;
import unibo.basicomm23.udp.UdpClientSupport;
import unibo.basicomm23.utils.CommUtils;

public class CallerAsClientOld {
private Interaction2021 conn;
protected String name ;		//could be a uri
protected ProtocolType protocol ;

/*
 * Realizza la connessione di tipo Interaction2021 (concetto astratto)
 * in modo diverso, a seconda del protocollo indicato (tecnologia specifica)
 */

	public CallerAsClientOld(String name, String host, String entry, ProtocolType protocol ) {
		try {
			CommUtils.outblue(name+"  | CREATING entry= "+entry+" protocol=" + protocol );
			this.name     = name;
			this.protocol = protocol;			 
			setConnection(host,  entry,  protocol);
			CommUtils.outblue(name+"  | CREATED entry= "+entry+" conn=" + conn );
		} catch (Exception e) {
			CommUtils.outred( name+"  |  ERROR " + e.getMessage());		}
	}
	
 	
	protected void setConnection( String host, String entry, ProtocolType protocol  ) throws Exception {
		switch( protocol ) {
			case tcp : {
				int port = Integer.parseInt(entry);
				//conn = new TcpConnection( new Socket( host, port ) ) ; //non fa attempts
				conn = TcpClientSupport.connect(host,  port, 10); //10 = num of attempts
				CommUtils.outblue(name + " |  setConnection "  + conn );		
				break;
			}
			case udp : {
				int port = Integer.parseInt(entry);
 				conn = UdpClientSupport.connect(host,  port );  
				break;
			}
			case coap : {
 				//conn = new CoapConnection( host,  entry );
				break;
			}
			case mqtt : {
				//La connessione col Broker viene stabilita in fase di configurazione
				//La entry � quella definita per ricevere risposte;
				//CommUtils.outblue(name+"  | ProxyAsClient connect MQTT entry=" + entry );
				//conn = MqttConnection.getSupport();					
 				break;
			}	
			default :{
				CommUtils.outred(name + " | Protocol unknown");
			}
		}
	}
  	
	public void sendCommandOnConnection( String cmd )  {
 		//CommUtils.outblue( name+"  | sendCommandOnConnection " + cmd + " conn=" + conn );
		try {
			conn.forward(cmd);
		} catch (Exception e) {
			CommUtils.outred( name+"  | sendCommandOnConnection ERROR=" + e.getMessage()  );
		}
	}
	
	public String sendRequestOnConnection( String request )  {
 		//CommUtils.outblue( name+"  | sendRequestOnConnection request=" + request + " conn=" + conn );
		try {
			String answer = conn.request(request);
			//CommUtils.outblue( name+"  | sendRequestOnConnection-answer=" + answer  );
			return  answer  ;
		} catch (Exception e) {
			CommUtils.outred( name+"  | sendRequestOnConnection ERROR=" + e.getMessage()  );
			return null;
		}
 	}	
	public Interaction2021 getConn() {
		return conn;
	}
	
	public void close() {
		try {
			conn.close();
			CommUtils.outblue(name + " |  CLOSED " + conn   );
		} catch (Exception e) {
			CommUtils.outred( name+"  | sendRequestOnConnection ERROR=" + e.getMessage()  );		}
	}
}
