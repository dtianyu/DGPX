/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dgpx.ejb;

import com.dgpx.entity.ExamCard;
import com.dgpx.entity.ExamNumber;
import com.dgpx.entity.ExamPaperPool;
import com.dgpx.entity.ExamSetting;
import com.dgpx.entity.ItemPool;
import com.dgpx.entity.Knowledge;
import com.lightshell.comm.SuperEJB;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.LocalBean;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author kevindong
 */
@Stateless
@LocalBean
public class ExamNumberBean extends SuperEJB<ExamNumber> {

    @PersistenceContext(unitName = "DGPX-ejbPU")
    private EntityManager em;
    @EJB
    private ExamSettingBean examSettingBean;
    @EJB
    private KnowledgeBean knowledgeBean;
    @EJB
    private ExamPaperPoolBean examPaperPoolBean;
    @EJB
    private ItemPoolBean itemPoolBean;
    @EJB
    private ExamCardBean examCardBean;

    protected List<ExamCard> detailList;

    public ExamNumberBean() {
        super(ExamNumber.class);
    }

    @Override
    public EntityManager getEntityManager() {
        return em;
    }

    public long getRowCountHasExam(ExamNumber entity) {
        Query query = em.createQuery("SELECT COUNT(e) FROM ExamCard e WHERE e.examnumber.id = :pid AND e.status<>'N' AND e.status<>'V' ");
        query.setParameter("pid", entity.getId());
        try {
            return (long) query.getSingleResult();
        } catch (Exception e) {
            return 0;
        }
    }

    @Override
    public void setDetail(Object value) {
        setDetailList(examCardBean.findByPId(value));
        if (getDetailList() == null) {
            setDetailList(new ArrayList<>());
        }
    }

    @Override
    public ExamNumber unverify(ExamNumber entity) {
        try {
            ExamNumber e = getEntityManager().merge(entity);
            List<ExamPaperPool> examPaperPoolList = examPaperPoolBean.findByPId(entity.getId());
            if (examPaperPoolList == null || examPaperPoolList.isEmpty()) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(null, "资料异常!"));
                throw new RuntimeException("资料异常");
            } else {
                for (ExamPaperPool detail : examPaperPoolList) {
                    examPaperPoolBean.delete(detail);
                }
            }
            return e;
        } catch (RuntimeException e) {
            throw e;
        }
    }

    @Override
    public ExamNumber verify(ExamNumber entity) {
        double randomNumber;
        double ratioNumber;
        int itemCount;
        int itemIndex;
        int poolCount;
        int ctgyCount;
        int totalCount;
        HashMap<Integer, Integer> itemIndexs = new HashMap<>();
        ItemPool item;
        ExamPaperPool paperItem;
        Random r = new Random();
        try {
            ExamNumber e = getEntityManager().merge(entity);
            //生成考卷
            List<ExamSetting> examSettingList = examSettingBean.findByPId(entity.getId());
            if (examSettingList == null || examSettingList.isEmpty()) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(null, "试卷题型设定错误!"));
                throw new RuntimeException("题型设定错误");
            }
            List<Knowledge> knowledgeList = knowledgeBean.findAll();
            if (knowledgeList == null || knowledgeList.isEmpty()) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(null, "知识点设定错误!"));
                throw new RuntimeException("知识点设定错误");
            }
            List<ItemPool> itemPoolList;
            for (int suit = 0; suit < entity.getPapercount(); suit++) {
                totalCount = 0;
                for (ExamSetting s : examSettingList) {
                    ctgyCount = 0;
                    itemIndexs.clear();
                    do {
                        for (Knowledge k : knowledgeList) {
                            poolCount = 0;
                            ratioNumber = k.getRatio().multiply(BigDecimal.valueOf(s.getQty())).divide(BigDecimal.valueOf(100), 4, RoundingMode.UP).doubleValue();
                            randomNumber = r.nextDouble();
                            itemCount = (int) (randomNumber + ratioNumber);
                            itemPoolList = itemPoolBean.findByItemctgyIdAndKnowledgeId(s.getItemcategory().getId(), k.getId());
                            if (itemPoolList == null || itemPoolList.isEmpty()) {
                                continue;
                            }
                            poolCount = itemPoolList.size();
                            for (int i = 0; i < itemCount && ctgyCount < s.getQty(); i++) {
                                ctgyCount++;
                                totalCount++;
                                //产生一个池中的随机Index
                                itemIndex = r.nextInt(poolCount);
                                item = itemPoolList.get(itemIndex);
                                while (itemIndexs.containsValue(item.getId())) {
                                    itemIndex = r.nextInt(poolCount);
                                    item = itemPoolList.get(itemIndex);
                                }//确保没有重复取题
                                //记录已使用的Id
                                itemIndexs.put(ctgyCount, item.getId());
                                paperItem = new ExamPaperPool();
                                paperItem.setPid(entity.getId());
                                paperItem.setPformid(entity.getFormid());
                                paperItem.setPsuit(suit);
                                paperItem.setSeq(totalCount);
                                paperItem.setItemctgyseq(ctgyCount);
                                paperItem.setItempoolid(item.getId());
                                paperItem.setExamctgyid(item.getExamcategory().getId());
                                paperItem.setItemctgyid(item.getItemcategory().getId());
                                paperItem.setKnowledgeid(item.getKnowledge().getId());
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
                                paperItem.setScore(s.getScore());
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
                                examPaperPoolBean.persist(paperItem);
                            }
                        }
                    } while (ctgyCount < s.getQty());//如果某个题型的题目不足就再循环提取
                }
            }
            //更新准考证编号
            List<ExamCard> examCardList = examCardBean.findByPId(e.getId());
            int cardCount = 1;
            for (ExamCard card : examCardList) {
                card.setFormid(e.getFormid() + String.format("%04d", cardCount));
                examCardBean.update(card);
                cardCount++;
            }
            return e;
        } catch (RuntimeException e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(null, "系统异常!"));
            throw e;
        }
    }

    /**
     * @return the detailList
     */
    public List<ExamCard> getDetailList() {
        return detailList;
    }

    /**
     * @param detailList the detailList to set
     */
    public void setDetailList(List<ExamCard> detailList) {
        this.detailList = detailList;
    }

}
