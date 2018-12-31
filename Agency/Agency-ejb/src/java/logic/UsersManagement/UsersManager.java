    /*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logic.UsersManagement;

import javax.ejb.EJB;
import javax.ejb.Singleton;
import logic.Config;
import logic.TUserDTO;

/**
 *
 * @author bruno
 */
@Singleton
public class UsersManager implements UsersManagerLocal {

    @EJB
    TUserFacadeLocal userFacade;
    
    public UsersManager() {      
        
    }
    
    @Override
    public boolean signIn(String username, String password) {
        TUser user = getTUserByUsername(username);
        
        if(user == null)
            return false;
        
        if(!user.getPassword().equals(password))
            return false;
        
        return true;
    }
    
    @Override
    public boolean signUp(TUserDTO userDTO) {
        
        verifyIfAdminExist();
        
        TUser useraux = getTUserByUsername(userDTO.getUsername());
        
        if(useraux!=null)
            return false;
        
        if(userDTO.getUsertype() != Config.CLIENT && userDTO.getUsertype() != Config.OPERATOR)
            return false;
        
        TUser newUser = new TUser();
        
        newUser.setUsername(userDTO.getUsername());
        newUser.setPassword(userDTO.getPassword());
        newUser.setUsertype(userDTO.getUsertype());
        newUser.setAccepted(false);
        
        if(userDTO.getUsertype()==Config.CLIENT)
        {
            newUser.setBalance(userDTO.getBalance());
            newUser.setClientname(userDTO.getClientName());
        }
        
        userFacade.create(newUser);
        return true;
    }
    
    @Override
    public boolean acceptTUser(TUserDTO userDTO, String username) {
        TUser user = getTUserByUsername(userDTO.getUsername());
        if(user == null)
            return false;
        user.setAccepted(true);
        return true;
        
    }
    
    @Override
    public TUserDTO getTUserDTO(String username) {
        TUser user = getTUserByUsername(username);
        return getTUserDTOFromTUser(user);
    }
    
    
    @Override
    public TUser getTUserByUsername(String usernameOfWantedUser){
        for(TUser user : userFacade.findAll())
        {
            if(user.getUsername().equals(usernameOfWantedUser))
                return user;
        }
        return null;
    }
    
    //Auxiliary methoths
    
    private TUserDTO getTUserDTOFromTUser(TUser user){
        TUserDTO tUserDTO = new TUserDTO();
        tUserDTO.setUsername(user.getUsername());
        tUserDTO.setPassword(user.getPassword());
        tUserDTO.setAccepted(user.getAccepted());
        tUserDTO.setUsertype(user.getUsertype());
        
        if(user.getUsertype() == Config.CLIENT)
        {
            tUserDTO.setBalance(user.getBalance());
            tUserDTO.setClientName(user.getClientname());
        }
        return tUserDTO;
    }

    //TODO: este metodo nao esta' a ser chamado. Tinha no construtor e bugava
    private void verifyIfAdminExist() {
        if(getTUserByUsername("admin")==null)
        {
            TUser admin = new TUser();
            admin.setAccepted(true);
            admin.setUsername("admin");
            admin.setPassword("admin");
            admin.setUsertype(Config.OPERATOR);
            
            userFacade.create(admin);
        }
}
}
