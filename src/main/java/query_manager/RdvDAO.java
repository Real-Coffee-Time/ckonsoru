/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package query_manager;

import items_manager.Rendezvous;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author adech
 */
public interface RdvDAO {
    public ArrayList<Rendezvous> GetAllRdv() throws SQLException;
    public ArrayList<Rendezvous> GetClientRdv() throws SQLException;
    public boolean TakeAppointement() throws SQLException;
    public boolean DeleteAppointement() throws SQLException;
    public ArrayList<Rendezvous> GetCanceledRdv() throws SQLException;
    public void PrintWaitList() throws SQLException;
}
