/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logic.TripsManagement;

import logic.TripsManagement.TPlane.TPlane;
import logic.TripsManagement.TPlane.TPlaneFacadeLocal;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.Singleton;
import logic.Config;
import logic.NoPermissionException;
import logic.TAirlineDTO;
import logic.TPlaneDTO;
import logic.TUserDTO;
import logic.TripsManagement.TAirline.TAirline;
import logic.TripsManagement.TAirline.TAirlineFacadeLocal;
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

    @EJB
    TAirlineFacadeLocal airlineFacade;
    
    //Planes
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
        
        if(!validatePlaneDTO(planeDTO))
            return false;
        
        tplane.setPlanename(planeDTO.getPlaneName());
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
        
        if(!validatePlaneDTO(planeDTO))
            return false;
        
        tplane.setPlanename(planeDTO.getPlaneName());
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
    
    private boolean validatePlaneDTO(TPlaneDTO planeDTO)
    {
        if(planeDTO.getPlaneName()== null || planeDTO.getPlaneName().isEmpty())
            return false;
        if(planeDTO.getPlaneLimit()<0)
            return false;
            
        return true;
    }
    
    //-------------------------------------------------------------------------------------------------------------------
    //Airline
    @Override
    public List<TAirlineDTO> findAllAirlines(String username) throws NoPermissionException {
        verifyPermission(username, Config.OPERATOR);
        
        List<TAirlineDTO> tAirlineDTOList = new ArrayList<>();
        for(TAirline tairline : airlineFacade.findAll())
        {
            tAirlineDTOList.add(airlineToDTO(tairline));
        }
        return tAirlineDTOList;
    }

    @Override
    public TAirlineDTO findAirline(int id, String username) throws NoPermissionException {
        verifyPermission(username, Config.OPERATOR);

        for(TAirline tairline : airlineFacade.findAll())
        {
            if(tairline.getId()==id)
                return airlineToDTO(tairline);
        }
        return null;
    }

    @Override
    public boolean addAirline(TAirlineDTO airlineDTO, String username) throws NoPermissionException {
        verifyPermission(username, Config.OPERATOR);

        TAirline tairline = new TAirline();
        
        if(!validateAirlineDTO(airlineDTO))
            return false;
        
        tairline.setAirlinename(airlineDTO.getAirlineName());
        tairline.setPhonenumber(airlineDTO.getPhoneNumber());
        
        airlineFacade.create(tairline);
        return true;
    }

    @Override
    public boolean editAirline(TAirlineDTO airlineDTO, String username) throws NoPermissionException {
        
        verifyPermission(username, Config.OPERATOR);
        
        TAirline tairline = airlineFacade.find(airlineDTO.getId());
        
        if(airlineDTO == null)
            return false;
        
        if(!validateAirlineDTO(airlineDTO))
            return false;
        
        tairline.setAirlinename(airlineDTO.getAirlineName());
        tairline.setPhonenumber(airlineDTO.getPhoneNumber());
        
        airlineFacade.edit(tairline);
        return true;
    }

    @Override
    public boolean removeAirline(TAirlineDTO airlineDTO, String username) throws NoPermissionException {
        verifyPermission(username, Config.OPERATOR);
        
        TAirline tairline = airlineFacade.find(airlineDTO.getId());
        
        if(tairline == null)
            return false;
        airlineFacade.remove(tairline);
        return true;
    }
    
    private TAirlineDTO airlineToDTO(TAirline airline){
        return new TAirlineDTO(airline.getId(), airline.getAirlinename(), airline.getPhonenumber());
    }
    
    private boolean validateAirlineDTO(TAirlineDTO airlineDTO)
    {
        if(airlineDTO.getAirlineName()== null || airlineDTO.getAirlineName().isEmpty())
            return false;
        if(airlineDTO.getPhoneNumber()== null || airlineDTO.getPhoneNumber().isEmpty())
            return false;
        return true;
    }
    
    //auxiliar methods
    
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
