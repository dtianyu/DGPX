/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dgpx.ejb;

import com.dgpx.entity.Sysprg;
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
public class SysprgBean extends SuperEJB<Sysprg> {

    @PersistenceContext(unitName = "DGPX-ejbPU")
    private EntityManager em;

    public SysprgBean() {
        super(Sysprg.class);
    }

    public List<Sysprg> findByModuleId(int id) {
        Query query = em.createNamedQuery("Sysprg.findByModuleId");
        query.setParameter("moduleid", id);
        return query.getResultList();
    }

    public Sysprg findByAPI(String api) {
        Query query = em.createNamedQuery("Sysprg.findByAPI");
        query.setParameter("api", api);
        try {
            return (Sysprg) query.getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public EntityManager getEntityManager() {
        return em;
    }

}
