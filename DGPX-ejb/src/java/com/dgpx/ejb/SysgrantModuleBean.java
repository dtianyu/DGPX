/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dgpx.ejb;

import com.dgpx.entity.SysgrantModule;
import com.lightshell.comm.SuperEJB;
import java.util.List;
import javax.ejb.Stateless;
import javax.ejb.LocalBean;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author kevindong
 */
@Stateless
@LocalBean
public class SysgrantModuleBean extends SuperEJB<SysgrantModule> {

    @PersistenceContext(unitName = "DGPX-ejbPU")
    private EntityManager em;

    public SysgrantModuleBean() {
        super(SysgrantModule.class);
    }

    public int getRowCountByUserId(int id) {
        Query query = em.createNamedQuery("SysgrantModule.getRowCountByUserId");
        query.setParameter("userid", id);
        try {
            return Integer.parseInt(query.getSingleResult().toString());
        } catch (Exception e) {
            return 0;
        }       
    }

    public List<SysgrantModule> findByUserId(int id) {
        Query query = em.createNamedQuery("SysgrantModule.findByUserId");
        query.setParameter("userid", id);
        return query.getResultList();
    }

    @Override
    public EntityManager getEntityManager() {
        return em;
    }

}
