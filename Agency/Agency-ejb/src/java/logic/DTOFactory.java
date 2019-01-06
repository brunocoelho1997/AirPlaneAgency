/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logic;

import java.util.ArrayList;
import java.util.List;
import logic.LogsManagement.TLog;
import logic.TripsManagement.TAirline;
import logic.TripsManagement.TPlace;
import logic.TripsManagement.TPlacefeedback;
import logic.TripsManagement.TPlane;
import logic.TripsManagement.TSeat;
import logic.TripsManagement.TTrip;
import logic.TripsManagement.TTripfeedback;
import logic.UsersManagement.TUser;


public class DTOFactory {
    
    /* From DB object to DTO */
    
    public static TUserDTO getTUserDTOFromTUser(TUser user){
        TUserDTO tUserDTO = new TUserDTO();
        tUserDTO.setUsername(user.getUsername());
        tUserDTO.setPassword(user.getPassword());
        tUserDTO.setAccepted(user.getAccepted());
        tUserDTO.setUsertype(user.getUsertype());
        tUserDTO.setId(user.getId());
        
        if(user.getUsertype() == Config.CLIENT)
        {
            tUserDTO.setBalance(user.getBalance());
            tUserDTO.setClientName(user.getClientname());
        }
        return tUserDTO;
    }
    
    public static TLogDTO getTLogDTOFromTLog(TLog log) {
        TUserDTO user = getTUserDTOFromTUser(log.getUserid());
        return new TLogDTO(user, log.getMsg(), log.getDatelog());
    }
    
    /* From DTO to DB object */
    
    public static TUser getTUserFromTUserDTO(TUserDTO userDTO) {
        TUser newUser = new TUser();
        
        newUser.setUsername(userDTO.getUsername());
        newUser.setPassword(userDTO.getPassword());
        newUser.setUsertype(userDTO.getUsertype());
        newUser.setAccepted(false);
        newUser.setBalance((double) 0);
        
        if(userDTO.getUsertype()==Config.CLIENT)
        {
            newUser.setBalance(userDTO.getBalance());
            newUser.setClientname(userDTO.getClientName());
        }
        
        return newUser;
    }
    
    public static TLog getTLogFromTLogDTO(TLogDTO log) {        
        TLog newLog = new TLog();
        newLog.setMsg(log.getMsg());
        newLog.setDatelog(log.getDate());
        return newLog;
    }
    
    public static TPlaneDTO getTPlaneDTOFromTPlane(TPlane plane){
        return new TPlaneDTO(plane.getId(), plane.getPlanename(), plane.getPlanelimit());
    }
    
    public static TPlaceFeedbackDTO getTPlacefeedbackDTOFromTPlacefeedback(TPlacefeedback feedback){
        return new TPlaceFeedbackDTO(feedback.getId(), feedback.getScore());
    }
    
    public static TPlaceDTO getTPlaceDTOFromTPlace(TPlace place){
        List<TPlaceFeedbackDTO> placeFeedBackList = new ArrayList();
        for(TPlacefeedback placeFeedback : place.getTPlacefeedbackCollection())
        {
            placeFeedBackList.add(getTPlacefeedbackDTOFromTPlacefeedback(placeFeedback));
        }
        
        return new TPlaceDTO(place.getId(), place.getCountry(), place.getCity(), place.getAddress(),placeFeedBackList);
    }
    
    public static TAirlineDTO getTAirlineDTOFromTAirline(TAirline airline){
        return new TAirlineDTO(airline.getId(), airline.getAirlinename(), airline.getPhonenumber());
    }
    
    public static TTripFeedbackDTO getTTripfeedbackDTOFromTTripfeedback(TTripfeedback feedback){
        return new TTripFeedbackDTO(feedback.getId(), feedback.getScore());
    }
    
    public static TTripDTO getTTripDTOFromTTrip(TTrip trip){
        List<TTripFeedbackDTO> tripFeedBackList = new ArrayList();
        for(TTripfeedback tripfeedback : trip.getTTripfeedbackCollection())
        {
            tripFeedBackList.add(getTTripfeedbackDTOFromTTripfeedback(tripfeedback));
        }             
        return new TTripDTO(trip.getId(), trip.getPrice(), trip.getDone(), trip.getCanceled(), trip.getDatetrip(),getTAirlineDTOFromTAirline(trip.getAirlineid()), getTPlaceDTOFromTPlace(trip.getPlaceid()), getTPlaneDTOFromTPlane(trip.getPlaneid()), tripFeedBackList);
    }
    
    public static TSeatDTO getTSeatDTOFromTSeat(TSeat seat){
        return new TSeatDTO(seat.getId(), seat.getLuggage(), seat.getAuctioned(), seat.getPrice(), getTTripDTOFromTTrip(seat.getTripid()), getTUserDTOFromTUser(seat.getUserid()));
    }
    
}