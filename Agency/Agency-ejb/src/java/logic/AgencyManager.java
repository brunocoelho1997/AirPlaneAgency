package logic;

import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateful;
import logic.TripsManagement.TripsManagerLocal;
import logic.UsersManagement.UsersManagerLocal;



@Stateful
public class AgencyManager implements AgencyManagerRemote {

    @EJB
    UsersManagerLocal usersManagerLocal;
    @EJB
    TripsManagerLocal tripsManagerLocal;
    
    String username;

    //users
    @Override
    public boolean signIn(String username, String password) {
        boolean result = usersManagerLocal.signIn(username, password);
        //if the user logged with success username var has is username, otherwise username var is null or empty
        if(result)
            this.username = username;
        return result;
    }
    @Override
    public boolean signUp(TUserDTO userDTO) {
        return usersManagerLocal.signUp(userDTO);
    }

    @Override
    public boolean acceptUser(TUserDTO userDTO) {
        return usersManagerLocal.acceptTUser(userDTO, username);
    }

    
    //Planes
    @Override
    public List<TPlaneDTO> findAllPlanes() throws NoPermissionException{
        return tripsManagerLocal.findAllPlanes(username);
    }
    
    @Override
    public TPlaneDTO findPlane(int id) {
        return tripsManagerLocal.findPlane(id, username);
    }

    @Override
    public boolean addPlane(TPlaneDTO planeDTO) {
        return tripsManagerLocal.addPlane(planeDTO, username);
    }

    @Override
    public boolean editPlane(TPlaneDTO planeDTO) {
        return tripsManagerLocal.editPlane(planeDTO, username);
    }

    @Override
    public boolean removePlane(TPlaneDTO planeDTO) {
        return tripsManagerLocal.removePlane(planeDTO, username);
    }

    
    
    
}
