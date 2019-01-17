/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Logica;

import Data.ArchivoData;
import Data.UsuarioData;
import Entidades.Data;
import Entidades.Usuario;
import Hilos.ServidorHilo;
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

/**
 *
 * @author JoseAngel
 */
public class Logica {
    private ArchivoData dataArchivos = new ArchivoData();

    public int consultarUsuario(UsuarioData datos, String username, String password) {
        Usuario users = datos.buscarUsuario(username, password);
        if (users == null) {
            return 0;
        } else {
            return 1;
        }
    }

    public int crearArchivo(String username, String file, int idServer) {
        String directorio = username;
        File folder = new File("../DistribuidosServidor/datos/" + directorio);
        folder.mkdir();
        int ultid;
        try {
            java.util.Date fecha = new Date();
            DateFormat fechaconvertida = new SimpleDateFormat("yyyyMMdd HHmmss");
            String fechastring = fechaconvertida.format(fecha);
            ultid = dataArchivos.buscarId();
            Data archivo = new Data(ultid + 1, file, "../DistribuidosServidor/datos/" + directorio + "/" + fechastring + "_" + file, fechastring, username);
            boolean resultado = dataArchivos.agregarArchivo(archivo);
            Files.move(Paths.get("../DistribuidosServidor/" + file), Paths.get("../DistribuidosServidor/datos/" + directorio + "/" + fechastring + "_" + file), StandardCopyOption.REPLACE_EXISTING);
            return 1;
        } catch (IOException ex) {
            Logger.getLogger(ServidorHilo.class.getName()).log(Level.SEVERE, null, ex);
            return 0;
        }
    }
    
    public int buscarArchivo(){
        int ultid;
        try{
            ultid = dataArchivos.buscarId();
            return ultid;
        } catch (Exception e){
            System.out.println("Error buscarArchivo " + e);
            return 0;
        }
     }
    
    public String buscarPathArchivo(int id){
        String path;
        try{
        path = dataArchivos.buscarArchivoId(id);
            System.out.println(path);
        } catch (Exception e){
            System.out.println("Error en buscarPathArchivo "+ e);
            return "";
        }
         
        return path;
    }
    
    public ArrayList<Data> buscarTodosArchivos(String user){
        ArrayList<Data> archivo;
        try{
            archivo = dataArchivos.todosLosArchivos(user);
            return archivo;
        } catch (Exception e){
            System.out.println("Error buscarArchivo " + e);
            return null;
        }    
    }
    
    public ArrayList<Data> buscarUltimosArchivos(String user){
        ArrayList<Data> archivo;
        try{
            archivo = dataArchivos.ultimosArchivos(user);
                        System.out.println(archivo);

            return archivo;
        } catch (Exception e){
            System.out.println("Error buscarArchivo " + e);
            return null;
        }    
    }
    
        public String buscarArchivoSeleccionado(String user, String name, String date){
        String path;
        try{
            path = dataArchivos.buscarArchivoSeleccionado(user, name, date);
            return path;
        } catch (Exception e){
            System.out.println("Error buscarArchivo " + e);
            return "";
        }
     }
    
    
}
