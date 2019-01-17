/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Control;

import dominio.Archivo;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author richa
 */
public class Controladora {
    JFrame Menu;
    
    public Controladora (JFrame Menu){
        this.Menu = Menu;
    }
    
    public void activarPanel (JPanel panelA, JPanel panelB, JPanel panelC, JPanel panelD){
        panelA.setVisible(true);
        panelB.setVisible(false);
        panelC.setVisible(false);
        panelD.setVisible(false);
    }
    
    public void llenarTabla(ArrayList datos, JTable tablaUpdate){
        ArrayList<Archivo> Lista = datos;
            for (Archivo archivo : Lista)
            {
                   DefaultTableModel dtm = (DefaultTableModel) tablaUpdate.getModel();
                   Archivo arch = archivo;
                   String[] row = {arch.getName(), arch.getDate()};
                   dtm.addRow(row);
                   tablaUpdate.setModel(dtm);
            }  
                
    }
    
    public int crearArchivo(String username, String file, int idServer){
        String directorio = username;
        String nombre = System.getProperty("user.name");
        File folder = new File("C:\\Users\\"+nombre+"\\Desktop\\"+directorio);
        folder.mkdir();
        try{
            Files.move(Paths.get("../Cliente/"+file), Paths.get("C:\\Users\\"+nombre+"\\Desktop\\"+directorio+"/"+file), StandardCopyOption.REPLACE_EXISTING);
            return 1;
        } catch (Exception ex) {
            System.out.println("Error al crear archivo del cliente");
            return 0;
        }    
    }
    
}
