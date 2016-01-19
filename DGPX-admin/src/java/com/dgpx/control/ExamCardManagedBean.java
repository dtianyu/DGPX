/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dgpx.control;

import com.dgpx.ejb.ExamCardBean;
import com.dgpx.ejb.ExamDistrictBean;
import com.dgpx.ejb.ExamHallBean;
import com.dgpx.ejb.ExamNumberBean;
import com.dgpx.entity.ExamCard;
import com.dgpx.entity.ExamDistrict;
import com.dgpx.entity.ExamHall;
import com.dgpx.entity.ExamNumber;
import com.dgpx.lazy.ExamCardModel;
import com.dgpx.rpt.ExamPaperReport;
import com.dgpx.web.SuperSingleBean;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import org.eclipse.birt.report.engine.api.EngineConstants;

/**
 *
 * @author kevindong
 */
@ManagedBean(name = "examCardManagedBean")
@SessionScoped
public class ExamCardManagedBean extends SuperSingleBean<ExamCard> {

    @EJB
    protected ExamNumberBean examNumberBean;
    @EJB
    protected ExamHallBean examHallBean;
    @EJB
    protected ExamDistrictBean examDistrictBean;
    @EJB
    protected ExamCardBean examCardBean;

    protected List<ExamDistrict> examDistrictList;
    protected List<ExamHall> examHallList;
    protected List<ExamNumber> examNumberList;

    protected String queryIdCard;

    public ExamCardManagedBean() {
        super(ExamCard.class);
    }

    @Override
    public void create() {
        super.create();
        newEntity.setFormdate(this.getDate());
        newEntity.setMark(BigDecimal.ZERO);
    }

    @Override
    protected boolean doBeforePersist() throws Exception {
        if (this.newEntity != null && this.currentSysprg != null) {
            String formid = "";
            if (this.currentSysprg.getNoauto()) {
                formid = this.superEJB.getFormId(newEntity.getFormdate(), this.currentSysprg.getNolead(), this.currentSysprg.getNoformat(), this.currentSysprg.getNoseqlen());
            }
            this.newEntity.setFormid(formid);
            return true;
        }
        return false;
    }

    @Override
    public void init() {
        setSuperEJB(examCardBean);
        setModel(new ExamCardModel(examCardBean));
        setExamDistrictList(examDistrictBean.findByStatus("V"));
        setExamHallList(examHallBean.findByStatus("V"));
        HashMap<String, Object> f = new HashMap<>();
        f.put("status", "N");
        f.put("formdateBegin", this.getDate());
        setExamNumberList(examNumberBean.findAll(f));
        super.init();
    }

    @Override
    public void print() throws Exception {
        if (currentEntity == null) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Warn", "没有可打印数据!"));
            return;
        }
        //设置报表参数
        HashMap<String, Object> params = new HashMap<>();
        params.put("id", currentEntity.getId());
        params.put("JNDIName", this.currentSysprg.getRptjndi());
        //设置报表名称
        String reportName = reportPath + this.currentSysprg.getRptdesign();
        String outputName = reportOutputPath + currentEntity.getFormid() + "." + reportOutputFormat;
        this.reportViewPath = reportViewContext + currentEntity.getFormid() + "." + reportOutputFormat;
        try {
            //初始配置
            this.reportInitAndConfig();
            //生成报表
            this.reportRunAndOutput(reportName, params, outputName, reportOutputFormat, null);
            //预览报表
            this.preview();
        } catch (Exception ex) {
            throw ex;
        }
    }

    @Override
    public void query() {
        if (this.model != null && this.model.getFilterFields() != null) {
            this.model.getFilterFields().clear();
            if (this.queryFormId != null && !"".equals(this.queryFormId)) {
                this.model.getFilterFields().put("formid", this.queryFormId);
            }
            if (this.queryName != null && !"".equals(this.queryName)) {
                this.model.getFilterFields().put("name", this.queryName);
            }
            if (this.queryIdCard != null && !"".equals(this.queryIdCard)) {
                this.model.getFilterFields().put("idcard", this.queryIdCard);
            }
            if (this.queryState != null && !"ALL".equals(this.queryState)) {
                this.model.getFilterFields().put("status", this.queryState);
            }
        }
    }

    @Override
    protected void reportInitAndConfig() {
        super.reportInitAndConfig();
        reportEngineConfig.getAppContext().put(EngineConstants.APPCONTEXT_CLASSLOADER_KEY, ExamPaperReport.class.getClassLoader());
    }

    /**
     * @return the queryIdCard
     */
    public String getQueryIdCard() {
        return queryIdCard;
    }

    /**
     * @param queryIdCard the queryIdCard to set
     */
    public void setQueryIdCard(String queryIdCard) {
        this.queryIdCard = queryIdCard;
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

    /**
     * @return the examNumberList
     */
    public List<ExamNumber> getExamNumberList() {
        return examNumberList;
    }

    /**
     * @param examNumberList the examNumberList to set
     */
    public void setExamNumberList(List<ExamNumber> examNumberList) {
        this.examNumberList = examNumberList;
    }

}
