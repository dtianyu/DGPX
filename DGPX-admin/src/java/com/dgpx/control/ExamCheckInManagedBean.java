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
import javax.faces.context.FacesContext;

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
        this.model.getFilterFields().put("status", "V");
    }

    @Override
    public void reset() {
        super.reset();
        this.model.getFilterFields().put("status", "V");
    }

    @Override
    protected void setToolBar() {
        if (currentEntity != null && currentSysprg != null && currentEntity.getStatus() != null) {
            switch (currentEntity.getStatus()) {
                case "V":
                    this.doEdit = currentSysprg.getDoedit() && true;
                    this.doDel = currentSysprg.getDodel() && true;
                    this.doCfm = currentSysprg.getDocfm() && true;
                    this.doUnCfm = false;
                    break;
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
                    currentEntity.setStatus("V");//简化查询条件,此处不再提供修改状态(M)
                    currentEntity.setOptuser(getUserManagedBean().getCurrentUser().getUserid());
                    currentEntity.setOptdateToNow();
                    currentEntity.setCfmuser(null);
                    currentEntity.setCfmdate(null);
                    superEJB.unverify(currentEntity);
                    doAfterUnverify();
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info", "更新成功!"));
                } else {
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Warn", "取消前检查失败!"));
                }
            } catch (Exception e) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(null, e.getMessage()));
            }
        } else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Warn", "没有可更新数据!"));
        }
    }

    @Override
    public void verify() {
        if (null != getCurrentEntity()) {
            try {
                if (doBeforeVerify()) {
                    currentEntity.setCountleft(currentEntity.getExamnumber().getExamcount());
                    currentEntity.setTimeleft(currentEntity.getExamnumber().getExamtime());
                    currentEntity.setStatus("Y");
                    currentEntity.setCfmuser(getUserManagedBean().getCurrentUser().getUserid());
                    currentEntity.setCfmdateToNow();
                    superEJB.verify(currentEntity);
                    doAfterVerify();
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info", "更新成功!"));
                } else {
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Warn", "审核前检查失败!"));
                }
            } catch (Exception e) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(null, e.getMessage()));
            }
        } else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Warn", "没有可更新数据!"));
        }
    }
}
