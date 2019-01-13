/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logic.TripsManagement;

import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

/**
 *
 * @author bruno
 */
@Stateless
public class TSeatFacade extends AbstractFacade<TSeat> implements TSeatFacadeLocal {

    @PersistenceContext(unitName = "Agency-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public TSeatFacade() {
        super(TSeat.class);
    }
    
    @Override
    public List<TSeat> findBoughtSeatsOfTrip(TTrip trip) {
        
        //font: https://docs.oracle.com/cd/E19798-01/821-1841/gjivi/index.html
        
        CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
        CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
        
        Root<TSeat> seat = cq.from(TSeat.class);
        
        
        /* is showing all seats which pertence that trip or all seats that purchase is done... and need to be a AND and not  OR
        cq.where(cb.equal(seat.get(TSeat_.tripid).get(TTrip_.id), trip.getId()));      
        cq.where(cb.equal(seat.get(TSeat_.purchaseid).get(TPurchase_.done), true));
        */
        
        cq.where(cb.and(cb.equal(seat.get(TSeat_.tripid).get(TTrip_.id), trip.getId()), cb.equal(seat.get(TSeat_.purchaseid).get(TPurchase_.done), true)));

        Query q = getEntityManager().createQuery(cq);
        
        //q.setParameter("done", true);
        
        return q.getResultList();

    }

    //all seats which already had a bid (has auctioned = true)
    @Override
    public List<TSeat> findAuctionedSeats() {
        CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
        CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
        
        Root<TSeat> seat = cq.from(TSeat.class);
        
        cq.where(cb.equal(seat.get(TSeat_.auctioned), true));
        
        Query q = getEntityManager().createQuery(cq);
        return q.getResultList();
    }

    //all seats which already had a bid (has auctioned = true) and belongs to trip...
    @Override
    public List<TSeat> findAuctionedSeatsOfTrip(TTrip trip) {
        CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
        CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
        
        Root<TSeat> seat = cq.from(TSeat.class);
        
        cq.where(cb.and(cb.equal(seat.get(TSeat_.tripid).get(TTrip_.id), trip.getId()), cb.equal(seat.get(TSeat_.auctioned), true)));

        Query q = getEntityManager().createQuery(cq);
        return q.getResultList();
    }
    
}
