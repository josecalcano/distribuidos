/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package persistencia;

import dominio.Archivo;
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
 * @author Zisko
 */
public class ArchivoXml {
    private Element root;
   
    private String fileLocation = "src//Archivos//ArchivoBaseDatos.xml";
    
    public ArchivoXml() {
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
    
    private Element ArchivotoXmlElement(Archivo nArchivo) {
        Element archivoE = new Element("Archivo");
        
        Element id = new Element("Id");
        id.setText(Integer.toString(nArchivo.getId()));
        
        Element server = new Element("Server");
        server.setText(Integer.toString(nArchivo.getServer()));
        
        Element name = new Element("Name");
        name.setText(nArchivo.getName());
        
        Element path = new Element("Path");
        path.setText(nArchivo.getPath());
        
        Element date = new Element("Date");
        date.setText(nArchivo.getDate());
        
        Element username = new Element("Username");
        username.setText(nArchivo.getUsername());

        archivoE.addContent(id);
        archivoE.addContent(server);
        archivoE.addContent(name);
        archivoE.addContent(path);
        archivoE.addContent(date);
        archivoE.addContent(username);

        return archivoE;
    }
    
     private Archivo ArchivoToObject(Element element) throws ParseException {
        Archivo nArchivo = new Archivo(Integer.parseInt(element.getChildText("Id")), Integer.parseInt(element.getChildText("Server")), element.getChildText("Name"), element.getChildText("Path"), element.getChildText("Date"), element.getChildText("Username")) { 
       };
       return nArchivo;
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
    
    public boolean agregarArchivo(Archivo nArchivo) {
        boolean resultado = false;
        root.addContent(ArchivotoXmlElement((Archivo) nArchivo));
        resultado = updateDocument();
        return resultado;
    }
    
    public ArrayList<Archivo> todosLosArchivos() {
        ArrayList<Archivo> resultado = new ArrayList<Archivo>();     
        for (Object it : root.getChildren()) {
            Element xmlElem = (Element) it;
            try {               
                resultado.add(ArchivoToObject(xmlElem));                  
            } catch (ParseException ex) {
                System.out.println(ex.getMessage());
            }
        }
        return resultado;
    }
        
   public static Element buscar(List raiz, String name, String username) {
        Iterator i = raiz.iterator();
        while (i.hasNext()) {
            Element e = (Element) i.next();
            if (username.equals(e.getChild("Username").getText()) && name.equals(e.getChild("Name").getText())) {
                return e;
            }
        }
        return null;
    }
    
    public Archivo buscarArchivo(String name, String username) {
        Element aux = new Element("Archivo");
        List Archivos = this.root.getChildren("Archivo");
        while (aux != null) {
            
            aux = ArchivoXml.buscar(Archivos, name, username);
            if (aux != null) {
                try {
                    return ArchivoToObject(aux);
                } catch (ParseException ex) {
                    System.out.println(ex.getMessage());
                }
            }
        }
        return null;
    }   
    
    public static Element buscarporId(List raiz, int id) {
        Iterator i = raiz.iterator();
        while (i.hasNext()) {
            Element e = (Element) i.next();
            if (Integer.toString(id).equals(e.getChild("Id").getText())) {
                return e;
            }
        }
        return null;
    }
        
    public String buscarArchivoId(int id) {
        Element aux = new Element("Archivo");
        List Archivos = this.root.getChildren("Archivo");
        while (aux != null) {
            
            aux = ArchivoXml.buscarporId(Archivos, id);
            if (aux != null) {
                try {
                    return aux.getChild("Username").getText()+"-"+aux.getChild("Path").getText()+"/"+aux.getChild("Name");
                } catch (Exception ex) {
                    System.out.println(ex.getMessage());
                }
            }
        }
        return null;
    } 
    
    public int buscarId(){
        int id = 0;
        try {
        SAXBuilder saxBuilder = new SAXBuilder();
        Document document = saxBuilder.build(fileLocation);
        id = document.getRootElement().getChildren().size();
        return id;
        } catch (JDOMException ex) {
            System.out.println("No se pudo iniciar la operacion por: " + ex.getMessage());
            return id;
        } catch (IOException ex) {
            System.out.println("No se pudo iniciar la operacion por: " + ex.getMessage());
            return id;
        }    
    }

}
