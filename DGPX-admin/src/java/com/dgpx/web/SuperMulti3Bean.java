/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dgpx.web;

import com.lightshell.comm.SuperEntity;
import com.dgpx.control.UserManagedBean;
import com.dgpx.ejb.SysprgBean;
import com.dgpx.entity.Sysprg;
import com.lightshell.comm.SuperDetailEntity;
import com.lightshell.comm.SuperMulti3ManagedBean;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedProperty;
import javax.faces.context.FacesContext;

/**
 *
 * @author KevinDong
 * @param <T>
 * @param <V>
 * @param <X>
 * @param <Z>
 */
public abstract class SuperMulti3Bean<T extends SuperEntity, V extends SuperDetailEntity, X extends SuperDetailEntity, Z extends SuperDetailEntity> extends SuperMulti3ManagedBean<T, V, X, Z> {

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
     * @param detailClass
     * @param detailClass2
     * @param detailClass3
     */
    public SuperMulti3Bean(Class<T> entityClass, Class<V> detailClass, Class<X> detailClass2, Class<Z> detailClass3) {
        this.entityClass = entityClass;
        this.detailClass = detailClass;
        this.detailClass2 = detailClass2;
        this.detailClass3 = detailClass3;
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
        FacesContext fc = FacesContext.getCurrentInstance();
        appDataPath = fc.getExternalContext().getRealPath("/") + fc.getExternalContext().getInitParameter("com.dgpx.web.appdatapath");
        appImgPath = fc.getExternalContext().getRealPath("/") + fc.getExternalContext().getInitParameter("com.dgpx.web.appimgpath");
        reportPath = fc.getExternalContext().getRealPath("/") + fc.getExternalContext().getInitParameter("com.dgpx.web.reportpath");
        reportOutputFormat = fc.getExternalContext().getInitParameter("com.dgpx.web.reportoutputformat");
        reportOutputPath = fc.getExternalContext().getRealPath("/") + fc.getExternalContext().getInitParameter("com.dgpx.web.reportoutputpath");
        reportViewContext = fc.getExternalContext().getInitParameter("com.dgpx.web.reportviewcontext");
        persistenceUnitName = fc.getExternalContext().getInitParameter("com.dgpx.jpa.unitname");
        int beginIndex = fc.getViewRoot().getViewId().lastIndexOf("/") + 1;
        int endIndex = fc.getViewRoot().getViewId().lastIndexOf(".");
        currentSysprg = sysprgBean.findByAPI(fc.getViewRoot().getViewId().substring(beginIndex, endIndex));
        if (currentSysprg != null) {
            this.doAdd = currentSysprg.getDoadd();
            this.doPrt = currentSysprg.getDoprt();
        }
        super.construct();
    }

    @Override
    public void create() {
        if (this.detailList != null && !this.detailList.isEmpty()) {
            this.detailList.clear();
        } else if (this.detailList == null) {
            this.detailList = new ArrayList<>();
        }
        if (this.detailList2 != null && !this.detailList2.isEmpty()) {
            this.detailList2.clear();
        } else if (this.detailList2 == null) {
            this.detailList2 = new ArrayList<>();
        }
        if (this.detailList3 != null && !this.detailList3.isEmpty()) {
            this.detailList3.clear();
        } else if (this.detailList3 == null) {
            this.detailList3 = new ArrayList<>();
        }
        if (getNewEntity() == null) {
            try {
                T entity = entityClass.newInstance();
                entity.setStatus("N");
                entity.setCreator(getUserManagedBean().getCurrentUser().getUserid());
                entity.setCredateToNow();
                setNewEntity(entity);
            } catch (InstantiationException | IllegalAccessException ex) {
                Logger.getLogger(SuperMulti3Bean.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
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
        FacesContext.getCurrentInstance().getExternalContext().redirect(this.reportViewPath);
    }

    @Override
    public void pull() {

    }

    @Override
    public void push() {
        buildJsonArray();
    }

    @Override
    protected void setToolBar() {
        if (currentEntity != null && currentSysprg != null && currentEntity.getStatus() != null) {
            switch (currentEntity.getStatus()) {
                case "N":
                    this.doEdit = currentSysprg.getDoedit() && true;
                    this.doDel = currentSysprg.getDodel() && true;
                    this.doCfm = currentSysprg.getDocfm() && true;
                    this.doUnCfm = false;
                    break;
                case "V":
                    this.doEdit = currentSysprg.getDoedit() && false;
                    this.doDel = currentSysprg.getDodel() && false;
                    this.doCfm = false;
                    this.doUnCfm = currentSysprg.getDouncfm() && true;
                    break;
                default:
                    this.doEdit = false;
                    this.doDel = false;
                    this.doCfm = false;
                    this.doUnCfm = false;
            }
        } else {
            this.doEdit = false;
            this.doDel = false;
            this.doCfm = false;
            this.doUnCfm = false;
        }
    }

    @Override
    public void unverify() {
        if (null != getCurrentEntity()) {
            try {
                if (doBeforeUnverify()) {
                    currentEntity.setStatus("M");
                    currentEntity.setOptuser(getUserManagedBean().getCurrentUser().getUserid());
                    currentEntity.setOptdateToNow();
                    currentEntity.setCfmuser(null);
                    currentEntity.setCfmdate(null);
                    superEJB.unverify(currentEntity);
                    doAfterUnverify();
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info", "更新成功!"));
                } else {
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Warn", "取消前检查失败!"));
                }
            } catch (Exception e) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(null, e.getMessage()));
            }
        } else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Warn", "没有可更新数据!"));
        }
    }

    @Override
    public void verify() {
        if (null != getCurrentEntity()) {
            try {
                if (doBeforeVerify()) {
                    currentEntity.setStatus("V");
                    currentEntity.setCfmuser(getUserManagedBean().getCurrentUser().getUserid());
                    currentEntity.setCfmdateToNow();
                    superEJB.verify(currentEntity);
                    doAfterVerify();
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info", "更新成功!"));
                } else {
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Warn", "审核前检查失败!"));
                }
            } catch (Exception e) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(null, e.getMessage()));
            }
        } else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Warn", "没有可更新数据!"));
        }
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
