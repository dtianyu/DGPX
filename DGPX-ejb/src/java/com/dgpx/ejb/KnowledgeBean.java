/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dgpx.ejb;

import com.dgpx.entity.Knowledge;
import com.lightshell.comm.SuperEJB;
import javax.ejb.Stateless;
import javax.ejb.LocalBean;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author kevindong
 */
@Stateless
@LocalBean
public class KnowledgeBean extends SuperEJB<Knowledge> {

    @PersistenceContext(unitName = "DGPX-ejbPU")
    private EntityManager em;

    public KnowledgeBean() {
        super(Knowledge.class);
    }

    @Override
    public EntityManager getEntityManager() {
        return em;
    }

}
