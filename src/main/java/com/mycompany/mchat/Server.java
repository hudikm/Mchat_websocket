/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.mchat;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Martin
 */
public class Server implements ReceivedInterface, IConnectionChange {

    private static int portNumber = 8081;
    static Logger logger = Logger.getLogger("Server");

    List<IServerHandler> clientHanders = new ArrayList();

    public static void main(String[] args) {
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                String host = "localhost";
//                int port = 8887;
//                org.java_websocket.server.WebSocketServer server = new SimpleServer(new InetSocketAddress(host, port),);
//                server.run();
//            }
//        }).start();

        new Server().runServer();

    }
    int id = 0;
    public void runServer() {
       
        try {

            String host = "localhost";
            int port = 8887;
            SimpleServer.factory(new InetSocketAddress(host, port), this, this).start();

            logger.log(Level.INFO, "Start server");
            ServerSocket serverSocket = new ServerSocket(portNumber);
            for (;;) {
                Socket clientSocket = serverSocket.accept();
                ServerHandler serverHandler = new ServerHandler(clientSocket, this, id++);
                clientHanders.add(serverHandler);
                new Thread(serverHandler).start();
            }

//            PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
//            BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
//
//            String newLine;
//            while ((newLine = in.readLine()) != null) {
//                System.out.println(newLine);
//                out.println("Echo:" + newLine);
//            }
        } catch (IOException ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void onReceived(String newLine, IServerHandler serverHandler) {
        clientHanders.forEach(s -> {
            if (!s.equals(serverHandler)) {
                s.sendLine(serverHandler.getId() + " : " + newLine);
            }
        });

    }

    @Override
    public void newConnection(IServerHandler iServerHandler) {
        clientHanders.add(iServerHandler);
           }

    @Override
    public void clossedConnection(IServerHandler iServerHandler) {
        clientHanders.remove(iServerHandler);
    }

    @Override
    public int getIndex() {
        return id;
    }

}
