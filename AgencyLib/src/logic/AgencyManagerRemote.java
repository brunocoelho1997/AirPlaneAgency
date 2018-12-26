package logic;

import java.util.List;
import javax.ejb.Remote;

@Remote
public interface AgencyManagerRemote {
    
    //users
    boolean signIn(String username, String password);
    boolean signUp(TUserDTO userDTO);
    boolean logout();
    boolean acceptUser(TUserDTO userDTO);
    
    //planes
    List<TPlaneDTO> findAllPlanes() throws NoPermissionException;
    TPlaneDTO findPlane(int id);
    boolean addPlane(TPlaneDTO planeDTO);
    boolean editPlane(TPlaneDTO planeDTO);
    boolean removePlane(TPlaneDTO planeDTO);
    
}
