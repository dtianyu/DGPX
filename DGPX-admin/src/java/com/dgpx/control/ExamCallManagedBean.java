/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dgpx.control;

import com.dgpx.ejb.ExamSeatBean;
import com.dgpx.entity.ExamCard;
import com.dgpx.lazy.ExamCardModel;
import com.dgpx.web.BaiduTTSBean;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
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
public class ExamCallManagedBean extends ExamCardManagedBean {

    @EJB
    private ExamSeatBean examSeatBean;
    @EJB
    private BaiduTTSBean baiduTTSBean;

    protected String queryNumberId;
    protected List<String> status;

    private String audio;//需要发音文字
    private boolean flag = true;//控制两次呼叫
    private boolean stop = true;
    private int idle = 0;//空闲座席数量
    private int i = 0;

    public ExamCallManagedBean() {
    }

    public void call() throws IOException, InterruptedException {
        if (!this.model.getDataList().isEmpty()) {
            ExamCard e;
            if (i < this.model.getDataList().size() && i < getIdle()) {
                stop = false;
                e = (ExamCard) this.model.getDataList().get(i);
                if (!entityList.contains(e)) {
                    entityList.add(e);
                    if (e.getStatus().equals("V")) {
                        //更新状态,叫了号才能登录考试
                        e.setStatus("Y");
                        e.setRemark("已叫号");
                        superEJB.update(e);
                    }
                    if (e.getExamhall() == null) {
                        audio = baiduTTSBean.ttsURL("请" + e.getFormid().substring(e.getFormid().length() - 3) + "号" + e.getName() + "到机房考试");
                    } else {
                        audio = baiduTTSBean.ttsURL("请" + e.getFormid().substring(e.getFormid().length() - 3) + "号" + e.getName() + "到" + e.getExamhall().getName() + "考试");
                    }
                }
                if (!flag) {
                    i++;
                    if (i == this.model.getDataList().size() || i == idle) {
                        stop = true;
                    }
                }
                flag = !flag;
            } else {
                stop = true;
            }
        }
    }

    public void call(ExamCard e) throws IOException {
        if (e != null) {
            if (e.getStatus().equals("V")) {
                e.setStatus("Y");
                e.setRemark("已叫号");
                superEJB.update(e);
            }
            if (e.getExamhall() == null) {
                audio = baiduTTSBean.ttsURL("请" + e.getFormid().substring(e.getFormid().length() - 3) + "号" + e.getName() + "到机房考试");
            } else {
                audio = baiduTTSBean.ttsURL("请" + e.getFormid().substring(e.getFormid().length() - 3) + "号" + e.getName() + "到" + e.getExamhall().getName() + "考试");
            }
        }
    }

    @Override
    public void init() {
        this.entityList = new ArrayList<>();
        setSuperEJB(examCardBean);
        setModel(new ExamCardModel(examCardBean));
        status = new ArrayList<>();
        status.add("V");
        status.add("Y");
        this.model.getFilterFields().put("status IN ", status);//已签到
        if (this.getModel().getDataList() != null && !this.model.getDataList().isEmpty()) {
            setCurrentEntity((ExamCard) this.getModel().getDataList().get(0));
            try {
                call();
            } catch (IOException | InterruptedException ex) {
                Logger.getLogger(ExamCallManagedBean.class
                        .getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            setCurrentEntity(getNewEntity());
        }
        i = 0;
        idle = examSeatBean.getIdleCount();
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
            this.model.getFilterFields().put("status IN", status);
        }
    }

    @Override
    public void reset() {
        super.reset();
        this.model.getFilterFields().put("status IN ", status);
        this.entityList.clear();
        this.audio = "";
        i = 0;
        setIdle(examSeatBean.getIdleCount());
        if (getIdle() == 0) {
            this.stop = true;
        } else {
            this.stop = false;
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

    /**
     * @return the idle
     */
    public int getIdle() {
        return idle;
    }

    /**
     * @param idle the idle to set
     */
    public void setIdle(int idle) {
        this.idle = idle;
    }

}
