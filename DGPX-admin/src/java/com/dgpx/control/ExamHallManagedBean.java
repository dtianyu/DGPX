/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dgpx.control;

import com.dgpx.ejb.ExamDistrictBean;
import com.dgpx.ejb.ExamHallBean;
import com.dgpx.entity.ExamDistrict;
import com.dgpx.entity.ExamHall;
import com.dgpx.lazy.ExamHallModel;
import com.dgpx.web.SuperSingleBean;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

/**
 *
 * @author kevindong
 */
@ManagedBean(name = "examHallManagedBean")
@SessionScoped
public class ExamHallManagedBean extends SuperSingleBean<ExamHall> {

    @EJB
    private ExamDistrictBean examDistrictBean;

    @EJB
    private ExamHallBean examHallBean;

    protected List<ExamDistrict> examDistrictList;

    public ExamHallManagedBean() {
        super(ExamHall.class);
    }

    @Override
    public void create() {
        super.create();
        newEntity.setMaxnum(0);
        newEntity.setNownum(0);
    }

    @Override
    public void init() {
        setSuperEJB(examHallBean);
        setModel(new ExamHallModel(examHallBean));
        setExamDistrictList(examDistrictBean.findByStatus("V"));
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

    /**
     * @return the examDistrictList
     */
    public List<ExamDistrict> getExamDistrictList() {
        return examDistrictList;
    }

    /**
     * @param examDistrictList the examDistrictList to set
     */
    public void setExamDistrictList(List<ExamDistrict> examDistrictList) {
        this.examDistrictList = examDistrictList;
    }

}
