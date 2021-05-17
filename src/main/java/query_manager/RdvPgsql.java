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
        
        String date_rdv = this.input.InputString("Date de votre rdv");
        
        String query = "WITH creneauxDisponibles AS " +
                            "	(SELECT vet_nom, generate_series('" + date_rdv + "'::date+dis_debut," +
                            "						   '" + date_rdv + "'::date+dis_fin-'00:20:00'::time," +
                            "						   '20 minutes'::interval) debut" +
                            "	FROM disponibilite" +
                            "		INNER JOIN veterinaire" +
                            "			ON veterinaire.vet_id = disponibilite.vet_id" +
                            "	WHERE dis_jour = EXTRACT('DOW' FROM '" + date_rdv + "'::date)" +
                            "	ORDER BY vet_nom, dis_id)," +
                            "	creneauxReserves AS " +
                            "	(SELECT vet_nom, rv_debut debut" +
                            "	 FROM rendezvous" +
                            "		INNER JOIN veterinaire" +
                            "		ON veterinaire.vet_id = rendezvous.vet_id" +
                            "		WHERE rv_debut " +
                            "		BETWEEN '" + date_rdv + "'::date " +
                            "		AND '" + date_rdv + "'::date +'23:59:59'::time)," +
                            "	creneauxRestants AS" +
                            "	(SELECT * FROM creneauxDisponibles" +
                            "	EXCEPT" +
                            "	SELECT * FROM creneauxReserves)" +
                            "SELECT * FROM creneauxRestants " +
                            "ORDER BY vet_nom, debut;";
        
        ResultSet result = this.qm.ExecuteQuery(query);
        
        if (result.next() == false) {
            Integer choix = this.input.InputInt("Pas de disponibilité, appuyez sur 1 pour être sur une liste d'attente, 0 pour retourner au menu.");
            
            if (choix == 1) {
                String nom = this.input.InputString("Nom : ");
                String tel = this.input.InputString("Tel : ");
                
                String query2 = "INSERT INTO listeAttente (la_client, la_numtel, la_dateAuPlusTard, la_creneauPropose)"
                                + "VALUES ('" + nom +"', '" + tel +"', '" + date_rdv +"'::date, NULL);";
                
                this.qm.ExecuteUpdate(query2);
            }
            
            return null;
        }
        
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
        String date = this.input.InputString("Date du rendez-vous :");
        String heure = this.input.InputString("Heure du rendez-vous :");
        
        String horaire = date + " " + heure;
        
        IsRdvAvailable is_rdv_available = new IsRdvAvailable();
        
        if (is_rdv_available.IsRdvAvailablePgsql(nom_veterinaire, date, heure)) {
            
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
        String nom_veterinaire = this.input.InputString("Nom du vétérinaire :");
        String date = this.input.InputString("Date du rdv :");
        String heure = this.input.InputString("Heure du rdv :");
        
        String horaire = date + " " + heure;
        
        IsExistingRdv rdv_exists = new IsExistingRdv();
        
        if (rdv_exists.IsExistingRdvPgsql(nom_client, horaire)) {
            // System.out.println("Rendez vous trouvé !");
            
            String request = "DELETE FROM rendezvous"
                    + " WHERE rv_client = '" + nom_client + "' AND rv_debut = '" + horaire + "';";
            
            if (this.qm.ExecuteUpdate(request) == 1) {
                System.out.println("Rendez-vous supprimé avec succès.");
                
                // On renseigne l'annulation
                
                String r = "INSERT INTO annulation (ann_client, ann_creneau, vet_id, ann_delai)\n" +
"	VALUES ('" + nom_client + "', '" + horaire + "',(SELECT vet_id FROM veterinaire WHERE vet_nom = '" + nom_veterinaire + "'),'" + heure + "'::time);";
                
                // Récupérer le premier sur la liste d'attente pour le créneau
                
                String query = "SELECT la_id, la_client, la_numTel, la_dateAuPlusTard, \n" +
                                "la_dateDemande, la_creneauPropose\n" +
                                "FROM listeAttente\n" +
                                "WHERE '" + date + "'::date <= la_dateAuPlusTard\n" +
                                "AND la_creneauPropose IS NULL\n" +
                                "ORDER BY la_dateDemande ASC\n" +
                                "LIMIT 1";
                
                ResultSet rs = this.qm.ExecuteQuery(query);
                
                if (rs.next()) {
                    query = "BEGIN;\n" +
                            "UPDATE listeAttente \n" +
                            "	SET la_creneauPropose = '" + horaire + "'\n" +
                            "WHERE la_id = " + rs.getString(1) + "\n" +
                            "SELECT * FROM listeAttente;\n" +
                            "ROLLBACK;";
                    
                    this.qm.ExecuteUpdate(query);
                }
                
                
                
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

    @Override
    public void PrintWaitList() throws SQLException {
        String query = "SELECT la_client, la_numTel, la_dateAuPlusTard, \n" +
                        "la_dateDemande, la_creneauPropose, vet_nom\n" +
                        "FROM listeAttente\n" +
                        "	LEFT JOIN disponibilite\n" +
                        "		ON dis_jour = EXTRACT('DOW' FROM la_creneauPropose)\n" +
                        "	LEFT JOIN veterinaire\n" +
                        "		ON veterinaire.vet_id = disponibilite.vet_id\n" +
                        "		AND NOT EXISTS(\n" +
                        "			SELECT 1 FROM rendezvous \n" +
                        "			WHERE vet_id = disponibilite.vet_id\n" +
                        "			AND la_creneauPropose = rv_debut)\n" +
                        "WHERE la_creneaupropose is NULL \n" +
                        "OR vet_nom IS NOT NULL;";
        
        ResultSet result = this.qm.ExecuteQuery(query);
        
        while (result.next()) {
            System.out.println(result.getString(1) + " contacter au " + result.getString(2) + " Pour le " + result.getString(3) + " au " + result.getString(4) + " creneau " + result.getString(5)+ " avec " + result.getString(5) );
        }
                
             
    }
    
}
