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
    private String audio = "ready.mp3";
    private String audioURL;//需要发音文字
    private boolean flag = true;//控制两次呼叫
    private boolean stop = true;//控制呼叫结束
    private int i = 0;
    private int idle = 0;//空闲座席数量
    private int interval = 64;//控制呼叫间隔

    public ExamCallManagedBean() {
    }

    public void call() throws IOException, InterruptedException {
        if (!this.model.getDataList().isEmpty()) {
            ExamCard e;
            if (i < this.model.getDataList().size() && i < getIdle()) {
                e = (ExamCard) this.model.getDataList().get(i);
                i++;
                if (i == this.model.getDataList().size() || i == idle) {
                    stop = true;
                } else {
                    stop = false;
                }
                if (e.getCallcount() < 3) {
                    //entityList.add(e);
                    e.setCallcount(e.getCallcount() + 1);
                    if (e.getStatus().equals("V")) {
                        //更新状态,叫了号才能登录考试
                        e.setStatus("Y");
                        e.setRemark("已叫号");
                    }
                    superEJB.update(e);
                    if (e.getExamhall() == null) {
                        audioURL = baiduTTSBean.ttsURL("请" + e.getFormid().substring(e.getFormid().length() - 3) + e.getName() + "到机房考试");
                    } else {
                        audioURL = baiduTTSBean.ttsURL("请" + e.getFormid().substring(e.getFormid().length() - 3) + e.getName() + "到" + e.getExamhall().getName() + "考试");
                    }
                    audio = e.getFormid() + ".mp3";
                } else {
                    this.call();
                }
                //叫号2次改成叫号1次
                //if (!flag) {
                //    i++;
                //    if (i == this.model.getDataList().size() || i == idle) {
                //        stop = true;
                //    }
                //}
                //flag = !flag;               
            } else {
                audio = "ready.mp3";
                stop = true;
            }
        } else {
            audio = "ready.mp3";
            stop = true;
        }
    }

    public void call(ExamCard e) throws IOException {
        if (e != null) {
            ExamCard c = examCardBean.findById(e.getId());
            if (e.getStatus().equals("V")) {
                e.setStatus("Y");
                e.setRemark("已叫号");
                superEJB.update(e);
            }
            if (e.getExamhall() == null) {
                audioURL = baiduTTSBean.ttsURL("请" + e.getFormid().substring(e.getFormid().length() - 3) + e.getName() + "到机房考试");
            } else {
                audioURL = baiduTTSBean.ttsURL("请" + e.getFormid().substring(e.getFormid().length() - 3) + e.getName() + "到" + e.getExamhall().getName() + "考试");
            }
            audio = e.getFormid() + ".mp3";
        }
    }

    @Override
    public void init() {
        this.entityList = new ArrayList<>();
        setSuperEJB(examCardBean);
        setModel(new ExamCardModel(examCardBean));
        this.model.getSortFields().clear();
        status = new ArrayList<>();
        status.add("V");
        status.add("Y");
        this.model.getFilterFields().put("status IN ", status);//已签到
        this.model.getSortFields().put("id", "ASC");
        if (this.getModel().getDataList() != null && !this.model.getDataList().isEmpty()) {
            setCurrentEntity((ExamCard) this.getModel().getDataList().get(0));
        } else {
            setCurrentEntity(getNewEntity());
        }
        i = 0;
        this.interval = 8;
        setIdle();
    }

    @Override
    public void query() {
        if (this.model != null && this.model.getFilterFields() != null) {
            this.model.getFilterFields().clear();
            this.model.getSortFields().clear();
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
            this.model.getSortFields().put("id", "ASC");
        }
    }

    @Override
    public void reset() {
        super.reset();
        this.model.getFilterFields().clear();
        this.model.getSortFields().clear();
        this.model.getFilterFields().put("status IN ", status);
        this.model.getSortFields().put("id", "ASC");
        this.entityList.clear();
        this.audioURL = "";
        i = 0;
        setIdle();
        this.stop = getIdle() == 0;
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
     * @return the audioURL
     */
    public String getAudioURL() {
        return audioURL;
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

    public void setIdle() {
        this.idle = examSeatBean.getIdleCount();
        if (this.idle == 0) {
            this.interval = 8;
        } else if ((this.idle > 0) && (this.idle <= 3)) {
            this.interval = 16;
        } else if ((this.idle > 3) && (this.idle <= 6)) {
            this.interval = 24;
        } else {
            this.interval = 32;
        }
    }

    /**
     * @return the interval
     */
    public int getInterval() {
        return interval;
    }

    /**
     * @param interval the interval to set
     */
    public void setInterval(int interval) {
        this.interval = interval;
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

}
