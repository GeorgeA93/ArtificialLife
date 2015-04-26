package com.allen.george.artificiallife.main.NanoHTTP.websocket.src.main.java.fi.iki.elonen;

import com.allen.george.artificiallife.main.NanoHTTP.core.src.main.java.fi.iki.elonen.NanoHTTPD;

public interface IWebSocketFactory {
    WebSocket openWebSocket(NanoHTTPD.IHTTPSession handshake);
}
