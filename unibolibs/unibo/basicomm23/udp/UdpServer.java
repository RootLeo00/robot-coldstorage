package unibo.basicomm23.udp;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import unibo.basicomm23.interfaces.IApplMsgHandler;
import unibo.basicomm23.utils.ColorsOut;
import unibo.basicomm23.utils.CommUtils;


public class UdpServer extends Thread{
private DatagramSocket socket;
private byte[] buf;
public Map<UdpEndpoint,UdpServerConnection> connectionsMap; //map a port to a specific connection object, if any
protected IApplMsgHandler userDefHandler;
protected String name;
protected boolean stopped = true;

 	public UdpServer( String name, int port,  IApplMsgHandler userDefHandler   ) {
		super(name);
		connectionsMap = new ConcurrentHashMap<UdpEndpoint,UdpServerConnection>();
	      try {
	  		this.userDefHandler   = userDefHandler;
			  CommUtils.outblue(getName() + " | costructor port=" + port );
			this.name             = getName();
			socket                = new DatagramSocket( port );
	     }catch (Exception e) {
			  CommUtils.outred(getName() + " | costruct ERROR: " + e.getMessage());
	     }
	}
	
	@Override
	public void run() {
	      try {
		  	ColorsOut.out( "UdpServer | STARTING ... ", ColorsOut.BLUE  );
			while( ! stopped ) {
				//Wait a packet				 
				ColorsOut.out( "UdpServer | waits a packet ", ColorsOut.BLUE  );	 
				buf = new byte[UdpConnection.MAX_PACKET_LEN];
				DatagramPacket packet = new DatagramPacket(buf, buf.length);
				socket.receive(packet);
				InetAddress address = packet.getAddress();
	            int port = packet.getPort();
	            UdpEndpoint client = new UdpEndpoint(address, port);
	            //String received = new String(packet.getData(), 0, packet.getLength());
	            ColorsOut.out( "UdpServer | received packet from " + client, ColorsOut.BLUE   ); 
	            UdpServerConnection conn = connectionsMap.get(client);
	            if(conn == null) {
	            	conn = new UdpServerConnection(socket, client, connectionsMap);
	            	connectionsMap.put(client, conn);
			 		//Create HERE a message handler on the connection !!!
			 		new UdpApplMessageHandler( userDefHandler, conn );		 	 		
            }else {
	            	 ColorsOut.outappl("UdpServer | CONNECTION ALREADY SET conn= " + conn + " client="+ client, ColorsOut.BLUE   ); 
	            }
	            conn.handle(packet);		 
		 		//Create a message handler on the connection NOT HERE!!
		 		//new UdpApplMessageHandler( userDefHandler, conn );			 		
			}//while
		  }catch (Exception e) {  //Scatta quando la deactive esegue: serversock.close();
			  ColorsOut.out( "UdpServer |  probably socket closed: " + e.getMessage(), ColorsOut.BLUE);		 
		  }
	}
	
	public void activate() {
		if( stopped ) {
			stopped = false;
			this.start();
		}//else already activated
	}
 
	public void deactivate() {
		try {
			ColorsOut.out( "UdpServer |  DEACTIVATE serversock=" +  socket, ColorsOut.BLUE);
			stopped = true;
			socket.close();
			connectionsMap.clear();
		} catch (Exception e) {
			ColorsOut.outerr( "UdpServer |  deactivate ERROR: " + e.getMessage());	 
		}
	}

	public int getNumConnections() {
		return connectionsMap.size();
	}
 
}
