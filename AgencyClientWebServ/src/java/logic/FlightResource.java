/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logic;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ws.rs.Produces;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
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
    @Path("{origin}")
    @Produces(MediaType.APPLICATION_XML)
    public Flight getFlight(@PathParam("origin") String origin) {
        return flightManager.getFlight(origin);
    }
}