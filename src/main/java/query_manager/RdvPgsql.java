/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package query_manager;
import dbmanager.QueryManagerPgsql;
import ihm.Input;
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
public class RdvPgsql implements RdvDAO {
    public QueryManagerPgsql qm = new QueryManagerPgsql();
    public Input input = new Input();
    
    @Override
    public ArrayList<Rendezvous> GetAllRdv() throws SQLException {
        ArrayList<Rendezvous> liste_rdv = new ArrayList();
        
        String query = "WITH creneauxDisponibles AS " +
                            "	(SELECT vet_nom, generate_series('18-03-2021'::date+dis_debut," +
                            "						   '18-03-2021'::date+dis_fin-'00:20:00'::time," +
                            "						   '20 minutes'::interval) debut" +
                            "	FROM disponibilite" +
                            "		INNER JOIN veterinaire" +
                            "			ON veterinaire.vet_id = disponibilite.vet_id" +
                            "	WHERE dis_jour = EXTRACT('DOW' FROM '18-03-2021'::date)" +
                            "	ORDER BY vet_nom, dis_id)," +
                            "	creneauxReserves AS " +
                            "	(SELECT vet_nom, rv_debut debut" +
                            "	 FROM rendezvous" +
                            "		INNER JOIN veterinaire" +
                            "		ON veterinaire.vet_id = rendezvous.vet_id" +
                            "		WHERE rv_debut " +
                            "		BETWEEN '18-03-2021'::date " +
                            "		AND '18-03-2021'::date +'23:59:59'::time)," +
                            "	creneauxRestants AS" +
                            "	(SELECT * FROM creneauxDisponibles" +
                            "	EXCEPT" +
                            "	SELECT * FROM creneauxReserves)" +
                            "SELECT * FROM creneauxRestants " +
                            "ORDER BY vet_nom, debut;";
        
        ResultSet result = this.qm.ExecuteQuery(query);
        
        while (result.next()) {
            System.out.println(result.getString(1) + " " + result.getString(2) );
        }
        
        return null;
    }
    
    @Override
    public ArrayList<Rendezvous> GetClientRdv() throws SQLException {
        String client_name = this.input.InputString("Nom du client :");
        
        // String horaire_client = this.input.InputString("Horaire du rdv :");
        
        String query = "SELECT rv_id, rv_debut, rv_client, vet_nom" +
                                " FROM rendezvous" +
                                " INNER JOIN veterinaire ON rendezvous.vet_id = veterinaire.vet_id" +
                                " WHERE rv_client = '" + client_name + "'" +
                                " ORDER BY rv_debut DESC;";
        
        ResultSet result = this.qm.ExecuteQuery(query);
        
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
    
    @Override
    public boolean TakeAppointement () throws SQLException {
        
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
            
            if (this.qm.ExecuteUpdate(query) != 1) {
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
    
    @Override
    public boolean DeleteAppointement() throws SQLException {
        String nom_client = this.input.InputString("Nom du client :");
        String horaire = this.input.InputString("Horaire du rdv :");
        
        IsExistingRdv rdv_exists = new IsExistingRdv();
        
        if (rdv_exists.IsExistingRdvPgsql(nom_client, horaire)) {
            // System.out.println("Rendez vous trouvé !");
            
            String request = "DELETE FROM rendezvous"
                    + " WHERE rv_client = '" + nom_client + "' AND rv_debut = '" + horaire + "';";
            
            if (this.qm.ExecuteUpdate(request) == 1) {
                System.out.println("Rendez-vous supprimé avec succés.");
                return true;
            } 
            
            return false;
        }
        
        System.out.println("Aucun rendez-vous n'a été trouvé pour ce client à cette date.");
        return false;
    }

    public ArrayList<Rendezvous> GetCanceledRdv() throws SQLException {
        ArrayList<Rendezvous> liste_rdv = new ArrayList();

        String query = "SELECT ann_client, ann_creneau, vet_nom, ann_delai" +
                            "   FROM annulation" +
                            "	LEFT JOIN veterinaire" +
		                    "   ON veterinaire.vet_id = annulation.vet_id;";

        ResultSet result = this.qm.ExecuteQuery(query);

        while (result.next()) {
            System.out.println(result.getString(1) + " le " + result.getString(2) + " avec " + result.getString(3) + " (" + result.getString(4) + "avant)" );
        }

        return null;

    }
    
}
