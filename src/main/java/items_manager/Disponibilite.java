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
public class Disponibilite {
    public Integer dispo_id, veto_id;
    public String heure_debut, heure_fin, jour;

    public Disponibilite(Integer dispo_id, Integer veto_id, String heure_debut, String heure_fin, String jour) {
        this.dispo_id = dispo_id;
        this.veto_id = veto_id;
        this.heure_debut = heure_debut;
        this.heure_fin = heure_fin;
        this.jour = jour;
    }

    @Override
    public String toString() {
        return this.heure_debut + " -> " + this.heure_fin + " le " + this.jour ;
    }
    
}
