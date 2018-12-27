/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logic.TripsManagement;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.Singleton;
import logic.Config;
import logic.NoPermissionException;
import logic.TPlaneDTO;
import logic.TUserDTO;
import logic.UsersManagement.TUserFacadeLocal;
import logic.UsersManagement.UsersManagerLocal;

/**
 *
 * @author bruno
 */
@Singleton
public class TripsManager implements TripsManagerLocal {

    @EJB
    TPlaneFacadeLocal planeFacade;
    
    @EJB
    UsersManagerLocal userManager;

    
    @Override
    public List<TPlaneDTO> findAllPlanes(String username) throws NoPermissionException {
               
        verifyPermission(username, Config.OPERATOR);
        
        List<TPlaneDTO> tPlaneDTOList = new ArrayList<>();
        for(TPlane tplane : planeFacade.findAll())
        {
            tPlaneDTOList.add(planeToDTO(tplane));
        }
        return tPlaneDTOList;
    }

    @Override
    public TPlaneDTO findPlane(int id, String username) throws NoPermissionException {
        
        verifyPermission(username, Config.OPERATOR);

        for(TPlane tplane : planeFacade.findAll())
        {
            if(tplane.getId()==id)
                return planeToDTO(tplane);
        }
        return null;
    }
    
    @Override
    public boolean addPlane(TPlaneDTO planeDTO, String username) throws NoPermissionException {
        
        verifyPermission(username, Config.OPERATOR);

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
    public boolean editPlane(TPlaneDTO planeDTO, String username) throws NoPermissionException {
        
        verifyPermission(username, Config.OPERATOR);
        
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
    public boolean removePlane(TPlaneDTO planeDTO, String username) throws NoPermissionException {
        
        verifyPermission(username, Config.OPERATOR);
        
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

    
    private void verifyPermission(String username, int permissionType) throws NoPermissionException{
        if(username == null || username.isEmpty())
            throw new NoPermissionException(Config.msgNoPermissionOperator);
        
        TUserDTO userDTO = userManager.getTUserDTO(username);
        
        if(userDTO == null)
            throw new NoPermissionException(Config.msgNoPermissionOperator);
        
        if(!userDTO.getAccepted())
            throw new NoPermissionException(Config.msgNoPermissionOperator);       

        if(userDTO.getUsertype() != permissionType)
            throw new NoPermissionException(Config.msgNoPermissionOperator);       
    }
    
    
}
