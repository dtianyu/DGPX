/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dgpx.entity;

import com.lightshell.comm.BaseDetailEntity;
import java.math.BigDecimal;
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
@Table(name = "examsetting")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ExamSetting.getRowCount", query = "SELECT COUNT(e) FROM ExamSetting e"),
    @NamedQuery(name = "ExamSetting.findAll", query = "SELECT e FROM ExamSetting e"),
    @NamedQuery(name = "ExamSetting.findById", query = "SELECT e FROM ExamSetting e WHERE e.id = :id"),
    @NamedQuery(name = "ExamSetting.findByPId", query = "SELECT e FROM ExamSetting e WHERE e.pid = :pid"),
    @NamedQuery(name = "ExamSetting.findByPformId", query = "SELECT e FROM ExamSetting e WHERE e.pformid = :pformid")})
public class ExamSetting extends BaseDetailEntity {

    @JoinColumn(name = "itemctgyid", referencedColumnName = "id")
    @ManyToOne(optional = false)
    protected ItemCategory itemcategory;

    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "pformid")
    private String pformid;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Basic(optional = false)
    @NotNull
    @Column(name = "score")
    private BigDecimal score;
    @Basic(optional = false)
    @NotNull
    @Column(name = "qty")
    private int qty;

    public ExamSetting() {
    }

    public String getPformid() {
        return pformid;
    }

    public void setPformid(String pformid) {
        this.pformid = pformid;
    }

    public BigDecimal getScore() {
        return score;
    }

    public void setScore(BigDecimal score) {
        this.score = score;
    }

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
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
        if (!(object instanceof ExamSetting)) {
            return false;
        }
        ExamSetting other = (ExamSetting) object;
        if (this.pid != other.pid) {
            return false;
        }
        if (this.pid == other.pid && this.itemcategory != other.itemcategory) {
            return false;
        }
        return this.seq == other.seq;
    }

    @Override
    public String toString() {
        return "com.dgpx.entity.ExamSetting[ id=" + id + " ]";
    }

    /**
     * @return the itemcategory
     */
    public ItemCategory getItemcategory() {
        return itemcategory;
    }

    /**
     * @param itemcategory the itemcategory to set
     */
    public void setItemcategory(ItemCategory itemcategory) {
        this.itemcategory = itemcategory;
    }

}
