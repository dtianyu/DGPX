/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dgpx.control;

import com.dgpx.entity.ExamCard;
import com.dgpx.lazy.ExamCardModel;
import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

/**
 *
 * @author kevindong
 */
@ManagedBean
@SessionScoped
public class ExamCardStatusBean extends ExamCardManagedBean {

    protected String queryNumberId;
    protected List<String> status;

    public ExamCardStatusBean() {
    }

    @Override
    public void init() {
        setSuperEJB(examCardBean);
        setModel(new ExamCardModel(examCardBean));
        status = new ArrayList<>();
        status.add("V");
        status.add("Y");
        status.add("E");
        status.add("Z");
        this.model.getFilterFields().put("status IN ", status);
        if (this.getModel().getDataList() != null && !this.model.getDataList().isEmpty()) {
            setCurrentEntity((ExamCard) this.getModel().getDataList().get(0));
        } else {
            setCurrentEntity(getNewEntity());
        }
    }

    @Override
    public void query() {
        if (this.model != null && this.model.getFilterFields() != null) {
            this.model.getFilterFields().clear();
            if (this.queryNumberId != null && !"".equals(this.queryNumberId)) {
                this.model.getFilterFields().put("examnumber.formid", this.queryNumberId);
            }
            if (this.queryFormId != null && !"".equals(this.queryFormId)) {
                this.model.getFilterFields().put("formid", this.queryFormId);
            }
            if (this.queryName != null && !"".equals(this.queryName)) {
                this.model.getFilterFields().put("name", this.queryName);
            }
            if (this.queryState != null && !"ALL".equals(this.queryState)) {
                this.model.getFilterFields().put("status", this.queryState);
            } else {
                this.model.getFilterFields().put("status IN ", status);
            }
        }
    }

    @Override
    public void reset() {
        super.reset();
        this.model.getFilterFields().put("status IN ", status);
    }

    /**
     * @return the queryNumberId
     */
    public String getQueryNumberId() {
        return queryNumberId;
    }

    /**
     * @param queryNumberId the queryNumberId to set
     */
    public void setQueryNumberId(String queryNumberId) {
        this.queryNumberId = queryNumberId;
    }

}
