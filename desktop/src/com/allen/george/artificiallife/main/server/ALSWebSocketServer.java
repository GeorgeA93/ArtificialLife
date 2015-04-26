package com.allen.george.artificiallife.main.server;

import com.allen.george.artificiallife.main.NanoHTTP.websocket.src.main.java.fi.iki.elonen.NanoWebSocketServer;
import com.allen.george.artificiallife.main.NanoHTTP.websocket.src.main.java.fi.iki.elonen.WebSocket;


/**
 * Created by George on 19/02/2015.
 */
public class ALSWebSocketServer extends NanoWebSocketServer {

    private final boolean debug;

    public ALSWebSocketServer(int port, boolean debug) {
        super(port);
        this.debug = debug;
    }

    @Override
    public WebSocket openWebSocket(IHTTPSession handshake) {
        return new ALSWebSocket(handshake, debug);
    }

}
