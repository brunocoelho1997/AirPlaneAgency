package logic;

import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateful;
import logic.TripsManagement.TripsManagerLocal;
import logic.UsersManagement.UsersManagerLocal;

/*

SQL COMMANDS


-Plane

drop sequence if exists plane_seq;

create sequence plane_seq;

drop table if exists t_plane;

create table t_plane(
    id int primary key default nextval('plane_seq'),
    planename varchar(30) not null,
    planelimit int not null default 50
);


*/

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
    public boolean signUp(String username, String password, String confirmPassword, String name) {
        return usersManagerLocal.signUp(username, password, confirmPassword, name);
    }

    
    //Planes
    @Override
    public List<TPlaneDTO> findAll() {
        return tripsManagerLocal.findAll();
    }
    
    @Override
    public TPlaneDTO findPlane(int id) {
        return tripsManagerLocal.findPlane(id);
    }

    @Override
    public boolean addPlane(TPlaneDTO planeDTO) {
        return tripsManagerLocal.addPlane(planeDTO);
    }

    @Override
    public boolean editPlane(TPlaneDTO planeDTO) {
        return tripsManagerLocal.editPlane(planeDTO);
    }

    @Override
    public boolean removePlane(TPlaneDTO planeDTO) {
        return tripsManagerLocal.removePlane(planeDTO);
    }

    
}
