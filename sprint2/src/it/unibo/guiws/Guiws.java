package it.unibo.guiws;


import okhttp3.internal.ws.RealWebSocket;

import javax.websocket.EncodeException;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.HashMap;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

@ServerEndpoint("/guiws")
public class Guiws {
    private Session session;
    private static Set<Guiws> chatEndpoints
            = new CopyOnWriteArraySet<>();
    private static HashMap<String, String> users = new HashMap<>();

    @OnOpen
    public void onOpen(Session session) throws IOException {
        // Get session and WebSocket connection
            broadcast("hello");

    }
    private static void broadcast(String message)
               {

        chatEndpoints.forEach(endpoint -> {
            synchronized (endpoint) {
                try {
                    endpoint.session.getBasicRemote().
                            sendText(message);
                } catch (IOException  e) {
                    e.printStackTrace();
                }
            }
        });
    }

}
