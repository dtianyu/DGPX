/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dgpx.ejb;

import com.dgpx.entity.ExamCategory;
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
public class ExamCategoryBean extends SuperEJB<ExamCategory> {

    @PersistenceContext(unitName = "DGPX-ejbPU")
    private EntityManager em;

    public ExamCategoryBean() {
        super(ExamCategory.class);
    }

    @Override
    public EntityManager getEntityManager() {
        return em;
    }

    public long getRowCountHasExamNumber(ExamCategory entity) {
        Query query = em.createQuery("SELECT COUNT(e) FROM ExamNumber e WHERE e.examcategory.id = :examcategory ");
        query.setParameter("examcategory", entity.getId());
        try {
            return (long) query.getSingleResult();
        } catch (Exception e) {
            return 0;
        }
    }

    public ExamCategory findByFormid(String value) {
        Query query = em.createNamedQuery("ExamCategory.findByFormId");
        query.setParameter("formid", value);
        Object o = query.getSingleResult();
        if (o != null) {
            return (ExamCategory) o;
        } else {
            return null;
        }
    }

}
