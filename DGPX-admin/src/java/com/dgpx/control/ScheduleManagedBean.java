/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dgpx.control;

import com.dgpx.ejb.ExamNumberBean;
import com.dgpx.entity.ExamNumber;
import com.lightshell.comm.BaseLib;
import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import org.primefaces.model.DefaultScheduleEvent;
import org.primefaces.model.DefaultScheduleModel;
import org.primefaces.model.ScheduleEvent;
import org.primefaces.model.ScheduleModel;

/**
 *
 * @author kevindong
 */
@ManagedBean
@ViewScoped
public class ScheduleManagedBean {

    @EJB
    private ExamNumberBean examNumberBean;

    protected ScheduleModel eventModel;
    private ScheduleEvent event;

    protected List<ExamNumber> examNumberList;

    public ScheduleManagedBean() {
    }

    @PostConstruct
    public void construct() {
        HashMap<String, Object> filters = new HashMap<>();
        filters.put("formdateBegin", BaseLib.getDate());
        setExamNumberList(examNumberBean.findByFilters(filters));
        setEventModel(new DefaultScheduleModel());
        Calendar calendar = Calendar.getInstance();
        for (ExamNumber entity : examNumberList) {
            Date eventBegin;
            try {
                eventBegin = BaseLib.getDate("yyyy-MM-dd_HH:mm", BaseLib.formatDate("yyyy-MM-dd", entity.getFormdate()) + "_" + BaseLib.formatDate("HH:ss", entity.getFormtime()));
                calendar.setTime(eventBegin);
                calendar.add(Calendar.MINUTE, entity.getExamtime());
                event = new DefaultScheduleEvent(entity.getExamcategory().getName(), eventBegin,
                        calendar.getTime());
                getEventModel().addEvent(event);
            } catch (ParseException ex) {
                Logger.getLogger(ScheduleManagedBean.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
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

    /**
     * @return the eventModel
     */
    public ScheduleModel getEventModel() {
        return eventModel;
    }

    /**
     * @param eventModel the eventModel to set
     */
    public void setEventModel(ScheduleModel eventModel) {
        this.eventModel = eventModel;
    }

}
