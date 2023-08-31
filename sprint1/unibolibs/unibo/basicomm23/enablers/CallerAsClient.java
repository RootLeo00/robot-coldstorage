package unibo.basicomm23.enablers;

import unibo.basicomm23.interfaces.Interaction2021;
import unibo.basicomm23.msg.ProtocolType;
import unibo.basicomm23.tcp.TcpClientSupport;
import unibo.basicomm23.udp.UdpClientSupport;
import unibo.basicomm23.utils.CommUtils;
import unibo.basicomm23.utils.ConnectionFactory;

public class CallerAsClient {
private Interaction2021 conn; 
protected String name ;


	public CallerAsClient(String name, String host, String entry, ProtocolType protocol ) {
		try {
			CommUtils.outblue(name+"  | CREATING entry= "+entry+" protocol=" + protocol );
			this.name     = name;
			conn = ConnectionFactory.createClientSupport(protocol,host,entry);
			CommUtils.outblue(name+"  | CREATED entry= "+entry+" conn=" + conn );
		} catch (Exception e) {
			CommUtils.outred( name+"  |  ERROR " + e.getMessage());		}
	}
	public Interaction2021 getConn() {
		return conn;
	}

}
