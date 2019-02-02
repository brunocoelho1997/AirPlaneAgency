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
import logic.TPlaceDTO;
import logic.TripsManagement.TripsManagerLocal;

/**
 *
 * @author bruno
 */
@Named("tripsController")
@SessionScoped
public class TripsController implements Serializable {
    
    @EJB
    TripsManagerLocal tripsManager; 
    
    private String username;

        
    public TripsController() {
        // Do nothing
    }
    
    public List<TPlaceDTO> findAllPlaces(){
        List<TPlaceDTO> places = tripsManager.findAllPlaces(username);
        for (TPlaceDTO place : places) {
            System.out.println(place);
        }
        
        return places;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
    
}
