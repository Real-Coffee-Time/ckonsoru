/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dbmanager;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author adech
 */
public class QueryManagerPgsql implements QueryManagerDAO {
    
    @Override
    public ResultSet ExecuteQuery(String query) throws SQLException {
        ConnectionManager c = ConnectionManager.GetInstance();
        
        c.Connect();
        
        PreparedStatement statement = c.connection.prepareStatement(query);
        ResultSet result = statement.executeQuery();
      
        c.Disconnect();
        
        return result;
    }
    
    @Override
    public Integer ExecuteUpdate(String query) throws SQLException {
        ConnectionManager c = ConnectionManager.GetInstance();
        
        c.Connect();
        
        PreparedStatement statement = c.connection.prepareStatement(query);
        Integer result = statement.executeUpdate();
        
        c.Disconnect();
        
        return result;
    }
    
}
