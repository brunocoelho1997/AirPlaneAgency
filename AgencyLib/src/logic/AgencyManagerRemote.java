package logic;

import javax.ejb.Remote;

@Remote
public interface AgencyManagerRemote {
    boolean signIn(String username, String password);
    boolean signUp(String username, String password, String passwordConfirmation, String name);

}
