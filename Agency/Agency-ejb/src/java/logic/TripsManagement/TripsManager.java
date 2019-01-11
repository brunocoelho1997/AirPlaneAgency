/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logic.TripsManagement;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.ejb.Singleton;
import javax.inject.Inject;
import javax.jms.JMSConnectionFactory;
import javax.jms.JMSContext;
import javax.jms.ObjectMessage;
import javax.jms.Queue;
import logic.Config;
import logic.DTOFactory;
import logic.LogTypes;
import logic.TLogDTO;
import logic.NoPermissionException;
import logic.TAirlineDTO;
import logic.TPlaceFeedbackDTO;
import logic.TPlaceDTO;
import logic.TPlaneDTO;
import logic.TPurchaseDTO;
import logic.TSeatDTO;
import logic.TTripDTO;
import logic.TTripFeedbackDTO;
import logic.TUserDTO;
import logic.TimerManagement.TimerManagerLocal;
import logic.UsersManagement.TUser;
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
    
    @EJB
    TSeatFacadeLocal seatFacade;
    
    @EJB
    TimerManagerLocal timerManager;
    
    @Resource(mappedName = "jms/MyQueue")
    Queue logsQueue;
    
    @Inject
    @JMSConnectionFactory("jms/MyConnFactory")
    private JMSContext jmsContext;
   
    //Planes
    @Override
    public List<TPlaneDTO> findAllPlanes(String username) throws NoPermissionException {
               
        userManager.verifyPermission(username, Config.OPERATOR);
        
        List<TPlaneDTO> tPlaneDTOList = new ArrayList<>();
        for(TPlane tplane : planeFacade.findAll())
        {
            tPlaneDTOList.add(DTOFactory.getTPlaneDTOFromTPlane(tplane));
        }
        return tPlaneDTOList;
    }

    @Override
    public TPlaneDTO findPlane(int id, String username) throws NoPermissionException {
        
        userManager.verifyPermission(username, Config.OPERATOR);

        TPlane plane = planeFacade.find(id);
        
        if(plane == null)
            return null;
        
        return DTOFactory.getTPlaneDTOFromTPlane(plane);
    }
    
    @Override
    public boolean addPlane(TPlaneDTO planeDTO, String username) throws NoPermissionException {
        
        userManager.verifyPermission(username, Config.OPERATOR);

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
        
        userManager.verifyPermission(username, Config.OPERATOR);
        
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
        
        userManager.verifyPermission(username, Config.OPERATOR);
        
        TPlane tplane = planeFacade.find(planeDTO.getId());
        
        if(tplane == null)
            return false;
        planeFacade.remove(tplane);
        return true;
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
        userManager.verifyPermission(username, Config.OPERATOR);
        
        List<TAirlineDTO> tAirlineDTOList = new ArrayList<>();
        for(TAirline tairline : airlineFacade.findAll())
        {
            tAirlineDTOList.add(DTOFactory.getTAirlineDTOFromTAirline(tairline));
        }
        return tAirlineDTOList;
    }

    @Override
    public TAirlineDTO findAirline(int id, String username) throws NoPermissionException {
        userManager.verifyPermission(username, Config.OPERATOR);
        
        TAirline airline = airlineFacade.find(id);
        
        if(airline == null)
            return null;
        
        return DTOFactory.getTAirlineDTOFromTAirline(airline);
    }

    @Override
    public boolean addAirline(TAirlineDTO airlineDTO, String username) throws NoPermissionException {
        userManager.verifyPermission(username, Config.OPERATOR);

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
        
        userManager.verifyPermission(username, Config.OPERATOR);
        
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
        userManager.verifyPermission(username, Config.OPERATOR);
        
        TAirline tairline = airlineFacade.find(airlineDTO.getId());
        
        if(tairline == null)
            return false;
        airlineFacade.remove(tairline);
        return true;
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
            tplaceDTOList.add(DTOFactory.getTPlaceDTOFromTPlace(place));
        }
        return tplaceDTOList;
    }

    @Override
    public TPlaceDTO findPlace(int id) {
        TPlace place = placeFacade.find(id);
        
        if(place == null)
            return null;
        
        return DTOFactory.getTPlaceDTOFromTPlace(place);
        
    }

    @Override
    public boolean addPlace(TPlaceDTO placeDTO, String username) throws NoPermissionException {
        userManager.verifyPermission(username, Config.OPERATOR);

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
        
        userManager.verifyPermission(username, Config.OPERATOR);
        
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
        userManager.verifyPermission(username, Config.OPERATOR);
        
        TPlace place = placeFacade.find(placeDTO.getId());
        
        if(place == null)
            return false;
        placeFacade.remove(place);
        return true;
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
        
        userManager.verifyPermission(username, Config.CLIENT);
        
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
        
        return DTOFactory.getTPlacefeedbackDTOFromTPlacefeedback(placeFeedback);
    }
    
    @Override
    public boolean editFeedbackOfPlace(TPlaceFeedbackDTO feedbackDTO, String username) throws NoPermissionException {
        userManager.verifyPermission(username, Config.CLIENT);
        
        TPlacefeedback placeFeedback = placeFeedbackFacade.find(feedbackDTO.getId());
        
        if(placeFeedback == null)
            return false;
        
        //se o comentario for de um user diferente do que esta a alterar manda excecao
        if(!placeFeedback.getUserid().getUsername().equals(username))
            throw new NoPermissionException(Config.MSG_NO_PERMISSION_FEEDBACK);
        
        if(!validatePlaceFeedbackDTO(feedbackDTO))
            return false;
        
        placeFeedback.setScore(feedbackDTO.getScore());
        
        placeFeedbackFacade.edit(placeFeedback);
        
        return true;
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
        userManager.verifyPermission(username, Config.CLIENT);
        
        TPlacefeedback placeFeedback = placeFeedbackFacade.find(feedbackDTO.getId());
        
        if(placeFeedback == null)
            return false;
        
        TPlace place = placeFeedback.getPlaceid();
        
        if(place == null)
            return false;
        
        //se o comentario for de um user diferente do que esta a alterar manda excecao
        if(!placeFeedback.getUserid().getUsername().equals(username))
            throw new NoPermissionException(Config.MSG_NO_PERMISSION_FEEDBACK);
        
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
            tTripDTOList.add(DTOFactory.getTTripDTOFromTTrip(trip));
        }
        return tTripDTOList;
    }

    @Override
    public TTripDTO findTrip(int id) {
        TTrip trip = tripFacade.find(id);
        
        if(trip == null)
            return null;
        
        return DTOFactory.getTTripDTOFromTTrip(trip);
    }

    @Override
    public boolean addTrip(TTripDTO tripDTO, String username) throws NoPermissionException {
        userManager.verifyPermission(username, Config.OPERATOR);
        
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
        
        sendLogMessage(username, LogTypes.CREATE_TRIP, timerManager.getDate());
        
        return true;
    }

    @Override
    public boolean editTrip(TTripDTO tripDTO, String username) throws NoPermissionException {
        
        userManager.verifyPermission(username, Config.OPERATOR);
        
        TTrip trip = tripFacade.find(tripDTO.getId());     
        if(trip == null)
            return false;
        
        if(!trip.getTSeatCollection().isEmpty()) //se a viagem ja tem lugares comprados mandar excecao
            throw new NoPermissionException(Config.MSG_NO_PERMISSION_CHANGE_TRIP);
        
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

    
    //this method should not be used. We should use canceltrip()
    
    @Override
    public boolean removeTrip(TTripDTO tripDTO, String username) throws NoPermissionException {
        userManager.verifyPermission(username, Config.OPERATOR);
        
        TTrip trip = tripFacade.find(tripDTO.getId());
        if(trip == null)
            return false;
        tripFacade.remove(trip);
        
        return true;
    }
    
    @Override
    public boolean cancelTrip(TTripDTO tripDTO, String username) throws NoPermissionException {
        boolean resultTmp = false;
        
        userManager.verifyPermission(username, Config.OPERATOR);
        
        TTrip trip = tripFacade.find(tripDTO.getId());     
        if(trip == null)
            return false;
        
        trip.setCanceled(true);
        
        //when we cancel a trip we need to refund the users who bought seats
        resultTmp = refundUsers(trip);
        if(!resultTmp)
            return false;
        
        tripFacade.edit(trip);
        return true;
    }
    
    private boolean refundUsers(TTrip trip){
        boolean result = false;
        
        if(!trip.getTSeatCollection().isEmpty())
        {
            for(TSeat seatTmp : trip.getTSeatCollection()){
                TUser userTmp = seatTmp.getUserid();
                userTmp.setBalance(userTmp.getBalance() + trip.getPrice());
                
                result = userManager.editTUser(userTmp);
                
                if(!result)
                    return false;
            }
        }
        
        return true;
    }

    @Override
    public boolean setTripDone(TTripDTO tripDTO, String username) throws NoPermissionException {
        userManager.verifyPermission(username, Config.OPERATOR);
        
        TTrip trip = tripFacade.find(tripDTO.getId());     
        if(trip == null)
            return false;
        
        trip.setDone(true);
        
        tripFacade.edit(trip);
        return true;
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
        
        return DTOFactory.getTTripfeedbackDTOFromTTripfeedback(tripFeedback);
    }

    @Override
    public boolean addFeedbackToTrip(TTripDTO tripDTO, TTripFeedbackDTO tripFeedbackDTO, String username) throws NoPermissionException {
        userManager.verifyPermission(username, Config.CLIENT);
        
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
        userManager.verifyPermission(username, Config.CLIENT);
        
        TTripfeedback tripFeedback = tripFeedbackFacade.find(tripFeedbackDTO.getId());
        
        if(tripFeedback == null)
            return false;
        
        //se o comentario for de um user diferente do que esta a alterar manda excecao
        if(!tripFeedback.getUserid().getUsername().equals(username))
            throw new NoPermissionException(Config.MSG_NO_PERMISSION_FEEDBACK);
        
        
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
        userManager.verifyPermission(username, Config.CLIENT);
        
        TTripfeedback tripFeedback = tripFeedbackFacade.find(tripFeedbackDTO.getId());
        
        if(tripFeedback == null)
            return false;
        
        TTrip trip = tripFeedback.getTripid();
        
        if(trip == null)
            return false;
        
        //se o comentario for de um user diferente do que esta a alterar manda excecao
        if(!tripFeedback.getUserid().getUsername().equals(username))
            throw new NoPermissionException(Config.MSG_NO_PERMISSION_FEEDBACK);
        
        tripFeedbackFacade.remove(tripFeedback);
        
        trip.getTTripfeedbackCollection().remove(tripFeedback);
        tripFacade.edit(trip);
        
        return true;
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
    
    
    //-------------------------------------------------------------------------------------------------------------------
    //purchase
    @Override
    public List<TPurchaseDTO> findAllPurchases(String username) throws NoPermissionException {
        userManager.verifyPermission(username, Config.OPERATOR);
        
        List<TPurchaseDTO> purchaseDTOList = new ArrayList<>();
        for(TPurchase purchase : purchaseFacade.findAll())
        {
            purchaseDTOList.add(DTOFactory.getTPurchaseDTOFromTPurchase(purchase));
        }
        return purchaseDTOList;
    }

    @Override
    public List<TPurchaseDTO> findAllPurchasesOfUser(String username) throws NoPermissionException {
        userManager.verifyPermission(username, Config.CLIENT);

        TUser user = userManager.getTUserByUsername(username);
        
        List<TPurchaseDTO> purchaseDTOList = new ArrayList<>();
        
        for(TPurchase purchase : user.getTPurchaseCollection())
        {
            purchaseDTOList.add(DTOFactory.getTPurchaseDTOFromTPurchase(purchase));
        }

        return purchaseDTOList;
    }

    @Override
    public TPurchaseDTO findPurchase(int id, String username) throws NoPermissionException {
        TPurchase purchase = purchaseFacade.find(id);
        
        if(purchase == null)
            return null;
        
        return DTOFactory.getTPurchaseDTOFromTPurchase(purchase);
    }
    
    @Override
    public TPurchaseDTO getActualPurchase(String username) throws NoPermissionException {
        
        userManager.verifyPermission(username, Config.CLIENT);
        
        TUser user = userManager.getTUserByUsername(username);
        if(user == null)
            return null;
        
        //purchase---------------- get the atual purchase - the only which is undone
        TPurchase purchase = null;
        
        for(TPurchase purchaseTmp : user.getTPurchaseCollection()){
            if(!purchaseTmp.getDone())
            {
                purchase = purchaseTmp;
                break;
            }
        }
        
        if(purchase == null)
            return null;
        
        return DTOFactory.getTPurchaseDTOFromTPurchase(purchase);
    }
    
    @Override
    public boolean buySeatsToTrip(TTripDTO tripDTO, List<TSeatDTO> seatDTOList, String username) throws NoPermissionException {
        
        userManager.verifyPermission(username, Config.CLIENT);
        
        //user----------------
        TUser user = userManager.getTUserByUsername(username);
        
        if(user == null)
            return false;
        
        //trip----------------
        TTrip trip = tripFacade.find(tripDTO.getId());
        
        if(trip == null)
            return false;
        
        //purchase----------------
        TPurchase purchase = null;
        
        for(TPurchase purchaseTmp : user.getTPurchaseCollection()){
            if(!purchaseTmp.getDone())
            {
                purchase = purchaseTmp;
                break;
            }
        }
        
        if(purchase == null)
        {
            purchase = new TPurchase();
            purchase.setDone(false);
            purchase.setTSeatCollection(new ArrayList());
            purchase.setUserid(user);
            purchaseFacade.create(purchase);
            user.getTPurchaseCollection().add(purchase);
        }
        
        //validate all seats...
        for(TSeatDTO seatDTO : seatDTOList)
        {
            if(!validateTSeatDTO(seatDTO))
                return false;
        }
        
        for(TSeatDTO seatDTO : seatDTOList)
        {
            //create seat----------------
            TSeat seat = new TSeat();
            seat.setAuctioned(seatDTO.getAuctioned());
            seat.setLuggage(seatDTO.getLuggage());
            seat.setPrice(trip.getPrice());
            seat.setTripid(trip);
            seat.setUserid(user);
            seat.setPurchaseid(purchase);

            seat = seatFacade.createAndGetEntity(seat);

            purchase.getTSeatCollection().add(seat);
            user.getTSeatCollection().add(seat);
            
            trip.getTSeatCollection().add(seat);
        }
        purchaseFacade.edit(purchase);
        userManager.editTUser(user);
        tripFacade.edit(trip);
        
        return true;
    }
    
    @Override
    public boolean editActualPurchase(TPurchaseDTO purchaseDTO, String username) throws NoPermissionException {
        userManager.verifyPermission(username, Config.CLIENT);
        
        TUser user = userManager.getTUserByUsername(username);
        if(user == null)
            return false;
        
        //purchase---------------- get the atual purchase - the only which is undone
        TPurchase purchase = null;
        
        for(TPurchase purchaseTmp : user.getTPurchaseCollection()){
            if(!purchaseTmp.getDone())
            {
                purchase = purchaseTmp;
                break;
            }
        }
        
        if(purchase == null)
            return false;
        
        
        //validate all seats...
        for(TSeatDTO seatDTO : purchaseDTO.gettSeatCollection())
        {
            if(!validateTSeatDTO(seatDTO))
                return false;
        }
        
        for(TSeatDTO seatDTO : purchaseDTO.gettSeatCollection()){
            TSeat seat = seatFacade.find(seatDTO.getId());
        
            if(seat == null)
                return false;

            seat.setAuctioned(seatDTO.getAuctioned());
            seat.setLuggage(seatDTO.getLuggage());
            seat.setPrice(seatDTO.getPrice());

            seatFacade.edit(seat);
        }
        
        return true;
    }

    public boolean validateTSeatDTO(TSeatDTO seatDTO){
        
        if(seatDTO == null)
            return false;
        
        return true;
    }
    
    @Override
    public boolean removeSeatsOfActualPurchase(TPurchaseDTO purchaseDTO, TTripDTO tripDTO, String username) throws NoPermissionException {
        boolean removedSeats = false;
        List<TSeat> seatsToRemove = new ArrayList();
        userManager.verifyPermission(username, Config.CLIENT);
        
        TPurchase purchase = purchaseFacade.find(purchaseDTO.getId());
        if(purchase == null)
            return false;
        
        TUser user = userManager.getTUserByUsername(username);
        //if the purchase belongs to the user who is editing
        if(!purchase.getUserid().equals(user))
            return false;
        
        TTrip trip = tripFacade.find(tripDTO.getId());     
        if(trip == null)
            return false;
        
        for(TSeat seatTmp : purchase.getTSeatCollection()){
            if(seatTmp.getTripid().equals(trip))
            {
                removedSeats = true;
                seatsToRemove.add(seatTmp);
                
                seatFacade.remove(seatTmp);
                
            }
        }
        
        if(!removedSeats)
            return false;
        
        for(TSeat seatTmp : seatsToRemove)     
        {
            purchase.getTSeatCollection().remove(seatTmp);
        }
            
        purchaseFacade.edit(purchase);
        
        return true;
    }
    
    @Override
    public boolean removeActualPurchase(TPurchaseDTO purchaseDTO, String username) throws NoPermissionException {
        
        userManager.verifyPermission(username, Config.CLIENT);
        
        TTrip trip = null;
        List<TSeat> seatsToRemove = new ArrayList();
       
        TPurchase purchase = purchaseFacade.find(purchaseDTO.getId());
        if(purchase == null)
            return false;
        
        TUser user = userManager.getTUserByUsername(username);
        //if the purchase belongs to the user who is editing
        if(!purchase.getUserid().equals(user))
            return false;
        
        for(TSeat seatTmp : purchase.getTSeatCollection()){
            if(seatTmp.getTripid().equals(trip))
            {
                
                seatsToRemove.add(seatTmp);
                
                seatFacade.remove(seatTmp);
                
            }
        }
        /*
        if(!removedSeats)
            return false;
           
        
         for(TSeat seatTmp : seatsToRemove)     
            purchase.getTSeatCollection().remove(seatTmp);
        */
       
            
        purchaseFacade.remove(purchase);
        
        user.getTPurchaseCollection().remove(purchase);
        userManager.editTUser(user);
        
        return true;
        
    }

    @Override
    public boolean finishActualPurchase(TPurchaseDTO purchaseDTO, String username) throws NoPermissionException {
        int countTmp;
        double totalCostPurchase = 0; //the cost that client will need to pay
        userManager.verifyPermission(username, Config.CLIENT);
        
        //purchase
        TPurchase purchase = purchaseFacade.find(purchaseDTO.getId());
        if(purchase == null)
            return false;
        
        if(purchase.getTSeatCollection().isEmpty())
            return false;
        
        if(!verifyIfUserHasMoneyToPay(purchase.getUserid(), purchase))
            throw new NoPermissionException(Config.MSG_NO_PERMISSION_MONEY);
        
        //verifying if some trip already done
        for(TSeat seatTmp : purchase.getTSeatCollection())
        {
            if(seatTmp.getTripid().getDone())
                throw new NoPermissionException(Config.MSG_NO_PERMISSION_TRIP_DONE);
        }
        
        //verifying if planes has space for all seats:
        List<TTrip> differentTrips = new ArrayList();
        //get all different trips and the total of money that client will pay
        for(TSeat seatTmp : purchase.getTSeatCollection()){
            TTrip tripTmp = seatTmp.getTripid();
            if(!differentTrips.contains(tripTmp))
                differentTrips.add(tripTmp);
            
            totalCostPurchase += tripTmp.getPrice();
        }
        
        //the number of all different trips (eg: one to Lisbon; another to Porto) included on the current purchase and the ones already bought are going to be counted ... if the sum between both is higher than the plan limit, then the order can't be completed.
        for(TTrip tripTmp : differentTrips){
            countTmp = 0; //will have the total wanted seats for this trip 
            for(TSeat seatTmp : purchase.getTSeatCollection())
            {
                if(seatTmp.getTripid().equals(tripTmp))
                    countTmp++;
            }
            
            //System.out.println("\n\n\n\n\n\n Trip id: " + tripTmp.getId() +" - todos os  - " + tripFacade.findBoughtSeatsOfTrip(tripTmp));

            if((tripFacade.findBoughtSeatsOfTrip(tripTmp).size() + countTmp) > tripTmp.getPlaneid().getPlanelimit())
                throw new NoPermissionException(Config.MSG_NO_PERMISSION_PLANE_LIMIT_EXCEDED);

        }
        
        //set new balance for the user
        TUser userTmp = purchase.getUserid();
        userTmp.setBalance(userTmp.getBalance() - totalCostPurchase);
        userManager.editTUser(userTmp);
        
        purchase.setDone(true);
        purchaseFacade.edit(purchase);
        
        return true;
    }
    
    //if user has money return true, otherwise return false
    private boolean verifyIfUserHasMoneyToPay(TUser user, TPurchase purchase) {
        double total = 0;
        
        for(TSeat seat : purchase.getTSeatCollection())    
            total += seat.getTripid().getPrice();
        
        return ( (user.getBalance() - total) > 0 ? true : false );
    }
    
//-----------------------------------------------------------------------------------------------------------------
    //auxiliar methods
    
    private void sendLogMessage(String username, String msg, int date) throws NoPermissionException {        
        if (username == null || username.isEmpty())
                throw new NoPermissionException(Config.MSG_NO_PERMISSION_LOG);
        
        
        TUserDTO userDTO = userManager.getTUserDTO(username);
        
        System.out.println("sendLogMessage: user retrieved:" + userDTO);
        
        if(userDTO == null)
            throw new NoPermissionException(Config.MSG_NO_PERMISSION_LOG);
        
        TLogDTO log = new TLogDTO(userDTO, msg, date);
        ObjectMessage message = jmsContext.createObjectMessage(log);
        jmsContext.createProducer().send(logsQueue, message);
    }

    

    

}
