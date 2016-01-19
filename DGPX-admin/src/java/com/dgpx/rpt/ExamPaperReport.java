/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dgpx.rpt;

import com.dgpx.ejb.ExamCardBean;
import com.dgpx.entity.ExamCard;
import com.dgpx.entity.ExamPaper;
import com.lightshell.comm.SuperMultiReportBean;
import java.util.List;

/**
 *
 * @author kevindong
 */
public class ExamPaperReport extends SuperMultiReportBean<ExamCardBean, ExamCard, ExamPaper> {

    public ExamPaperReport() {

    }

    @Override
    public ExamCard getEntity(int value) throws Exception {
        ExamCard entity = (ExamCard) superEJB.findById(value);
        return entity;
    }

    @Override
    public List<ExamPaper> getDetail(int value) throws Exception {
        superEJB.setDetail((Object) value);
        return superEJB.getDetailList();
    }

}
