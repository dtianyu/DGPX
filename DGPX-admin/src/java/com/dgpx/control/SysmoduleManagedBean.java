/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dgpx.control;

import com.dgpx.ejb.SysmoduleBean;
import com.dgpx.entity.Sysmodule;
import com.dgpx.lazy.SysmoduleModel;
import com.dgpx.web.SuperSingleBean;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

/**
 *
 * @author kevindong
 */
@ManagedBean
@SessionScoped
public class SysmoduleManagedBean extends SuperSingleBean<Sysmodule> {

    @EJB
    private SysmoduleBean sysmoduleBean;

    /**
     * Creates a new instance of SysmoduleManagedBean
     */
    public SysmoduleManagedBean() {
        super(Sysmodule.class);
    }

    @Override
    public void create() {
        super.create();
        newEntity.setSortid(1);
    }

    @Override
    public void init() {
        this.superEJB = sysmoduleBean;
        setModel(new SysmoduleModel(sysmoduleBean));
        super.init();
    }

}
