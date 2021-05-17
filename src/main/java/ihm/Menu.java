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
    public RdvDAO rdv_dao;

    public Menu(String header, ArrayList<String> choices, RdvDAO rdv_dao) {
        this.header = header;
        this.choices = choices;
        this.rdv_dao = rdv_dao;
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
                this.rdv_dao.GetAllRdv();
                break;
                
            case 2:
                this.output.Output("Liste des rendez-vous par clients");
                this.rdv_dao.GetClientRdv();
                break;
                
            case 3:
                this.output.Output("Prendre un rendez-vous");
                this.rdv_dao.TakeAppointement();
                break;
                
            case 4:
                this.output.Output("Supprimer un rendez-vous");
                this.rdv_dao.DeleteAppointement();
                break;

            case 8 : 
                this.output.Output("Liste des rendez-vous annulés");
                this.rdv_dao.GetCanceledRdv();
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
