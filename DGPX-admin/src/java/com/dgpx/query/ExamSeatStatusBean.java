/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dgpx.query;

import com.dgpx.ejb.ExamSeatBean;
import com.dgpx.entity.ExamSeat;
import com.dgpx.lazy.ExamSeatModel;
import com.dgpx.web.SuperQueryBean;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

/**
 *
 * @author kevindong
 */
@ManagedBean
@RequestScoped
public class ExamSeatStatusBean extends SuperQueryBean<ExamSeat> {

    @EJB
    private ExamSeatBean examSeatBean;

    public ExamSeatStatusBean() {
        super(ExamSeat.class);
    }

    @Override
    public void init() {
        setSuperEJB(examSeatBean);
        setModel(new ExamSeatModel(examSeatBean));
        super.init();
    }
}
