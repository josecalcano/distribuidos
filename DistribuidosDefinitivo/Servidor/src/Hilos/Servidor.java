/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Hilos;

import Logica.Controladora;
import java.io.*;
import java.net.*;
import java.util.logging.*;

/**
 *
 * @author JoseAngel
 */
public class Servidor {

    /**
     * @param args the command line arguments
     */

    public static void main(String[] args) throws IOException {
      //Se crean los serversockets
        int id = 0;
        ServerSocket socketC = null;  
        ServerSocket socketS01 = null;
        //##########################
        System.out.print("Inicializando servidor... ");
        try {
            //Se crean los puertos de los serversockets
            socketC = new ServerSocket(8000);
            socketS01 = new ServerSocket(10000);
            //#########################################
            System.out.println("\t[OK]");
            //Para identificar los ids de cada conexión y validaciones
            int idSessionC = 0;
            int idSessionS = 0;
            //########################################################
            while (true) {
                //Se crean los sockets de conexión
                Socket socketCC;
                Socket socketSS01;

                //#################################
                //Se espera conexión de los servidores
                //####################################
                //Se espera por conexiones de los clientes
                try {
                    socketC.setSoTimeout(2000);
                    socketCC = socketC.accept();
                    if(socketCC.isBound()){
                        System.out.println("Nueva conexión entrante de cliente: "+socketCC);
                        ((HiloC) new HiloC(socketCC, idSessionC)).start();
                        idSessionC++; 
                    }
                } catch (java.io.InterruptedIOException e) {
                    System.out.println("Tiempo de espera de cliente agotado");
                }
                try {
                    socketS01.setSoTimeout(2000);
                    socketSS01 = socketS01.accept();
                    if(socketSS01.isBound()){
                        idSessionS = 01;
                        System.out.println("Nueva conexión entrante del servidor: "+socketSS01);
                        ((HiloS01) new HiloS01(socketSS01, idSessionS)).start(); 
                    }
                } catch (java.io.InterruptedIOException e) {
                    System.out.println("Tiempo de espera del servidor 1 agotado");
                }

                //########################################
            }
        } catch (IOException ex) {
            Logger.getLogger(Servidor.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
