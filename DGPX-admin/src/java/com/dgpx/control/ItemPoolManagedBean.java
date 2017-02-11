/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dgpx.control;

import com.dgpx.ejb.ExamCategoryBean;
import com.dgpx.ejb.ItemCategoryBean;
import com.dgpx.ejb.ItemPoolBean;
import com.dgpx.ejb.KnowledgeBean;
import com.dgpx.entity.ExamCategory;
import com.dgpx.entity.ItemCategory;
import com.dgpx.entity.ItemPool;
import com.dgpx.entity.Knowledge;
import com.dgpx.lazy.ItemPoolModel;
import com.dgpx.web.SuperSingleBean;
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
@ManagedBean(name = "itemPoolManagedBean")
@SessionScoped
public class ItemPoolManagedBean extends SuperSingleBean<ItemPool> {

    @EJB
    private ItemPoolBean itempoolBean;
    @EJB
    private KnowledgeBean knowledgeBean;
    @EJB
    private ItemCategoryBean itemCategoryBean;
    @EJB
    private ExamCategoryBean examCategoryBean;

    protected List<ExamCategory> examCategoryList;
    protected List<ItemCategory> itemCategoryList;
    protected List<Knowledge> knowledgeList;

    protected String queryItemctgyId;
    protected String queryKnowledgeId;

    public ItemPoolManagedBean() {
        super(ItemPool.class);
    }

    @Override
    public void create() {
        super.create();
        newEntity.setChoice1("");
        newEntity.setChoice2("");
        newEntity.setChoice3("");
        newEntity.setChoice4("");
        newEntity.setChoice5("");
        newEntity.setChoice6("");
        newEntity.setChoice7("");
        newEntity.setChoice8("");
        newEntity.setAnswer("");
        newEntity.setKey1(false);
        newEntity.setKey2(false);
        newEntity.setKey3(false);
        newEntity.setKey4(false);
        newEntity.setKey5(false);
        newEntity.setKey6(false);
        newEntity.setKey7(false);
        newEntity.setKey8(false);
    }

    @Override
    protected boolean doBeforeDelete(ItemPool entity) throws Exception {
        return super.doBeforeDelete(entity);
    }

    @Override
    protected boolean doBeforePersist() throws Exception {
        if (newEntity == null) {
            return false;
        }
        int count;
        switch (newEntity.getItemcategory().getFormid()) {
            case "PD":
                count = 0;
                if (newEntity.getKey1()) {
                    newEntity.setAnswer("true");
                    count++;
                }
                if (newEntity.getKey2()) {
                    newEntity.setAnswer("false");
                    count++;
                }
                if (count != 1) {
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Fatal", "判断题答案不唯一!"));
                    return false;
                }
                break;
            case "D1":
            case "JS":
                count = 0;
                if (newEntity.getKey1()) {
                    newEntity.setAnswer("A");
                    count++;
                }
                if (newEntity.getKey2()) {
                    newEntity.setAnswer("B");
                    count++;
                }
                if (newEntity.getKey3()) {
                    newEntity.setAnswer("C");
                    count++;
                }
                if (newEntity.getKey4()) {
                    newEntity.setAnswer("D");
                    count++;
                }
                if (newEntity.getKey5()) {
                    newEntity.setAnswer("E");
                    count++;
                }
                if (newEntity.getKey6()) {
                    newEntity.setAnswer("F");
                    count++;
                }
                if (newEntity.getKey7()) {
                    newEntity.setAnswer("G");
                    count++;
                }
                if (newEntity.getKey8()) {
                    newEntity.setAnswer("H");
                    count++;
                }
                if (count != 1) {
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Fatal", "单选或计算答案不唯一!"));
                    return false;
                }
                break;
            case "DN":
                count = 0;
                StringBuilder sb = new StringBuilder();
                if (newEntity.getKey1()) {
                    sb.append("A");
                    count++;
                }
                if (newEntity.getKey2()) {
                    sb.append("B");
                    count++;
                }
                if (newEntity.getKey3()) {
                    sb.append("C");
                    count++;
                }
                if (newEntity.getKey4()) {
                    sb.append("D");
                    count++;
                }
                if (newEntity.getKey5()) {
                    sb.append("E");
                    count++;
                }
                if (newEntity.getKey6()) {
                    sb.append("F");
                    count++;
                }
                if (newEntity.getKey7()) {
                    sb.append("G");
                    count++;
                }
                if (newEntity.getKey8()) {
                    sb.append("H");
                    count++;
                }
                if (count == 0) {
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Fatal", "必须有一个答案!"));
                    return false;
                }
                newEntity.setAnswer(sb.toString());
                break;
        }
        return super.doBeforePersist();
    }

    @Override
    protected boolean doBeforeUpdate() throws Exception {
        if (currentEntity == null) {
            return false;
        }
        int count;
        switch (currentEntity.getItemcategory().getFormid()) {
            case "PD":
                count = 0;
                if (currentEntity.getKey1()) {
                    currentEntity.setAnswer("true");
                    count++;
                }
                if (currentEntity.getKey2()) {
                    currentEntity.setAnswer("false");
                    count++;
                }
                if (count != 1) {
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Fatal", "判断题答案不唯一!"));
                    return false;
                }
                break;
            case "D1":
            case "JS":
                count = 0;
                if (currentEntity.getKey1()) {
                    currentEntity.setAnswer("A");
                    count++;
                }
                if (currentEntity.getKey2()) {
                    currentEntity.setAnswer("B");
                    count++;
                }
                if (currentEntity.getKey3()) {
                    currentEntity.setAnswer("C");
                    count++;
                }
                if (currentEntity.getKey4()) {
                    currentEntity.setAnswer("D");
                    count++;
                }
                if (currentEntity.getKey5()) {
                    currentEntity.setAnswer("E");
                    count++;
                }
                if (currentEntity.getKey6()) {
                    currentEntity.setAnswer("F");
                    count++;
                }
                if (currentEntity.getKey7()) {
                    currentEntity.setAnswer("G");
                    count++;
                }
                if (currentEntity.getKey8()) {
                    currentEntity.setAnswer("H");
                    count++;
                }
                if (count != 1) {
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Fatal", "单选或计算答案不唯一!"));
                    return false;
                }
                break;
            case "DN":
                count = 0;
                StringBuilder sb = new StringBuilder();
                if (currentEntity.getKey1()) {
                    sb.append("A");
                    count++;
                }
                if (currentEntity.getKey2()) {
                    sb.append("B");
                    count++;
                }
                if (currentEntity.getKey3()) {
                    sb.append("C");
                    count++;
                }
                if (currentEntity.getKey4()) {
                    sb.append("D");
                    count++;
                }
                if (currentEntity.getKey5()) {
                    sb.append("E");
                    count++;
                }
                if (currentEntity.getKey6()) {
                    sb.append("F");
                    count++;
                }
                if (currentEntity.getKey7()) {
                    sb.append("G");
                    count++;
                }
                if (currentEntity.getKey8()) {
                    sb.append("H");
                    count++;
                }
                if (count == 0) {
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Fatal", "必须有一个答案!"));
                    return false;
                }
                currentEntity.setAnswer(sb.toString());
                break;
        }
        return super.doBeforeUpdate();
    }

    @Override
    public void init() {
        setSuperEJB(itempoolBean);
        setModel(new ItemPoolModel(itempoolBean));
        setExamCategoryList(examCategoryBean.findByStatus("V"));
        setItemCategoryList(itemCategoryBean.findByStatus("V"));
        setKnowledgeList(knowledgeBean.findByStatus("V"));
        super.init();
    }

    @Override
    public void query() {
        if (this.model != null && this.model.getFilterFields() != null) {
            this.model.getFilterFields().clear();
            if (this.queryItemctgyId != null && !"".equals(this.queryItemctgyId)) {
                this.model.getFilterFields().put("itemcategory.id", Integer.parseInt(this.queryItemctgyId));
            }
            if (this.queryKnowledgeId != null && !"".equals(this.queryKnowledgeId)) {
                this.model.getFilterFields().put("knowledge.id", Integer.parseInt(this.queryKnowledgeId));
            }
            if (this.queryState != null && !"ALL".equals(this.queryState)) {
                model.getFilterFields().put("status", this.queryState);
            }
        }
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
     * @return the knowledgeList
     */
    public List<Knowledge> getKnowledgeList() {
        return knowledgeList;
    }

    /**
     * @param knowledgeList the knowledgeList to set
     */
    public void setKnowledgeList(List<Knowledge> knowledgeList) {
        this.knowledgeList = knowledgeList;
    }

    /**
     * @return the queryItemctgyId
     */
    public String getQueryItemctgyId() {
        return queryItemctgyId;
    }

    /**
     * @param queryItemctgyId the queryItemctgyId to set
     */
    public void setQueryItemctgyId(String queryItemctgyId) {
        this.queryItemctgyId = queryItemctgyId;
    }

    /**
     * @return the queryKnowledgeId
     */
    public String getQueryKnowledgeId() {
        return queryKnowledgeId;
    }

    /**
     * @param queryKnowledgeId the queryKnowledgeId to set
     */
    public void setQueryKnowledgeId(String queryKnowledgeId) {
        this.queryKnowledgeId = queryKnowledgeId;
    }

}
