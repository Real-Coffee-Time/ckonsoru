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
public class Rendezvous {
    public Integer rdv_id, veto_id;
    public String debut, client;

    public Rendezvous(Integer rdv_id, Integer veto_id, String debut, String client) {
        this.rdv_id = rdv_id;
        this.veto_id = veto_id;
        this.debut = debut;
        this.client = client;
    }

    @Override
    public String toString() {
        return this.client + " le " + this.debut;
    }
    
}
