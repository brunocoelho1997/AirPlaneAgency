/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package web.controller;

import java.io.Serializable;
import java.util.List;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.ConfigurableNavigationHandler;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ComponentSystemEvent;
import logic.Config;
import logic.SignInValue;
import static logic.SignInValue.SUCCESS;
import logic.TTripDTO;
import logic.TUserDTO;
import logic.TripsManagement.TripsManagerLocal;
import logic.UsersManagement.UsersManagerLocal;
import web.util.Utils;

/**
 *
 * @author bruno
 */

@Named("sessionController")
@SessionScoped
public class SessionController implements Serializable {

    private static final long serialVersionUID = 1094801825228386363L;
	
    private String pwd;
    private String user;

    @EJB
    private UsersManagerLocal userManager;
    
    @EJB
    private TripsManagerLocal tripsManager;
    
    public SessionController() {
    }
    
    
    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }
    
    public String processSignIn() {
        //session source: https://stackoverflow.com/questions/3841361/jsf-http-session-login
        
        FacesContext context = FacesContext.getCurrentInstance();
        SignInValue value = userManager.signIn(user, pwd);
        
        if(value.equals(SUCCESS)){
            
            context.getExternalContext().getSessionMap().put("user", user);
            
            if(userManager.getTUserDTO(user).getUsertype() == Config.CLIENT)
                return "ClientPages/index?faces-redirect=true";
            else
                return "OperatorPages/index?faces-redirect=true";
        }
        else
        {
            String errorMsg = Utils.getSignUpValueString(value, user);
            
            FacesContext.getCurrentInstance().addMessage(
                                "myForm:errorMessage",
                                new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                errorMsg,
                                                null));
            return "signin";
        }
    }

    public String processSignUp() {
        return "signin";
    }
    
    public String processLogout() {
        FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
        return "index?faces-redirect=true";
    }
    
    public void isOperator(ComponentSystemEvent event){
				
	FacesContext fc = FacesContext.getCurrentInstance();
	
        String username = (String) fc.getExternalContext().getSessionMap().get("user");
        
        TUserDTO userDTO = userManager.getTUserDTO(username);
        
        if(userDTO == null || userDTO.getUsertype() != Config.OPERATOR)
        {
            ConfigurableNavigationHandler nav 
		   = (ConfigurableNavigationHandler) 
			fc.getApplication().getNavigationHandler();
		
		nav.performNavigation("/signin");
        }
        	
    }	
    
    public void isClient(ComponentSystemEvent event){
				
	FacesContext fc = FacesContext.getCurrentInstance();
	
        String username = (String) fc.getExternalContext().getSessionMap().get("user");
        
        TUserDTO userDTO = userManager.getTUserDTO(username);
        
        if(userDTO == null || userDTO.getUsertype() != Config.CLIENT)
        {
            ConfigurableNavigationHandler nav 
		   = (ConfigurableNavigationHandler) 
			fc.getApplication().getNavigationHandler();
		
		nav.performNavigation("/signin");
        }	
    }
    
    public List<TTripDTO> getNextTrips() {        
        return tripsManager.getActiveTripsByUser(user);
    }
    
    public int getNumberOfSeats(TTripDTO trip) {
        return tripsManager.getNoOfSeatsFromTripByUser(user, trip);
    }
}
