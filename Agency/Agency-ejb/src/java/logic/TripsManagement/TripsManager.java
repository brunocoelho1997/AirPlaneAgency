/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logic.TripsManagement;

import logic.TripsManagement.TPlane;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.Singleton;
import logic.Config;
import logic.NoPermissionException;
import logic.TAirlineDTO;
import logic.TPlaceFeedbackDTO;
import logic.TPlaceDTO;
import logic.TPlaneDTO;
import logic.TTripDTO;
import logic.TTripFeedbackDTO;
import logic.TUserDTO;
import logic.TripsManagement.TAirline;
import logic.TripsManagement.TAirlineFacadeLocal;
import logic.TripsManagement.TPlace;
import logic.TripsManagement.TPlaceFacadeLocal;
import logic.UsersManagement.TUser;
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
    
    @EJB
    TPlacefeedbackFacadeLocal placeFeedbackFacade;
    
    @EJB
    TTripfeedbackFacadeLocal tripFeedbackFacade;
    
    @EJB
    TPlaceFacadeLocal placeFacade; 
    
    @EJB
    TTripFacadeLocal tripFacade;
    
    @EJB
    TPurchaseFacadeLocal purchaseFacade;
    
    
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

        TPlane plane = planeFacade.find(id);
        
        if(plane == null)
            return null;
        
        return planeToDTO(plane);
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
        
        TAirline airline = airlineFacade.find(id);
        
        if(airline == null)
            return null;
        
        return airlineToDTO(airline);
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
    
    
    //-------------------------------------------------------------------------------------------------------------------
    //Place
    @Override
    public List<TPlaceDTO> findAllPlaces(String username) {
        List<TPlaceDTO> tplaceDTOList = new ArrayList<>();
        for(TPlace place : placeFacade.findAll())
        {
            tplaceDTOList.add(placeToDTO(place));
        }
        return tplaceDTOList;
    }

    @Override
    public TPlaceDTO findPlace(int id) {
        TPlace place = placeFacade.find(id);
        
        if(place == null)
            return null;
        
        return placeToDTO(place);
        
    }

    @Override
    public boolean addPlace(TPlaceDTO placeDTO, String username) throws NoPermissionException {
        verifyPermission(username, Config.OPERATOR);

        TPlace tplace = new TPlace();
          
        if(!validatePlaceDTO(placeDTO))
            return false;
        
        tplace.setCity(placeDTO.getCity());
        tplace.setCountry(placeDTO.getCountry());
        tplace.setTPlacefeedbackCollection(new ArrayList());
        if(placeDTO.getAddress() != null && !placeDTO.getAddress().isEmpty())
            tplace.setAddress(placeDTO.getAddress());
        
        
        placeFacade.create(tplace);
        return true;
    }

    @Override
    public boolean editPlace(TPlaceDTO placeDTO, String username) throws NoPermissionException {
        
        verifyPermission(username, Config.OPERATOR);
        
        TPlace place = placeFacade.find(placeDTO.getId());
        
        if(place == null)
            return false;
        
        if(!validatePlaceDTO(placeDTO))
            return false;
        
        place.setCity(placeDTO.getCity());
        place.setCountry(placeDTO.getCountry());
        place.setTPlacefeedbackCollection(new ArrayList());
        if(placeDTO.getAddress() != null && !placeDTO.getAddress().isEmpty())
            place.setAddress(placeDTO.getAddress());
        
        placeFacade.edit(place);
        return true;
    }

    @Override
    public boolean removePlace(TPlaceDTO placeDTO, String username) throws NoPermissionException {
        verifyPermission(username, Config.OPERATOR);
        
        TPlace place = placeFacade.find(placeDTO.getId());
        
        if(place == null)
            return false;
        placeFacade.remove(place);
        return true;
    }
    
    private TPlaceDTO placeToDTO(TPlace place){
        List<TPlaceFeedbackDTO> placeFeedBackList = new ArrayList();
        for(TPlacefeedback placeFeedback : place.getTPlacefeedbackCollection())
        {
            placeFeedBackList.add(placeFeedbackToDTO(placeFeedback));
        }
        
        return new TPlaceDTO(place.getId(), place.getCountry(), place.getCity(), place.getAddress(),placeFeedBackList);
    }
    
    private boolean validatePlaceDTO(TPlaceDTO placeDTO)
    {
        if(placeDTO == null)
            return false;
        
        if(placeDTO.getCity() == null || placeDTO.getCity().isEmpty())
            return false;
        
        if(placeDTO.getCountry()== null || placeDTO.getCountry().isEmpty())
            return false;
        
        return true;
    }
    
    //-------------------------------------------------------------------------------------------------------------------
    //place feedback
    
    @Override
    public boolean addFeedbackToPlace(TPlaceDTO placeDTO, TPlaceFeedbackDTO feedbackDTO, String username ) throws NoPermissionException {
        
        verifyPermission(username, Config.CLIENT);
        
        TPlace place = placeFacade.find(placeDTO.getId());
        
        if(place == null)
            return false;
        
        TUser user = userManager.getTUserByUsername(username);
        
        if(user == null)
            return false;
        
        if(!validatePlaceFeedbackDTO(feedbackDTO))
            return false;
        
        TPlacefeedback placeFeedback = new TPlacefeedback();
        placeFeedback.setPlaceid(place);
        placeFeedback.setUserid(user);
        placeFeedback.setScore(feedbackDTO.getScore());
        
        placeFeedbackFacade.create(placeFeedback);
        
        place.getTPlacefeedbackCollection().add(placeFeedback);
        placeFacade.edit(place);
        
        return true;
    }
    
    @Override
    public TPlaceFeedbackDTO findPlacefeedback(int id) {
        TPlacefeedback placeFeedback = placeFeedbackFacade.find(id);
        
        if(placeFeedback == null)
            return null;
        
        return placeFeedbackToDTO(placeFeedback);
    }
    
    @Override
    public boolean editFeedbackOfPlace(TPlaceFeedbackDTO feedbackDTO, String username) throws NoPermissionException {
        verifyPermission(username, Config.CLIENT);
        
        TPlacefeedback placeFeedback = placeFeedbackFacade.find(feedbackDTO.getId());
        
        if(placeFeedback == null)
            return false;
        
        //se o comentario for de um user diferente do que esta a alterar manda excecao
        if(!placeFeedback.getUserid().getUsername().equals(username))
            throw new NoPermissionException(Config.msgNoPermissionFeedback);
        
        if(!validatePlaceFeedbackDTO(feedbackDTO))
            return false;
        
        placeFeedback.setScore(feedbackDTO.getScore());
        
        placeFeedbackFacade.edit(placeFeedback);
        
        return true;
    }
    
    private TPlaceFeedbackDTO placeFeedbackToDTO(TPlacefeedback feedback){
        return new TPlaceFeedbackDTO(feedback.getId(), feedback.getScore());
    }
    
    private boolean validatePlaceFeedbackDTO(TPlaceFeedbackDTO placeFeedback)
    {
        if(placeFeedback == null)
            return false;
        
        if(placeFeedback.getScore() < 0)
            return false;
        
        return true;
    }
    
    @Override
    public boolean removeFeedbackOfPlace(TPlaceFeedbackDTO feedbackDTO, String username) throws NoPermissionException {
        verifyPermission(username, Config.CLIENT);
        
        TPlacefeedback placeFeedback = placeFeedbackFacade.find(feedbackDTO.getId());
        
        if(placeFeedback == null)
            return false;
        
        TPlace place = placeFeedback.getPlaceid();
        
        if(place == null)
            return false;
        
        //se o comentario for de um user diferente do que esta a alterar manda excecao
        if(!placeFeedback.getUserid().getUsername().equals(username))
            throw new NoPermissionException(Config.msgNoPermissionFeedback);
        
        placeFeedbackFacade.remove(placeFeedback);
        
        place.getTPlacefeedbackCollection().remove(placeFeedback);
        placeFacade.edit(place);
        
        return true;
    }
    

    //-------------------------------------------------------------------------------------------------------------------
    //trip
  
    @Override
    public List<TTripDTO> findAllTrips() {
        List<TTripDTO> tTripDTOList = new ArrayList<>();
        for(TTrip trip : tripFacade.findAll())
        {
            tTripDTOList.add(tripToDTO(trip));
        }
        return tTripDTOList;
    }

    @Override
    public TTripDTO findTrip(int id) {
        TTrip trip = tripFacade.find(id);
        
        if(trip == null)
            return null;
        
        return tripToDTO(trip);
    }

    @Override
    public boolean addTrip(TTripDTO tripDTO, String username) throws NoPermissionException {
        verifyPermission(username, Config.OPERATOR);
        
        TPlace place = placeFacade.find(tripDTO.getPlaceDTO().getId());     
        if(place == null)
            return false;
        
        TAirline airline = airlineFacade.find(tripDTO.getAirlineDTO().getId());
        if(airline == null)
            return false;
        
        TPlane plane = planeFacade.find(tripDTO.getPlaneDTO().getId());
        if(plane == null)
            return false;
        
        if(!validateTripDTO(tripDTO))
            return false;
        
        TTrip trip = new TTrip();
        trip.setPlaceid(place);
        trip.setAirlineid(airline);
        trip.setPlaneid(plane);
        trip.setDone(tripDTO.getDone());
        trip.setCanceled(tripDTO.getCanceled());
        trip.setPrice(tripDTO.getPrice());
        trip.setCanceled(false);
        trip.setDone(false);
        trip.setDatetrip(tripDTO.getDatetrip());
        trip.setTSeatCollection(new ArrayList());
        trip.setTTripfeedbackCollection(new ArrayList());

        tripFacade.create(trip);
        
        return true;
    }

    @Override
    public boolean editTrip(TTripDTO tripDTO, String username) throws NoPermissionException {
        
        verifyPermission(username, Config.OPERATOR);
        
        TTrip trip = tripFacade.find(tripDTO.getId());     
        if(trip == null)
            return false;
        
        TPlace place = placeFacade.find(tripDTO.getPlaceDTO().getId());     
        if(place == null)
            return false;
        
        TAirline airline = airlineFacade.find(tripDTO.getAirlineDTO().getId());
        if(airline == null)
            return false;
        
        TPlane plane = planeFacade.find(tripDTO.getPlaneDTO().getId());
        if(plane == null)
            return false;
        
        if(!validateTripDTO(tripDTO))
            return false;
        
        trip.setPlaceid(place);
        trip.setAirlineid(airline);
        trip.setPlaneid(plane);
        trip.setDone(tripDTO.getDone());
        trip.setCanceled(tripDTO.getCanceled());
        trip.setPrice(tripDTO.getPrice());
        trip.setDatetrip(tripDTO.getDatetrip());

        tripFacade.edit(trip);
        return true;
    }

    
    //este metodo nao convem ser usado... Deve ser usado o canceltrip()
    
    @Override
    public boolean removeTrip(TTripDTO tripDTO, String username) throws NoPermissionException {
        verifyPermission(username, Config.OPERATOR);
        
        TTrip trip = tripFacade.find(tripDTO.getId());
        if(trip == null)
            return false;
        tripFacade.remove(trip);
        
        return true;
    }
    
    @Override
    public boolean cancelTrip(TTripDTO tripDTO, String username) throws NoPermissionException {
        verifyPermission(username, Config.OPERATOR);
        
        TTrip trip = tripFacade.find(tripDTO.getId());     
        if(trip == null)
            return false;
        
        trip.setCanceled(true);
        
        tripFacade.edit(trip);
        return true;
    }

    @Override
    public boolean setTripDone(TTripDTO tripDTO, String username) throws NoPermissionException {
        verifyPermission(username, Config.OPERATOR);
        
        TTrip trip = tripFacade.find(tripDTO.getId());     
        if(trip == null)
            return false;
        
        trip.setDone(true);
        
        tripFacade.edit(trip);
        return true;
    }
    
    private TTripDTO tripToDTO(TTrip trip){
        List<TTripFeedbackDTO> tripFeedBackList = new ArrayList();
        for(TTripfeedback tripfeedback : trip.getTTripfeedbackCollection())
        {
            tripFeedBackList.add(tripFeedbackToDTO(tripfeedback));
        }             
        return new TTripDTO(trip.getId(), trip.getPrice(), trip.getDone(), trip.getCanceled(), trip.getDatetrip(),airlineToDTO(trip.getAirlineid()), placeToDTO(trip.getPlaceid()), planeToDTO(trip.getPlaneid()), tripFeedBackList);
    }

    private boolean validateTripDTO(TTripDTO tripDTO)
    {
        if(tripDTO == null)
            return false;
        
        if(tripDTO.getPrice()<0)
            return false;
        
        return true;
    }

    
    //-------------------------------------------------------------------------------------------------------------------
    //trip feedback
    @Override
    public TTripFeedbackDTO findTripfeedback(int id) {
        TTripfeedback tripFeedback = tripFeedbackFacade.find(id);
        
        if(tripFeedback == null)
            return null;
        
        return tripFeedbackToDTO(tripFeedback);
    }

    @Override
    public boolean addFeedbackToTrip(TTripDTO tripDTO, TTripFeedbackDTO tripFeedbackDTO, String username) throws NoPermissionException {
        verifyPermission(username, Config.CLIENT);
        
        TTrip trip = tripFacade.find(tripDTO.getId());
        
        if(trip == null)
            return false;
        
        TUser user = userManager.getTUserByUsername(username);
        
        if(user == null)
            return false;
        
        
        if(!validateTripFeedbackDTO(tripFeedbackDTO, trip, user))
            return false;
        
        TTripfeedback tripFeedback = new TTripfeedback();
        tripFeedback.setTripid(trip);
        tripFeedback.setUserid(user);
        tripFeedback.setScore(tripFeedbackDTO.getScore());
        
        tripFeedbackFacade.create(tripFeedback);
        
        trip.getTTripfeedbackCollection().add(tripFeedback);
        tripFacade.edit(trip);
        
        return true;
    }

    @Override
    public boolean editFeedbackOfTrip(TTripFeedbackDTO tripFeedbackDTO, String username) throws NoPermissionException {
        verifyPermission(username, Config.CLIENT);
        
        TTripfeedback tripFeedback = tripFeedbackFacade.find(tripFeedbackDTO.getId());
        
        if(tripFeedback == null)
            return false;
        
        //se o comentario for de um user diferente do que esta a alterar manda excecao
        if(!tripFeedback.getUserid().getUsername().equals(username))
            throw new NoPermissionException(Config.msgNoPermissionFeedback);
        
        
        TUser user = userManager.getTUserByUsername(username);
        
        if(user == null)
            return false;
        
        if(!validateTripFeedbackDTO(tripFeedbackDTO, tripFeedback.getTripid(), user))
            return false;
        
        tripFeedback.setScore(tripFeedbackDTO.getScore());
        
        tripFeedbackFacade.edit(tripFeedback);
        
        return true;
    }

    @Override
    public boolean removeFeedbackOfTrip(TTripFeedbackDTO tripFeedbackDTO, String username) throws NoPermissionException {
        verifyPermission(username, Config.CLIENT);
        
        TTripfeedback tripFeedback = tripFeedbackFacade.find(tripFeedbackDTO.getId());
        
        if(tripFeedback == null)
            return false;
        
        TTrip trip = tripFeedback.getTripid();
        
        if(trip == null)
            return false;
        
        //se o comentario for de um user diferente do que esta a alterar manda excecao
        if(!tripFeedback.getUserid().getUsername().equals(username))
            throw new NoPermissionException(Config.msgNoPermissionFeedback);
        
        tripFeedbackFacade.remove(tripFeedback);
        
        trip.getTTripfeedbackCollection().remove(tripFeedback);
        tripFacade.edit(trip);
        
        return true;
    }
    
    private TTripFeedbackDTO tripFeedbackToDTO(TTripfeedback feedback){
        return new TTripFeedbackDTO(feedback.getId(), feedback.getScore());
    }
    
    private boolean validateTripFeedbackDTO(TTripFeedbackDTO tripFeedback, TTrip trip, TUser user)
    {
        boolean userDoneTripTemp = false;
        if(tripFeedback == null)
            return false;
        
        //apenas pode dar feedback caso esta ja se tenha realizado
        if(!trip.getDone())
            return false;
        
        /*
        
        
        //verifica se o user pertenceu 'a trip
        for(TSeat seat : trip.getTSeatCollection())
        {
            if(seat.getUserid().equals(user))
            {
                userDoneTripTemp = true;
                break;
            }
        }
        
        if(!userDoneTripTemp)
            return false;
        */
        
        if(tripFeedback.getScore() < 0)
            return false;
        
        return true;
    }
    
//-----------------------------------------------------------------------------------------------------------------
    //auxiliar methods
    
    private void verifyPermission(String username, int permissionType) throws NoPermissionException{
        
        String errorMessage = (Config.CLIENT == permissionType? Config.msgNoPermission: Config.msgNoPermissionOperator);
        
        if(username == null || username.isEmpty())
            throw new NoPermissionException(errorMessage);
        
        TUserDTO userDTO = userManager.getTUserDTO(username);
        
        if(userDTO == null)
            throw new NoPermissionException(errorMessage);
        
        if(!userDTO.getAccepted())
            throw new NoPermissionException(errorMessage);       

        //se for um cliente e a permissao exigida for do tipo de cliente permite... caso contrario nao deixa (os operadores podem fazer tudo, portanto nao fiz validacao para os operadores)
        if(userDTO.getUsertype() == Config.CLIENT && permissionType != Config.CLIENT)
            throw new NoPermissionException(errorMessage);       
    }

    


    

    

    

    

}
