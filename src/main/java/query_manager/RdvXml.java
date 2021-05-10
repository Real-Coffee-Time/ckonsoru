/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package query_manager;

import items_manager.Rendezvous;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author adech
 */
public class RdvXml implements RdvDAO {

    @Override
    public ArrayList<Rendezvous> GetAllRdv() throws SQLException {
        System.out.println("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        
        return null;
    }

    @Override
    public ArrayList<Rendezvous> GetClientRdv() throws SQLException {
        System.out.println("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        return null;
    }

    @Override
    public boolean TakeAppointement() throws SQLException {
        System.out.println("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        return false;
    }

    @Override
    public boolean DeleteAppointement() throws SQLException {
        System.out.println("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        return false;
    }
    
}
