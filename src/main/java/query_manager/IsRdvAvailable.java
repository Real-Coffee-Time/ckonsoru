/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package query_manager;

import dbmanager_pgsql.QueryManager;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author adech
 */
public class IsRdvAvailable {
    public QueryManager qm = new QueryManager();
    
    public boolean IsRdvAvailablePgsql(String nom_veterinaire, String horaire) throws SQLException {
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
                if (nom_veterinaire.equals(result.getString(1)) && horaire.equals(result.getString(2)) ) {
                    return true;
                }
        }
        return false;
    }
}
