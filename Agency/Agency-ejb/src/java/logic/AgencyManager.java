package logic;

import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateful;
import logic.TripsManagement.TripsManagerLocal;
import logic.UsersManagement.UsersManagerLocal;



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
    public boolean signUp(TUserDTO userDTO) {
        return usersManagerLocal.signUp(userDTO);
    }
    
    @Override
    public TUserDTO getTUserDTO(String username) {
        return usersManagerLocal.getTUserDTO(username);
    }

    @Override
    public boolean acceptUser(TUserDTO userDTO) {
        return usersManagerLocal.acceptTUser(userDTO, username);
    }

    @Override
    public boolean logout() {
        this.username = null;
        return true;
    }
    //----------------------------------------------------------------------
    //Planes
    @Override
    public List<TPlaneDTO> findAllPlanes() throws NoPermissionException{
        return tripsManagerLocal.findAllPlanes(username);
    }
    
    @Override
    public TPlaneDTO findPlane(int id) throws NoPermissionException {
        return tripsManagerLocal.findPlane(id, username);
    }

    @Override
    public boolean addPlane(TPlaneDTO planeDTO) throws NoPermissionException {
        return tripsManagerLocal.addPlane(planeDTO, username);
    }

    @Override
    public boolean editPlane(TPlaneDTO planeDTO) throws NoPermissionException {
        return tripsManagerLocal.editPlane(planeDTO, username);
    }

    @Override
    public boolean removePlane(TPlaneDTO planeDTO) throws NoPermissionException {
        return tripsManagerLocal.removePlane(planeDTO, username);
    }

    //----------------------------------------------------------------------
    //Airlines
    @Override
    public List<TAirlineDTO> findAllAirlines() throws NoPermissionException {
        return tripsManagerLocal.findAllAirlines(username);
    }

    @Override
    public TAirlineDTO findAirline(int id) throws NoPermissionException {
        return tripsManagerLocal.findAirline(id, username);
    }

    @Override
    public boolean addAirline(TAirlineDTO airlineDTO) throws NoPermissionException {
        return tripsManagerLocal.addAirline(airlineDTO, username);
    }

    @Override
    public boolean editAirline(TAirlineDTO airlineDTO) throws NoPermissionException {
        return tripsManagerLocal.editAirline(airlineDTO, username);
    }

    @Override
    public boolean removeAirline(TAirlineDTO airlineDTO) throws NoPermissionException {
        return tripsManagerLocal.removeAirline(airlineDTO, username);
    }
//----------------------------------------------------------------------
    //Places
    @Override
    public List<TPlaceDTO> findAllPlaces() {
        return tripsManagerLocal.findAllPlaces(username);
    }

    @Override
    public TPlaceDTO findPlace(int id) {
        return tripsManagerLocal.findPlace(id);
    }

    @Override
    public boolean addPlace(TPlaceDTO placeDTO) throws NoPermissionException {
        return tripsManagerLocal.addPlace(placeDTO, username);
    }

    @Override
    public boolean editPlace(TPlaceDTO placeDTO) throws NoPermissionException {
        return tripsManagerLocal.editPlace(placeDTO, username);
    }

    @Override
    public boolean removePlace(TPlaceDTO placeDTO) throws NoPermissionException {
        return tripsManagerLocal.removePlace(placeDTO, username);
    }
    //----------------------------------------------------------------------
    //Feedback Places
    @Override
    public TPlaceFeedbackDTO findPlacefeedback(int id) {
        return tripsManagerLocal.findPlacefeedback(id);
    }
    
    @Override
    public boolean addFeedbackToPlace(TPlaceDTO placeDTO, TPlaceFeedbackDTO feedbackDTO) throws NoPermissionException {
        return tripsManagerLocal.addFeedbackToPlace(placeDTO, feedbackDTO, username);
    }

    @Override
    public boolean editFeedbackOfPlace(TPlaceFeedbackDTO feedbackDTO) throws NoPermissionException {
        return tripsManagerLocal.editFeedbackOfPlace(feedbackDTO, username);
    }

    @Override
    public boolean removeFeedbackOfPlace(TPlaceFeedbackDTO feedbackDTO) throws NoPermissionException {
        return tripsManagerLocal.removeFeedbackOfPlace(feedbackDTO, username);
    }
    //----------------------------------------------------------------------    
//Trips
    @Override
    public List<TTripDTO> findAllTrips() {
        return tripsManagerLocal.findAllTrips();
    }

    @Override
    public TTripDTO findTrip(int id) {
        return tripsManagerLocal.findTrip(id);
    }

    @Override
    public boolean addTrip(TTripDTO tripDTO) throws NoPermissionException {
        return tripsManagerLocal.addTrip(tripDTO, username);
    }

    @Override
    public boolean editTrip(TTripDTO tripDTO) throws NoPermissionException {
        return tripsManagerLocal.editTrip(tripDTO, username);
    }

    @Override
    public boolean removeTrip(TTripDTO tripDTO) throws NoPermissionException {
        return tripsManagerLocal.removeTrip(tripDTO, username);
    }

    @Override
    public boolean cancelTrip(TTripDTO tripDTO) throws NoPermissionException {
        return tripsManagerLocal.cancelTrip(tripDTO, username);
    }

    @Override
    public boolean setTripDone(TTripDTO tripDTO) throws NoPermissionException {
        return tripsManagerLocal.setTripDone(tripDTO, username);
    }

     //----------------------------------------------------------------------    
//Trips feedback
    
    @Override
    public TTripFeedbackDTO findTripfeedback(int id) {
        return tripsManagerLocal.findTripfeedback(id);
    }

    @Override
    public boolean addFeedbackToTrip(TTripDTO tripDTO, TTripFeedbackDTO feedbackDTO) throws NoPermissionException {
        return tripsManagerLocal.addFeedbackToTrip(tripDTO, feedbackDTO, username);
    }

    @Override
    public boolean editFeedbackOfTrip(TTripFeedbackDTO feedbackDTO) throws NoPermissionException {
        return tripsManagerLocal.editFeedbackOfTrip(feedbackDTO, username);
    }

    @Override
    public boolean removeFeedbackOfTrip(TTripFeedbackDTO feedbackDTO) throws NoPermissionException {
        return tripsManagerLocal.removeFeedbackOfTrip(feedbackDTO, username);
    }
 
}
