/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dgpx.control;

import com.dgpx.ejb.ExamHallBean;
import com.dgpx.ejb.ExamSeatBean;
import com.dgpx.entity.ExamHall;
import com.dgpx.entity.ExamSeat;
import com.dgpx.lazy.ExamSeatModel;
import com.dgpx.web.SuperSingleBean;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

/**
 *
 * @author kevindong
 */
@ManagedBean
@SessionScoped
public class ExamSeatManagedBean extends SuperSingleBean<ExamSeat> {

    @EJB
    protected ExamHallBean examHallBean;
    @EJB
    private ExamSeatBean examSeatBean;

    protected List<ExamHall> examHallList;

    public ExamSeatManagedBean() {
        super(ExamSeat.class);
    }

    @Override
    public void create() {
        super.create();
        newEntity.setStyle("GreenBack");
    }

    @Override
    protected boolean doBeforeVerify() throws Exception {
        if (currentEntity != null) {
            currentEntity.setStyle("GreenBack");
            currentEntity.setExamcard(null);
        }
        return super.doBeforeVerify();
    }

    @Override
    public void init() {
        setSuperEJB(examSeatBean);
        setModel(new ExamSeatModel(examSeatBean));
        examHallList = examHallBean.findByStatus("V");
        super.init();
    }

    @Override
    public void query() {
        if (this.model != null && this.model.getFilterFields() != null) {
            this.model.getFilterFields().clear();
            if (this.queryFormId != null && !"".equals(this.queryFormId)) {
                this.model.getFilterFields().put("formid", this.queryFormId);
            }
            if (this.queryState != null && !"ALL".equals(this.queryState)) {
                this.model.getFilterFields().put("status", this.queryState);
            }
        }
    }

    /**
     * @return the examHallList
     */
    public List<ExamHall> getExamHallList() {
        return examHallList;
    }

    /**
     * @param examHallList the examHallList to set
     */
    public void setExamHallList(List<ExamHall> examHallList) {
        this.examHallList = examHallList;
    }

}
