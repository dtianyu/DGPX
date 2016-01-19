/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dgpx.rpt;

import com.dgpx.ejb.ExamNumberBean;
import com.dgpx.entity.ExamCard;
import com.dgpx.entity.ExamNumber;
import com.lightshell.comm.SuperMultiReportBean;
import java.util.List;

/**
 *
 * @author kevindong
 */
public class ExamMarkReport extends SuperMultiReportBean<ExamNumberBean, ExamNumber, ExamCard> {

    public ExamMarkReport() {

    }

    @Override
    public ExamNumber getEntity(int value) throws Exception {
        ExamNumber entity = (ExamNumber) superEJB.findById(value);
        return entity;
    }

    @Override
    public List<ExamCard> getDetail(int value) throws Exception {
        superEJB.setDetail((Object) value);
        return superEJB.getDetailList();
    }

}
