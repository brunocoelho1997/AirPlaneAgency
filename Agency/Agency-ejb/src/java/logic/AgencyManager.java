package logic;

import javax.ejb.EJB;
import javax.ejb.Stateful;
import logic.UsersManagement.UsersManagerLocal;

@Stateful
public class AgencyManager implements AgencyManagerRemote {

    @EJB
    UsersManagerLocal usersManagerLocal;
    
    String username;

    @Override
    public boolean signIn(String username, String password) {
        boolean result = usersManagerLocal.signIn(username, password);
        //if the user logged with success username var has is username, otherwise username var is null or empty
        if(result)
            this.username = username;
        return result;
    }

    @Override
    public boolean signUp(String username, String password, String confirmPassword, String name) {
        return usersManagerLocal.signUp(username, password, confirmPassword, name);
    }
}
