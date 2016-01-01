/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dgpx.validator;

import com.dgpx.ejb.SystemUserBean;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

/**
 *
 * @author C0160
 */
@FacesValidator("com.dgpx.validator.UserId")
public class UserIdValidator implements Validator {

    @EJB
    SystemUserBean systemUserBean;

    @Override
    public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {
        if (value != null) {
            if (systemUserBean.findByUserId(value.toString()) == null) {
                return;
            }
        }
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO,"号码已注册", "号码已注册");
        throw new ValidatorException(message);
    }

}
