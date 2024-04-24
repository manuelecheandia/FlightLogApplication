/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.flightlog.websocket;

import com.flightlog.model.FlightLogEntry;
import java.io.IOException;
import java.util.HashMap;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.logging.Level;
import java.util.logging.Logger;
import jakarta.websocket.CloseReason;
import jakarta.websocket.EncodeException;
import jakarta.websocket.OnClose;
import jakarta.websocket.OnError;
import jakarta.websocket.OnMessage;
import jakarta.websocket.OnOpen;
import jakarta.websocket.Session;
import jakarta.websocket.server.PathParam;
import jakarta.websocket.server.ServerEndpoint;

/**
 *
 * @author manue
 */
@ServerEndpoint(value = "/flightlog/{pilotName}",
                decoders = FlightLogDecoder.class,
                encoders = FlightLogEncoder.class)
public class FlightLogEndPoint {

    private Session session;
    
    private static Set<FlightLogEndPoint> activeConnections = new CopyOnWriteArraySet<>();

    private static HashMap<String, String> users = new HashMap<>();
    
@OnOpen
public void onOpen(Session session, @PathParam("pilotName") String pilotName) throws IOException, EncodeException {
    System.out.println("Pilot connected: " + pilotName); 
    if (!users.containsValue(pilotName)) {
        this.session = session;
        activeConnections.add(this);
        users.put(session.getId(), pilotName);
 
    } else {
        session.close(new CloseReason(CloseReason.CloseCodes.VIOLATED_POLICY, "Pilot Name already in Use"));
    }
}
    
@OnMessage
public void onMessage(Session session, FlightLogEntry flightLogEntry) throws IOException, EncodeException {
    String pilotName = users.get(session.getId());
    flightLogEntry.setPilotName(pilotName);
    String json = FlightLogEncoder.getGson().toJson(flightLogEntry);
    System.out.println("Serialized FlightLogEntry JSON: " + json); 
    broadcast(flightLogEntry);
}
    
    @OnError
    public void onError(Session session, Throwable throwable) {
        Logger.getLogger(FlightLogEndPoint.class.getName()).log(Level.SEVERE, "WebSocket error occurred", throwable);
    }
    
    @OnClose
    public void onClose(Session session) throws IOException, EncodeException {
        String sessionId = session.getId();
        String username = users.get(sessionId);
        
        if(username != null){
            activeConnections.remove(this);
            broadcast(new FlightLogEntry("System", "", "Pilot disconnected", 0, ""));
        }
        
        
    }
    
    private static void broadcast(FlightLogEntry flightLogEntry) throws IOException, EncodeException {
        activeConnections.forEach(endpoint -> {
            synchronized(endpoint) {
                try {
                    endpoint.session.getBasicRemote().sendObject(flightLogEntry);
                } catch (IOException | EncodeException ex) {
                    Logger.getLogger(FlightLogEndPoint.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }
}