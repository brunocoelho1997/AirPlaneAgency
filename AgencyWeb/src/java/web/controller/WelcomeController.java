/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package web.controller;

import java.io.Serializable;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

@Named("welcomeController")
@SessionScoped
public class WelcomeController implements Serializable {
    
    public WelcomeController() {
        // Do nothing
    }
    
    public String signIn() {
        // TODO not working. Also, validation missing
        return "main";
    }
}