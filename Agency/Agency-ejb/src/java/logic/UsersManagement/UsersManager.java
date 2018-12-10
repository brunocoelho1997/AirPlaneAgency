/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logic.UsersManagement;

import java.util.ArrayList;
import java.util.List;
import javax.ejb.Singleton;

/**
 *
 * @author bruno
 */
@Singleton
public class UsersManager implements UsersManagerLocal {

    private List<User> usersList;

    public UsersManager() {
        this.usersList = new ArrayList();
        this.usersList.add(new User("admin", "admin"));
    }
    
    
    
    @Override
    public boolean signIn(String username, String password) {
        User user = getUserByUsername(username);
        
        if(user == null)
            return false;
        
        if(!user.getPassword().equals(password))
            return false;
        
        return true;
    }

    @Override
    public boolean signUp(String username, String password, String passwordConfirmation, String name) {
        
        User useraux = getUserByUsername(username);
        
        if(useraux!=null)
            return false;
        
        if(!password.equals(passwordConfirmation))
            return false;
        
        User newUser = new Client(username, password, name);
        this.usersList.add(newUser);
        return true;
    }
    
    
    //Auxiliary methoths
    
    private User getUserByUsername(String username)
    {
        for(User user : usersList)
        {
            if(user.getUsername().equals(username))
                return user;
        }
        return null;
    }
}
