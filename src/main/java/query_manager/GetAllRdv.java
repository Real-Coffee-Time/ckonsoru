/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package query_manager;
import dbmanager_pgsql.*;
import items_manager.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author adech
 */
public class GetAllRdv {
    public QueryManager qm = new QueryManager();
    
    public ArrayList<Rendezvous> GetAllRdvPgsql() throws SQLException {
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
    
}
