/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dgpx.lazy;

import com.dgpx.entity.ExamSeat;
import com.lightshell.comm.BaseLazyModel;
import com.lightshell.comm.SuperEJB;
import java.util.List;
import java.util.Map;
import org.primefaces.model.SortOrder;

/**
 *
 * @author kevindong
 */
public class ExamSeatModel extends BaseLazyModel<ExamSeat> {

    public ExamSeatModel(SuperEJB superEJB) {
        this.superEJB = superEJB;
    }

    @Override
    public List<ExamSeat> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {
        this.sortFields.put("status", "ASC");
        return super.load(first, pageSize, sortField, sortOrder, filters);
    }

}
