/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dgpx.entity;

import com.lightshell.comm.SuperEntity;
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
@Table(name = "examcard")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ExamCard.getRowCount", query = "SELECT COUNT(e) FROM ExamCard e"),
    @NamedQuery(name = "ExamCard.findAll", query = "SELECT e FROM ExamCard e"),
    @NamedQuery(name = "ExamCard.findById", query = "SELECT e FROM ExamCard e WHERE e.id = :id"),
    @NamedQuery(name = "ExamCard.findByPId", query = "SELECT e FROM ExamCard e WHERE e.examnumber.id = :pid"),
    @NamedQuery(name = "ExamCard.findByFormId", query = "SELECT e FROM ExamCard e WHERE e.formid = :formid"),
    @NamedQuery(name = "ExamCard.findByFormIdAndCheckIn", query = "SELECT e FROM ExamCard e WHERE e.formid = :formid AND (e.status='Y' or e.status='E')"),
    @NamedQuery(name = "ExamCard.findByFormdate", query = "SELECT e FROM ExamCard e WHERE e.formdate = :formdate"),
    @NamedQuery(name = "ExamCard.findByName", query = "SELECT e FROM ExamCard e WHERE e.name = :name"),
    @NamedQuery(name = "ExamCard.findByGender", query = "SELECT e FROM ExamCard e WHERE e.gender = :gender"),
    @NamedQuery(name = "ExamCard.findByIdcard", query = "SELECT e FROM ExamCard e WHERE e.idcard = :idcard"),
    @NamedQuery(name = "ExamCard.findByExamdistrictId", query = "SELECT e FROM ExamCard e WHERE e.examdistrict.id = :examdistrictid"),
    @NamedQuery(name = "ExamCard.findByExamhallId", query = "SELECT e FROM ExamCard e WHERE e.examhall.id = :examhallid"),
    @NamedQuery(name = "ExamCard.findByStatus", query = "SELECT e FROM ExamCard e WHERE e.status = :status")})
public class ExamCard extends SuperEntity {

    @JoinColumn(name = "pid", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private ExamNumber examnumber;
    @JoinColumn(name = "examdistrictid", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private ExamDistrict examdistrict;
    @JoinColumn(name = "examhallid", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private ExamHall examhall;

    @Size(max = 20)
    @Column(name = "formid")
    private String formid;
    @Basic(optional = false)
    @NotNull
    @Column(name = "formdate")
    @Temporal(TemporalType.DATE)
    private Date formdate;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "name")
    private String name;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 10)
    @Column(name = "gender")
    private String gender;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "idcard")
    private String idcard;
    @Size(max = 20)
    @Column(name = "phone")
    private String phone;
    @Size(max = 20)
    @Column(name = "certificate")
    private String certificate; 
    @Column(name = "lastlogin")
    @Temporal(TemporalType.TIMESTAMP)
    private Date lastlogin;
    @Column(name = "newestlogin")
    @Temporal(TemporalType.TIMESTAMP)
    private Date newestlogin;
    @Column(name = "countleft")
    private Integer countleft;
    @Column(name = "timeleft")
    private Integer timeleft;
    @Size(max = 20)
    @Column(name = "seat")
    private String seat;
    @Basic(optional = false)
    @NotNull
    @Column(name = "mark")
    private BigDecimal mark;
    @Size(max = 100)
    @Column(name = "remark")
    private String remark;

    public ExamCard() {
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getIdcard() {
        return idcard;
    }

    public void setIdcard(String idcard) {
        this.idcard = idcard;
    }

    public String getSeat() {
        return seat;
    }

    public void setSeat(String seat) {
        this.seat = seat;
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
        if (!(object instanceof ExamCard)) {
            return false;
        }
        ExamCard other = (ExamCard) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.dgpx.entity.ExamCard[ id=" + id + " ]";
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

    /**
     * @return the examhall
     */
    public ExamHall getExamhall() {
        return examhall;
    }

    /**
     * @param examhall the examhall to set
     */
    public void setExamhall(ExamHall examhall) {
        this.examhall = examhall;
    }

    /**
     * @return the examnumber
     */
    public ExamNumber getExamnumber() {
        return examnumber;
    }

    /**
     * @param examnumber the examnumber to set
     */
    public void setExamnumber(ExamNumber examnumber) {
        this.examnumber = examnumber;
    }

    /**
     * @return the phone
     */
    public String getPhone() {
        return phone;
    }

    /**
     * @param phone the phone to set
     */
    public void setPhone(String phone) {
        this.phone = phone;
    }

    /**
     * @return the lastlogin
     */
    public Date getLastlogin() {
        return lastlogin;
    }

    /**
     * @param lastlogin the lastlogin to set
     */
    public void setLastlogin(Date lastlogin) {
        this.lastlogin = lastlogin;
    }

    /**
     * @return the newestlogin
     */
    public Date getNewestlogin() {
        return newestlogin;
    }

    /**
     * @param newestlogin the newestlogin to set
     */
    public void setNewestlogin(Date newestlogin) {
        this.newestlogin = newestlogin;
    }

    /**
     * @return the timeleft
     */
    public Integer getTimeleft() {
        return timeleft;
    }

    /**
     * @param timeleft the timeleft to set
     */
    public void setTimeleft(Integer timeleft) {
        this.timeleft = timeleft;
    }

    /**
     * @return the countleft
     */
    public Integer getCountleft() {
        return countleft;
    }

    /**
     * @param countleft the countleft to set
     */
    public void setCountleft(Integer countleft) {
        this.countleft = countleft;
    }

    /**
     * @return the mark
     */
    public BigDecimal getMark() {
        return mark;
    }

    /**
     * @param mark the mark to set
     */
    public void setMark(BigDecimal mark) {
        this.mark = mark;
    }

    /**
     * @return the certificate
     */
    public String getCertificate() {
        return certificate;
    }

    /**
     * @param certificate the certificate to set
     */
    public void setCertificate(String certificate) {
        this.certificate = certificate;
    }

}
