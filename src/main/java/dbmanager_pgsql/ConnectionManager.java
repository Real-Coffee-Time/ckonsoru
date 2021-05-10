/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dbmanager_pgsql;

import java.sql.*;

/**
 *
 * @author adech
 */
public class ConnectionManager {
    public Connection connection;
    public String jdbcUrl, username, password;

    public ConnectionManager() {
        this.jdbcUrl = "jdbc:postgresql://localhost:5432/ckonsoru";
        this.username = "postgres";
        this.password = "Clar1nette";
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
    
}