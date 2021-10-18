/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dgpx.control.exam;

import com.dgpx.ejb.ExamCardBean;
import com.dgpx.ejb.ExamPaperBean;
import com.dgpx.ejb.ExamSeatBean;
import com.dgpx.ejb.TimerBean;
import com.dgpx.entity.ExamCard;
import com.dgpx.entity.ExamPaper;
import com.dgpx.entity.ExamSeat;
import com.dgpx.web.exam.SuperMultiBean;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author kevindong
 */
@ManagedBean
@SessionScoped
public class ExamManagedBean extends SuperMultiBean<ExamCard, ExamPaper> {

    @EJB
    private TimerBean timerBean;

    @EJB
    private ExamCardBean examCardBean;
    @EJB
    private ExamPaperBean examPaperBean;
    @EJB
    private ExamSeatBean examSeatBean;

    protected boolean hasNext;
    protected boolean hasPrev;
    protected boolean timeout;
    private boolean flag;
    private String path = "over";

    private ExamSeat examSeat;

    public ExamManagedBean() {
        super(ExamCard.class, ExamPaper.class);
    }

    @Override
    protected boolean doAfterVerify() throws Exception {
        if (examSeat != null) {
            examSeat.setExamcard(null);
            examSeat.setStyle("GreenBack");
            examSeatBean.update(examSeat);
        }
        return super.doAfterVerify();
    }

    @Override
    protected boolean doBeforeVerify() throws Exception {
        doConfirmDetail();
        if (flag) {
            examPaperBean.update(currentDetail);
        }
        return true;
    }

    @Override
    public void doConfirmDetail() {
        flag = true;
        if (currentDetail == null) {
            flag = false;
        }
        int count;
        switch (currentDetail.getItemcategory().getFormid()) {
            case "PD":
                count = 0;
                if (currentDetail.getKey1()) {
                    currentDetail.setUseranswer("A");
                    count++;
                }
                if (currentDetail.getKey2()) {
                    currentDetail.setUseranswer("B");
                    count++;
                }
                if (count != 1) {
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "选一个答案!"));
                    flag = false;
                }
                break;
            case "D1":
            case "JS":
                count = 0;
                if (currentDetail.getKey1()) {
                    currentDetail.setUseranswer("A");
                    count++;
                }
                if (currentDetail.getKey2()) {
                    currentDetail.setUseranswer("B");
                    count++;
                }
                if (currentDetail.getKey3()) {
                    currentDetail.setUseranswer("C");
                    count++;
                }
                if (currentDetail.getKey4()) {
                    currentDetail.setUseranswer("D");
                    count++;
                }
                if (currentDetail.getKey5()) {
                    currentDetail.setUseranswer("E");
                    count++;
                }
                if (currentDetail.getKey6()) {
                    currentDetail.setUseranswer("F");
                    count++;
                }
                if (currentDetail.getKey7()) {
                    currentDetail.setUseranswer("G");
                    count++;
                }
                if (currentDetail.getKey8()) {
                    currentDetail.setUseranswer("H");
                    count++;
                }
                if (count != 1) {
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "选一个答案!"));
                    flag = false;
                }
                break;
            case "DN":
                count = 0;
                StringBuilder sb = new StringBuilder();
                if (currentDetail.getKey1()) {
                    sb.append("A");
                    count++;
                }
                if (currentDetail.getKey2()) {
                    sb.append("B");
                    count++;
                }
                if (currentDetail.getKey3()) {
                    sb.append("C");
                    count++;
                }
                if (currentDetail.getKey4()) {
                    sb.append("D");
                    count++;
                }
                if (currentDetail.getKey5()) {
                    sb.append("E");
                    count++;
                }
                if (currentDetail.getKey6()) {
                    sb.append("F");
                    count++;
                }
                if (currentDetail.getKey7()) {
                    sb.append("G");
                    count++;
                }
                if (currentDetail.getKey8()) {
                    sb.append("H");
                    count++;
                }
                if (count == 0) {
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "必须有一个答案!"));
                    flag = false;
                }
                currentDetail.setUseranswer(sb.toString());
                break;
        }
        if (flag) {
            if (!"GreenButton".equals(currentDetail.getStatus())) {
                currentEntity.setCountleft(examPaperBean.getRowCountNotDoneByPId(currentEntity.getId()) - 1);
            }
            currentDetail.setStatus("GreenButton");
        }
    }

    public void handleKey1ValueChanged(ValueChangeEvent event) {
        if (currentDetail != null) {
            if (!"DN".equals(currentDetail.getItemcategory().getFormid())) {
                //currentDetail.setKey1(false);
                currentDetail.setKey2(false);
                currentDetail.setKey3(false);
                currentDetail.setKey4(false);
                currentDetail.setKey5(false);
                currentDetail.setKey6(false);
                currentDetail.setKey7(false);
                currentDetail.setKey8(false);
            }
        }
    }

    public void handleKey2ValueChanged(ValueChangeEvent event) {
        if (currentDetail != null) {
            if (!"DN".equals(currentDetail.getItemcategory().getFormid())) {
                currentDetail.setKey1(false);
                //currentDetail.setKey2(false);
                currentDetail.setKey3(false);
                currentDetail.setKey4(false);
                currentDetail.setKey5(false);
                currentDetail.setKey6(false);
                currentDetail.setKey7(false);
                currentDetail.setKey8(false);
            }
        }
    }

    public void handleKey3ValueChanged(ValueChangeEvent event) {
        if (currentDetail != null) {
            if (!"DN".equals(currentDetail.getItemcategory().getFormid())) {
                currentDetail.setKey1(false);
                currentDetail.setKey2(false);
                //currentDetail.setKey3(false);
                currentDetail.setKey4(false);
                currentDetail.setKey5(false);
                currentDetail.setKey6(false);
                currentDetail.setKey7(false);
                currentDetail.setKey8(false);
            }
        }
    }

    public void handleKey4ValueChanged(ValueChangeEvent event) {
        if (currentDetail != null) {
            if (!"DN".equals(currentDetail.getItemcategory().getFormid())) {
                currentDetail.setKey1(false);
                currentDetail.setKey2(false);
                currentDetail.setKey3(false);
                //currentDetail.setKey4(false);
                currentDetail.setKey5(false);
                currentDetail.setKey6(false);
                currentDetail.setKey7(false);
                currentDetail.setKey8(false);
            }
        }
    }

    public void handleKey5ValueChanged(ValueChangeEvent event) {
        if (currentDetail != null) {
            if (!"DN".equals(currentDetail.getItemcategory().getFormid())) {
                currentDetail.setKey1(false);
                currentDetail.setKey2(false);
                currentDetail.setKey3(false);
                currentDetail.setKey4(false);
                //currentDetail.setKey5(false);
                currentDetail.setKey6(false);
                currentDetail.setKey7(false);
                currentDetail.setKey8(false);
            }
        }
    }

    public void handleKey6ValueChanged(ValueChangeEvent event) {
        if (currentDetail != null) {
            if (!"DN".equals(currentDetail.getItemcategory().getFormid())) {
                currentDetail.setKey1(false);
                currentDetail.setKey2(false);
                currentDetail.setKey3(false);
                currentDetail.setKey4(false);
                currentDetail.setKey5(false);
                //currentDetail.setKey6(false);
                currentDetail.setKey7(false);
                currentDetail.setKey8(false);
            }
        }
    }

    public void handleKey7ValueChanged(ValueChangeEvent event) {
        if (currentDetail != null) {
            if (!"DN".equals(currentDetail.getItemcategory().getFormid())) {
                currentDetail.setKey1(false);
                currentDetail.setKey2(false);
                currentDetail.setKey3(false);
                currentDetail.setKey4(false);
                currentDetail.setKey5(false);
                currentDetail.setKey6(false);
                //currentDetail.setKey7(false);
                currentDetail.setKey8(false);
            }
        }
    }

    public void handleKey8ValueChanged(ValueChangeEvent event) {
        if (currentDetail != null) {
            if (!"DN".equals(currentDetail.getItemcategory().getFormid())) {
                currentDetail.setKey1(false);
                currentDetail.setKey2(false);
                currentDetail.setKey3(false);
                currentDetail.setKey4(false);
                currentDetail.setKey5(false);
                currentDetail.setKey6(false);
                currentDetail.setKey7(false);
                //currentDetail.setKey8(false);
            }
        }
    }

    @Override
    public void init() {
        this.superEJB = examCardBean;
        this.detailEJB = examPaperBean;
        setCurrentEntity(this.userManagedBean.getCurrentUser());
        setDetailList(getDetailEJB().findByPId(currentEntity.getId()));
        if (!detailList.isEmpty()) {
            setCurrentDetail(detailList.get(0));
        } else {
            showMsg(FacesMessage.SEVERITY_ERROR, "Error", "却少考卷,请联系教务人员");
        }
        currentEntity.setStatus("E");//设置为考试中
        currentEntity.setRemark("考试中");
        hasPrev = false;
        hasNext = true;

        FacesContext fc = FacesContext.getCurrentInstance();
        timerBean.setTimer((long) currentEntity.getTimeleft(), fc.getExternalContext().getSessionId(true));
        //Timer Debug
        String log = currentEntity.getFormid() + "-" + fc.getExternalContext().getSessionId(false) + "开始考试";
        Logger.getLogger(ExamManagedBean.class.getName()).log(Level.INFO, log);

        HttpServletRequest request = (HttpServletRequest) fc.getExternalContext().getRequest();
        String ip = request.getHeader("X-FORWARDED-FOR");
        if (ip == null) {
            ip = request.getRemoteAddr();
        }
        currentEntity.setSeat(ip);
        fc.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info", ip));
        examSeat = examSeatBean.findByIP(ip);
        if (examSeat != null) {
            examSeat.setExamcard(currentEntity);
            examSeat.setStyle("OrangeBack");
            examSeatBean.update(examSeat);
        }
        this.superEJB.update(currentEntity);
    }

    public void running() {
        FacesContext fc = FacesContext.getCurrentInstance();
        if (this.isTimeout()) {
            this.currentEntity.setTimeleft(0);
            try {
                currentEntity.setStatus("Z");
                currentEntity.setCfmuser(getUserManagedBean().getCurrentUser().getFormid());
                currentEntity.setCfmdateToNow();
                superEJB.verify(currentEntity);
                doAfterVerify();
                fc.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info", "更新成功！"));
                timerBean.removeSession(fc.getExternalContext().getSessionId(false));
                fc.getApplication().getNavigationHandler().handleNavigation(fc, null, path);
            } catch (Exception e) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(null, e.toString()));
            }
        } else {
            long n = timerBean.getTimeleft(fc.getExternalContext().getSessionId(false));
            if (n != -999) {
                this.currentEntity.setTimeleft((int) n);
            } else {
                //Timer Debug
                String log = currentEntity.getFormid() + "-" + fc.getExternalContext().getSessionId(false) + "考试中断";
                Logger.getLogger(ExamManagedBean.class.getName()).log(Level.INFO, log);
            }
        }
    }

    @Override
    public void setCurrentDetail(ExamPaper currentDetail) {
        super.setCurrentDetail(currentDetail);
        int idx = detailList.indexOf(currentDetail);
        if (idx == 0) {
            hasPrev = false;
            hasNext = true;
        } else if (idx == (currentEntity.getExamnumber().getExamcount() - 1)) {
            hasPrev = true;
            hasNext = false;
        } else {
            hasPrev = true;
            hasNext = true;
        }
    }

    public void toHold() {
        if (currentDetail == null) {
            return;
        }
        int idx = detailList.indexOf(currentDetail) + 1;
        currentDetail.setStatus("RedButton");
        examPaperBean.update(currentDetail);
        if (idx < currentEntity.getExamnumber().getExamcount()) {
            setCurrentDetail(detailList.get(idx));
        }
    }

    public void toNext() {
        doConfirmDetail();
        if (!flag) {
            return;
        }
        int idx = detailList.indexOf(currentDetail) + 1;
        examPaperBean.update(currentDetail);
        examCardBean.update(currentEntity);
        if (idx < currentEntity.getExamnumber().getExamcount()) {
            setCurrentDetail(detailList.get(idx));
        }
    }

    public void toPrev() {
        if (currentDetail == null) {
            return;
        }
        int idx = detailList.indexOf(currentDetail) - 1;
        if (idx >= 0) {
            setCurrentDetail(detailList.get(idx));
        }
    }

    @Override
    public void verify() {
        if (null != getCurrentEntity()) {
            try {
                if (doBeforeVerify()) {
                    currentEntity.setStatus("Z");
                    currentEntity.setCfmuser(getUserManagedBean().getCurrentUser().getFormid());
                    currentEntity.setCfmdateToNow();
                    superEJB.verify(currentEntity);
                    doAfterVerify();
                    FacesContext fc = FacesContext.getCurrentInstance();
                    //Timer Debug
                    String log = currentEntity.getFormid() + "-" + fc.getExternalContext().getSessionId(false) + "完成考试";
                    Logger.getLogger(ExamManagedBean.class.getName()).log(Level.INFO, log);

                    fc.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info", "更新成功！"));
                    timerBean.removeSession(fc.getExternalContext().getSessionId(false));
                    fc.getApplication().getNavigationHandler().handleNavigation(fc, null, path);
                } else {
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Warn", "审核前检查失败!"));
                }
            } catch (Exception e) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(null, e.toString()));
            }
        } else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Warn", "没有可更新数据!"));
        }
    }

    /**
     * @return the hasNext
     */
    public boolean isHasNext() {
        return hasNext;
    }

    /**
     * @return the hasPrev
     */
    public boolean isHasPrev() {
        return hasPrev;
    }

    /**
     * @return the timeout
     */
    public boolean isTimeout() {
        return timerBean.isTimeout(FacesContext.getCurrentInstance().getExternalContext().getSessionId(false));
    }


}
