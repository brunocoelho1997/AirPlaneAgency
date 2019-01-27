/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package web.controller;

import java.io.Serializable;
import java.util.List;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import logic.TTripDTO;
import logic.TripsManagement.TripsManagerLocal;

@Named("welcomeController")
@SessionScoped
public class WelcomeController implements Serializable {
    
    @EJB
    TripsManagerLocal tripsManager; 
    
    public WelcomeController() {
        // Do nothing
    }
    
    public String signIn() {
        // TODO not working. Also, validation missing
        return "main";
    }
    
    public List<TTripDTO> getCheapestTrips() {
        List<TTripDTO> trips = tripsManager.getCheapeastTrips(5);
        for (TTripDTO trip : trips) {
            System.out.println(trip);
        }
        
        return trips;
    }
    
    public int getAvailableSeats(TTripDTO trip) {
        return tripsManager.getAvailableSeats(trip);
    }
}
