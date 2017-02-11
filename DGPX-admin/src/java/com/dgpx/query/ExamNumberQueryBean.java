/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dgpx.query;

import com.dgpx.ejb.ExamNumberBean;
import com.dgpx.entity.ExamNumber;
import com.dgpx.lazy.ExamNumberModel;
import com.dgpx.web.SuperQueryBean;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

/**
 *
 * @author kevindong
 */
@ManagedBean(name = "examNumberQueryBean")
@ViewScoped
public class ExamNumberQueryBean extends SuperQueryBean<ExamNumber> {

    @EJB
    private ExamNumberBean examNumberBean;

    public ExamNumberQueryBean() {
        super(ExamNumber.class);
    }

    @Override
    public void init() {
        setSuperEJB(examNumberBean);
        setModel(new ExamNumberModel(examNumberBean));
        super.init();
    }

}
