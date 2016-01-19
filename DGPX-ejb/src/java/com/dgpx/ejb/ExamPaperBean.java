/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dgpx.ejb;

import com.dgpx.entity.ExamPaper;
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
public class ExamPaperBean extends SuperEJB<ExamPaper> {

    @PersistenceContext(unitName = "DGPX-ejbPU")
    private EntityManager em;

    public ExamPaperBean() {
        super(ExamPaper.class);
    }

    @Override
    public EntityManager getEntityManager() {
        return em;
    }

    public int getRowCountNotDoneByPId(Object pid) {
        Query query = em.createNamedQuery("ExamPaper.getRowCountNotDoneByPId");
        query.setParameter("pid", (int) pid);
        try {
            return ((Long) query.getSingleResult()).intValue();
        } catch (Exception e) {
            return 0;
        }
    }

}
