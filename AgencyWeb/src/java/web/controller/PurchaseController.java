package web.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.el.ValueExpression;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;
import javax.inject.Named;
import logic.NoPermissionException;
import logic.TPurchaseDTO;
import logic.TSeatDTO;
import logic.TTripDTO;
import logic.TripsManagement.TripsManagerLocal;
import web.util.Utils;


@Named("purchaseController")
@SessionScoped
public class PurchaseController implements Serializable {

    @EJB
    private TripsManagerLocal tripsManager;
    
    private String username;

    //for buying purchases
    private List<TSeatDTO> seatsToTrip;
        
    private TTripDTO tripDTOTemp = new TTripDTO();
  
    
    public PurchaseController() {
    }
    
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
    
    public Collection<TSeatDTO> getAtualPurchase() {
        
        try {
            TPurchaseDTO purchase = tripsManager.getActualPurchase(username);

            if (purchase == null) {
                return new ArrayList<>();
            }

            return purchase.gettSeatCollection();
        } catch (NoPermissionException ex) {
            return new ArrayList<>();
        }
    
    }
    public List<TPurchaseDTO> getAllMyPurchases() {
        try {
            List<TPurchaseDTO> purchases = tripsManager.findAllMyPurchases(username);
            
            return purchases;
        } catch (NoPermissionException ex) {
            return new ArrayList();
        }
    }
    public List<TPurchaseDTO> getAllPurchases() {
        try {
            List<TPurchaseDTO> purchases = tripsManager.findAllPurchases(username);
            
            return purchases;
        } catch (NoPermissionException ex) {
            return new ArrayList();
        }
    }
    
    public String finishActualPurchase() {
        boolean result;
        String msg; 
        
        try {
            TPurchaseDTO actualPurchase = tripsManager.getActualPurchase(username);
            
            if(actualPurchase == null)
            {
                msg = "A problem occurred. Your actual purchase is empty.";
                Utils.throwMessage(msg);
                return null;
            }
            
            result = tripsManager.finishActualPurchase(actualPurchase, username);
            if(!result)
                msg = "A problem occurred. The system didn't finished the actual purchase.";
            else
                msg = "You finished the actual purchase.";

        } catch (NoPermissionException ex) {
            msg = "Error: " + ex.getMessage();
        }
        Utils.throwMessage(msg);

	return null;
    }
    
    public String removeActualPurchase() {
        boolean result;
        String msg; 
        
        try {
            TPurchaseDTO actualPurchase = tripsManager.getActualPurchase(username);
            
            if(actualPurchase == null)
            {
                msg = "A problem occurred. Your actual purchase is empty.";
                Utils.throwMessage(msg);
                return null;
            }
            
            result = tripsManager.removeActualPurchase(actualPurchase, username);
            if(!result)
                msg = "A problem occurred. The system didn't removed the actual purchase.";
            else
                msg = "You removed the actual purchase.";

        } catch (NoPermissionException ex) {
            msg = "Error: " + ex.getMessage();
        }
        Utils.throwMessage(msg);

	return null;

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
        
        return seatsToTrip;
    }
    
    public String processBuySeatsToTrip() {
        String message;
        boolean result;

        try {
            
            if(seatsToTrip == null || seatsToTrip.isEmpty())
            {
                message = "Need to select a valid number of seats.";
                Utils.throwMessage(message);
                return null;
            }
                        
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
    
    public String buySeatsToTrip(TTripDTO trip) {
	    
        this.tripDTOTemp = trip; 
	return "buySeatsToTrip";
    }
}