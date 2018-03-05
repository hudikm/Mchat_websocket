/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.mchat;

/**
 *
 * @author Martin
 */
public interface IConnectionChange {
    void newConnection(IServerHandler iServerHandler);
    void clossedConnection(IServerHandler iServerHandler);
    int getIndex();
}
