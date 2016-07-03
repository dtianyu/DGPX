/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dgpx.control;

import com.dgpx.ejb.ExamCardBean;
import com.dgpx.ejb.ExamCategoryBean;
import com.dgpx.ejb.ExamNumberBean;
import com.dgpx.ejb.ExamSettingBean;
import com.dgpx.ejb.ItemCategoryBean;
import com.dgpx.entity.ExamCard;
import com.dgpx.entity.ExamCategory;
import com.dgpx.entity.ExamNumber;
import com.dgpx.entity.ExamSetting;
import com.dgpx.entity.ItemCategory;
import com.dgpx.lazy.ExamNumberModel;
import com.dgpx.rpt.ExamPaperReport;
import com.dgpx.web.SuperMultiBean;
import com.lowagie.text.pdf.PdfCopyFields;
import com.lowagie.text.pdf.PdfReader;
import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.OutputStream;
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
    @EJB
    private ExamCardBean examCardBean;

    private List<ExamCategory> examCategoryList;
    private List<ItemCategory> itemCategoryList;

    public ExamNumberManagedBean() {
        super(ExamNumber.class, ExamSetting.class);
    }

    @Override
    public void create() {
        super.create();
        newEntity.setExamcategory(examCategoryBean.findByFormid("GYSZ"));
        newEntity.setExamcount(0);
        newEntity.setScore(BigDecimal.valueOf(100));
        newEntity.setExamtime(60);
        newEntity.setPapercount(4);
        newEntity.setRegnum(0);
        newEntity.setActnum(0);
        newEntity.setPassnum(0);
        newEntity.setFailnum(0);
        FacesContext fc = FacesContext.getCurrentInstance();
        int beginIndex = fc.getViewRoot().getViewId().lastIndexOf("/") + 1;
        int endIndex = fc.getViewRoot().getViewId().lastIndexOf(".");
        if ("examnumber".equals(fc.getViewRoot().getViewId().substring(beginIndex, endIndex))) {
            this.addedDetailList.clear();
            this.editedDetailList.clear();
            this.deletedDetailList.clear();
            this.createDetail();
            this.newDetail.setItemcategory(itemCategoryBean.findById(1));
            this.newDetail.setQty(55);
            this.doConfirmDetail();
            this.createDetail();
            this.newDetail.setItemcategory(itemCategoryBean.findById(2));
            this.newDetail.setQty(45);
            this.doConfirmDetail();
        }
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
        //存在报考记录不能删除
        examNumberBean.setDetail(entity.getId());
        if (!examNumberBean.getDetailList().isEmpty()) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Warn", "已有报名资料,不能删除!"));
            return false;
        }
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
    protected boolean doBeforeUnverify() throws Exception {
        if (currentEntity == null) {
            return false;
        }
        //存在考试记录不能取消
        if (examNumberBean.getRowCountHasExam(currentEntity) != 0) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Warn", "已有考试资料,不能取消!"));
            return false;
        }
        return super.doBeforeUnverify();
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
    public void print() throws Exception {

        if (currentEntity == null) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Warn", "没有可打印数据"));
            return;
        }
        examNumberBean.setDetail(currentEntity.getId());
        if (examNumberBean.getDetailList().isEmpty()) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Warn", "没有可打印数据"));
            return;
        } else {
            for (ExamCard c : examNumberBean.getDetailList()) {
                if (!c.getStatus().equals("Z")) {
                    c.setStatus("Z");
                    examCardBean.update(c);
                }
            }
        }

        String reportName, outputName;
        //设置报表名称
        reportName = reportPath + this.currentSysprg.getRptdesign();
        //设置导出文件名称
        outputName = reportOutputPath + currentEntity.getFormid() + ".pdf";
        OutputStream os = new FileOutputStream(outputName);
        PdfCopyFields pdfCopy = new PdfCopyFields(os);
        HashMap<String, Object> params = new HashMap<>();
        ByteArrayOutputStream baos;
        for (ExamCard c : examNumberBean.getDetailList()) {
            //设置报表参数
            baos = new ByteArrayOutputStream();
            params.put("id", c.getId());
            params.put("JNDIName", this.currentSysprg.getRptjndi());
            try {
                //初始配置
                this.reportInitAndConfig();
                //生成报表
                this.reportRunAndOutput(reportName, params, null, "pdf", baos);
            } catch (Exception ex) {
                throw ex;
            } finally {
                params.clear();
            }
            pdfCopy.addDocument(new PdfReader(baos.toByteArray()));
        }
        pdfCopy.close();
        this.reportViewPath = reportViewContext + currentEntity.getFormid() + ".pdf";
        this.preview();
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
            if (this.queryDateBegin != null) {
                this.model.getFilterFields().put("formdateBegin", this.queryDateBegin);
            }
            if (this.queryDateEnd != null) {
                this.model.getFilterFields().put("formdateEnd", this.queryDateEnd);
            }
        }
    }

    @Override
    protected void reportInitAndConfig() {
        super.reportInitAndConfig();
        reportEngineConfig.getAppContext().put(EngineConstants.APPCONTEXT_CLASSLOADER_KEY, ExamPaperReport.class.getClassLoader());
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
