/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logic.TripsManagement.TPlace;

import java.util.List;
import javax.ejb.Local;
import logic.TripsManagement.TPlaceFeedback.TPlacefeedback;

/**
 *
 * @author bruno
 */
@Local
public interface TPlacefeedbackFacadeLocal {

    void create(TPlacefeedback tPlacefeedback);

    void edit(TPlacefeedback tPlacefeedback);

    void remove(TPlacefeedback tPlacefeedback);

    TPlacefeedback find(Object id);

    List<TPlacefeedback> findAll();

    List<TPlacefeedback> findRange(int[] range);

    int count();
    
}
