/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logic.websearch;

import logic.TPlaceDTO;
import logic.TTripDTO;

public class FlightUtils {
    
    private FlightUtils() {
        // Do nothing.
    }
    
    public static Flight createFlightFromTrip(TTripDTO trip) {
        TPlaceDTO origin = trip.getFromPlaceDTO();
        TPlaceDTO destiny = trip.getFromPlaceDTO();
        
        Flight flight = new Flight(trip.getPrice(), 0, origin.getCity(), destiny.getCity());
        return flight;
    }
    
    public static boolean isTripActive(TTripDTO trip) {
        return !trip.getDone() && !trip.getCanceled();
    }
}
