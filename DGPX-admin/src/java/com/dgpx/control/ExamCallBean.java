/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dgpx.control;

import com.dgpx.entity.ExamCard;
import com.dgpx.lazy.ExamCardModel;
import com.dgpx.web.BaiduTTSBean;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

/**
 *
 * @author kevindong
 */
@ManagedBean
@SessionScoped
public class ExamCallBean extends ExamCardManagedBean {

    @EJB
    private BaiduTTSBean baiduTTSBean;

    protected String queryNumberId;

    private String audio;
    private boolean flag = true;
    private boolean stop = true;

    public ExamCallBean() {
    }

    public void call() throws IOException, InterruptedException {
        if (!this.model.getDataList().isEmpty()) {
            for (Object o : this.model.getDataList()) {
                ExamCard e = (ExamCard) o;
                if (!entityList.contains(e)) {
                    stop = false;
                    if (flag) {
                        entityList.add(e);
                        if (e.getExamhall() == null) {
                            audio = baiduTTSBean.ttsURL("请" + e.getFormid().substring(e.getFormid().length() - 3) + "号" + e.getName() + "到机房考试");
                        } else {
                            audio = baiduTTSBean.ttsURL("请" + e.getFormid().substring(e.getFormid().length() - 3) + "号" + e.getName() + "到" + e.getExamhall().getName() + "考试");
                        }
                    }
                    break;
                }
                stop = true;
            }
        }
        flag = !flag;
    }

    public void doCall() {
        if (!this.model.getDataList().isEmpty() && entityList.isEmpty()) {
            this.stop = false;
        } else if (!this.model.getDataList().isEmpty() && !entityList.isEmpty()) {
            for (Object o : this.model.getDataList()) {
                ExamCard e = (ExamCard) o;
                if (!entityList.contains(e)) {
                    this.stop = false;
                    break;
                }
                this.stop = true;
            }
            for (ExamCard e : entityList) {
                if (!this.model.getDataList().contains(e)) {
                    entityList.remove(e);
                }
            }
        }
    }

    @Override
    public void init() {
        setSuperEJB(examCardBean);
        setModel(new ExamCardModel(examCardBean));
        this.model.getFilterFields().put("status", "Y");
        if (this.getModel().getDataList() != null && !this.model.getDataList().isEmpty()) {
            setCurrentEntity((ExamCard) this.getModel().getDataList().get(0));
            try {
                call();
            } catch (IOException | InterruptedException ex) {
                Logger.getLogger(ExamCallBean.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            setCurrentEntity(getNewEntity());
        }
        this.entityList = new ArrayList<>();
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
            this.model.getFilterFields().put("status", "Y");
        }
    }

    @Override
    public void reset() {
        super.reset();
        this.model.getFilterFields().put("status", "Y");
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

    /**
     * @return the audio
     */
    public String getAudio() {
        return audio;
    }

    /**
     * @param audio the audio to set
     */
    public void setAudio(String audio) {
        this.audio = audio;
    }

    /**
     * @return the stop
     */
    public boolean isStop() {
        return stop;
    }

    /**
     * @param stop the stop to set
     */
    public void setStop(boolean stop) {
        this.stop = stop;
    }

}
