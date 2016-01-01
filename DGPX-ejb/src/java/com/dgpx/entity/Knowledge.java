/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dgpx.entity;

import com.lightshell.comm.BaseEntityWithOperate;
import java.math.BigDecimal;
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
@Table(name = "knowledge")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Knowledge.getRowCount", query = "SELECT COUNT(k) FROM Knowledge k"),
    @NamedQuery(name = "Knowledge.findAll", query = "SELECT k FROM Knowledge k"),
    @NamedQuery(name = "Knowledge.findById", query = "SELECT k FROM Knowledge k WHERE k.id = :id"),
    @NamedQuery(name = "Knowledge.findByFormId", query = "SELECT k FROM Knowledge k WHERE k.formid = :formid"),
    @NamedQuery(name = "Knowledge.findByName", query = "SELECT k FROM Knowledge k WHERE k.name = :name"),
    @NamedQuery(name = "Knowledge.findByStatus", query = "SELECT k FROM Knowledge k WHERE k.status = :status")})
public class Knowledge extends BaseEntityWithOperate {

    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "formid")
    private String formid;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "name")
    private String name;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Basic(optional = false)
    @NotNull
    @Column(name = "ratio")
    private BigDecimal ratio;
    @Size(max = 100)
    @Column(name = "remark")
    private String remark;

    public Knowledge() {
    }

    public Knowledge(String formid, String name, BigDecimal ratio, String status) {
        this.formid = formid;
        this.name = name;
        this.ratio = ratio;
        this.status = status;
    }

    public String getFormid() {
        return formid;
    }

    public void setFormid(String formid) {
        this.formid = formid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getRatio() {
        return ratio;
    }

    public void setRatio(BigDecimal ratio) {
        this.ratio = ratio;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
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
        if (!(object instanceof Knowledge)) {
            return false;
        }
        Knowledge other = (Knowledge) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.dgpx.entity.Knowledge[ id=" + id + " ]";
    }

}
