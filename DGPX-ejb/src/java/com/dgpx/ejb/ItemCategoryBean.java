/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dgpx.ejb;

import com.dgpx.entity.ItemCategory;
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
public class ItemCategoryBean extends SuperEJB<ItemCategory> {

    @PersistenceContext(unitName = "DGPX-ejbPU")
    private EntityManager em;

    public ItemCategoryBean() {
        super(ItemCategory.class);
    }

    @Override
    public EntityManager getEntityManager() {
        return em;
    }
    
    public long getRowCountHasExamSetting(ItemCategory entity){
        Query query = em.createQuery("SELECT COUNT(e) FROM ExamSetting e WHERE e.itemcategory.id = :itemcategory ");
        query.setParameter("itemcategory", entity.getId());
        try {
            return (long)query.getSingleResult();
        } catch (Exception e) {
            return 0;
        }
    }

}
