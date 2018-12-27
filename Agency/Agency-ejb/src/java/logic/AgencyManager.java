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
    public TUserDTO getTUserDTO(String username) {
        return usersManagerLocal.getTUserDTO(username);
    }

    @Override
    public boolean acceptUser(TUserDTO userDTO) {
        return usersManagerLocal.acceptTUser(userDTO, username);
    }

    @Override
    public boolean logout() {
        this.username = null;
        return true;
    }
    
    //Planes
    @Override
    public List<TPlaneDTO> findAllPlanes() throws NoPermissionException{
        return tripsManagerLocal.findAllPlanes(username);
    }
    
    @Override
    public TPlaneDTO findPlane(int id) throws NoPermissionException {
        return tripsManagerLocal.findPlane(id, username);
    }

    @Override
    public boolean addPlane(TPlaneDTO planeDTO) throws NoPermissionException {
        return tripsManagerLocal.addPlane(planeDTO, username);
    }

    @Override
    public boolean editPlane(TPlaneDTO planeDTO) throws NoPermissionException {
        return tripsManagerLocal.editPlane(planeDTO, username);
    }

    @Override
    public boolean removePlane(TPlaneDTO planeDTO) throws NoPermissionException {
        return tripsManagerLocal.removePlane(planeDTO, username);
    }

    @Override
    public List<TAirlineDTO> findAllAirlines() throws NoPermissionException {
        return tripsManagerLocal.findAllAirlines(username);
    }

    @Override
    public TAirlineDTO findAirline(int id) throws NoPermissionException {
        return tripsManagerLocal.findAirline(id, username);
    }

    @Override
    public boolean addAirline(TAirlineDTO airlineDTO) throws NoPermissionException {
        return tripsManagerLocal.addAirline(airlineDTO, username);
    }

    @Override
    public boolean editAirline(TAirlineDTO airlineDTO) throws NoPermissionException {
        return tripsManagerLocal.editAirline(airlineDTO, username);
    }

    @Override
    public boolean removeAirline(TAirlineDTO airlineDTO) throws NoPermissionException {
        return tripsManagerLocal.removeAirline(airlineDTO, username);
    }

    
    

    
    
    
}
