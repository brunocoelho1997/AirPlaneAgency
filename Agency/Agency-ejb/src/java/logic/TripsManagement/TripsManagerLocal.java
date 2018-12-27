/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logic.TripsManagement;

import java.util.List;
import javax.ejb.Local;
import logic.NoPermissionException;
import logic.TAirlineDTO;
import logic.TPlaneDTO;

/**
 *
 * @author bruno
 */
@Local
public interface TripsManagerLocal {
    
    //planes
    List<TPlaneDTO> findAllPlanes(String username) throws NoPermissionException;
    TPlaneDTO findPlane(int id, String username) throws NoPermissionException;
    boolean addPlane(TPlaneDTO planeDTO, String username) throws NoPermissionException;
    boolean editPlane(TPlaneDTO planeDTO, String username) throws NoPermissionException;
    boolean removePlane(TPlaneDTO planeDTO, String username) throws NoPermissionException;
    
    //Airline
    List<TAirlineDTO> findAllAirlines(String username) throws NoPermissionException;
    TAirlineDTO findAirline(int id, String username) throws NoPermissionException;
    boolean addAirline(TAirlineDTO airlineDTO, String username) throws NoPermissionException;
    boolean editAirline(TAirlineDTO airlineDTO, String username) throws NoPermissionException;
    boolean removeAirline(TAirlineDTO airlineDTO, String username) throws NoPermissionException;
}
