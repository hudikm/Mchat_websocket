/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.mchat;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;

import org.java_websocket.WebSocket;
import org.java_websocket.handshake.ClientHandshake;
import org.java_websocket.server.WebSocketServer;

public class SimpleServer extends WebSocketServer {

    private final ReceivedInterface recvInt;

    static Thread factory(InetSocketAddress address, ReceivedInterface recvInt, IConnectionChange iConnectionChange) {
        return new Thread(new Runnable() {
            @Override
            public void run() {
                WebSocketServer server = new SimpleServer(address, recvInt, iConnectionChange);
                server.run();
            }
        });
    }
    private IConnectionChange iConnectionChange;

    class WebSocketServerHandler implements IServerHandler {

        private WebSocket conn;
        int id;

        public WebSocketServerHandler(WebSocket conn) {
            this.conn = conn;
            this.id = id;
        }

        @Override
        public void sendLine(String newLine) {
            if (conn != null) {
                conn.send(newLine);
            }

        }

        @Override
        public String getId() {
            return String.valueOf(conn.getRemoteSocketAddress());
        }

        @Override
        public boolean equals(Object obj) {
            if( obj instanceof WebSocketServerHandler)
            return ((WebSocketServerHandler)obj).conn.equals(conn);
            else return false;
        }
        

    }

    private SimpleServer(InetSocketAddress address, ReceivedInterface recvInt, IConnectionChange iConnectionChange) {
        super(address);
        this.recvInt = recvInt;
        this.iConnectionChange = iConnectionChange;
    }

    @Override
    public void onOpen(WebSocket conn, ClientHandshake handshake) {

        iConnectionChange.newConnection(new WebSocketServerHandler(conn));
        conn.send("Welcome to the chat Room"); //This method sends a message to the new client
        //broadcast( "new connection: " + handshake.getResourceDescriptor() ); //This method sends a message to all clients connected
        System.out.println("new connection to " + conn.getRemoteSocketAddress());
    }

    @Override
    public void onClose(WebSocket conn, int code, String reason, boolean remote) {
        System.out.println("closed " + conn.getRemoteSocketAddress() + " with exit code " + code + " additional info: " + reason);
         iConnectionChange.clossedConnection(new WebSocketServerHandler(conn));
    }

    @Override
    public void onMessage(WebSocket conn, String message) {
        System.out.println("received message from " + conn.getRemoteSocketAddress() + ": " + message);
        recvInt.onReceived(message, new WebSocketServerHandler(conn));
    }

    @Override
    public void onMessage(WebSocket conn, ByteBuffer message) {
        System.out.println("received ByteBuffer from " + conn.getRemoteSocketAddress());
    }

    @Override
    public void onError(WebSocket conn, Exception ex) {
        System.err.println("an error occured on connection " + conn.getRemoteSocketAddress() + ":" + ex);
    }

    @Override
    public void onStart() {
        System.out.println("server started successfully");
    }

    public static void main(String[] args) {

    }

}
