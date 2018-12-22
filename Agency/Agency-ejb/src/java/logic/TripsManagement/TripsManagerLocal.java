/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logic.TripsManagement;

import java.util.List;
import javax.ejb.Local;
import logic.TPlaneDTO;

/**
 *
 * @author bruno
 */
@Local
public interface TripsManagerLocal {
    
    //planes
    List<TPlaneDTO> findAll();
    TPlaneDTO findPlane(int id);
    boolean addPlane(TPlaneDTO planeDTO);
    boolean editPlane(TPlaneDTO planeDTO);
    boolean removePlane(TPlaneDTO planeDTO);
    
}
