/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dgpx.control;

import com.dgpx.ejb.ExamPaperBean;
import com.dgpx.entity.ExamCard;
import com.dgpx.entity.ExamPaper;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

/**
 *
 * @author kevindong
 */
@ManagedBean(name = "batchCheckInManagedBean")
@SessionScoped
public class BatchCheckInManagedBean extends ExamCardManagedBean {

    //@EJB
    //private BaiduTTSBean baiduTTSBean;
    @EJB
    private ExamPaperBean examPaperBean;
    protected String queryNumberId;
    protected List<String> status;

    private String audioURL;

    public BatchCheckInManagedBean() {
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
        entityList = new ArrayList<>();
        status = new ArrayList<>();
        status.add("N");
        this.model.getFilterFields().put("status IN ", status);
        this.model.getSortFields().put("status", "ASC");
        setToolBar();
    }

    @Override
    public void query() {
        if (this.model != null && this.model.getFilterFields() != null) {
            this.model.getFilterFields().clear();
            if (this.getQueryNumberId() != null && !"".equals(this.queryNumberId)) {
                this.model.getFilterFields().put("examnumber.formid", this.getQueryNumberId());
            }
            if (this.queryFormId != null && !"".equals(this.queryFormId)) {
                this.model.getFilterFields().put("formid", this.queryFormId);
            }
            if (this.queryName != null && !"".equals(this.queryName)) {
                this.model.getFilterFields().put("name", this.queryName);
            }
            if (this.queryIdCard != null && !"".equals(this.queryIdCard)) {
                this.model.getFilterFields().put("idcard", this.queryIdCard);
            }
        }
    }

    @Override
    public void reset() {
        super.reset();
        this.model.getFilterFields().put("status IN ", status);
    }

    @Override
    protected void setToolBar() {
        if (currentSysprg != null) {
            this.doAdd = false;
            this.doEdit = false;
            this.doDel = false;
            this.doCfm = true;
            this.doUnCfm = false;
        } else {
            this.doAdd = false;
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
                    currentEntity.setStatus("N");//简化查询条件,此处不再提供修改状态(M)
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
        if (null != getEntityList()) {
            try {
                for (ExamCard c : this.entityList) {
                    c.setCountleft(c.getExamnumber().getExamcount());
                    c.setTimeleft(c.getExamnumber().getExamtime());
                    c.setStatus("V");
                    c.setCfmuser(getUserManagedBean().getCurrentUser().getUserid());
                    c.setCfmdateToNow();
                    superEJB.verify(c);
                    /*
                    //保存语言文件
                    if (c.getExamhall() == null) {
                        audioURL = baiduTTSBean.ttsURL("请" + c.getFormid().substring(c.getFormid().length() - 3) + c.getName() + "到机房考试");
                    } else {
                        audioURL = baiduTTSBean.ttsURL("请" + c.getFormid().substring(c.getFormid().length() - 3) + c.getName() + "到" + c.getExamhall().getName() + "考试");
                    }
                    this.fileName = this.getAppDataPath() + "//" + c.getFormid() + ".mp3";
                    baiduTTSBean.saveTTS(audioURL, fileName);
                    */
                }
                showMsg(FacesMessage.SEVERITY_INFO, "Info", "更新成功!");
            } catch (Exception e) {
                showMsg(FacesMessage.SEVERITY_ERROR, "Error", e.getMessage());
            }
        } else {
            showMsg(FacesMessage.SEVERITY_WARN, "Warn", "没有可更新数据!");
        }
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
