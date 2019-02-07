/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package web.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.el.ValueExpression;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;
import javax.inject.Named;
import logic.Config;
import logic.NoPermissionException;
import logic.TAirlineDTO;
import logic.TPlaceDTO;
import logic.TPlaneDTO;
import logic.TSeatDTO;
import logic.TTripDTO;
import logic.TripsManagement.TripsManagerLocal;
import org.apache.taglibs.standard.tag.el.fmt.BundleTag;
import web.util.Utils;

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
    
    private TTripDTO tripDTOTemp = new TTripDTO();
  
    //for buying purchases
    private List<TSeatDTO> seatsToTrip;
    
    @PostConstruct
    private void init() {
        FacesContext ctx = FacesContext.getCurrentInstance();
        
        ValueExpression vex =
                ctx.getApplication().getExpressionFactory()
                        .createValueExpression(ctx.getELContext(),
                                "#{sessionController}", SessionController.class);

        SessionController sessionController = (SessionController)vex.getValue(ctx.getELContext());
        this.username = sessionController.getUsername();
        
        //System.out.println("\n\n\n\n USERNAME ON POSTCONTRUCT: " + username);
    }
    
    public TripsController() {
        // Do nothing
    }
    
    public List<TTripDTO> getAllTrips() throws NoPermissionException{
        List<TTripDTO> trips = tripsManager.findAllTrips();
        return trips;
    }
    
    public List<TPlaceDTO> getAllPlaces(){
        List<TPlaceDTO> places = tripsManager.findAllPlaces(username);
        for (TPlaceDTO place : places) {
            System.out.println(place);
        }
        
        return places;
    }
    
    public TPlaceDTO findPlace(int id){
        TPlaceDTO place = tripsManager.findPlace(id);
        return place;
    }
    
    public TAirlineDTO findAirline(int id) throws NoPermissionException{
        TAirlineDTO airline = tripsManager.findAirline(id, username);
        return airline;
    }
    
    public List<TAirlineDTO> getAllAirlines() throws NoPermissionException{
        List<TAirlineDTO> airlines = tripsManager.findAllAirlines(username);
        return airlines;
    }
    
    public TPlaneDTO findPlane(int id) throws NoPermissionException{
        TPlaneDTO plane = tripsManager.findPlane(id, username);
        return plane;
    }
    
    public TTripDTO findTrip(int id) {
        return tripsManager.findTrip(id);
    }
    
    public List<TPlaneDTO> getAllPlanes() throws NoPermissionException{
        List<TPlaneDTO> planes = tripsManager.findAllPlanes(username);
        return planes;
    }
    
    public String processAddTrip() {
        String message;
        boolean result;
        //System.out.println("\n\n\n\n USERNAME: " + username);
        //System.out.println("\n\n\n\n TRIP: " + tripDTOTemp);

        try {
            result = tripsManager.addTrip(tripDTOTemp, username);
            
            
            if(!result)
            {
                message = "error TODO improve this message";
                Utils.throwMessage(message);
                return "addTrip";

            }
            else
            {
                message = "Sign up with sucess! Now you can sign in, after operator approval " + tripDTOTemp + ".";
                Utils.throwMessage(message);
                tripDTOTemp = new TTripDTO();
                return "indexTrips";
            }
        
        } catch (NoPermissionException ex) {
            message = "Error occoured: " + ex;
            Utils.throwMessage(message);
            return "addTrip";
        }
        
    }
    
    public String buySeatsToTrip(TTripDTO trip) throws NoPermissionException {
	    
        this.tripDTOTemp = trip;
        seatsToTrip = new ArrayList();

	return "buySeatsToTrip";
    }
    
    public String refreshSeatsToTrip(ValueChangeEvent e) throws NoPermissionException {
	seatsToTrip = new ArrayList();
        int nSeats = Integer.parseInt(e.getNewValue().toString());
        
        while(nSeats > 0)
        {
            seatsToTrip.add(new TSeatDTO());
            nSeats--;
        }
	return null;
    }
    
    public List<TSeatDTO> getSeatsToTrip() throws NoPermissionException{
        if(seatsToTrip == null || seatsToTrip.isEmpty())
        {
            seatsToTrip = new ArrayList();
            seatsToTrip.add(new TSeatDTO());
        }
        
        return seatsToTrip;
    }
    
    public String processBuySeatsToTrip() {
        String message;
        boolean result;

        try {
            result = tripsManager.buySeatsToTrip(tripDTOTemp, seatsToTrip, username);
            
            
            if(!result)
            {
                message = "error TODO improve this message";
                Utils.throwMessage(message);
                return "buySeatsToTrip";

            }
            else
            {
                message = "Sign up with sucess! Now you can sign in, after operator approval " + tripDTOTemp + ".";
                Utils.throwMessage(message);
                tripDTOTemp = new TTripDTO();
                seatsToTrip = new ArrayList();
                return "indexTrips";
            }
        
        } catch (NoPermissionException ex) {
            message = "Error occoured: " + ex;
            Utils.throwMessage(message);
            return "indexTrips";
        }
        
    }
    
            
    
    public String processCancelTrip(TTripDTO trip) throws NoPermissionException {
	    
        tripsManager.cancelTrip(trip, username);
	return null;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public TTripDTO getTripDTOTemp() {
        return tripDTOTemp;
    }

    public void setTripDTOTemp(TTripDTO tripDTOTemp) {
        this.tripDTOTemp = tripDTOTemp;
    }

    

}
