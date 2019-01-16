/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entidades;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;

/**
 *
 * @author JoseAngel
 */
public class SocketModelo {
    protected Socket socket;
    protected String ip;
    protected int puerto;
    protected ObjectOutputStream objectOutputStream;
    protected ObjectInputStream objectInputStream;
    protected DataInputStream dataInputStream;
    protected DataOutputStream dataOutputStream;
    
    public SocketModelo(){
        try{
        this.ip = "192.168.1.117";
        this.puerto = 10578;
        this.socket = new Socket(this.ip,this.puerto);
        } catch (IOException ex){
            System.out.println("Conexion caida");
        }
    }

    public Boolean iniciarSesion(Usuario user){
        try{
            dataOutputStream = new DataOutputStream(socket.getOutputStream());

            dataOutputStream.writeUTF("LOGIN-"+user.getNombreUsuario()+"-"+user.getClave()+"-"+user.getRuta()+"");
            System.out.println("Dato enviado");
            //Recepcion de respuesta por parte del servidor
            dataInputStream = new DataInputStream(socket.getInputStream());
            String respuesta = dataInputStream.readUTF();
            System.out.println("Dato recibido");
            dataInputStream.close();
            dataOutputStream.close();
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
            this.socket.close();
            return 1;
        } catch (IOException ex) {
            System.out.println("Problema al desconectar, imposible cerrar sesion.");
            return 0;
        }
    }

    public Socket getsocket() {
        return socket;
    }

    public void setsocket(Socket socket) {
        this.socket = socket;
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

    public ObjectOutputStream getobjectOutputStream() {
        return objectOutputStream;
    }

    public void setobjectOutputStream(ObjectOutputStream objectOutputStream) {
        this.objectOutputStream = objectOutputStream;
    }

    public ObjectInputStream getobjectInputStream() {
        return objectInputStream;
    }

    public void setobjectInputStream(ObjectInputStream objectInputStream) {
        this.objectInputStream = objectInputStream;
    }

    public DataInputStream getdataInputStream() {
        return dataInputStream;
    }

    public void setdataInputStream(DataInputStream dataInputStream) {
        this.dataInputStream = dataInputStream;
    }

    public DataOutputStream getdataOutputStream() {
        return dataOutputStream;
    }

    public void setdataOutputStream(DataOutputStream dataOutputStream) {
        this.dataOutputStream = dataOutputStream;
    }    
}
