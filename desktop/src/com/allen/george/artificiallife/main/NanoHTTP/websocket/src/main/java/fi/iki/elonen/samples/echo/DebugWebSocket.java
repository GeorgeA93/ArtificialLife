package com.allen.george.artificiallife.main.NanoHTTP.websocket.src.main.java.fi.iki.elonen.samples.echo;

import com.allen.george.artificiallife.main.NanoHTTP.core.src.main.java.fi.iki.elonen.NanoHTTPD;
import com.allen.george.artificiallife.main.NanoHTTP.websocket.src.main.java.fi.iki.elonen.WebSocket;
import com.allen.george.artificiallife.main.NanoHTTP.websocket.src.main.java.fi.iki.elonen.WebSocketFrame;


import java.io.IOException;

/**
* @author Paul S. Hawke (paul.hawke@gmail.com)
*         On: 4/23/14 at 10:34 PM
*/
class DebugWebSocket extends WebSocket {
    private final boolean debug;

    public DebugWebSocket(NanoHTTPD.IHTTPSession handshake, boolean debug) {
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
        try {
            System.out.println("We recieved a message from someone. Contents are: " + messageFrame.getTextPayload());
            messageFrame.setUnmasked();
            sendFrame(messageFrame);
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
}
