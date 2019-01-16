/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Data;

import Entidades.Data;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;
import org.jdom.output.XMLOutputter;

/**
 *
 * @author JoseAngel
 */
public class ArchivoData {

    private Element root;

    private String fileLocation = "src//Data//Data.xml";

    public ArchivoData() {
        try {
            SAXBuilder builder = new SAXBuilder(false);
            Document doc = null;
            doc = builder.build(fileLocation);
            root = doc.getRootElement();
        } catch (JDOMException ex) {
            System.out.println("Error de JDOM" + ex.getMessage());
        } catch (IOException ex) {
            System.out.println("Error de IOException" + ex.getMessage());
        }
    }
    
        private Element ArchivotoXmlElement(Data nArchivo) {
        Element archivoE = new Element("ArchivoData");
        
        Element id = new Element("Id");
        id.setText(Integer.toString(nArchivo.getId()));
       
        
        Element name = new Element("Nombre");
        name.setText(nArchivo.getNombre());
        
        Element path = new Element("Ruta");
        path.setText(nArchivo.getRuta());
        
        Element date = new Element("Fecha");
        date.setText(nArchivo.getFecha());
        
        Element username = new Element("NombreUsuario");
        username.setText(nArchivo.getNombreUsuario());

        archivoE.addContent(id);
        archivoE.addContent(name);
        archivoE.addContent(path);
        archivoE.addContent(date);
        archivoE.addContent(username);

        return archivoE;
    }
    
     private Data ArchivoToObject(Element element) throws ParseException {
        Data nArchivo = new Data(Integer.parseInt(element.getChildText("Id")), element.getChildText("Name"), element.getChildText("Path"), element.getChildText("Date"), element.getChildText("Username")) { 
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
    
    public boolean agregarArchivo(Data nArchivo) {
        boolean resultado = false;
        root.addContent(ArchivotoXmlElement((Data) nArchivo));
        resultado = updateDocument();
        return resultado;
    }
    
    public ArrayList<Data> todosLosArchivos(String user) {
        ArrayList<Data> resultado = new ArrayList<Data>();     
        for (Object it : root.getChildren()) {
            Element xmlElem = (Element) it;
            try {
                if(user.equals(xmlElem.getChildText("NombreUsuario"))){
                    resultado.add(ArchivoToObject(xmlElem));  
                }                                  
            } catch (ParseException ex) {
                System.out.println(ex.getMessage());
            }
        }
        return resultado;
    }
    
    public ArrayList<Data> ultimosArchivos(String user) {
    ArrayList<Data> listaTodos = new ArrayList<Data>();
    ArrayList<Data> listaRepetidos = new ArrayList<Data>();
        for (Object it : root.getChildren()) {
            Element xmlElem = (Element) it;
            try {
                if(user.equals(xmlElem.getChildText("NombreUsuario"))){
                    listaTodos.add(ArchivoToObject(xmlElem));  
                } 
            } catch (ParseException ex) {
                System.out.println(ex.getMessage());
            }
        }
        for (Data archivo1 : listaTodos)
        {
            for (Data archivo2 : listaTodos)
            {  
                if(archivo1.getNombre().equals(archivo2.getNombre())){
                    if(archivo1.getId() == archivo2.getId()){
                        listaRepetidos.add(archivo2);
                    }
                    if(archivo1.getId() < archivo2.getId()){
                        listaRepetidos.add(archivo2);
                    }
                    if(archivo1.getId() > archivo2.getId()){
                        listaRepetidos.add(archivo1);
                    }
                }
            }  
        }
        ArrayList<Data> listaFinal = new ArrayList<Data>();
        ArrayList<String> listaIds = new ArrayList<String>();
        int idmax = 0;
        for (Data archivo1 : listaRepetidos)
        {
            for (Data archivo2 : listaRepetidos)
            {  
                if(archivo1.getNombre().equals(archivo2.getNombre())){
                    if(archivo1.getId() == archivo2.getId()){
                        if(idmax<archivo1.getId()){
                            idmax = archivo1.getId();
                        }
                    }
                    if(archivo1.getId() < archivo2.getId()){
                        if(idmax<archivo2.getId()){
                            idmax = archivo2.getId();
                        }
                    }
                    if(archivo1.getId() > archivo2.getId()){
                        if(idmax<archivo1.getId()){
                            idmax = archivo1.getId();
                        }
                    }
                }
            }
            listaIds.add(Integer.toString(idmax));
            idmax = 0;
        }
        Set<String> hs = new HashSet<>();
        hs.addAll(listaIds);
        listaIds.clear();
        listaIds.addAll(hs);
        for (String id : listaIds){
            Data archivoid = buscarUpdate(Integer.parseInt(id));
            listaFinal.add(archivoid);
        }
        return listaFinal;
    }
        
   public static Element buscar(List raiz, String name, String username) {
        Iterator i = raiz.iterator();
        while (i.hasNext()) {
            Element e = (Element) i.next();
            if (username.equals(e.getChild("NombreUsuario").getText()) && name.equals(e.getChild("Nombre").getText())) {
                return e;
            }
        }
        return null;
    }
    
    public Data buscarArchivo(String name, String username) {
        Element aux = new Element("Archivo");
        List Archivos = this.root.getChildren("Archivo");
        while (aux != null) {
            aux = ArchivoData.buscar(Archivos, name, username);
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
            
            aux = ArchivoData.buscarporId(Archivos, id);
            if (aux != null) {
                try {
                    return aux.getChild("Username").getText()+"-"+aux.getChild("Path").getText();
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
    
    public static Element buscarUp(List raiz, int id) {
        Iterator i = raiz.iterator();
        while (i.hasNext()) {
            Element e = (Element) i.next();
            if (id == Integer.parseInt(e.getChild("Id").getText())) {
                return e;
            }
        }
        return null;
    }
    
    public Data buscarUpdate(int id) {
        Element aux = new Element("Archivo");
        List Archivos = this.root.getChildren("Archivo");
        while (aux != null) {
            aux = ArchivoData.buscarUp(Archivos, id);
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

    public static Element buscarSeleccionado(List raiz, String username, String name, String date) {
        Iterator i = raiz.iterator();
        while (i.hasNext()) {
            Element e = (Element) i.next();
            if (username.equals(e.getChild("Username").getText()) && name.equals(e.getChild("Name").getText()) && date.equals(e.getChild("Date").getText())) {
                return e;
            }
        }
        return null;
    }
    
    public String buscarArchivoSeleccionado(String username, String name, String date) {
        Element aux = new Element("Archivo");
        List Archivos = this.root.getChildren("Archivo");
        while (aux != null) {
            aux = ArchivoData.buscarSeleccionado(Archivos, username, name, date);
            if (aux != null) {
                try {
                    System.out.println(aux.getChild("Path").getText());
                    return aux.getChild("Path").getText();
                } catch (Exception ex) {
                    System.out.println(ex.getMessage());
                }
            }
        }
        return null;
    }  
    

}
