/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Servidor;

import java.io.*;
import java.net.*;
import java.util.logging.*;

/**
 *
 * @author alber
 */
public class HiloS23 extends Thread{
    
    private Socket socket;
    private DataOutputStream dos;
    private DataInputStream dis;
    private int idSessio;
    public HiloS23(Socket socket, int id) {
        this.socket = socket;
        this.idSessio = id;
        try {
            dos = new DataOutputStream(socket.getOutputStream());
            dis = new DataInputStream(socket.getInputStream());
        } catch (IOException ex) {
            Logger.getLogger(HiloS23.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    /*public void desconnectar() {
        try {
            socket.close();
        } catch (IOException ex) {
            Logger.getLogger(ServidorHilo.class.getName()).log(Level.SEVERE, null, ex);
        }
    }*/
    @Override
    public void run() {
        System.out.println("El servidor de id= "+this.idSessio+" saluda");
        //desconnectar();
    }
    
}

