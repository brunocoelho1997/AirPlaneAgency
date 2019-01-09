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
    boolean depositToAccount(float amount);
    List<TUserDTO> findAllUsers();
        
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

    //trip
    List<TTripDTO> findAllTrips();
    TTripDTO findTrip(int id);
    boolean addTrip(TTripDTO tripDTO) throws NoPermissionException;
    boolean editTrip(TTripDTO tripDTO) throws NoPermissionException;
    boolean removeTrip(TTripDTO tripDTO) throws NoPermissionException;
    boolean cancelTrip(TTripDTO tripDTO) throws NoPermissionException;
    boolean setTripDone(TTripDTO tripDTO) throws NoPermissionException;

    //trip feedback
    TTripFeedbackDTO findTripfeedback(int id);
    boolean addFeedbackToTrip(TTripDTO tripDTO, TTripFeedbackDTO feedbackDTO) throws NoPermissionException;
    boolean editFeedbackOfTrip(TTripFeedbackDTO feedbackDTO) throws NoPermissionException;
    boolean removeFeedbackOfTrip(TTripFeedbackDTO feedbackDTO) throws NoPermissionException;
    
    //date
    int getDate();
    boolean setDate(int date);
    boolean setDurationTimer(long durationSeconds);
    String getTimerInformation();

    // logs
    /*
    Zero for all logs. Non-zero for defined number of log lines
    */
    List<TLogDTO> getLogs(int lines) throws NoPermissionException;
    void removeLogs() throws NoPermissionException;
    
    //purchase
    List<TPurchaseDTO> findAllPurchases() throws NoPermissionException;
    List<TPurchaseDTO> findAllPurchasesOfUser() throws NoPermissionException;
    TPurchaseDTO findPurchase(int id) throws NoPermissionException;
    boolean buySeatsToTrip(TTripDTO tripDTO, List<TSeatDTO> seatDTOList) throws NoPermissionException;
    boolean editPurchase(TPurchaseDTO purchaseDTO) throws NoPermissionException;
    boolean removePurchase(TPurchaseDTO purchaseDTO) throws NoPermissionException;
    boolean finishPurchase(TPurchaseDTO purchaseDTO) throws NoPermissionException;
}
