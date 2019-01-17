/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Hilos;

import Data.UsuarioData;
import Entidades.Data;
import Logica.Logica;
import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.logging.*;

/**
 *
 * @author JoseAngel
 */
public class ServidorHilo extends Thread {

    private Socket socket;
    private DataOutputStream dataOutputStream;
    private DataInputStream dataInputStream;
    private int idSesion;
    private Logica logica;
    UsuarioData data = new UsuarioData();

    public ServidorHilo(Socket socket, int id) {
        this.socket = socket;
        this.idSesion = id;
        try {
            dataOutputStream = new DataOutputStream(socket.getOutputStream());
            dataInputStream = new DataInputStream(socket.getInputStream());
            logica = new Logica();
        } catch (IOException ex) {
            Logger.getLogger(ServidorHilo.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void desconectar() {
        try {
            socket.close();
        } catch (IOException ex) {
            Logger.getLogger(ServidorHilo.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void run() {
        String accion = "";
        try {
            accion = dataInputStream.readUTF();
            String[] parts = accion.split("-");
            if (parts[0].equals("LOGIN")) {
                int resultado = logica.consultarUsuario(data, parts[1], parts[2]);
                dataOutputStream.writeUTF(Integer.toString(resultado));
                System.out.println("Intentando hacer login");
            }
            if (parts[0].equals("COMMIT")) {
                System.out.println("Preparando commit");
                BufferedInputStream bis;
                BufferedOutputStream bos;
                byte[] receivedData;
                int in;
                String file;
                receivedData = new byte[1024];
                bis = new BufferedInputStream(socket.getInputStream());
                DataInputStream dis = new DataInputStream(socket.getInputStream());
                file = dis.readUTF();
                file = file.substring(file.indexOf('\\') + 1, file.length());
                bos = new BufferedOutputStream(new FileOutputStream(file));
                while ((in = bis.read(receivedData)) != -1) {
                    bos.write(receivedData, 0, in);
                }
                bos.close();
                dis.close();
                int resultado = logica.crearArchivo(parts[1], file, 0);
                if (resultado == 1) {
                    System.out.println("Guardado completo");
                }
                if (resultado == 0) {
                    System.out.println("Error guardando");
                }
            }
            if (parts[0].equals("UPDATE")) {
                System.out.println("Preparando update");
                System.out.println(parts[1]);

                ArrayList<Data> listaArchivos = logica.buscarUltimosArchivos(parts[1]);                
                ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
                oos.writeObject(listaArchivos);
                oos.close();
            }
            if (parts[0].equals("BUSQUEDAUPDATE")) {
                System.out.println("005-Recibido datos de busqueda de archivo seleccionado.");
                DataInputStream input;
                BufferedInputStream bis;
                BufferedOutputStream bos;
                int in;
                byte[] byteArray;
                try {
                    String path = logica.buscarArchivoSeleccionado(parts[1], parts[2], parts[3]);
                    File file = new File(path);
                    System.out.println("005-Encontrado el archivo seleccionado.");
                    bis = new BufferedInputStream(new FileInputStream(file));
                    bos = new BufferedOutputStream(socket.getOutputStream());
                    DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
                    dos.writeUTF(file.getName());
                    System.out.println("005-Enviado el archivo seleccionado.");
                    //Enviamos el fichero
                    byteArray = new byte[8192];
                    while ((in = bis.read(byteArray)) != -1) {
                        bos.write(byteArray, 0, in);
                    }
                    bis.close();
                    bos.close();
                } catch (Exception e) {
                    System.err.println("Error en el HiloC. " + e);
                }
            }
            if (accion.equals("hola")) {
                System.out.println("El cliente con idSesion " + this.idSesion + " saluda");
                dataOutputStream.writeUTF("adios");
            }
        } catch (IOException ex) {
            Logger.getLogger(ServidorHilo.class.getName()).log(Level.SEVERE, null, ex);
        }
        desconectar();
    }
}
