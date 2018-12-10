package logic;

import javax.ejb.Remote;

@Remote
public interface AgencyManagerRemote {
    boolean signIn(String username, String password);
}
