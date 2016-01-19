/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dgpx.control;

import com.dgpx.ejb.ExamCategoryBean;
import com.dgpx.entity.ExamCategory;
import com.dgpx.lazy.ExamCategoryModel;
import com.dgpx.web.SuperSingleBean;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

/**
 *
 * @author kevindong
 */
@ManagedBean
@SessionScoped
public class ExamCategoryManagedBean extends SuperSingleBean<ExamCategory> {

    @EJB
    private ExamCategoryBean examCategoryBean;

    public ExamCategoryManagedBean() {
        super(ExamCategory.class);
    }

    @Override
    public void create() {
        super.create();
        newEntity.setExamcount(0);
    }

    @Override
    protected boolean doBeforeDelete(ExamCategory entity) throws Exception {
        if (examCategoryBean.getRowCountHasExamNumber(entity) != 0) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Warn", "已有考试资料,不能删除!"));
            return false;
        }
        return false;
        //return super.doBeforeDelete(entity);
    }

    @Override
    public void init() {
        superEJB = examCategoryBean;
        setModel(new ExamCategoryModel(examCategoryBean));
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

}
