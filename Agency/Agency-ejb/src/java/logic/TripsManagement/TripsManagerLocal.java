/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logic.TripsManagement;

import java.util.List;
import javax.ejb.Local;
import logic.NoPermissionException;
import logic.TPlaneDTO;

/**
 *
 * @author bruno
 */
@Local
public interface TripsManagerLocal {
    
    //planes
    List<TPlaneDTO> findAllPlanes(String username) throws NoPermissionException;
    TPlaneDTO findPlane(int id, String username);
    boolean addPlane(TPlaneDTO planeDTO, String username);
    boolean editPlane(TPlaneDTO planeDTO, String username);
    boolean removePlane(TPlaneDTO planeDTO, String username);
    
}
