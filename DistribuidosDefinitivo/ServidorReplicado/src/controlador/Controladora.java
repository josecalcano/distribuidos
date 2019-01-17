/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import dominio.Archivo;
import dominio.Usuario;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import persistencia.ArchivoXml;
import persistencia.ServidorXml;
import persistencia.UsuarioXml;
import servidor.HiloC;
/**
 *
 * @author alber
 */
public class Controladora {
    ArchivoXml datosarchivos = new ArchivoXml();
    
    
    public int consultarUsuario(UsuarioXml datos, String username, String password){
        Usuario users = datos.buscarUsuario(username, password);
            if (users == null) {
                return 0;
            }
            else{
               return 1;
            }
    }
    
    public int crearArchivo(String username, String file, int idServer){
        String directorio = username;
        File folder = new File("../Servidor/almacenamiento/"+directorio);
        folder.mkdir();
        int ultid;
        try{
            java.util.Date fecha = new Date();
            DateFormat fechaconvertida = new SimpleDateFormat("yyyyMMdd HHmmss");
            String fechastring = fechaconvertida.format(fecha);
            ultid = datosarchivos.buscarId();
            Archivo archivo = new Archivo(ultid+1,idServer, file, "../Servidor/almacenamiento/"+directorio+"/"+file, fechastring, username);
            boolean resultado = datosarchivos.agregarArchivo(archivo);
            Files.move(Paths.get("../Servidor/"+file), Paths.get("../Servidor/almacenamiento/"+directorio+"/"+file), StandardCopyOption.REPLACE_EXISTING);
            return 1;
        } catch (IOException ex) {
            Logger.getLogger(HiloC.class.getName()).log(Level.SEVERE, null, ex);
            return 0;
        }    
    }
    
    public int buscarArchivo(){
        int ultid;
        try{
            ultid = datosarchivos.buscarId();
            return ultid;
        } catch (Exception e){
            System.out.println("Error buscarArchivo " + e);
            return -1;
        }
     }
    
    public String buscarPathArchivo(int id){
        String path;
        try{
        path = datosarchivos.buscarArchivoId(id);
        } catch (Exception e){
            System.out.println("Error en buscarPathArchivo "+ e);
            return "";
        }
         
        return path;
        
    }
}
