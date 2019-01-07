   /*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logic.UsersManagement;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Singleton;
import logic.Config;
import logic.DTOFactory;
import logic.TUserDTO;

/**
 *
 * @author bruno
 */
@Singleton
public class UsersManager implements UsersManagerLocal {

    @EJB
    TUserFacadeLocal userFacade;
    
    @PostConstruct
    private void init() {
        verifyIfAdminExist();
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
        newUser.setBalance((double) 0);
        newUser.setTPurchaseCollection(new ArrayList());
        newUser.setTSeatCollection(new ArrayList());
        
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
        return DTOFactory.getTUserDTOFromTUser(user);
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
    
    @Override
    public boolean depositToAccount(float amount, String username) {
        TUser user = getTUserByUsername(username);
        
        if(user == null)
            return false;
        
        if(amount <= 0)
            return false;
        
        user.setBalance(user.getBalance() + amount);
        userFacade.edit(user);
        
        return true;
        
    }
    
    @Override
    public List<TUserDTO> findAllUsers() {
        List<TUserDTO> userList = new ArrayList();
        
        for(TUser user : userFacade.findAll())
        {
            userList.add(DTOFactory.getTUserDTOFromTUser(user));
        }
        return userList;
    }
    
    
    //Auxiliary methoths

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

    @Override
    public boolean editTUser(TUser userTmp) {
        if(userTmp == null)
            return false;
        userFacade.edit(userTmp);
        return true;
    }
    
}