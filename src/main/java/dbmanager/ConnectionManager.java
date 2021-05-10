/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dbmanager;

import ckonsoru.ConfigLoader;
import java.sql.*;
import java.util.Properties;

/**
 *
 * @author adech
 */
public class ConnectionManager {
    public Connection connection;
    private static ConnectionManager connection_manager;
    public String jdbcUrl, username, password;

    private ConnectionManager() {
        ConfigLoader cf = new ConfigLoader();
        Properties properties = cf.getProperties();
        this.jdbcUrl = properties.getProperty("bdd.url");           // "jdbc:postgresql://localhost:5432/ckonsoru";
        this.username = properties.getProperty("bdd.login");        // "Postgres"
        this.password = properties.getProperty("bdd.mdp");          // Not gonna throw my password for obvious reasons
    }

    public Connection Connect() {
        try {            
            this.connection = (Connection) DriverManager.getConnection(this.jdbcUrl, this.username, this.password);
            // System.out.println("Connection to database succeeded");
            
            return this.connection;
            
        } catch (SQLException ex) {
            System.out.println("Error occured while connecting to database : "+ex);
        }
        
        return null;
    }
    
    public Boolean Disconnect() {
        try {
            this.connection.close();
            // System.out.println("Fin de la connection avec la base de données.");
            return true;
        } catch (SQLException ex) {
            System.out.println("Error occurend while closing the database : "+ex);
        }
        return false;
    }
    
    public static ConnectionManager GetInstance() {
        if (ConnectionManager.connection_manager == null) {
            ConnectionManager.connection_manager = new ConnectionManager();
        }
        return ConnectionManager.connection_manager;
    }
}
