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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
@Table(name = "examhall")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ExamHall.getRowCount", query = "SELECT COUNT(e) FROM ExamHall e"),
    @NamedQuery(name = "ExamHall.findAll", query = "SELECT e FROM ExamHall e"),
    @NamedQuery(name = "ExamHall.findById", query = "SELECT e FROM ExamHall e WHERE e.id = :id"),
    @NamedQuery(name = "ExamHall.findByPId", query = "SELECT e FROM ExamHall e WHERE e.examdistrict.id = :pid"),
    @NamedQuery(name = "ExamHall.findByName", query = "SELECT e FROM ExamHall e WHERE e.name = :name"),
    @NamedQuery(name = "ExamHall.findByStatus", query = "SELECT e FROM ExamHall e WHERE e.status = :status")})
public class ExamHall extends BaseEntityWithOperate {

    @JoinColumn(name = "pid", referencedColumnName = "id")
    @ManyToOne(optional = false)
    protected ExamDistrict examdistrict;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "name")
    private String name;
    @Size(max = 40)
    @Column(name = "address")
    private String address;
    @Basic(optional = false)
    @NotNull
    @Column(name = "maxnum")
    private int maxnum;
    @Size(max = 20)
    @Column(name = "nowexam")
    private String nowexam;
    @Basic(optional = false)
    @NotNull
    @Column(name = "nownum")
    private int nownum;
    @Size(max = 100)
    @Column(name = "remark")
    private String remark;

    public ExamHall() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getMaxnum() {
        return maxnum;
    }

    public void setMaxnum(int maxnum) {
        this.maxnum = maxnum;
    }

    public String getNowexam() {
        return nowexam;
    }

    public void setNowexam(String nowexam) {
        this.nowexam = nowexam;
    }

    public int getNownum() {
        return nownum;
    }

    public void setNownum(int nownum) {
        this.nownum = nownum;
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
        if (!(object instanceof ExamHall)) {
            return false;
        }
        ExamHall other = (ExamHall) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.dgpx.entity.ExamHall[ id=" + id + " ]";
    }

    /**
     * @return the examdistrict
     */
    public ExamDistrict getExamdistrict() {
        return examdistrict;
    }

    /**
     * @param examdistrict the examdistrict to set
     */
    public void setExamdistrict(ExamDistrict examdistrict) {
        this.examdistrict = examdistrict;
    }

}
