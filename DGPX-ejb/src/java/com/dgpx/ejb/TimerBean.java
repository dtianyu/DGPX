/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dgpx.ejb;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.Set;
import javax.annotation.Resource;
import javax.ejb.Stateless;
import javax.ejb.LocalBean;
import javax.ejb.Schedule;
import javax.ejb.Startup;
import javax.ejb.Timeout;
import javax.ejb.Timer;
import javax.ejb.TimerService;

/**
 *
 * @author kevindong
 */
@Stateless
@LocalBean
@Startup
public class TimerBean implements Serializable {

    @Resource
    TimerService timerService;

    protected HashMap<String, Date> beginTime;
    protected HashMap<String, Date> endTime;

    protected HashMap<String, Long> timeleft;
    protected HashMap<String, Boolean> timeout;

    public TimerBean() {
        this.timeleft = new HashMap<>();
        this.timeout = new HashMap<>();
    }

    public void removeSession(String sessionId) {
        timeleft.remove(sessionId);
    }

    public void setTimer(long minutes, String info) {
        timeleft.put(info, minutes);
        timeout.put(info, Boolean.FALSE);
        Timer timer = timerService.createTimer(minutes * 60000, info);
    }

    @Timeout
    public void timeout(Timer timer) {
        timeleft.replace(timer.getInfo().toString(), (long) 0);
        timeout.replace(timer.getInfo().toString(), Boolean.TRUE);
    }

    @Schedule(minute = "*/1", hour = "*", persistent = false)
    public void runningTimerService() {
        for (Entry<String, Long> entry : timeleft.entrySet()) {
            entry.setValue(entry.getValue() - 1);
        }
    }

    /**
     * @param sessionId
     * @return the timeleft
     */
    public long getTimeleft(String sessionId) {
        return timeleft.get(sessionId);
    }

    /**
     * @param sessionId
     * @return the timeout
     */
    public boolean isTimeout(String sessionId) {
        if (timeout.containsKey(sessionId)) {
            return timeout.get(sessionId);
        } else {
            return true;
        }
    }

    /**
     * @param sessionId
     * @return the beginTime
     */
    public Date getBeginTime(String sessionId) {
        return beginTime.get(sessionId);
    }

    /**
     * @param sessionId
     * @return the endTime
     */
    public Date getEndTime(String sessionId) {
        return endTime.get(sessionId);
    }

}
