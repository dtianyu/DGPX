/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dgpx.entity;

import com.lightshell.comm.SuperEntity;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author kevindong
 */
@Entity
@Table(name = "sysmodule")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Sysmodule.getRowCount", query = "SELECT COUNT(s) FROM Sysmodule s"),
    @NamedQuery(name = "Sysmodule.findAll", query = "SELECT s FROM Sysmodule s ORDER BY s.sortid"),
    @NamedQuery(name = "Sysmodule.findById", query = "SELECT s FROM Sysmodule s WHERE s.id = :id"),
    @NamedQuery(name = "Sysmodule.findByName", query = "SELECT s FROM Sysmodule s WHERE s.name = :name"),
    @NamedQuery(name = "Sysmodule.findByStatus", query = "SELECT s FROM Sysmodule s WHERE s.status = :status")})
public class Sysmodule extends SuperEntity {

    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "name")
    private String name;
    @Basic(optional = false)
    @NotNull
    @Column(name = "sortid")
    private int sortid;
    @Size(max = 50)
    @Column(name = "descript")
    private String descript;

    public Sysmodule() {
    }

    public Sysmodule(Integer id) {
        this.id = id;
    }

    public Sysmodule(Integer id, String name, int sortid, String status) {
        this.id = id;
        this.name = name;
        this.sortid = sortid;
        this.status = status;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getSortid() {
        return sortid;
    }

    public void setSortid(int sortid) {
        this.sortid = sortid;
    }

    public String getDescript() {
        return descript;
    }

    public void setDescript(String descript) {
        this.descript = descript;
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
        if (!(object instanceof Sysmodule)) {
            return false;
        }
        Sysmodule other = (Sysmodule) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.hhsc.entity.Sysmodule[ id=" + id + " ]";
    }

}
