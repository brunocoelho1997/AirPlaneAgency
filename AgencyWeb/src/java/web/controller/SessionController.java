/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package web.controller;

import java.io.Serializable;
import javax.annotation.ManagedBean;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.Dependent;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.ConfigurableNavigationHandler;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ComponentSystemEvent;
import javax.servlet.http.HttpSession;
import logic.Config;
import logic.SignInValue;
import static logic.SignInValue.SUCCESS;
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
	
    private String password;
    private String passwordConfirmation;
    private String username;
    private String clientName;
    private Boolean isLogged;
    
    @EJB
    private UsersManagerLocal userManager;
    
    public SessionController() {
    }
    
    
    public String processSignIn() {
        //session source: https://stackoverflow.com/questions/3841361/jsf-http-session-login
        
        FacesContext context = FacesContext.getCurrentInstance();
        SignInValue value = userManager.signIn(username, password);
        
        if(value.equals(SUCCESS)){
            
            context.getExternalContext().getSessionMap().put("user", username);
            
            this.isLogged = true;
            
            if(userManager.getTUserDTO(username).getUsertype() == Config.CLIENT)
                return "indexClient?faces-redirect=true";
            else
                return "indexOperator?faces-redirect=true";
        }
        else
        {
            String errorMsg = Utils.getSignUpValueString(value, username);
            
            FacesContext.getCurrentInstance().addMessage(
                                "myForm:errorMessage",
                                new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                                errorMsg,
                                                null));
            return "signin";
        }
    }

    public String processSignUp() {
        
        TUserDTO userDTO = new TUserDTO();
        userDTO.setClientName(clientName);
        userDTO.setUsername(username);
        
        userDTO.setPassword(password);
        
        
        
        
        //need to confirm if the password is equals to passwordConfirmation
        //if user can signup return to index and send a message that user need be accepted to signin
        //if user can't signup return to index and send a message that user has wrong (to the same page... how?)
        
        
        /*
            SignInValue value = userManager.signUp()
        
        if(value.equals(SUCCESS)){
            
            
            
            this.isLogged = true;
            
            if(userManager.getTUserDTO(user).getUsertype() == Config.CLIENT)
                return "indexClient?faces-redirect=true";
            else
                return "indexOperator?faces-redirect=true";
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
        */
        return "index?faces-redirect=true";

    }
    
    public String processLogout() {
        FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
        return "index?faces-redirect=true";
    }
    
    public void validateOperatorPermissions(ComponentSystemEvent event){
				
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
    
    public void validateClientPermissions(ComponentSystemEvent event){
				
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
    
    public boolean isOperator(ComponentSystemEvent event){
				
	FacesContext fc = FacesContext.getCurrentInstance();
	
        String username = (String) fc.getExternalContext().getSessionMap().get("user");
        
        TUserDTO userDTO = userManager.getTUserDTO(username);
        
        if(userDTO == null && userDTO.getUsertype() == Config.OPERATOR)
        {
            return true;
        }
        return false;
    }
    public boolean isClient(ComponentSystemEvent event){
				
	FacesContext fc = FacesContext.getCurrentInstance();
	
        String username = (String) fc.getExternalContext().getSessionMap().get("user");
        
        TUserDTO userDTO = userManager.getTUserDTO(username);
        
        if(userDTO == null && userDTO.getUsertype() == Config.CLIENT)
        {
            return true;
        }
        return false;
    }

    
    //getters and setters

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPasswordConfirmation() {
        return passwordConfirmation;
    }

    public void setPasswordConfirmation(String passwordConfirmation) {
        this.passwordConfirmation = passwordConfirmation;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public Boolean getIsLogged() {
        return isLogged;
    }

    public void setIsLogged(Boolean isLogged) {
        this.isLogged = isLogged;
    }
    
    
    
    
}
