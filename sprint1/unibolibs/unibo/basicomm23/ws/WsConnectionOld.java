package unibo.basicomm23.ws;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import unibo.basicomm23.interfaces.*;
import unibo.basicomm23.utils.ColorsOut;
import unibo.basicomm23.utils.CommUtils;

import javax.websocket.*;
import java.net.URI;
import java.util.HashMap;
import java.util.Vector;

@ClientEndpoint
public class WsConnectionOld implements Interaction2021, IObservable{
	private static HashMap<String, WsConnectionOld> connMap=new HashMap<String, WsConnectionOld>();
	private Vector<IObserver> observers= new Vector<IObserver>();
    private boolean opened = false;

    private Session userSession    = null;
    private IWsMsgHandler messageHandler;
    private JSONParser simpleparser = new JSONParser();

	public static WsConnectionOld create(String addr ){
        //ColorsOut.outappl("WsConnection | wsconnect addr=" + addr + " " + connMap.containsKey(addr), ColorsOut.GREEN );
		if( ! connMap.containsKey(addr)){
			connMap.put(addr, new WsConnectionOld( addr ) );
		}else {
		    CommUtils.outyellow("WsConnection | ALREADY connected to addr=" + addr  );
		}
		return connMap.get(addr);
	}


	private WsConnectionOld(String addr  ) {
		wsconnect(addr);
		addMessageHandler( new IWsMsgHandler() {
			@Override
			public void handleMessage(String message) {
				updateObservers(message);			
			}			
		});
 	}
	
    private void wsconnect(String wsAddr){    // localhost:8091
        try {
            WebSocketContainer container = ContainerProvider.getWebSocketContainer();
            URI uri = new URI("ws://"+wsAddr);
            //CommUtils.outyellow("WsConnection | wsconnect container=" + container + " uri=" + uri );
            container.connectToServer(this, uri);
            CommUtils.outyellow("WsConnection | wsconnected to wsAddr=" + wsAddr  );
        } catch ( Exception ex) {
        	CommUtils.outred("WsConnection | URISyntaxException exception: " + ex.getMessage());
        }
    } 
    
    @OnOpen
    public void onOpen(Session userSession) {
    	//CommUtils.outblue("WsConnection | opening websocket userSession="+userSession);
        this.userSession = userSession;
    }

    @OnClose
    public void onClose(Session userSession, CloseReason reason) {
    	CommUtils.outblue("WsConnection | closing websocket");
        this.userSession = null;
    }

    @OnMessage
    public void onMessage(String message)   {
    	//CommUtils.outyellow("WsConnection | onMessage message=" + message);
        if (this.messageHandler != null) {
            this.messageHandler.handleMessage(message);
        }
    }
	
    /**
     * register message handler
     */
    public void addMessageHandler(IWsMsgHandler msgHandler) {
        this.messageHandler = msgHandler;
    }
    
    public void sendMessage(String message ) throws Exception {
    	//CommUtils.outblue("WsConnection | sendMessage " + message + " userSession=" + userSession);
//        this.userSession.getAsyncRemote().sendText(message);
        this.userSession.getBasicRemote().sendText(message);    //synch: blocks until the message has been transmitted
    }

//Since Interaction2021  
	@Override
	public void forward( String msg) throws Exception {
    	try {
		    ColorsOut.out("WsConnection | sendALine "  + msg, ColorsOut.CYAN);
		    sendMessage(msg);
			observers.forEach( v -> {
				//if( v instanceof WsConnSysObserver) ((WsConnSysObserver)v).startMoveTime() ;
			});
    	}catch( Exception e ) {
    	    ColorsOut.outerr("WsConnection | ERROR "  + e.getMessage()  );  		
    	}
 	}

	@Override
	public String request(String msg) throws Exception {
		//We could call forward(msg) but the answer? It is is related to updateObservers
		return "SORRY: not connected for HTTP";
	}

	@Override
	public void reply(String msgJson) throws Exception {
		forward(msgJson);
	}

	@Override
	public String receiveMsg() throws Exception {
 	     ColorsOut.out("WsConnection | receiveMsg, perhaps onMessage"  , ColorsOut.MAGENTA);
		return null;
	}

	@Override
	public void close() throws Exception {
	     //boolean gracefulShutdown = myWs.close(1000, "close");
	     //ColorsOut.out("WsConnection | close gracefulShutdown=" + gracefulShutdown, ColorsOut.YELLOW);
	}
	
 
    protected void setMsgHandler( IGoon goon ){
    	CommUtils.outgreen("WsConnection | setObserver as listener:"  );
        addMessageHandler( new IWsMsgHandler() {
            public void handleMessage(String message) {
                try {
                    //{"collision":"move ","target":"..."} or {"sonarName":"sonar2","distance":19,"axis":"x"}
                	//CommUtils.outgreen("MessageHandler | handleMessage:" + message);
                    JSONObject jsonObj = (JSONObject) simpleparser.parse(message);
                    if (jsonObj.get("endmove") != null) {
                        boolean endmove = jsonObj.get("endmove").toString().equals("true");
                        String  move    = jsonObj.get("move").toString();
                        //CommUtils.outgreen("MessageHandler | handleMessage " + move + " endmove=" + endmove);
                        if( endmove ) goon.nextStep(false);
                    } else if (jsonObj.get("collision") != null) {
                        boolean collision = true; //jsonObj.get("collision").toString().equals("true");
                        String move = jsonObj.get("collision").toString();
                        CommUtils.outmagenta(message);
                        goon.nextStep(collision);
                    } else if (jsonObj.get("sonarName") != null) {
                        String sonarNAme = jsonObj.get("sonarName").toString();
                        String distance = jsonObj.get("distance").toString();
                        CommUtils.outgreen("MessageHandler | handleMessage sonaraAme=" + sonarNAme + " distance=" + distance);
                    }

                } catch (Exception e) {
                	CommUtils.outred("handleMessage ERROR:"+e.getMessage());
                }
            }});
        };//setMsgHandler

 
     
//Since IObservable    
	@Override
	public void addObserver(IObserver obs) {
        ColorsOut.out("WsConnection | addObserver " + obs, ColorsOut.YELLOW );
		observers.add( obs);		
	}
	@Override
	public void deleteObserver(IObserver obs) {
		observers.remove( obs);
	}
    protected void updateObservers( String msg ){
        ColorsOut.out("WsConnection | updateObservers " + observers.size(), ColorsOut.YELLOW );
        observers.forEach( v -> v.update(null, msg) );
    }	

    
    /*
    MAIN
     */
        public static void main(String[] args) {
            try{
           	 String turnrightcmd  = "{\"robotmove\":\"turnRight\"   , \"time\": \"300\"}";
        	 String turnleftcmd  = "{\"robotmove\":\"turnLeft\"     , \"time\": \"300\"}";
        	 String forwardcmd   = "{\"robotmove\":\"moveForward\"  , \"time\": \"-1\"}";
        	 String backwardcmd  = "{\"robotmove\":\"moveBackward\" , \"time\": \"2300\"}";
        	 String haltcmd      = "{\"robotmove\":\"alarm\" , \"time\": \"10\"}";
           	WsConnectionOld wsconn = WsConnectionOld.create("localhost:8091"); // new WsConnection("localhost:8091");
            	//int count = 1;
                wsconn.setMsgHandler( new IGoon() {
                	int count = 1;
                    @Override
                    public void nextStep( boolean collision ) throws Exception {
                    	CommUtils.outblue(" %%% nextStep collision=" + collision + " count=" + count);
                        if (count > 4) {
                        	CommUtils.outgreen("WsConnection | BYE (from nextStep)" );
                            return;
                        }
                        if( collision ) {                       	
                            if (count <= 4) {
                                count++;
                                //String cmd = "{\"robotmove\":\"turnLeft\" , \"time\": 300}";
                                wsconn.sendMessage( turnleftcmd );
                            }
                        } else {  //no collision
                             //String cmd = "{\"robotmove\":\"moveForward\" , \"time\": 600}";
                             wsconn.sendMessage(forwardcmd);
                        };

                        }
                }); //setObserver
                 
               wsconn.sendMessage(forwardcmd);
//             	CommUtils.delay(500);
//             	appl.sendMessage(backwardcmd);
//            	CommUtils.delay(500);
//            	appl.sendMessage(haltcmd);
//             	CommUtils.delay(500);
//             	appl.sendMessage(turnrightcmd);
//             	CommUtils.delay(500);
//             	appl.sendMessage(turnleftcmd);
//             	CommUtils.delay(500);
//             	appl.sendMessage(backwardcmd);
          	
               // wait  for messages from websocket
               Thread.sleep(10000);
                CommUtils.outgreen("WsConnection | doJob ENDS ===== " );
           } catch( Exception ex ) {
            	CommUtils.outred("WsConnection | exception: " + ex.getMessage());
            }
        }
 }
