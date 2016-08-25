/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dgpx.entity;

import com.lightshell.comm.SuperEntity;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author kevindong
 */
@Entity
@Table(name = "examseat")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ExamSeat.getRowCount", query = "SELECT COUNT(e) FROM ExamSeat e"),
    @NamedQuery(name = "ExamSeat.findAll", query = "SELECT e FROM ExamSeat e"),
    @NamedQuery(name = "ExamSeat.findById", query = "SELECT e FROM ExamSeat e WHERE e.id = :id"),
    @NamedQuery(name = "ExamSeat.findByFormId", query = "SELECT e FROM ExamSeat e WHERE e.formid = :formid"),
    @NamedQuery(name = "ExamSeat.findByName", query = "SELECT e FROM ExamSeat e WHERE e.name = :name"),
    @NamedQuery(name = "ExamSeat.findByIP", query = "SELECT e FROM ExamSeat e WHERE e.ip = :ip"),
    @NamedQuery(name = "ExamSeat.findByStyle", query = "SELECT e FROM ExamSeat e WHERE e.style = :style"),
    @NamedQuery(name = "ExamSeat.findByExamcardId", query = "SELECT e FROM ExamSeat e WHERE e.examcard.id = :examcardid"),
    @NamedQuery(name = "ExamSeat.findByStatus", query = "SELECT e FROM ExamSeat e WHERE e.status = :status"),
    @NamedQuery(name = "ExamSeat.findIdle", query = "SELECT e FROM ExamSeat e WHERE e.status = 'V' and e.style='GreenBack'")})
public class ExamSeat extends SuperEntity {

    @JoinColumn(name = "pid", referencedColumnName = "id")
    @ManyToOne(optional = false)
    protected ExamHall examhall;
    @JoinColumn(name = "examcardid", referencedColumnName = "id")
    @ManyToOne(optional = true)
    protected ExamCard examcard;
    @Size(max = 20)
    @Column(name = "formid")
    private String formid;
    @Size(max = 45)
    @Column(name = "name")
    private String name;
    @Size(max = 45)
    @Column(name = "ip")
    private String ip;
    @Size(max = 45)
    @Column(name = "style")
    private String style;
    @Size(max = 100)
    @Column(name = "remark")
    private String remark;

    public ExamSeat() {
    }

    public ExamSeat(Integer id) {
        this.id = id;
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

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getStyle() {
        return style;
    }

    public void setStyle(String style) {
        this.style = style;
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
        if (!(object instanceof ExamSeat)) {
            return false;
        }
        ExamSeat other = (ExamSeat) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.dgpx.entity.ExamSeat[ id=" + id + " ]";
    }

    /**
     * @return the examcard
     */
    public ExamCard getExamcard() {
        return examcard;
    }

    /**
     * @param examcard the examcard to set
     */
    public void setExamcard(ExamCard examcard) {
        this.examcard = examcard;
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

}
