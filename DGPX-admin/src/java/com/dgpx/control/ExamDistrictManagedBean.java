/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dgpx.control;

import com.dgpx.ejb.ExamDistrictBean;
import com.dgpx.entity.ExamDistrict;
import com.dgpx.lazy.ExamDistrictModel;
import com.dgpx.web.SuperSingleBean;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

/**
 *
 * @author kevindong
 */
@ManagedBean(name = "examDistrictManagedBean")
@SessionScoped
public class ExamDistrictManagedBean extends SuperSingleBean<ExamDistrict> {

    @EJB
    private ExamDistrictBean examDistrictBean;

    public ExamDistrictManagedBean() {
        super(ExamDistrict.class);
    }

    @Override
    public void init() {
        setSuperEJB(examDistrictBean);
        setModel(new ExamDistrictModel(examDistrictBean));
        super.init();
    }

    @Override
    public void query() {
        if (this.model != null && this.model.getFilterFields() != null) {
            this.model.getFilterFields().clear();
            if (this.queryName != null && !"".equals(this.queryName)) {
                this.model.getFilterFields().put("name", this.queryName);
            }
            if (this.queryState != null && !"ALL".equals(this.queryState)) {
                this.model.getFilterFields().put("status", this.queryState);
            }
        }
    }

}
