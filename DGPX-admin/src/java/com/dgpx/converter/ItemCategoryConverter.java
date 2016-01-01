/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dgpx.converter;

import com.dgpx.ejb.ItemCategoryBean;
import com.dgpx.entity.ItemCategory;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;

/**
 *
 * @author kevindong
 */
@FacesConverter("itemCategoryConverter")
public class ItemCategoryConverter implements Converter {

    @EJB
    private ItemCategoryBean itemCategoryBean;

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        if (value.trim().equals("")) {
            return null;
        } else {
            try {
                ItemCategory entity = itemCategoryBean.findById(Integer.parseInt(value));
                if (entity != null) {
                    return entity;
                } else {
                    return null;
                }
            } catch (ConverterException exception) {
                throw new ConverterException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Conversion Error", "Not a valid entity"));
            }
        }
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        if (value == null || value.equals("")) {
            return "";
        } else {
            return ((ItemCategory) value).getId().toString();
        }
    }

}
