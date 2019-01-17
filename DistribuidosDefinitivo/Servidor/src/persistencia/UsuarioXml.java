/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package persistencia;

import dominio.Usuario;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;
import org.jdom.output.XMLOutputter;
/**
 *
 * @author alber
 */
public class UsuarioXml {
    private Element root;
   
    private String fileLocation = "src//Archivos//UsuarioBaseDatos.xml";
    
    public UsuarioXml() {
        try {
            SAXBuilder builder = new SAXBuilder(false);
            Document doc = null;
            doc = builder.build(fileLocation);
            root = doc.getRootElement();
        } catch (JDOMException ex) {
            System.out.println("No se pudo iniciar la operacion por: " + ex.getMessage());
        } catch (IOException ex) {
            System.out.println("No se pudo iniciar la operacion por: " + ex.getMessage());
        }
    }
    
    private Element UsuariotoXmlElement(Usuario nUsuario) {
        Element usuarioE = new Element("Usuario");
        
        Element username = new Element("Username");
        username.setText(nUsuario.getUsername());
        
        Element pass = new Element("Password");
        pass.setText(nUsuario.getPass());
        
        Element path = new Element("Path");
        path.setText(nUsuario.getPath());

        usuarioE.addContent(username);
        usuarioE.addContent(pass);
        usuarioE.addContent(path);
        return usuarioE;
    }
    
     private Usuario UsuarioToObject(Element element) throws ParseException {
        Usuario nUsuario = new Usuario(element.getChildText("Username"), element.getChildText("Password"), element.getChildText("Path")) { 
       };
       return nUsuario;
       }
     
    private boolean updateDocument() {
        try {
            XMLOutputter out = new XMLOutputter(org.jdom.output.Format.getPrettyFormat());
            FileOutputStream file = new FileOutputStream(fileLocation);
            out.output(root, file);
            file.flush();
            file.close();
            return true;
        } catch (Exception e) {
            System.out.println("error: " + e.getMessage());
            return false;
        }
    }
        
    public static Element buscar(List raiz, String username, String password) {
        Iterator i = raiz.iterator();
        while (i.hasNext()) {
            Element e = (Element) i.next();
            if (username.equals(e.getChild("Username").getText()) && password.equals(e.getChild("Password").getText())) {
                return e;
            }
        }
        return null;
    }
    
    public Usuario buscarUsuario(String username, String password) {
        Element aux = new Element("Usuario");
        List Usuarios = this.root.getChildren("Usuario");
        while (aux != null) {
            
            aux = UsuarioXml.buscar(Usuarios, username, password);
            if (aux != null) {
                try {
                    return UsuarioToObject(aux);
                } catch (ParseException ex) {
                    System.out.println(ex.getMessage());
                }
            }
        }
        return null;
    }
    
}
