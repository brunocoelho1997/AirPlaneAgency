/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logic;

import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ws.rs.Produces;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import logic.websearch.Flight;
import logic.websearch.FlightManagerLocal;

/**
 * REST Web Service
 */
@Path("flight")
@Stateless
public class FlightResource {
    
    @EJB
    private FlightManagerLocal flightManager;

    public FlightResource() {
        // Do nothing
    }
    
    @GET
    @Path("/listall")
    @Produces(MediaType.APPLICATION_XML)
    public GenericEntity<List<Flight>> getAllFlights() {
        List<Flight> flights = flightManager.getAllFlights();
        GenericEntity<List<Flight>> list = new GenericEntity<List<Flight>>(flights) {};
        return list;
    }

    @GET
    @Path("/list")
    @Produces(MediaType.APPLICATION_XML)
    public GenericEntity<List<Flight>> getFlights(@QueryParam("origin") String origin, @QueryParam("destiny") String destiny) {
        System.out.println("[FlightResource] origin=" + origin + ", destiny=" + destiny);
        List<Flight> flights = flightManager.getFlights(origin, destiny);
        GenericEntity<List<Flight>> list = new GenericEntity<List<Flight>>(flights) {};
        return list;
    }
}