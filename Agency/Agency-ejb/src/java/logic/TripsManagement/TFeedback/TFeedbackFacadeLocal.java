/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logic.TripsManagement.TFeedback;

import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author bruno
 */
@Local
public interface TFeedbackFacadeLocal {

    void create(TFeedback tFeedback);

    void edit(TFeedback tFeedback);

    void remove(TFeedback tFeedback);

    TFeedback find(Object id);

    List<TFeedback> findAll();

    List<TFeedback> findRange(int[] range);

    int count();
    
}
