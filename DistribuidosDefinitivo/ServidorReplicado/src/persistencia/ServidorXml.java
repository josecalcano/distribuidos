/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package persistencia;

import dominio.Servidor;
import dominio.Usuario;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.ParseException;
import java.util.Iterator;
import java.util.List;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;
import org.jdom.output.XMLOutputter;

/**
 *
 * @author Zisko
 */
public class ServidorXml {
    private Element root;
   
    private String fileLocation = "src//Archivos//ServidorBaseDatos.xml";
    
    public ServidorXml() {
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
    
    private Element ServidorXmlElement(Servidor nServidor) {
        Element servidorE = new Element("Servidor");
        
        Element id = new Element("Id");
        id.setText(Integer.toString(nServidor.getId()));
        
        Element ip = new Element("Ip");
        ip.setText(nServidor.getIp());
        
        Element rol = new Element("Rol");
        rol.setText(nServidor.getRol());
        
        Element port = new Element("Port");
        port.setText(Integer.toString(nServidor.getPort()));
        
        Element state = new Element("State");
        state.setText(Integer.toString(nServidor.getState()));

        servidorE.addContent(id);
        servidorE.addContent(ip);
        servidorE.addContent(rol);
        servidorE.addContent(port);
        servidorE.addContent(state);
        
        return servidorE;
    }
    
     private Servidor ServidorToObject(Element element) throws ParseException {
        Servidor nServidor = new Servidor(Integer.parseInt(element.getChildText("Id")), Integer.parseInt(element.getChildText("Port")), Integer.parseInt(element.getChildText("State")), element.getChildText("Ip"), element.getChildText("Rol")) { 
       };
       return nServidor;
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
   /*     
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
    }  */
}
