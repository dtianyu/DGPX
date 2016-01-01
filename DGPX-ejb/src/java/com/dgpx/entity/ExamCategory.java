/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dgpx.entity;

import com.lightshell.comm.BaseEntityWithOperate;
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
@Table(name = "examcategory")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ExamCategory.getRowCount", query = "SELECT COUNT(e) FROM ExamCategory e"),
    @NamedQuery(name = "ExamCategory.findAll", query = "SELECT e FROM ExamCategory e"),
    @NamedQuery(name = "ExamCategory.findById", query = "SELECT e FROM ExamCategory e WHERE e.id = :id"),
    @NamedQuery(name = "ExamCategory.findByFormId", query = "SELECT e FROM ExamCategory e WHERE e.formid = :formid"),
    @NamedQuery(name = "ExamCategory.findByName", query = "SELECT e FROM ExamCategory e WHERE e.name = :name"),
    @NamedQuery(name = "ExamCategory.findByStatus", query = "SELECT e FROM ExamCategory e WHERE e.status = :status")})
public class ExamCategory extends BaseEntityWithOperate {

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
    @Column(name = "examcount")
    private Integer examcount;
    @Size(max = 100)
    @Column(name = "remark")
    private String remark;

    public ExamCategory() {
    }

    public ExamCategory(String name, String code, String status) {
        this.name = name;
        this.formid = code;
        this.status = status;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFormid() {
        return formid;
    }

    public void setFormid(String formid) {
        this.formid = formid;
    }

    public Integer getExamcount() {
        return examcount;
    }

    public void setExamcount(Integer examcount) {
        this.examcount = examcount;
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
        if (!(object instanceof ExamCategory)) {
            return false;
        }
        ExamCategory other = (ExamCategory) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.dgpx.entity.ExamCategory[ id=" + id + " ]";
    }

}
