/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dgpx.ejb;

import com.dgpx.entity.ItemPool;
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
public class ItemPoolBean extends SuperEJB<ItemPool> {

    @PersistenceContext(unitName = "DGPX-ejbPU")
    private EntityManager em;

    public ItemPoolBean() {
        super(ItemPool.class);
    }

    @Override
    public EntityManager getEntityManager() {
        return em;
    }

    public List<ItemPool> findByItemctgyIdAndKnowledgeId(int itemctgyid, int knowledgeid) {
        Query query = em.createNamedQuery("ItemPool.findByItemctgyIdAndKnowledgeId");
        query.setParameter("itemctgyid", itemctgyid);
        query.setParameter("knowledgeid", knowledgeid);
        return query.getResultList();
    }

}
