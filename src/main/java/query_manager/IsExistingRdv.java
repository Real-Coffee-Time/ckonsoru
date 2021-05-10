/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package query_manager;

import dbmanager.QueryManagerPgsql;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author adech
 */
public class IsExistingRdv {
    public QueryManagerPgsql qm = new QueryManagerPgsql();
    
    public boolean IsExistingRdvPgsql (String nom_client, String horaire) throws SQLException {
        
        String query = "SELECT rv_id, rv_debut, rv_client" +
                                " FROM rendezvous" +
                                " WHERE rv_client = '" + nom_client + "' AND rv_debut = '" + horaire + "';";
        
        ResultSet result = this.qm.ExecuteQuery(query);
        
        while (result.next()) {
            if (nom_client.equals(result.getString((3))) && horaire.equals(result.getString(2))) {
                return true;
            }
        }
        
        
        return false;
    }
}
