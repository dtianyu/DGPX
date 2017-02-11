/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dgpx.ejb;

import com.dgpx.entity.ExamSeat;
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

    //判断是否满位
    public boolean isFullSeats() {
        Query query = em.createNamedQuery("ExamSeat.findIdle");
        List<ExamSeat> seats = query.getResultList();
        return seats.isEmpty();//没有空闲的座席就是满了
    }

    //获得空位数量
    public int getIdleCount() {
        Query query = em.createNamedQuery("ExamSeat.findIdle");
        List<ExamSeat> seats = query.getResultList();
        if (seats == null || seats.isEmpty()) {
            return 0;
        } else {
            return seats.size();
        }
    }

}
