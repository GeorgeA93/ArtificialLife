package com.allen.george.artificiallife.main.server;

import com.allen.george.artificiallife.main.NanoHTTP.websocket.src.main.java.fi.iki.elonen.NanoWebSocketServer;

import java.io.IOException;

/**
 * Created by George on 19/02/2015.
 */
public class ALSWebSocketServerMain {

    public static void main(String[] args) throws IOException {
        NanoWebSocketServer ws = new ALSWebSocketServer(9000, true);
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
