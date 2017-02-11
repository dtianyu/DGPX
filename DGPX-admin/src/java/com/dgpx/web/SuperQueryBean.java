/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dgpx.web;

import com.dgpx.control.UserManagedBean;
import com.dgpx.ejb.SysprgBean;
import com.dgpx.entity.Sysprg;
import com.lightshell.comm.BaseEntity;
import com.lightshell.comm.SuperSingleManagedBean;
import javax.ejb.EJB;
import javax.faces.bean.ManagedProperty;
import javax.faces.context.FacesContext;

/**
 *
 * @author KevinDong
 * @param <T>
 */
public abstract class SuperQueryBean<T extends BaseEntity> extends SuperSingleManagedBean<T> {

    @EJB
    protected SysprgBean sysprgBean;

    @ManagedProperty(value = "#{userManagedBean}")
    protected UserManagedBean userManagedBean;

    protected String persistenceUnitName;
    protected String appDataPath;
    protected String appImgPath;
    protected Sysprg currentSysprg;

    /**
     * @param entityClass
     */
    public SuperQueryBean(Class<T> entityClass) {
        this.entityClass = entityClass;
    }

    @Override
    protected void buildJsonObject() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    protected void buildJsonArray() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void construct() {
        //不需要进行操作权限设置
        FacesContext fc = FacesContext.getCurrentInstance();
        appDataPath = fc.getExternalContext().getRealPath("/") + fc.getExternalContext().getInitParameter("com.dgpx.web.appdatapath");
        appImgPath = fc.getExternalContext().getRealPath("/") + fc.getExternalContext().getInitParameter("com.dgpx.web.appimgpath");
        reportPath = fc.getExternalContext().getRealPath("/") + fc.getExternalContext().getInitParameter("com.dgpx.web.reportpath");
        reportOutputFormat = fc.getExternalContext().getInitParameter("com.dgpx.web.reportoutputformat");
        reportOutputPath = fc.getExternalContext().getRealPath("/") + fc.getExternalContext().getInitParameter("com.dgpx.web.reportoutputpath");
        reportViewContext = fc.getExternalContext().getInitParameter("com.dgpx.web.reportviewcontext");
        persistenceUnitName = fc.getExternalContext().getInitParameter("com.dgpx.jpa.unitname");
        super.construct();
    }

    @Override
    public void persist() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void update() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void delete() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public String getAppDataPath() {
        return this.appDataPath;
    }

    @Override
    public String getAppImgPath() {
        return this.appImgPath;
    }

    @Override
    public String getPersistenceUnitName() {
        return this.persistenceUnitName;
    }

    @Override
    public void print() throws Exception {

    }

    @Override
    public void preview() throws Exception {
        //FacesContext.getCurrentInstance().getExternalContext().redirect(this.reportViewPath);
    }

    @Override
    public void pull() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void push() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    protected void setToolBar() {

    }

    @Override
    public void unverify() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void verify() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    /**
     * @return the userManagedBean
     */
    public UserManagedBean getUserManagedBean() {
        return userManagedBean;
    }

    /**
     * @param userManagedBean the userManagedBean to set
     */
    public void setUserManagedBean(UserManagedBean userManagedBean) {
        this.userManagedBean = userManagedBean;
    }

}
