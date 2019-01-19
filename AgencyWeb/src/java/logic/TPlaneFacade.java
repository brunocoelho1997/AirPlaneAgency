/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package web;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author joao
 */
@Stateless
public class TPlaneFacade extends AbstractFacade<TPlane> {

    @PersistenceContext(unitName = "AgencyWebPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public TPlaneFacade() {
        super(TPlane.class);
    }
    
}
