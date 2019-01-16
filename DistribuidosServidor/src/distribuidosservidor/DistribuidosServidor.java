/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package distribuidosservidor;

import Hilos.ServidorHilo;
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

        try {
            System.out.print("Inicializando servidor... ");
            ServerSocket ss = new ServerSocket(10578);
            int idSession = 0;
            while (true) {
                Socket socket;
                socket = ss.accept();
                System.out.println("Nueva conexión entrante: "+socket);
                ServidorHilo servidorHilo = new ServidorHilo(socket, idSession);
                servidorHilo.start();
                idSession++;
            }
        } catch (IOException ex) {
            Logger.getLogger(DistribuidosServidor.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
