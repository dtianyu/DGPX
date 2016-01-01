/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dgpx.entity;

import com.lightshell.comm.BaseEntityWithOperate;
import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author kevindong
 */
@Entity
@Table(name = "examnumber")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ExamNumber.getRowCount", query = "SELECT COUNT(e) FROM ExamNumber e"),
    @NamedQuery(name = "ExamNumber.findAll", query = "SELECT e FROM ExamNumber e"),
    @NamedQuery(name = "ExamNumber.findById", query = "SELECT e FROM ExamNumber e WHERE e.id = :id"),
    @NamedQuery(name = "ExamNumber.findByFormId", query = "SELECT e FROM ExamNumber e WHERE e.formid = :formid"),
    @NamedQuery(name = "ExamNumber.findByFormdate", query = "SELECT e FROM ExamNumber e WHERE e.formdate = :formdate"),
    @NamedQuery(name = "ExamNumber.findByExamctgyId", query = "SELECT e FROM ExamNumber e WHERE e.examcategory.id = :examctgyid"),
    @NamedQuery(name = "ExamNumber.findByStatus", query = "SELECT e FROM ExamNumber e WHERE e.status = :status")})
public class ExamNumber extends BaseEntityWithOperate {

    @JoinColumn(name = "examctgyid", referencedColumnName = "id")
    @ManyToOne(optional = false)
    protected ExamCategory examcategory;

    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "formid")
    private String formid;
    @Basic(optional = false)
    @NotNull
    @Column(name = "formdate")
    @Temporal(TemporalType.DATE)
    private Date formdate;
    @Basic(optional = false)
    @NotNull
    @Column(name = "formtime")
    @Temporal(TemporalType.TIME)
    private Date formtime;
    @Basic(optional = false)
    @NotNull
    @Column(name = "examtime")
    private int examtime;
    @Column(name = "examcount")
    protected Integer examcount;
    @Basic(optional = false)
    @NotNull
    @Column(name = "score")
    protected BigDecimal score;
    @Basic(optional = false)
    @NotNull
    @Column(name = "papercount")
    private int papercount;
    @Basic(optional = false)
    @NotNull
    @Column(name = "regnum")
    private int regnum;
    @Basic(optional = false)
    @NotNull
    @Column(name = "actnum")
    private int actnum;
    @Basic(optional = false)
    @NotNull
    @Column(name = "passnum")
    private int passnum;
    @Basic(optional = false)
    @NotNull
    @Column(name = "failnum")
    private int failnum;

    public ExamNumber() {
    }

    public String getFormid() {
        return formid;
    }

    public void setFormid(String formid) {
        this.formid = formid;
    }

    public Date getFormdate() {
        return formdate;
    }

    public void setFormdate(Date formdate) {
        this.formdate = formdate;
    }

    public Date getFormtime() {
        return formtime;
    }

    public void setFormtime(Date formtime) {
        this.formtime = formtime;
    }

    public int getExamtime() {
        return examtime;
    }

    public void setExamtime(int examtime) {
        this.examtime = examtime;
    }

    public int getPapercount() {
        return papercount;
    }

    public void setPapercount(int papercount) {
        this.papercount = papercount;
    }

    public int getRegnum() {
        return regnum;
    }

    public void setRegnum(int regnum) {
        this.regnum = regnum;
    }

    public int getActnum() {
        return actnum;
    }

    public void setActnum(int actnum) {
        this.actnum = actnum;
    }

    public int getPassnum() {
        return passnum;
    }

    public void setPassnum(int passnum) {
        this.passnum = passnum;
    }

    public int getFailnum() {
        return failnum;
    }

    public void setFailnum(int failnum) {
        this.failnum = failnum;
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
        if (!(object instanceof ExamNumber)) {
            return false;
        }
        ExamNumber other = (ExamNumber) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.dgpx.entity.ExamNumber[ id=" + id + " ]";
    }

    /**
     * @return the examcategory
     */
    public ExamCategory getExamcategory() {
        return examcategory;
    }

    /**
     * @param examcategory the examcategory to set
     */
    public void setExamcategory(ExamCategory examcategory) {
        this.examcategory = examcategory;
    }

    /**
     * @return the score
     */
    public BigDecimal getScore() {
        return score;
    }

    /**
     * @param score the score to set
     */
    public void setScore(BigDecimal score) {
        this.score = score;
    }

    /**
     * @return the examcount
     */
    public Integer getExamcount() {
        return examcount;
    }

    /**
     * @param examcount the examcount to set
     */
    public void setExamcount(Integer examcount) {
        this.examcount = examcount;
    }

}
