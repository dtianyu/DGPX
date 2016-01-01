/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dgpx.control;

import com.dgpx.ejb.ItemCategoryBean;
import com.dgpx.entity.ItemCategory;
import com.dgpx.lazy.ItemCategoryModel;
import com.dgpx.web.SuperSingleBean;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

/**
 *
 * @author kevindong
 */
@ManagedBean(name="itemCategoryManagedBean")
@SessionScoped
public class ItemCategoryManagedBean extends SuperSingleBean<ItemCategory> {
    
    @EJB
    private ItemCategoryBean itemCategoryBean;
    
    public ItemCategoryManagedBean() {
        super(ItemCategory.class);
    }
    
    @Override
    public void create() {
        super.create();        
        newEntity.setItemcount(0);
    }
    
    @Override
    public void init() {
        superEJB = itemCategoryBean;
        setModel(new ItemCategoryModel(itemCategoryBean));
        super.init();
    }
    
    @Override
    public void query() {
        if (this.model != null && this.model.getFilterFields() != null) {
            this.model.getFilterFields().clear();
            if (this.queryFormId != null && !"".equals(this.queryFormId)) {
                this.model.getFilterFields().put("formid", this.queryFormId);
            }
            if (this.queryState != null && !"ALL".equals(this.queryState)) {
                this.model.getFilterFields().put("status", this.queryState);
            }
        }
    }
    
}
