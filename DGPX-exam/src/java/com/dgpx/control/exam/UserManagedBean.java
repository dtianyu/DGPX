/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dgpx.control.exam;

import com.dgpx.ejb.ExamCardBean;
import com.dgpx.entity.ExamCard;
import com.lightshell.comm.BaseLib;
import java.io.Serializable;
import java.util.Date;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

/**
 *
 * @author C0160
 */
@ManagedBean(name = "userManagedBean")
@SessionScoped
public class UserManagedBean implements Serializable {

    @EJB
    private ExamCardBean examCardBean;

    private ExamCard currentUser;
    private String userid;
    private boolean status;

    public UserManagedBean() {
        status = false;
    }

    public String login() {
        if (getUserid().length() == 0) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info", "请输入准考证编号"));
            return "";
        }
        try {
            //用来判断考试是否过期,格式化日期后再比较
            Date t = BaseLib.getDate("yyyy-MM-dd", BaseLib.formatDate("yyyy-MM-dd", BaseLib.getDate()));
            ExamCard u;
            u = examCardBean.findByFormIdAndCheckIn(getUserid());
            if (u != null && (u.getExamnumber().getFormdate().compareTo(t) != -1)) {
                currentUser = u;
                status = true;
                updateLoginTime();
            } else {
                u = examCardBean.findByFormId(getUserid());
                if (u != null && ("Z".equals(u.getStatus()) || (u.getExamnumber().getFormdate().compareTo(t) < 0))) {
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Warn", "您的考试已结束"));
                } else if (u != null && !"Z".equals(u.getStatus())) {
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Warn", "未签到,请先签到"));
                } else {
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "准考证不存在"));
                }
                status = false;
                return "";
            }
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Fatal", "登录系统发生异常,请联系管理员"));
            status = false;
            return "login";
        }
        return "home";
    }

    public String logout() {
        if (status) {
            currentUser = null;
            status = false;
            HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
            session.invalidate();
            return "login";
        } else {
            return "home";
        }
    }

    private void update() {
        try {
            examCardBean.update(currentUser);
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "系统更新失败!"));
        }
    }

    private void updateLoginTime() {
        if (currentUser != null) {
            currentUser.setLastlogin(currentUser.getNewestlogin());
            currentUser.setNewestlogin(BaseLib.getDate());
            update();
        }
    }

    /**
     * @return the currentUser
     */
    public ExamCard getCurrentUser() {
        return currentUser;
    }

    public boolean getStatus() {
        return status;
    }

    /**
     * @return the userid
     */
    public String getUserid() {
        return userid;
    }

    /**
     * @param userid the userid to set
     */
    public void setUserid(String userid) {
        this.userid = userid;
    }

}
