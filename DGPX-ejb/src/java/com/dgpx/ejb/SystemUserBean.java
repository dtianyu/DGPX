/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dgpx.ejb;

import com.lightshell.comm.SuperEJB;
import com.dgpx.entity.SystemUser;
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
public class SystemUserBean extends SuperEJB<SystemUser> {

    @PersistenceContext(unitName = "DGPX-ejbPU")
    protected EntityManager em;

    public SystemUserBean() {
        super(SystemUser.class);
    }

    public SystemUser findByUserId(String userid) {
        Query query = em.createNamedQuery("SystemUser.findByUserId");
        query.setParameter("userid", userid);
        try {
            return (SystemUser) query.getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }

    public SystemUser findByUserIdAndPwd(String userid, String pwd) {
        Query query = em.createNamedQuery("SystemUser.findByUserIdAndPwd");
        query.setParameter("userid", userid);
        query.setParameter("pwd", pwd);
        query.setParameter("email", userid);
        try {
            return (SystemUser) query.getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }

    public SystemUser findByMailAdd(String value) {
        Query query = em.createNamedQuery("SystemUser.findByMailAdd");
        query.setParameter("email", value);
        try {
            return (SystemUser) query.getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public EntityManager getEntityManager() {
        return em;
    }

}
