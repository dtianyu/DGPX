/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dgpx.control;

import com.dgpx.ejb.ExamPaperBean;
import com.dgpx.entity.ExamPaper;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

/**
 *
 * @author kevindong
 */
@ManagedBean(name = "examCheckInManagedBean")
@SessionScoped
public class ExamCheckInManagedBean extends ExamCardManagedBean {

    @EJB
    private ExamPaperBean examPaperBean;

    /**
     * Creates a new instance of ExamCheckInManagedBean
     */
    public ExamCheckInManagedBean() {
    }

    @Override
    protected boolean doBeforeUnverify() throws Exception {
        if (this.currentEntity != null) {
            List<ExamPaper> examPaperList = examPaperBean.findByPId(this.currentEntity.getId());
            if (examPaperList != null && !examPaperList.isEmpty()) {
                for (ExamPaper paper : examPaperList) {
                    examPaperBean.delete(paper);
                }
            }
            return true;
        }
        return false;
    }

    @Override
    public void init() {
        super.init();
        this.model.getFilterFields().put("status", "N");
        this.model.getSortFields().put("status", "ASC");
        this.model.getSortFields().put("cfmdate", "ASC");
    }

    @Override
    public void reset() {
        super.reset();
        this.model.getFilterFields().put("status", "N");
    }

    @Override
    protected void setToolBar() {
        if (currentEntity != null && currentSysprg != null && currentEntity.getStatus() != null) {
            switch (currentEntity.getStatus()) {
                case "N":
                    this.doEdit = currentSysprg.getDoedit() && true;
                    this.doDel = currentSysprg.getDodel() && true;
                    this.doCfm = currentSysprg.getDocfm() && true;
                    this.doUnCfm = false;
                    break;
                case "V":
                case "Y":
                    this.doEdit = currentSysprg.getDoedit() && false;
                    this.doDel = currentSysprg.getDodel() && false;
                    this.doCfm = false;
                    this.doUnCfm = currentSysprg.getDouncfm() && true;
                    break;
                default:
                    this.doEdit = false;
                    this.doDel = false;
                    this.doCfm = false;
                    this.doUnCfm = false;
            }
        } else {
            this.doEdit = false;
            this.doDel = false;
            this.doCfm = false;
            this.doUnCfm = false;
        }
    }

    @Override
    public void unverify() {
        if (null != getCurrentEntity()) {
            try {
                if (doBeforeUnverify()) {
                    currentEntity.setStatus("N");
                    currentEntity.setOptuser(getUserManagedBean().getCurrentUser().getUserid());
                    currentEntity.setOptdateToNow();
                    currentEntity.setCfmuser(null);
                    currentEntity.setCfmdate(null);
                    superEJB.unverify(currentEntity);
                    doAfterUnverify();
                    showMsg(FacesMessage.SEVERITY_INFO, "Info", "更新成功!");
                } else {
                    showMsg(FacesMessage.SEVERITY_WARN, "Warn", "取消前检查失败!");
                }
            } catch (Exception e) {
                showMsg(FacesMessage.SEVERITY_ERROR, "Error", e.getMessage());
            }
        } else {
            showMsg(FacesMessage.SEVERITY_WARN, "Warn", "没有可更新数据!");
        }
    }

    @Override
    public void verify() {
        if (null != getCurrentEntity()) {
            try {
                if (doBeforeVerify()) {
                    currentEntity.setCountleft(currentEntity.getExamnumber().getExamcount());
                    currentEntity.setTimeleft(currentEntity.getExamnumber().getExamtime());
                    currentEntity.setStatus("V");
                    currentEntity.setCfmuser(getUserManagedBean().getCurrentUser().getUserid());
                    currentEntity.setCfmdateToNow();
                    superEJB.verify(currentEntity);
                    doAfterVerify();
                    showMsg(FacesMessage.SEVERITY_INFO, "Info", "更新成功!");
                } else {
                    showMsg(FacesMessage.SEVERITY_WARN, "Warn", "审核前检查失败!");
                }
            } catch (Exception e) {
                showMsg(FacesMessage.SEVERITY_ERROR, "Error", e.getMessage());
            }
        } else {
            showMsg(FacesMessage.SEVERITY_WARN, "Warn", "没有可更新数据!");
        }
    }
}
