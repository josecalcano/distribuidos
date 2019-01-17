/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servidor;

import dominio.Usuario;
import controlador.Controladora;
import java.awt.Desktop;
import persistencia.UsuarioXml;
import java.io.*;
import java.net.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.logging.*;

/**
 *
 * @author alber
 */
public class HiloC extends Thread{
    
    private Socket socket;
    private DataOutputStream dos;
    private DataInputStream dis;
    Controladora control;
    UsuarioXml datos = new UsuarioXml();
    private int idSessio;
    public HiloC(Socket socket, int id) {
        this.socket = socket;
        this.idSessio = id;
        try {
            control = new Controladora();
            dos = new DataOutputStream(socket.getOutputStream());
            dis = new DataInputStream(socket.getInputStream());
            String mensaje = dis.readUTF();
            String[] parts = mensaje.split("-");
            if(parts[0].equals("001")){
                int resultado = control.consultarUsuario(datos, parts[1], parts[2]);
                dos.writeUTF(Integer.toString(resultado));
            }
            if(parts[0].equals("002")){
                DataOutputStream output;
                BufferedInputStream bis;
                BufferedOutputStream bos;
                byte[] receivedData;
                int in;
                String file;
                 //Buffer de 1024 bytes
                receivedData = new byte[1024];
                bis = new BufferedInputStream(socket.getInputStream());
                DataInputStream dis = new DataInputStream(socket.getInputStream());
                //Recibimos el nombre del fichero
                file = dis.readUTF();
                file = file.substring(file.indexOf('\\')+1,file.length());
                //Para guardar fichero recibido
                bos = new BufferedOutputStream(new FileOutputStream(file));
                while ((in = bis.read(receivedData)) != -1){
                    bos.write(receivedData,0,in);
                }
                bos.close();
                dis.close();
                /*try {
                    File objetofile = new File (file);
                     Desktop.getDesktop().open(objetofile);
                }catch (IOException ex) {
                        System.out.println(ex);
                 }*/
                int prueba = control.crearArchivo(parts[1], file, 0);
                if(prueba==1){
                    System.out.println("Hola wey");
                }
               /* String carpeta = "jfdeoliveira.15";
                File folder = new File("c:/Users/Zisko/Desktop/Servidor/almacenamiento/"+carpeta);
                folder.mkdir();
                Files.move(Paths.get("C:/Users/Zisko/Desktop/Servidor/"+file), Paths.get("C:/Users/Zisko/Desktop/Servidor/almacenamiento/"+carpeta+"/"+file), StandardCopyOption.REPLACE_EXISTING);
                System.out.println("hola maricon");*/
            }
        } catch (IOException ex) {
            Logger.getLogger(HiloC.class.getName()).log(Level.SEVERE, null, ex);
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
        System.out.println("El cliente de id= "+this.idSessio+" saluda");
        //desconnectar();
    }
    
}
