package unibo.basicomm23.tcp;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;
import unibo.basicomm23.interfaces.Interaction2021;
import unibo.basicomm23.utils.ColorsOut;
import unibo.basicomm23.utils.CommUtils;


public class TcpConnection implements Interaction2021{
private DataOutputStream outputChannel;
private BufferedReader inputChannel;
private Socket socket;

	public static Interaction2021 create(String host, int port) throws Exception{
		for( int i=1; i<=10; i++ ) {
			try {
				Socket socket         =  new Socket( host, port );
				Interaction2021 conn  =  new TcpConnection( socket );
				return conn;
			}catch(Exception e) {
				ColorsOut.out("    +++ TcpConnection | Another attempt to connect with host:" + host + " port=" + port);
				Thread.sleep(500);
			}
		}
		throw new Exception("    +++ TcpConnection ERROR");
	}
	public TcpConnection( String host, int port  ) throws Exception {
		//Socket socket  = new Socket( host, port );
		this( new Socket( host, port ) );
	}
	public TcpConnection( Socket socket  )  throws Exception {
		//try {
			this.socket = socket;
			OutputStream outStream = socket.getOutputStream();
			InputStream inStream = socket.getInputStream();
			outputChannel = new DataOutputStream(outStream);
			inputChannel  = new BufferedReader(new InputStreamReader(inStream));
		//}catch(Exception e){ CommUtils.outred("    +++ TcpConnection ERROR " + e.getMessage()); }
	}
	
	@Override
	public void forward(String msg)  throws Exception {
		//CommUtils.outyellow( "    +++ TcpConnection | sendALine  " + msg + " on " + outputChannel );
		try {
			outputChannel.writeBytes( msg+"\n" );
			outputChannel.flush();
			//Colors.out( "    +++ TcpConnection | has sent   " + msg, Colors.ANSI_YELLOW );	 
		} catch (IOException e) {
			//CommUtils.outred( "    +++ TcpConnection | sendALine ERROR " + e.getMessage());
			throw e;
		}	
	}

	@Override
	public String request(String msg)  throws Exception {
		forward(  msg );
		String answer = receiveMsg();
		return answer;
	}


	@Override
	public void reply(String msg) throws Exception {
		forward(msg);
	} 
	
	@Override
	public String receiveMsg()  { //called by TcpApplMessageHandler
 		try {
			//socket.setSoTimeout(timeOut)
			String	line = inputChannel.readLine() ; //blocking =>
			//CommUtils.outyellow( "    +++ TcpConnection | receiveMsg: " + line );
			return line;
		} catch ( Exception e   ) {
			CommUtils.outred( "    +++ TcpConnection | receiveMsg ERROR  " + e.getMessage() );
	 		return null;
		}		
	}

	@Override
	public void close() {  //called by TcpApplMessageHandler
		try {
			socket.close();
			CommUtils.outyellow( "    +++ TcpConnection | CLOSED port=" + socket.getPort() );
		} catch (IOException e) {
			CommUtils.outred( "    +++ TcpConnection | close ERROR " + e.getMessage());
		}
	}



}
