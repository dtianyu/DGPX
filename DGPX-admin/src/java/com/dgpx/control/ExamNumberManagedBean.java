/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dgpx.control;

import com.dgpx.ejb.ExamCategoryBean;
import com.dgpx.ejb.ExamNumberBean;
import com.dgpx.ejb.ExamSettingBean;
import com.dgpx.ejb.ItemCategoryBean;
import com.dgpx.entity.ExamCategory;
import com.dgpx.entity.ExamNumber;
import com.dgpx.entity.ExamSetting;
import com.dgpx.entity.ItemCategory;
import com.dgpx.lazy.ExamNumberModel;
import com.dgpx.web.SuperMultiBean;
import java.math.BigDecimal;
import java.util.List;
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
public class ExamNumberManagedBean extends SuperMultiBean<ExamNumber, ExamSetting> {

    @EJB
    private ExamCategoryBean examCategoryBean;
    @EJB
    private ItemCategoryBean itemCategoryBean;
    @EJB
    private ExamNumberBean examNumberBean;
    @EJB
    private ExamSettingBean examSettingBean;

    private List<ExamCategory> examCategoryList;
    private List<ItemCategory> itemCategoryList;

    public ExamNumberManagedBean() {
        super(ExamNumber.class, ExamSetting.class);
    }

    @Override
    public void create() {
        super.create();
        newEntity.setExamcount(0);
        newEntity.setScore(BigDecimal.valueOf(100));
        newEntity.setRegnum(0);
        newEntity.setActnum(0);
        newEntity.setPassnum(0);
        newEntity.setFailnum(0);
    }

    @Override
    public void createDetail() {
        super.createDetail();
        newDetail.setSeq(getMaxSeq(this.detailList));
        newDetail.setScore(BigDecimal.ONE);
        this.setCurrentDetail(newDetail);
    }

    @Override
    protected boolean doBeforeDelete(ExamNumber entity) throws Exception {
        if (entity == null) {
            return false;
        }
        //需要加入对是否存在报考纪录的逻辑检查    
        setDetailList(examSettingBean.findByPId(currentEntity.getId()));
        return super.doBeforeDelete(entity);
    }

    @Override
    protected boolean doBeforePersist() throws Exception {
        if (this.newEntity != null && this.currentSysprg != null) {
            this.newEntity.setExamcount(this.newEntity.getExamcategory().getExamcount());
            String formid = "";
            if (this.currentSysprg.getNoauto()) {
                formid = this.superEJB.getFormId(newEntity.getFormdate(), this.currentSysprg.getNolead(), this.currentSysprg.getNoformat(), this.currentSysprg.getNoseqlen());
            }
            this.newEntity.setFormid(formid);
            if (this.addedDetailList != null && !this.addedDetailList.isEmpty()) {
                for (ExamSetting detail : this.addedDetailList) {
                    detail.setPformid(formid);
                }
            }
            if (this.editedDetailList != null && !this.editedDetailList.isEmpty()) {
                for (ExamSetting detail : this.editedDetailList) {
                    detail.setPformid(formid);
                }
            }
            return true;
        }
        return false;
    }

    @Override
    protected boolean doBeforeUpdate() throws Exception {
        if (this.currentEntity != null) {
            this.currentEntity.setExamcount(this.currentEntity.getExamcategory().getExamcount());
            if (this.addedDetailList != null && !this.addedDetailList.isEmpty()) {
                for (ExamSetting detail : this.addedDetailList) {
                    detail.setPformid(this.currentEntity.getFormid());
                }
            }
            if (this.editedDetailList != null && !this.editedDetailList.isEmpty()) {
                for (ExamSetting detail : this.editedDetailList) {
                    detail.setPformid(this.currentEntity.getFormid());
                }
            }
            return super.doBeforeUpdate();
        }
        return false;
    }

    @Override
    protected boolean doBeforeVerify() throws Exception {
        if (currentEntity == null) {
            return false;
        }
        if (this.detailList != null && !this.detailList.isEmpty()) {
            this.detailList.clear();
        }
        setDetailList(examSettingBean.findByPId(currentEntity.getId()));
        BigDecimal mark = BigDecimal.ZERO;
        for (ExamSetting detail : detailList) {
            mark = mark.add(detail.getScore().multiply(BigDecimal.valueOf(detail.getQty())));
        }
        if (currentEntity.getScore().compareTo(mark) == 0) {
            return true;
        } else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "合计分数不等于总分!"));
            return false;
        }
    }

    @Override
    public void init() {
        this.superEJB = examNumberBean;
        this.detailEJB = examSettingBean;
        setModel(new ExamNumberModel(examNumberBean));
        setExamCategoryList(examCategoryBean.findByStatus("V"));
        setItemCategoryList(itemCategoryBean.findByStatus("V"));
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
     * @return the itemCategoryList
     */
    public List<ItemCategory> getItemCategoryList() {
        return itemCategoryList;
    }

    /**
     * @param itemCategoryList the itemCategoryList to set
     */
    public void setItemCategoryList(List<ItemCategory> itemCategoryList) {
        this.itemCategoryList = itemCategoryList;
    }

    /**
     * @return the examCategoryList
     */
    public List<ExamCategory> getExamCategoryList() {
        return examCategoryList;
    }

    /**
     * @param examCategoryList the examCategoryList to set
     */
    public void setExamCategoryList(List<ExamCategory> examCategoryList) {
        this.examCategoryList = examCategoryList;
    }

}
