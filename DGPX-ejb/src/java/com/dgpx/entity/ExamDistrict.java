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
@Table(name = "examdistrict")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ExamDistrict.getRowCount", query = "SELECT COUNT(e) FROM ExamDistrict e"),
    @NamedQuery(name = "ExamDistrict.findAll", query = "SELECT e FROM ExamDistrict e"),
    @NamedQuery(name = "ExamDistrict.findById", query = "SELECT e FROM ExamDistrict e WHERE e.id = :id"),
    @NamedQuery(name = "ExamDistrict.findByFormId", query = "SELECT e FROM ExamDistrict e WHERE e.formid = :formid"),
    @NamedQuery(name = "ExamDistrict.findByName", query = "SELECT e FROM ExamDistrict e WHERE e.name = :name"),
    @NamedQuery(name = "ExamDistrict.findByAddress", query = "SELECT e FROM ExamDistrict e WHERE e.address = :address"),
    @NamedQuery(name = "ExamDistrict.findByZipcode", query = "SELECT e FROM ExamDistrict e WHERE e.zipcode = :zipcode"),
    @NamedQuery(name = "ExamDistrict.findByTel", query = "SELECT e FROM ExamDistrict e WHERE e.tel = :tel"),
    @NamedQuery(name = "ExamDistrict.findByTel2", query = "SELECT e FROM ExamDistrict e WHERE e.tel2 = :tel2"),
    @NamedQuery(name = "ExamDistrict.findByFax", query = "SELECT e FROM ExamDistrict e WHERE e.fax = :fax"),
    @NamedQuery(name = "ExamDistrict.findByFax2", query = "SELECT e FROM ExamDistrict e WHERE e.fax2 = :fax2"),
    @NamedQuery(name = "ExamDistrict.findByContacter", query = "SELECT e FROM ExamDistrict e WHERE e.contacter = :contacter"),
    @NamedQuery(name = "ExamDistrict.findByPhone", query = "SELECT e FROM ExamDistrict e WHERE e.phone = :phone"),
    @NamedQuery(name = "ExamDistrict.findByContacter2", query = "SELECT e FROM ExamDistrict e WHERE e.contacter2 = :contacter2"),
    @NamedQuery(name = "ExamDistrict.findByPhone2", query = "SELECT e FROM ExamDistrict e WHERE e.phone2 = :phone2"),
    @NamedQuery(name = "ExamDistrict.findByAuthcode", query = "SELECT e FROM ExamDistrict e WHERE e.authcode = :authcode"),
    @NamedQuery(name = "ExamDistrict.findByRemark", query = "SELECT e FROM ExamDistrict e WHERE e.remark = :remark"),
    @NamedQuery(name = "ExamDistrict.findByStatus", query = "SELECT e FROM ExamDistrict e WHERE e.status = :status")})
public class ExamDistrict extends SuperEntity {

    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "formid")
    private String formid;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "name")
    private String name;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 200)
    @Column(name = "address")
    private String address;
    @Size(max = 10)
    @Column(name = "zipcode")
    private String zipcode;
    @Size(max = 20)
    @Column(name = "tel")
    private String tel;
    @Size(max = 20)
    @Column(name = "tel2")
    private String tel2;
    // @Pattern(regexp="^\\(?(\\d{3})\\)?[- ]?(\\d{3})[- ]?(\\d{4})$", message="电话/传真格式无效, 应为 xxx-xxx-xxxx")//if the field contains phone or fax number consider using this annotation to enforce field validation
    @Size(max = 20)
    @Column(name = "fax")
    private String fax;
    @Size(max = 20)
    @Column(name = "fax2")
    private String fax2;
    @Size(max = 20)
    @Column(name = "contacter")
    private String contacter;
    // @Pattern(regexp="^\\(?(\\d{3})\\)?[- ]?(\\d{3})[- ]?(\\d{4})$", message="电话/传真格式无效, 应为 xxx-xxx-xxxx")//if the field contains phone or fax number consider using this annotation to enforce field validation
    @Size(max = 20)
    @Column(name = "phone")
    private String phone;
    @Size(max = 20)
    @Column(name = "contacter2")
    private String contacter2;
    @Size(max = 20)
    @Column(name = "phone2")
    private String phone2;
    @Size(max = 45)
    @Column(name = "authcode")
    private String authcode;
    @Size(max = 100)
    @Column(name = "remark")
    private String remark;

    public ExamDistrict() {
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getZipcode() {
        return zipcode;
    }

    public void setZipcode(String zipcode) {
        this.zipcode = zipcode;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getTel2() {
        return tel2;
    }

    public void setTel2(String tel2) {
        this.tel2 = tel2;
    }

    public String getFax() {
        return fax;
    }

    public void setFax(String fax) {
        this.fax = fax;
    }

    public String getFax2() {
        return fax2;
    }

    public void setFax2(String fax2) {
        this.fax2 = fax2;
    }

    public String getContacter() {
        return contacter;
    }

    public void setContacter(String contacter) {
        this.contacter = contacter;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getContacter2() {
        return contacter2;
    }

    public void setContacter2(String contacter2) {
        this.contacter2 = contacter2;
    }

    public String getPhone2() {
        return phone2;
    }

    public void setPhone2(String phone2) {
        this.phone2 = phone2;
    }

    public String getAuthcode() {
        return authcode;
    }

    public void setAuthcode(String authcode) {
        this.authcode = authcode;
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
        if (!(object instanceof ExamDistrict)) {
            return false;
        }
        ExamDistrict other = (ExamDistrict) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.dgpx.entity.ExamDistrict[ id=" + id + " ]";
    }

}
