/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logic.websearch;

import java.util.ArrayList;
import java.util.List;
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
    public List<Flight> getAllFlights() {
        System.out.println("[FlightManager] getAllFlights");
                
        List<Flight> flightsList = new ArrayList<>();
        for (TTripDTO trip : tripsManager.findAllTrips()) {
            if (FlightUtils.isTripActive(trip)) {
                flightsList.add(FlightUtils.createFlightFromTrip(trip));
            }
        }
        
        System.out.println("[FlightManager] getFlight. results found=" + flightsList.size());
        return flightsList;
    }
    
    @Override
    public List<Flight> getFlights(String origin) {
        System.out.println("[FlightManager] getFlight. origin=" + origin);
                
        List<Flight> flightsList = new ArrayList<>();
        for (TTripDTO trip : tripsManager.findAllTrips()) {
            if (FlightUtils.isTripActive(trip)) {
                TPlaceDTO from = trip.getFromPlaceDTO();
                
                if (from.getCity().compareToIgnoreCase(origin) == 0) {
                    flightsList.add(FlightUtils.createFlightFromTrip(trip));
                }
            }
        }
        
        System.out.println("[FlightManager] getFlight. results found=" + flightsList.size());
        return flightsList;
    }
}