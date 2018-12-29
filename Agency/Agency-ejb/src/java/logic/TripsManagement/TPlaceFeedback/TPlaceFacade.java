/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logic.TripsManagement.TPlaceFeedback;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import logic.TripsManagement.TPlace.TPlace;

/**
 *
 * @author bruno
 */
@Stateless
public class TPlaceFacade extends AbstractFacade<TPlace> implements TPlaceFacadeLocal {

    @PersistenceContext(unitName = "Agency-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public TPlaceFacade() {
        super(TPlace.class);
    }
    
}
