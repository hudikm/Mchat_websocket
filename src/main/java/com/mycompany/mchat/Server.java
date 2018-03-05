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
        new Server().runServer();

    }
    int id = 0;
    public void runServer() {
       
        try {
           
            logger.log(Level.INFO, "Start server");
            ServerSocket serverSocket = new ServerSocket(portNumber);
            for (;;) {
                Socket clientSocket = serverSocket.accept();
                ServerHandler serverHandler = new ServerHandler(clientSocket, this, id++);
                clientHanders.add(serverHandler);
                new Thread(serverHandler).start();
            }

        } catch (IOException ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

 
}
