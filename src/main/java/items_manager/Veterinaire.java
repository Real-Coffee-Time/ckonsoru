/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package items_manager;

/**
 *
 * @author adech
 */
public class Veterinaire {
    public Integer id_veterinaire;
    public String nom;

    public Veterinaire(Integer id_veterinaire, String nom) {
        this.id_veterinaire = id_veterinaire;
        this.nom = nom;
    }

    @Override
    public String toString() {
        return this.nom;
    }
    
    
}
