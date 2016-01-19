/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dgpx.ejb;

import com.dgpx.entity.ExamSeat;
import com.lightshell.comm.SuperEJB;
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
public class ExamSeatBean extends SuperEJB<ExamSeat> {

    @PersistenceContext(unitName = "DGPX-ejbPU")
    private EntityManager em;

    public ExamSeatBean() {
        super(ExamSeat.class);
    }

    @Override
    public EntityManager getEntityManager() {
        return em;
    }

    public ExamSeat findByIP(String ip) {
        Query query = em.createNamedQuery("ExamSeat.findByIP");
        query.setParameter("ip", ip);
        try {
            return (ExamSeat) query.getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }

}
