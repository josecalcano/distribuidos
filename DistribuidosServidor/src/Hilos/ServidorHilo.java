/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Hilos;
import Data.UsuarioData;
import Logica.Logica;
import java.io.*;
import java.net.*;
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
        } 
        catch (IOException ex) {
            Logger.getLogger(ServidorHilo.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public void desconectar() {
        try {
            socket.close();
        } 
        catch (IOException ex) {
            Logger.getLogger(ServidorHilo.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    @Override
    public void run() {
        String accion = "";
        try {
            accion = dataInputStream.readUTF();
            String[] parts = accion.split("-");
            if(parts[0].equals("LOGIN")){
                int resultado = logica.consultarUsuario(data, parts[1], parts[2]);
                dataOutputStream.writeUTF(Integer.toString(resultado));
                System.out.println("Intentando hacer login");
            }
            if(accion.equals("hola")){
                System.out.println("El cliente con idSesion "+this.idSesion+" saluda");
                dataOutputStream.writeUTF("adios");
            }
        } catch (IOException ex) {
            Logger.getLogger(ServidorHilo.class.getName()).log(Level.SEVERE, null, ex);
        }
        desconectar();
    }
}

