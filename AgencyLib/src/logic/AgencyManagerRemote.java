package logic;

import java.util.List;
import javax.ejb.Remote;

@Remote
public interface AgencyManagerRemote {
    
    //users
    boolean signIn(String username, String password);
    boolean signUp(String username, String password, String passwordConfirmation, String name);

    //planes
    List<TPlaneDTO> findAll();
    TPlaneDTO findPlane(int id);
    boolean addPlane(TPlaneDTO planeDTO);
    boolean editPlane(TPlaneDTO planeDTO);
    boolean removePlane(TPlaneDTO planeDTO);
    
}
