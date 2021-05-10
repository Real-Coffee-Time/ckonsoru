/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package query_manager;
import ihm.*;
import dbmanager_pgsql.*;
import items_manager.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author adech
 */
public class GetClientRdv {
    public Input input = new Input();

    public ArrayList<Rendezvous> GetClientRdvPgsql() throws SQLException {
        String client_name = this.input.InputString("Nom du client :");
        
        // String horaire_client = this.input.InputString("Horaire du rdv :");
        
        String query = "SELECT rv_id, rv_debut, rv_client, vet_nom" +
                                " FROM rendezvous" +
                                " INNER JOIN veterinaire ON rendezvous.vet_id = veterinaire.vet_id" +
                                " WHERE rv_client = '" + client_name + "'" +
                                " ORDER BY rv_debut DESC;";
        
        QueryManager qm = new QueryManager();
        ResultSet result = qm.ExecuteQuery(query);
        
        Integer index = 0;
            
            while (result.next()) {
                System.out.println(result.getString(1) + ": " + result.getString(2) + " avec " + result.getString(4));
                index++;
            }
            
            if (index == 0) {
                System.out.println("Aucun résultat trouvé pour ce client.");
            }
        
        return null;
    }
    
    
}
