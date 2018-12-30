package logic;

import java.util.List;
import javax.ejb.Remote;

@Remote
public interface AgencyManagerRemote {
    
    //users
    boolean signIn(String username, String password);
    boolean signUp(TUserDTO userDTO);
    boolean logout();
    boolean acceptUser(TUserDTO userDTO);
    TUserDTO getTUserDTO(String username);
    
    //planes
    List<TPlaneDTO> findAllPlanes() throws NoPermissionException;
    TPlaneDTO findPlane(int id) throws NoPermissionException;
    boolean addPlane(TPlaneDTO planeDTO) throws NoPermissionException;
    boolean editPlane(TPlaneDTO planeDTO) throws NoPermissionException;
    boolean removePlane(TPlaneDTO planeDTO) throws NoPermissionException;
    
    //airlines
    List<TAirlineDTO> findAllAirlines() throws NoPermissionException;
    TAirlineDTO findAirline(int id) throws NoPermissionException;
    boolean addAirline(TAirlineDTO airlineDTO) throws NoPermissionException;
    boolean editAirline(TAirlineDTO airlineDTO) throws NoPermissionException;
    boolean removeAirline(TAirlineDTO airlineDTO) throws NoPermissionException;
    
    //places
    List<TPlaceDTO> findAllPlaces();
    TPlaceDTO findPlace(int id);
    boolean addPlace(TPlaceDTO placeDTO) throws NoPermissionException;
    boolean editPlace(TPlaceDTO placeDTO) throws NoPermissionException;
    boolean removePlace(TPlaceDTO placeDTO) throws NoPermissionException;
    
    //place feedback
    TPlaceFeedbackDTO findPlacefeedback(int id);
    boolean addFeedbackToPlace(TPlaceDTO placeDTO, TPlaceFeedbackDTO feedbackDTO) throws NoPermissionException;
    boolean editFeedbackOfPlace(TPlaceFeedbackDTO feedbackDTO) throws NoPermissionException;
    boolean removeFeedbackOfPlace(TPlaceFeedbackDTO feedbackDTO) throws NoPermissionException;

}
