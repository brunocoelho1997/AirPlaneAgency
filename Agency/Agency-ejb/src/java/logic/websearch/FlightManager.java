/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logic.websearch;

import javax.ejb.EJB;
import javax.ejb.Singleton;
import logic.TPlaceDTO;
import logic.TTripDTO;
import logic.TripsManagement.TripsManagerLocal;


@Singleton
public class FlightManager implements FlightManagerLocal {
    
    @EJB
    private TripsManagerLocal tripsManager;
    
    public FlightManager() {
        // Do nothing
    }

    @Override
    public Flight getFlight(String origin) {
        System.out.println("[FlightManager] getFlight. origin=" + origin);
        
        for (TTripDTO trip : tripsManager.findAllTrips()) {
            TPlaceDTO fromPlace = trip.getFromPlaceDTO();
            TPlaceDTO toPlaceDTO = trip.getToPlaceDTO();
            
            if (origin.compareToIgnoreCase(fromPlace.getCity()) == 0) {
                Flight flight = new Flight(0, 0, fromPlace.getCity(), toPlaceDTO.getCity());
                System.out.println("[FlightManager] getFlight. return=" + flight);
                return flight;
            }
        }
        
        System.out.println("[FlightManager] getFlight. no results found");
        return null;
    }
}