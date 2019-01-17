/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servidor;

import controlador.Controladora;
import java.io.*;
import java.net.*;
import java.util.logging.*;

/**
 *
 * @author alber
 */
public class Servidor {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException {
      //Se crean los serversockets
        int id = 1;
        ServerSocket socketS12 = null;
        ServerSocket socketS13 = null;
        Controladora control;
        //##########################
        System.out.print("Inicializando servidor... ");
        try {
            control = new Controladora();
            //Se crean los puertos de los serversockets
            socketS12 = new ServerSocket(10002);
            socketS13 = new ServerSocket(10004);
            //#########################################
            System.out.println("\t[OK]");
            //Para identificar los ids de cada conexión y validaciones
            //########################################################
            while (true) {
                //Se crean los sockets de conexión
                Socket socketSS12 = null;
                Socket socketSS13 = null;
                //#################################
                //Se espera conexión de los servidores
                //####################################
                //Se espera por conexiones de los clientes
                //########################################
                try {
                    Socket socket = new Socket("192.168.0.100",10000);
                    System.out.println("Conectado correctamente al servidor principal.");
                    DataOutputStream dos1 = new DataOutputStream(socket.getOutputStream());
                    int idarchivo = control.buscarArchivo();
                    dos1.writeUTF("003-"+Integer.toString(idarchivo));
                    System.out.println("003-Envio de solicitud de réplica.");
                    DataInputStream dis1 = new DataInputStream(socket.getInputStream());
                    String mensaje = dis1.readUTF();
                    System.out.println("003-Mensaje recibido.");
                    String[] parts = mensaje.split("-");
                    if(parts[0].equals("002")){
                        System.out.println("002-Recibido archivo a replicar.");
                        DataOutputStream output;
                        BufferedInputStream bis1;
                        BufferedOutputStream bos1;
                        byte[] receivedData;
                        int in;
                        String file;
                         //Buffer de 1024 bytes
                        receivedData = new byte[1024];
                        bis1 = new BufferedInputStream(socket.getInputStream());
                        dis1 = new DataInputStream(socket.getInputStream());
                        //Recibimos el nombre del fichero
                        file = dis1.readUTF();
                        file = file.substring(file.indexOf('\\')+1,file.length());
                        //Para guardar fichero recibido
                        bos1 = new BufferedOutputStream(new FileOutputStream(file));
                        while ((in = bis1.read(receivedData)) != -1){
                            bos1.write(receivedData,0,in);
                        }
                        bos1.close();
                        bis1.close();
                        int prueba = control.crearArchivo(parts[1], file, 0);
                        if(prueba==1){
                            System.out.println("002-Archivo replicado correctamente.");
                        }
                        if(prueba==0){
                            System.out.println("002-No se pudo replicar el archivo.");
                        }
                    }
                    else{
                        System.out.println("002-Aun no hay archivos que replicar");
                    }

                } catch (IOException ex) {
                    Logger.getLogger(HiloC.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        } catch (IOException ex) {
            Logger.getLogger(Servidor.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
