/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dbmanager;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author adech
 */
public interface QueryManagerDAO {
    public ResultSet ExecuteQuery(String query) throws SQLException;
    public Integer ExecuteUpdate(String query) throws SQLException;
}
