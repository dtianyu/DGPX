/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dgpx.control;

import com.dgpx.rpt.ExamMarkReport;
import java.util.HashMap;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import org.eclipse.birt.report.engine.api.EngineConstants;

/**
 *
 * @author kevindong
 */
@ManagedBean
@SessionScoped
public class ExamMarkManagedBean extends ExamNumberManagedBean {

    /**
     * Creates a new instance of ExamMarkManagedBean
     */
    public ExamMarkManagedBean() {
    }

    @Override
    public void init() {
        super.init();
        this.model.getFilterFields().put("status", "V");
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
        String outputName = reportOutputPath + currentEntity.getFormid() + ".xls";
        this.reportViewPath = reportViewContext + currentEntity.getFormid() + ".xls";
        try {
            //初始配置
            this.reportInitAndConfig();
            //生成报表
            this.reportRunAndOutput(reportName, params, outputName, "xls", null);
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
            if (this.queryDateBegin != null) {
                this.model.getFilterFields().put("formdate", this.queryDateBegin);
            }
            this.model.getFilterFields().put("status", "V");
        }
    }

    @Override
    protected void reportInitAndConfig() {
        super.reportInitAndConfig();
        reportEngineConfig.getAppContext().put(EngineConstants.APPCONTEXT_CLASSLOADER_KEY, ExamMarkReport.class.getClassLoader());
    }

    @Override
    public void reset() {
        super.reset();
        this.model.getFilterFields().put("status", "V");
    }

}
