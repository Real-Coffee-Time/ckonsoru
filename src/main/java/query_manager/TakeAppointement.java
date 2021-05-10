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
public class TakeAppointement {
    public Input input = new Input();
    public QueryManager qm = new QueryManager();
    
    public boolean TakeAppointementPgsql () throws SQLException {
        
        String nom_veterinaire = this.input.InputString("Nom du vétérinaire :");
        String horaire = this.input.InputString("Horaire du rendez-vous :");
        
        IsRdvAvailable is_rdv_available = new IsRdvAvailable();
        
        if (is_rdv_available.IsRdvAvailablePgsql(nom_veterinaire, horaire)) {
            
            // System.out.println("Rdv is available");
            
            String nom_client = this.input.InputString("Nom du client :");
            
            String query = "INSERT INTO rendezvous (vet_id, rv_debut, rv_client)" +
                            "    VALUES ((SELECT vet_id FROM veterinaire WHERE vet_nom = '" + nom_veterinaire + "')," +
                            "        '" + horaire + "'," +                                                       // Changer pour date
                            "        '" + nom_client + "');";
            
            if (this.qm.ExecuteUpdatePgsql(query) != 1) {
                System.out.println("Une erreur est survenue pendant l'enregistrement du rendez-vous.");
                return false;
            }
            
            List<String> animal_list = Arrays.asList("Cochon-Dinde", "Poulet Rôti", "Crocodile Gucci", "Dindon de la farce", "Tigre en voie de disparition", "Pigeon voyageur");
            Random rand = new Random();
            System.out.println("Rendez-vous pris pour le " + horaire + " avec le docteur " + nom_veterinaire + ". N'oubliez pas votre " + animal_list.get(rand.nextInt(animal_list.size())) + ".");
            return true;
        }
        System.out.println("Le rendez-vous n'est point disponible... Looser !");
        return false;
    }
}
