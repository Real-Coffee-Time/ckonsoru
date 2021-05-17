/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ckonsoru;

import ihm.*;
import java.sql.SQLException;
import java.util.ArrayList;

import java.util.Properties;
import query_manager.*;

/**
 * Launch the App
 * @author julie.jacques
 */
public class App {
    
    public static void main(String args[]) throws SQLException{
        
        System.out.println("Bienvenue sur Clinique Konsoru !");
        
        // chargement de la configuration de la persistence
        ConfigLoader cf = new ConfigLoader();
        Properties properties = cf.getProperties();
        System.out.println("Mode de persistence : "
                +properties.getProperty("persistence"));      
              
        
        // Managers ihm pour l'utilisateur
        // Input main_input = new Input();
        Output main_output = new Output();
        
        // Init menu content
        String menu_header = "Actions Disponibles :";
        
        ArrayList<String> choices = new ArrayList();
        choices.add("1 - Afficher les créneaux disponibles pour une date donnée");
        choices.add("2 - Lister les rdv passés, présents et à venir d'un client");
        choices.add("3 - Prendre un rdv");
        choices.add("4 - Supprimer un rdv");
        choices.add("5 - Liste d'attente");
        choices.add("8 - Liste des annulations");
        choices.add("9 - Quitter");
        
        RdvDAO rdv_dao = null;
        
        if (properties.getProperty("persistence").equals("xml")) {
            rdv_dao = new RdvXml();
        } else {
            rdv_dao = new RdvPgsql();
        }
        Menu main_menu = new Menu(menu_header, choices, rdv_dao);
        
        Integer user_action;
        do {
            user_action = main_menu.ManageUserChoice(); // Retourne le choix de l'utilisateur et gère les actions
        } while(user_action != 9);
        
        // Fin du programme
        main_output.Output("Au revoir !");
        
    }
    
}
