/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dgpx.control;

import com.dgpx.ejb.KnowledgeBean;
import com.dgpx.entity.Knowledge;
import com.dgpx.lazy.KnowledgeModel;
import com.dgpx.web.SuperSingleBean;
import java.math.BigDecimal;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

/**
 *
 * @author kevindong
 */
@ManagedBean
@SessionScoped
public class KnowledgeManagedBean extends SuperSingleBean<Knowledge> {

    @EJB
    private KnowledgeBean knowledgeBean;

    public KnowledgeManagedBean() {
        super(Knowledge.class);
    }

    @Override
    public void create() {
        super.create();
        newEntity.setRatio(BigDecimal.TEN);
    }

    @Override
    public void init() {
        superEJB = knowledgeBean;
        setModel(new KnowledgeModel(knowledgeBean));
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
