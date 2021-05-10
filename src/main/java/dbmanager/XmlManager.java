/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dbmanager;

import ckonsoru.ConfigLoader;
import java.io.File;
import java.util.Properties;
import javax.xml.parsers.*;
import javax.xml.xpath.*;
import org.w3c.dom.Document;

/**
 *
 * @author adech
 */
public class XmlManager {
    private static XmlManager xml_manager;
    public Document document;

    private XmlManager() {
        ConfigLoader cf = new ConfigLoader();
        var props = cf.getProperties();
        
        String filename = props.getProperty("xml.fichier");
        
        
        try {
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            this.document = db.parse(filename);
            
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        
    }

    public static XmlManager GetInstance() {
        if (XmlManager.xml_manager == null) {
            XmlManager.xml_manager = new XmlManager();
        }
        return XmlManager.xml_manager;
    }
    
}
