/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.mchat;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Martin
 */
public class ClientListener implements Runnable{

    BufferedReader in;

    public ClientListener(BufferedReader in) {
        this.in = in;
    }
    
    @Override
    public void run() {
        String newLine;
        try {
            while ((newLine = in.readLine()) != null) {
                System.out.println(newLine);
            }
        } catch (IOException ex) {
            Logger.getLogger(ClientListener.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
