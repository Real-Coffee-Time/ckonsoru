/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dbmanager;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.xpath.*;
import javax.xml.parsers.*;
import org.w3c.dom.NodeList;

/**
 *
 * @author adech
 */
public class QueryManagerXml implements QueryManagerDAO {
    public  XmlManager xm = XmlManager.GetInstance();

    @Override
    public ResultSet ExecuteQuery(String query) throws SQLException {
        try {
            // throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            XPath xp = XPathFactory.newInstance().newXPath();
            NodeList nl = null;
            
            nl = (NodeList) xp.compile(query).evaluate(this.xm.document, XPathConstants.NODESET);
            
            if (nl != null) {
                for (var i=0; i<nl.getLength()-1; i++) {
                    System.out.println(nl.item(i).getNodeValue());
                }
            }
            
            return null;
        } catch (XPathExpressionException ex) {
            Logger.getLogger(QueryManagerXml.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public Integer ExecuteUpdate(String query) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
