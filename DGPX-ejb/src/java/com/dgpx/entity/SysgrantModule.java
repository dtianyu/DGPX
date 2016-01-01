/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dgpx.entity;

import com.lightshell.comm.BaseEntityWithOperate;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author kevindong
 */
@Entity
@Table(name = "sysgrantmodule")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "SysgrantModule.getRowCount", query = "SELECT COUNT(s) FROM SysgrantModule s"),
    @NamedQuery(name = "SysgrantModule.getRowCountByUserId", query = "SELECT COUNT(s) FROM SysgrantModule s WHERE s.systemuser.id = :userid "),
    @NamedQuery(name = "SysgrantModule.findAll", query = "SELECT s FROM SysgrantModule s ORDER BY s.systemuser.id,s.sysmodule.id"),
    @NamedQuery(name = "SysgrantModule.findById", query = "SELECT s FROM SysgrantModule s WHERE s.id = :id"),
    @NamedQuery(name = "SysgrantModule.findByUserId", query = "SELECT s FROM SysgrantModule s WHERE s.systemuser.id = :userid ORDER BY s.sysmodule.sortid "),
    @NamedQuery(name = "SysgrantModule.findByModuleId", query = "SELECT s FROM SysgrantModule s WHERE s.sysmodule.id = :moduleid"),
    @NamedQuery(name = "SysgrantModule.findByStatus", query = "SELECT s FROM SysgrantModule s WHERE s.status = :status")})
public class SysgrantModule extends BaseEntityWithOperate {

    @JoinColumn(name = "userid", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private SystemUser systemuser;
    @JoinColumn(name = "moduleid", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Sysmodule sysmodule;

    public SysgrantModule() {
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SysgrantModule)) {
            return false;
        }
        SysgrantModule other = (SysgrantModule) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.hhsc.entity.SysgrantModule[ id=" + id + " ]";
    }

    /**
     * @return the sysmodule
     */
    public Sysmodule getSysmodule() {
        return sysmodule;
    }

    /**
     * @param sysmodule the sysmodule to set
     */
    public void setSysmodule(Sysmodule sysmodule) {
        this.sysmodule = sysmodule;
    }

    /**
     * @return the systemuser
     */
    public SystemUser getSystemuser() {
        return systemuser;
    }

    /**
     * @param systemuser the systemuser to set
     */
    public void setSystemuser(SystemUser systemuser) {
        this.systemuser = systemuser;
    }

}
