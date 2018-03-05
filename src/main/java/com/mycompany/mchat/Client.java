/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.mchat;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Martin
 */
public class Client {

    static Logger logger = Logger.getLogger("Client");
    private static int port = 8081;

    public static void main(String[] args) {
        logger.log(Level.INFO, "Start client");
        try {
            Socket socket = new Socket(InetAddress.getLocalHost(), port);
            logger.log(Level.INFO, "Connected to server " + socket.getInetAddress());
            
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            BufferedReader intStd = new BufferedReader(new InputStreamReader(System.in));
            
            new Thread(new ClientListener(in)).start();
            
            String newLine;
            while ((newLine = intStd.readLine()) != null) {
                out.println(newLine);
            }
            
        } catch (UnknownHostException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
}
