/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Logica;

import Data.UsuarioData;
import Entidades.Usuario;

/**
 *
 * @author JoseAngel
 */
public class Logica {
    
    public int consultarUsuario(UsuarioData datos, String username, String password){
        Usuario users = datos.buscarUsuario(username, password);
            if (users == null) {
                return 0;
            }
            else{
               return 1;
            }
    }
    
}
