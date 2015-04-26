package com.allen.george.artificiallife.main.server;

import com.allen.george.artificiallife.main.NanoHTTP.core.src.main.java.fi.iki.elonen.NanoHTTPD;
import com.allen.george.artificiallife.main.NanoHTTP.websocket.src.main.java.fi.iki.elonen.WebSocket;
import com.allen.george.artificiallife.main.NanoHTTP.websocket.src.main.java.fi.iki.elonen.WebSocketFrame;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by George on 19/02/2015.
 */
public class ALSWebSocket  extends WebSocket {

    private final boolean debug;

    private final int ERROR = -1;
    private final int SUCCESS = 1;



    public ALSWebSocket(NanoHTTPD.IHTTPSession handshake, boolean debug) {
        super(handshake);
        this.debug = debug;
    }

    @Override
    protected void onPong(WebSocketFrame pongFrame) {
        if (debug) {
            System.out.println("P " + pongFrame);
        }
    }

    @Override
    protected void onMessage(WebSocketFrame messageFrame) {
        int responseNum;
        String query = messageFrame.getTextPayload();
        if(query.contains("/")){//there are arguments
            String[] firstSplit =  query.split("/");
            String function = firstSplit[0];
            String[] arguments = firstSplit[1].split("&");
            responseNum = handleQuery(function, arguments);
        } else { //there are no arguments
            responseNum = handleQuery(query, null);
        }

        try {
            if(responseNum == ERROR){
                messageFrame.setUnmasked();
                messageFrame.setTextPayload("ERROR!");
                sendFrame(messageFrame);
            } else if(responseNum == SUCCESS){
                messageFrame.setUnmasked();
                messageFrame.setTextPayload("Success! ");
                sendFrame(messageFrame);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void onClose(WebSocketFrame.CloseCode code, String reason, boolean initiatedByRemote) {
        if (debug) {
            System.out.println("C [" + (initiatedByRemote ? "Remote" : "Self") + "] " +
                    (code != null ? code : "UnknownCloseCode[" + code + "]") +
                    (reason != null && !reason.isEmpty() ? ": " + reason : ""));
        }
    }

    @Override
    protected void onException(IOException e) {
        e.printStackTrace();
    }

    @Override
    protected void handleWebsocketFrame(WebSocketFrame frame) throws IOException {
        if (debug) {
            System.out.println("R " + frame);
        }
        super.handleWebsocketFrame(frame);
    }

    @Override
    public synchronized void sendFrame(WebSocketFrame frame) throws IOException {
        if (debug) {
            System.out.println("S " + frame);
        }
        super.sendFrame(frame);
    }

    private int handleQuery(String function, String[] arguments){
        if(arguments == null){
            return ERROR;
        } else {
            if(function.equals("Start")){

                String username = "";
                for(String s : arguments){
                    if(s.contains("Username")){
                        if(s.split("=").length == 1) return ERROR;
                        username = s.split("=")[1];
                    }
                }
                if(username.equals("")) return ERROR;
                
                if( ClientManager.clientIsntRunning(username)){
                    Client client = new Client(username);
                    ClientManager.clients.add(client);
                    client.request("Start", arguments);
                }

            } else if(function.equals("Stop")){

                String username = "";
                for(String s : arguments){
                    if(s.contains("Username")){
                        if(s.split("=").length == 1) return ERROR;
                        username = s.split("=")[1];
                    }
                }
                if(username.equals("")) return ERROR;

                Client client =  ClientManager.getClientForUsername(username);
                if(client == null) return ERROR;
                client.request("Stop", null);
                ClientManager.clients.remove(client);
            } else if(function.equals("Pause")){

            } else if(function.equals("Restart")){

            }
        }

        return SUCCESS;
    }





}
