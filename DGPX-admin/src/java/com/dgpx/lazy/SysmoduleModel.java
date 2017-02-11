/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dgpx.lazy;

import com.dgpx.entity.Sysmodule;
import com.lightshell.comm.BaseLazyModel;
import com.lightshell.comm.SuperEJB;

/**
 *
 * @author kevindong
 */
public class SysmoduleModel extends BaseLazyModel<Sysmodule> {

    public SysmoduleModel(SuperEJB superEJB) {
        this.superEJB = superEJB;
    }

}
