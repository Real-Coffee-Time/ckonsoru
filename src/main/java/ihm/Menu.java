/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ihm;

import java.sql.SQLException;
import java.util.ArrayList;
import query_manager.*;

/**
 *
 * @author adech
 */
public class Menu {
    public String header;
    public ArrayList<String> choices;
    public Input input = new Input();
    public Output output = new Output();

    public Menu(String header, ArrayList<String> choices) {
        this.header = header;
        this.choices = choices;
    }
    
    public int ManageUserChoice() throws SQLException {
        this.output.Output(this.header);
        this.choices.forEach((String choice) -> {
            this.output.Output("\t" + choice);
        });
        
        Integer user_choice = this.input.InputInt("Votre action :");
        this.ManageAction(user_choice);
        
        return user_choice;
    }
    
    public void ManageAction(int user_choice) throws SQLException {
        switch (user_choice) {
            case 1:
                this.output.Output("Créneaux disponibles");
                GetAllRdv rdv = new GetAllRdv();
                rdv.GetAllRdvPgsql();
                break;
                
            case 2:
                this.output.Output("Liste des rendez-vous par clients");
                GetClientRdv c_rdv = new GetClientRdv();
                c_rdv.GetClientRdvPgsql();
                break;
                
            case 3:
                this.output.Output("Prendre un rendez-vous");
                TakeAppointement t_rdv = new TakeAppointement();
                t_rdv.TakeAppointementPgsql();
                break;
                
            case 4:
                this.output.Output("Supprimer un rendez-vous");
                DeleteAppointement d_rdv = new DeleteAppointement();
                d_rdv.DeleteAppointementPgsql();
                break;
                
            case 9:
                this.output.Output("Fin du programme");
                break;
                
            default:
                this.output.Output("N'est pas une action valide.");
                break;
        }
    }

    @Override
    public String toString() {
        return "Menu{" + "header=" + header + ", choices=" + choices + '}';
    }
}
