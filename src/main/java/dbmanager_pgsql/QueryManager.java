/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dbmanager_pgsql;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author adech
 */
public class QueryManager {
    
    public ResultSet ExecuteQuery(String query) throws SQLException {
        ConnectionManager c = new ConnectionManager();
        
        c.Connect();
        
        PreparedStatement statement = c.connection.prepareStatement(query);
        ResultSet result = statement.executeQuery();
        
        c.Disconnect();
        
        return result;
    }
    
    public Integer ExecuteUpdatePgsql(String query) throws SQLException {
        ConnectionManager c = new ConnectionManager();
        
        c.Connect();
        
        PreparedStatement statement = c.connection.prepareStatement(query);
        Integer result = statement.executeUpdate();
        
        c.Disconnect();
        
        return result;
    }
    
}
