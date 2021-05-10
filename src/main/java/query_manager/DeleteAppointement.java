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
import java.util.Arrays;
import java.util.List;
import java.util.Random;

/**
 *
 * @author adech
 */
public class DeleteAppointement {
    public QueryManager qm = new QueryManager();
    Input input = new Input();
    
    public boolean DeleteAppointementPgsql() throws SQLException {
        String nom_client = this.input.InputString("Nom du client :");
        String horaire = this.input.InputString("Horaire du rdv :");
        
        IsExistingRdv rdv_exists = new IsExistingRdv();
        
        if (rdv_exists.IsExistingRdvPgsql(nom_client, horaire)) {
            // System.out.println("Rendez vous trouvé !");
            
            String request = "DELETE FROM rendezvous"
                    + " WHERE rv_client = '" + nom_client + "' AND rv_debut = '" + horaire + "';";
            
            if (this.qm.ExecuteUpdatePgsql(request) == 1) {
                System.out.println("Rendez-vous supprimé avec succés.");
                return true;
            } 
            
            return false;
        }
        
        System.out.println("Aucun rendez-vous n'a été trouvé pour ce client à cette date.");
        return false;
    }
}
