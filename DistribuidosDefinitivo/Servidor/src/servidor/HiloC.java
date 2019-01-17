/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servidor;

import dominio.Usuario;
import controlador.Controladora;
import dominio.Archivo;
import java.awt.Desktop;
import persistencia.UsuarioXml;
import java.io.*;
import java.net.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
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
                System.out.println("001-Recibido datos de inicio de sesión al servidor.");
                int resultado = control.consultarUsuario(datos, parts[1], parts[2]);
                dos.writeUTF(Integer.toString(resultado));
                System.out.println("001-Respuesta de solicitud de inicio de sesión al cliente.");
            }
            if(parts[0].equals("002")){
                System.out.println("002-Recibido archivo seleccionado del commit.");
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
                int prueba = control.crearArchivo(parts[1], file, 0);
                if(prueba==1){
                    System.out.println("002-Guardado archivo en el servidor.");
                }
                if(prueba==0){
                    System.out.println("002-Error al guardar el archivo.");
                }
            }
            if(parts[0].equals("003")){
                System.out.println("003-Recibido actualizacion de tabla de archivos del update.");
                ArrayList<Archivo> listaArchivos = control.buscarUltimosArchivos(parts[1]);
                ObjectOutputStream salida1 = null;
                salida1 = new ObjectOutputStream(socket.getOutputStream());
                salida1.writeObject(listaArchivos);       
                salida1.close();
                System.out.println("003-Enviada actualizacion de tabla de archivos del update.");
                }
            if(parts[0].equals("004")){
                System.out.println("004-Recibido actualizacion de tabla de archivos del checkout.");
                ArrayList<Archivo> listaArchivos = control.buscarTodosArchivos(parts[1]);
                ObjectOutputStream salida1 = null;
                salida1 = new ObjectOutputStream(socket.getOutputStream());
                salida1.writeObject(listaArchivos);       
                salida1.close();
                System.out.println(listaArchivos);
                System.out.println("004-Enviada actualizacion de tabla de archivos del update.");
            }
            if(parts[0].equals("005")){
                System.out.println("005-Recibido datos de busqueda de archivo seleccionado.");
                DataInputStream input;
                BufferedInputStream bis;
                BufferedOutputStream bos;
                int in;
                byte[] byteArray;
                try
                {
                    String path = control.buscarArchivoSeleccionado(parts[1], parts[2], parts[3]);
                    File file = new File(path);
                    System.out.println("005-Encontrado el archivo seleccionado.");
                    bis = new BufferedInputStream(new FileInputStream(file));                  
                    bos = new BufferedOutputStream(socket.getOutputStream());
                    DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
                    dos.writeUTF(file.getName());
                    System.out.println("005-Enviado el archivo seleccionado.");
                    //Enviamos el fichero
                    byteArray = new byte[8192];
                    while ((in = bis.read(byteArray)) != -1){
                        bos.write(byteArray,0,in);
                    }
                    bis.close();
                    bos.close();
                }catch ( Exception e ) {
                    System.err.println("Error en el HiloC. "+e);
                }
            }
        } catch (IOException ex) {
            Logger.getLogger(HiloC.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @Override
    public void run() {
        System.out.println("El cliente de id= "+this.idSessio+" se conectó");
    }
    
}
