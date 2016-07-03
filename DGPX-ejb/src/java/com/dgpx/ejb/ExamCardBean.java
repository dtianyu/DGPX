/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dgpx.ejb;

import com.dgpx.entity.ExamCard;
import com.dgpx.entity.ExamPaper;
import com.dgpx.entity.ExamPaperPool;
import com.lightshell.comm.SuperEJB;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.LocalBean;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author kevindong
 */
@Stateless
@LocalBean
public class ExamCardBean extends SuperEJB<ExamCard> {

    @PersistenceContext(unitName = "DGPX-ejbPU")
    private EntityManager em;

    @EJB
    private ItemCategoryBean itemCategoryBean;
    @EJB
    private ExamPaperPoolBean examPaperPoolBean;
    @EJB
    private ExamPaperBean examPaperBean;

    protected List<ExamPaper> detailList;

    public ExamCardBean() {
        super(ExamCard.class);
    }

    @Override
    public EntityManager getEntityManager() {
        return em;
    }

    public ExamCard findByFormId(String formid) {
        Query query = getEntityManager().createNamedQuery("ExamCard.findByFormId");
        query.setParameter("formid", formid);
        try {
            return (ExamCard) query.getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }

    public ExamCard findByFormIdAndCheckIn(String formid) {
        Query query = getEntityManager().createNamedQuery("ExamCard.findByFormIdAndCheckIn");
        query.setParameter("formid", formid);
        try {
            return (ExamCard) query.getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public void setDetail(Object value) {
        setDetailList(examPaperBean.findByPId(value));
        if (getDetailList() == null) {
            setDetailList(new ArrayList<>());
        }
    }

    @Override
    public ExamCard verify(ExamCard entity) {
        if ("Y".equals(entity.getStatus())) {
            entity.setRemark("已签到");
            //随机派发试卷
            int suit;
            Random r = new Random();
            ExamPaper paperItem;
            try {
                suit = r.nextInt(entity.getExamnumber().getPapercount());
                HashMap<String, Object> filters = new HashMap<>();
                filters.put("pid", entity.getExamnumber().getId());
                filters.put("psuit", suit);
                List<ExamPaperPool> pool = examPaperPoolBean.findByFilters(filters);
                for (ExamPaperPool item : pool) {
                    paperItem = new ExamPaper();
                    paperItem.setPid(entity.getId());
                    paperItem.setPformid(entity.getFormid());
                    paperItem.setSid(item.getId());
                    paperItem.setSformid(item.getPformid());
                    paperItem.setSsuit(item.getPsuit());
                    paperItem.setSeq(item.getSeq());
                    paperItem.setItemctgyseq(item.getItemctgyseq());
                    paperItem.setItempoolid(item.getItempoolid());
                    paperItem.setExamctgyid(item.getExamctgyid());
                    paperItem.setItemcategory(itemCategoryBean.findById(item.getItemctgyid()));
                    paperItem.setKnowledgeid(item.getKnowledgeid());
                    paperItem.setQuestion(item.getQuestion());
                    paperItem.setChoice1(item.getChoice1());
                    paperItem.setChoice2(item.getChoice2());
                    paperItem.setChoice3(item.getChoice3());
                    paperItem.setChoice4(item.getChoice4());
                    paperItem.setChoice5(item.getChoice5());
                    paperItem.setChoice6(item.getChoice6());
                    paperItem.setChoice7(item.getChoice7());
                    paperItem.setChoice8(item.getChoice8());
                    paperItem.setAnswer(item.getAnswer());
                    paperItem.setScore(item.getScore());
                    paperItem.setKey1(false);
                    paperItem.setKey2(false);
                    paperItem.setKey3(false);
                    paperItem.setKey4(false);
                    paperItem.setKey5(false);
                    paperItem.setKey6(false);
                    paperItem.setKey7(false);
                    paperItem.setKey8(false);
                    paperItem.setStatus("N");
                    paperItem.setUseranswer("");
                    paperItem.setMark(BigDecimal.ZERO);
                    examPaperBean.persist(paperItem);
                }
            } catch (RuntimeException e) {
                throw e;
            }
        }
        if ("Z".equals(entity.getStatus())) {
            //交卷评分
            entity.setRemark("已交卷");
            try {
                int len;
                String v;
                BigDecimal mark = BigDecimal.ZERO;
                List<ExamPaper> examPaperList = examPaperBean.findByPId(entity.getId());
                for (ExamPaper item : examPaperList) {
                    switch (item.getItemcategory().getFormid()) {
                        case "DN":
                            if ("".equals(item.getUseranswer())) {
                                break;
                            }
                            if (item.getAnswer().equalsIgnoreCase(item.getUseranswer())) {
                                item.setMark(item.getScore());
                                mark = mark.add(item.getMark());
                            } else {
                                len = item.getUseranswer().length();
                                for (int i = 0; i < len; i++) {
                                    v = item.getUseranswer().substring(i, 1);
                                    if (!item.getAnswer().contains(v)) {
                                        break;
                                    }
                                }
                                item.setMark(BigDecimal.ONE);
                                mark = mark.add(item.getMark());
                            }
                            break;
                        default:
                            if (item.getAnswer().equalsIgnoreCase(item.getUseranswer())) {
                                item.setMark(item.getScore());
                                mark = mark.add(item.getMark());
                            }
                    }
                    examPaperBean.update(item);
                }
                entity.setMark(mark);
            } catch (RuntimeException e) {
                throw e;
            }
        }
        return super.verify(entity);
    }

    /**
     * @return the detailList
     */
    public List<ExamPaper> getDetailList() {
        return detailList;
    }

    /**
     * @param detailList the detailList to set
     */
    public void setDetailList(List<ExamPaper> detailList) {
        this.detailList = detailList;
    }

}
