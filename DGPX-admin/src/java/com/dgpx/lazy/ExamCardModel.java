/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dgpx.lazy;

import com.dgpx.entity.ExamCard;
import com.lightshell.comm.BaseLazyModel;
import com.lightshell.comm.SuperEJB;
import java.util.List;
import java.util.Map;
import org.primefaces.model.SortOrder;

/**
 *
 * @author kevindong
 */
public class ExamCardModel extends BaseLazyModel<ExamCard> {

    public ExamCardModel(SuperEJB superEJB) {
        this.superEJB = superEJB;
        this.sortFields.put("status", "ASC");
        this.sortFields.put("id", "ASC");
    }

    @Override
    public List<ExamCard> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {
        return super.load(first, pageSize, sortField, sortOrder, filters);
    }

}
