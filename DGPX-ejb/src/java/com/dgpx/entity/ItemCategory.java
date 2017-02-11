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
@Table(name = "itemcategory")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ItemCategory.getRowCount", query = "SELECT COUNT(i) FROM ItemCategory i"),
    @NamedQuery(name = "ItemCategory.findAll", query = "SELECT i FROM ItemCategory i"),
    @NamedQuery(name = "ItemCategory.findById", query = "SELECT i FROM ItemCategory i WHERE i.id = :id"),
    @NamedQuery(name = "ItemCategory.findByFormId", query = "SELECT i FROM ItemCategory i WHERE i.formid = :formid"),
    @NamedQuery(name = "ItemCategory.findByName", query = "SELECT i FROM ItemCategory i WHERE i.name = :name"),
    @NamedQuery(name = "ItemCategory.findByStatus", query = "SELECT i FROM ItemCategory i WHERE i.status = :status")})
public class ItemCategory extends SuperEntity {

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
    @Column(name = "itemcount")
    private Integer itemcount;
    @Size(max = 100)
    @Column(name = "remark")
    private String remark;

    public ItemCategory() {
    }

    public ItemCategory(Integer id) {
        this.id = id;
    }

    public ItemCategory(Integer id, String formid, String name, String status) {
        this.id = id;
        this.formid = formid;
        this.name = name;
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

    public Integer getItemcount() {
        return itemcount;
    }

    public void setItemcount(Integer itemcount) {
        this.itemcount = itemcount;
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
        if (!(object instanceof ItemCategory)) {
            return false;
        }
        ItemCategory other = (ItemCategory) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.dgpx.entity.ItemCategory[ id=" + id + " ]";
    }

}
