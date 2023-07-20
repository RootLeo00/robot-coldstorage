package unibo.basicomm23.utils;

import unibo.basicomm23.coap.CoapConnection;
import unibo.basicomm23.http.HttpConnection;
import unibo.basicomm23.interfaces.Interaction2021;
import unibo.basicomm23.msg.ProtocolType;
import unibo.basicomm23.tcp.TcpConnection;
import unibo.basicomm23.udp.UdpClientSupport;
import unibo.basicomm23.udp.UdpConnection;
import unibo.basicomm23.ws.WsConnection;


public class ConnectionFactory {

        public static Interaction2021 createClientSupport(
                ProtocolType protocol, String host, String entry ){
          CommUtils.outyellow(
          "    --- ConnectionFactory | createClientSupport protocol=" + protocol + " entry=" +entry );
          try {
              switch( protocol ){
                case http  : {
                    Interaction2021 conn  = HttpConnection.create(  host+":"+entry );
                    CommUtils.outyellow("    --- ConnectionFactory | create htpp conn:" + conn);
                    return conn;
                }
                case ws   : {
                    Interaction2021 conn  =  WsConnection.create( host+":"+entry );
                    CommUtils.outyellow("    --- ConnectionFactory | create ws conn:" + conn);
                    return conn;
                }
                case tcp   : {
                    //entry = portNum
                    int port              = Integer.valueOf(entry);
                    Interaction2021 conn  = TcpConnection.create( host, port );
                    CommUtils.outyellow("    --- ConnectionFactory | create tcp conn:" + conn);
                    return conn;
                }
                case udp   : {
                      //entry = portNum
                      int port              = Integer.valueOf( entry );
                      Interaction2021 conn  = UdpConnection.create(host, port);
                      CommUtils.outyellow("    --- ConnectionFactory | create udp conn:" + conn);
                      return conn;
                }
                case coap   : {
                      CommUtils.outyellow("    --- ConnectionFactory | create coap conn"  );
                      Interaction2021 conn  = CoapConnection.create("localhost:"+entry, "basicomm23");
                      return null;
                }
                case mqtt   : {
                      CommUtils.outyellow("    --- ConnectionFactory | create mqtt conn TODO"  );
                      return null;
                }
                case bluetooth   : {
                      CommUtils.outyellow("    --- ConnectionFactory | create bluetooth conn TODO"  );
                      return null;
                }
                case serial   : {
                    CommUtils.outyellow("    --- ConnectionFactory | create serial conn TODO"  );
                    return null;
                }
                default: return null;
             }
          }catch (Exception e) {  e.printStackTrace(); return null; }
        }//createClientSupport
}
