/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entidades;
import java.io.*;
import java.net.Socket;
import java.util.*;
import java.util.logging.*;
/**
 *
 * @author JoseAngel
 */
public class Usuario {
    private String nombreUsuario;
    private String clave;
    private String ruta;

    public Usuario(String nombreUsuario, String clave, String ruta) {
        this.nombreUsuario = nombreUsuario;
        this.clave = clave;
        this.ruta = ruta;
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    public String getClave() {
        return clave;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }

    public String getRuta() {
        return ruta;
    }

    public void setRuta(String ruta) {
        this.ruta = ruta;
    }
}

