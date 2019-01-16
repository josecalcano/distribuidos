/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package distribuidosservidor;

import hilos.ServidorHilo;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author JoseAngel
 */
public class DistribuidosServidor {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException {
        System.out.print("Inicializando servidor... ");
        try {
            ServerSocket socketCliente = new ServerSocket(8000);
            int idSessionC = 0;
            while (true) {
                Socket socketCC;
                try {
                    socketCliente.setSoTimeout(2000);
                    socketCC = socketCliente.accept();
                    if(socketCC.isBound()){
                        System.out.println("Nueva conexión entrante de cliente: "+socketCC);
                        ServidorHilo servidorHilo = new ServidorHilo(socketCC,idSessionC).start();
                        idSessionC++; 
                    }
                } catch (java.io.InterruptedIOException e) {
                    System.out.println("Tiempo de espera de cliente agotado");
                }

            }
        } catch (IOException ex) {
            Logger.getLogger(DistribuidosServidor.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
