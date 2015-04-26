package com.allen.george.artificiallife.main.NanoHTTP.websocket.src.main.java.fi.iki.elonen.samples.echo;

import com.allen.george.artificiallife.main.NanoHTTP.websocket.src.main.java.fi.iki.elonen.NanoWebSocketServer;


import java.io.IOException;

public class EchoSocketSample {
    public static void main(String[] args) throws IOException {
        NanoWebSocketServer ws = new DebugWebSocketServer(9000, true);
        ws.start();
        System.out.println("Server started, hit Enter to stop.\n");
        try {
            System.in.read();
        } catch (IOException ignored) {
        }
        ws.stop();
        System.out.println("Server stopped.\n");
    }

}

