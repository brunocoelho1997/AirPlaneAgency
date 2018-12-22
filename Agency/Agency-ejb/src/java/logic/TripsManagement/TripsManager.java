/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logic.TripsManagement;

import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Singleton;
import logic.TPlaneDTO;

/**
 *
 * @author bruno
 */
@Singleton
public class TripsManager implements TripsManagerLocal {

    @EJB
    TPlaneFacadeLocal planeFacade;
    
    @Override
    public List<TPlaneDTO> findAll() {
        List<TPlaneDTO> tPlaneDTOList = new ArrayList<>();
        for(TPlane tplane : planeFacade.findAll())
        {
            tPlaneDTOList.add(planeToDTO(tplane));
        }
        return tPlaneDTOList;
    }

    @Override
    public TPlaneDTO findPlane(int id) {
        for(TPlane tplane : planeFacade.findAll())
        {
            if(tplane.getId()==id)
                return planeToDTO(tplane);
        }
        return null;
    }
    
    @Override
    public boolean addPlane(TPlaneDTO planeDTO) {
        TPlane tplane = new TPlane();
        
        if(planeDTO.getPlaneName()== null || planeDTO.getPlaneName().isEmpty())
            return false;
        tplane.setPlanename(planeDTO.getPlaneName());
        
        //otherwise, the db set the default value (50)
        if(planeDTO.getPlaneLimit()>0)
            tplane.setPlanelimit(planeDTO.getPlaneLimit());
        
        planeFacade.create(tplane);
        return true;
    }

    @Override
    public boolean editPlane(TPlaneDTO planeDTO) {
        TPlane tplane = planeFacade.find(planeDTO.getId());
        
        if(tplane == null)
            return false;
        
        if(planeDTO.getPlaneName()== null || planeDTO.getPlaneName().isEmpty())
            return false;
        tplane.setPlanename(planeDTO.getPlaneName());
        
        //otherwise, the db set the default value (50)
        if(planeDTO.getPlaneLimit()>0)
            tplane.setPlanelimit(planeDTO.getPlaneLimit());
        
        planeFacade.edit(tplane);
        return true;
    }

    @Override
    public boolean removePlane(TPlaneDTO planeDTO) {
        TPlane tplane = planeFacade.find(planeDTO.getId());
        
        if(tplane == null)
            return false;
        planeFacade.remove(tplane);
        return true;
    }
    
    private TPlaneDTO planeToDTO(TPlane tplane){
        return new TPlaneDTO(tplane.getId(), tplane.getPlanename(), tplane.getPlanelimit());
    }
    
    /*
    private TPlane DTOtoPlane(TPlaneDTO tplaneDTO)
    {
        return planeFacade.find(tplaneDTO.getId());
    }
    */

    
    
}
