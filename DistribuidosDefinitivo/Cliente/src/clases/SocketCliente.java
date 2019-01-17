/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clases;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;

/**
 *
 * @author richa
 */
public class SocketCliente {
    protected Socket sk;
    protected String ip;
    protected int puerto;
    protected ObjectOutputStream oos;
    protected ObjectInputStream ois;
    protected DataInputStream dis;
    protected DataOutputStream dos;
    
    public SocketCliente(){
        try{
        this.ip = "192.168.0.103";
        this.puerto = 8000;
        this.sk = new Socket(this.ip,this.puerto);
        } catch (IOException ex){
            System.out.println("Conexion caida");
        }
    }

    public Boolean iniciarSesion(Usuario user){
        try{
            dos = new DataOutputStream(sk.getOutputStream());
            //oos = new ObjectOutputStream(sk.getOutputStream());
            //Envio del usuario para su login
            //oos.writeObject(user);
            dos.writeUTF("001-"+user.getUsername()+"-"+user.getPass()+"-"+user.getPath()+"");
            System.out.println("Dato enviado");
            //Recepcion de respuesta por parte del servidor
            dis = new DataInputStream(sk.getInputStream());
            String respuesta = dis.readUTF();
            System.out.println("Dato recibido");
            dis.close();
            dos.close();
            if (respuesta.equals("1")){
                return true;
            }
            else{
                return false;
            }
            
        }catch (IOException ex){
            System.out.println("Conexion caida");
            return false;
        }
    }
    
    public int desconectar(){
        try {
            this.sk.close();
            return 1;
        } catch (IOException ex) {
            System.out.println("Problema al desconectar, imposible cerrar sesion.");
            return 0;
        }
    }

    public Socket getSk() {
        return sk;
    }

    public void setSk(Socket sk) {
        this.sk = sk;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public int getPuerto() {
        return puerto;
    }

    public void setPuerto(int puerto) {
        this.puerto = puerto;
    }

    public ObjectOutputStream getOos() {
        return oos;
    }

    public void setOos(ObjectOutputStream oos) {
        this.oos = oos;
    }

    public ObjectInputStream getOis() {
        return ois;
    }

    public void setOis(ObjectInputStream ois) {
        this.ois = ois;
    }

    public DataInputStream getDis() {
        return dis;
    }

    public void setDis(DataInputStream dis) {
        this.dis = dis;
    }

    public DataOutputStream getDos() {
        return dos;
    }

    public void setDos(DataOutputStream dos) {
        this.dos = dos;
    }
    
    
}


