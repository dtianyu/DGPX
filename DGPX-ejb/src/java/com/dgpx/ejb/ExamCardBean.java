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
    public ExamCard verify(ExamCard entity) {
        if ("Y".equals(entity.getStatus())) {
            //随机派发试卷
            int suit;
            Random r = new Random();
            ExamPaper paperItem;
            try {
                suit = r.nextInt(entity.getExamnumber().getPapercount());
                HashMap<String, Object> filters = new HashMap<>();
                filters.put("pformid", entity.getExamnumber().getFormid());
                filters.put("psuit", suit);
                List<ExamPaperPool> pool = examPaperPoolBean.findAll(filters);
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
                    paperItem.setKey1(item.getKey1());
                    paperItem.setKey2(item.getKey2());
                    paperItem.setKey3(item.getKey3());
                    paperItem.setKey4(item.getKey4());
                    paperItem.setKey5(item.getKey5());
                    paperItem.setKey6(item.getKey6());
                    paperItem.setKey7(item.getKey7());
                    paperItem.setKey8(item.getKey8());
                    paperItem.setStatus("V");
                    paperItem.setUseranswer("");
                    paperItem.setMark(BigDecimal.ZERO);
                    examPaperBean.persist(paperItem);
                }
            } catch (RuntimeException e) {
                throw e;
            }
        }
        return super.verify(entity);
    }

}
