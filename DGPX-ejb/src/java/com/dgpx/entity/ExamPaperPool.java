/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dgpx.entity;

import com.lightshell.comm.BaseDetailEntity;
import java.math.BigDecimal;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author kevindong
 */
@Entity
@Table(name = "exampaperpool")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ExamPaperPool.getRowCount", query = "SELECT COUNT(e) FROM ExamPaperPool e"),
    @NamedQuery(name = "ExamPaperPool.findAll", query = "SELECT e FROM ExamPaperPool e"),
    @NamedQuery(name = "ExamPaperPool.findById", query = "SELECT e FROM ExamPaperPool e WHERE e.id = :id"),
    @NamedQuery(name = "ExamPaperPool.findByPId", query = "SELECT e FROM ExamPaperPool e WHERE e.pid = :pid"),
    @NamedQuery(name = "ExamPaperPool.findByPformId", query = "SELECT e FROM ExamPaperPool e WHERE e.pformid = :pformid"),
    @NamedQuery(name = "ExamPaperPool.findByItempoolId", query = "SELECT e FROM ExamPaperPool e WHERE e.itempoolid = :itempoolid"),
    @NamedQuery(name = "ExamPaperPool.findByExamctgyId", query = "SELECT e FROM ExamPaperPool e WHERE e.examctgyid = :examctgyid"),
    @NamedQuery(name = "ExamPaperPool.findByItemctgyId", query = "SELECT e FROM ExamPaperPool e WHERE e.itemctgyid = :itemctgyid"),
    @NamedQuery(name = "ExamPaperPool.findByKnowledgeId", query = "SELECT e FROM ExamPaperPool e WHERE e.knowledgeid = :knowledgeid")})
public class ExamPaperPool extends BaseDetailEntity {

    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "pformid")
    private String pformid;
    @Basic(optional = false)
    @NotNull
    @Column(name = "psuit")
    private int psuit;
    @Column(name = "itemctgyseq")
    private Integer itemctgyseq;
    @Basic(optional = false)
    @NotNull
    @Column(name = "itempoolid")
    private int itempoolid;
    @Basic(optional = false)
    @NotNull
    @Column(name = "examctgyid")
    private int examctgyid;
    @Basic(optional = false)
    @NotNull
    @Column(name = "itemctgyid")
    private int itemctgyid;
    @Basic(optional = false)
    @NotNull
    @Column(name = "knowledgeid")
    private int knowledgeid;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 800)
    @Column(name = "question")
    private String question;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 80)
    @Column(name = "choice1")
    private String choice1;
    @Size(max = 80)
    @Column(name = "choice2")
    private String choice2;
    @Size(max = 80)
    @Column(name = "choice3")
    private String choice3;
    @Size(max = 80)
    @Column(name = "choice4")
    private String choice4;
    @Size(max = 80)
    @Column(name = "choice5")
    private String choice5;
    @Size(max = 80)
    @Column(name = "choice6")
    private String choice6;
    @Size(max = 80)
    @Column(name = "choice7")
    private String choice7;
    @Size(max = 80)
    @Column(name = "choice8")
    private String choice8;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "answer")
    private String answer;
    @Column(name = "score")
    protected BigDecimal score;
    @Basic(optional = false)
    @NotNull
    @Column(name = "key1")
    private boolean key1;
    @Basic(optional = false)
    @NotNull
    @Column(name = "key2")
    private boolean key2;
    @Basic(optional = false)
    @NotNull
    @Column(name = "key3")
    private boolean key3;
    @Basic(optional = false)
    @NotNull
    @Column(name = "key4")
    private boolean key4;
    @Basic(optional = false)
    @NotNull
    @Column(name = "key5")
    private boolean key5;
    @Basic(optional = false)
    @NotNull
    @Column(name = "key6")
    private boolean key6;
    @Basic(optional = false)
    @NotNull
    @Column(name = "key7")
    private boolean key7;
    @Basic(optional = false)
    @NotNull
    @Column(name = "key8")
    private boolean key8;
    @Basic(optional = false)
    @NotNull
    @Size(min = 0, max = 45)
    @Column(name = "status")
    private String status;
    @Basic(optional = false)
    @NotNull
    @Size(min = 0, max = 20)
    @Column(name = "useranswer")
    private String useranswer;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Basic(optional = false)
    @NotNull
    @Column(name = "mark")
    private BigDecimal mark;

    public ExamPaperPool() {
    }

    public String getPformid() {
        return pformid;
    }

    public void setPformid(String pformid) {
        this.pformid = pformid;
    }

    public int getPsuit() {
        return psuit;
    }

    public void setPsuit(int psuit) {
        this.psuit = psuit;
    }

    public Integer getItemctgyseq() {
        return itemctgyseq;
    }

    public void setItemctgyseq(Integer itemctgyseq) {
        this.itemctgyseq = itemctgyseq;
    }

    public int getItempoolid() {
        return itempoolid;
    }

    public void setItempoolid(int itempoolid) {
        this.itempoolid = itempoolid;
    }

    public int getExamctgyid() {
        return examctgyid;
    }

    public void setExamctgyid(int examctgyid) {
        this.examctgyid = examctgyid;
    }

    public int getItemctgyid() {
        return itemctgyid;
    }

    public void setItemctgyid(int itemctgyid) {
        this.itemctgyid = itemctgyid;
    }

    public int getKnowledgeid() {
        return knowledgeid;
    }

    public void setKnowledgeid(int knowledgeid) {
        this.knowledgeid = knowledgeid;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getChoice1() {
        return choice1;
    }

    public void setChoice1(String choice1) {
        this.choice1 = choice1;
    }

    public String getChoice2() {
        return choice2;
    }

    public void setChoice2(String choice2) {
        this.choice2 = choice2;
    }

    public String getChoice3() {
        return choice3;
    }

    public void setChoice3(String choice3) {
        this.choice3 = choice3;
    }

    public String getChoice4() {
        return choice4;
    }

    public void setChoice4(String choice4) {
        this.choice4 = choice4;
    }

    public String getChoice5() {
        return choice5;
    }

    public void setChoice5(String choice5) {
        this.choice5 = choice5;
    }

    public String getChoice6() {
        return choice6;
    }

    public void setChoice6(String choice6) {
        this.choice6 = choice6;
    }

    public String getChoice7() {
        return choice7;
    }

    public void setChoice7(String choice7) {
        this.choice7 = choice7;
    }

    public String getChoice8() {
        return choice8;
    }

    public void setChoice8(String choice8) {
        this.choice8 = choice8;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public boolean getKey1() {
        return key1;
    }

    public void setKey1(boolean key1) {
        this.key1 = key1;
    }

    public boolean getKey2() {
        return key2;
    }

    public void setKey2(boolean key2) {
        this.key2 = key2;
    }

    public boolean getKey3() {
        return key3;
    }

    public void setKey3(boolean key3) {
        this.key3 = key3;
    }

    public boolean getKey4() {
        return key4;
    }

    public void setKey4(boolean key4) {
        this.key4 = key4;
    }

    public boolean getKey5() {
        return key5;
    }

    public void setKey5(boolean key5) {
        this.key5 = key5;
    }

    public boolean getKey6() {
        return key6;
    }

    public void setKey6(boolean key6) {
        this.key6 = key6;
    }

    public boolean getKey7() {
        return key7;
    }

    public void setKey7(boolean key7) {
        this.key7 = key7;
    }

    public boolean getKey8() {
        return key8;
    }

    public void setKey8(boolean key8) {
        this.key8 = key8;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getUseranswer() {
        return useranswer;
    }

    public void setUseranswer(String useranswer) {
        this.useranswer = useranswer;
    }

    public BigDecimal getMark() {
        return mark;
    }

    public void setMark(BigDecimal mark) {
        this.mark = mark;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ExamPaperPool)) {
            return false;
        }
        ExamPaperPool other = (ExamPaperPool) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.dgpx.entity.ExamPaperPool[ id=" + id + " ]";
    }

    /**
     * @return the score
     */
    public BigDecimal getScore() {
        return score;
    }

    /**
     * @param score the score to set
     */
    public void setScore(BigDecimal score) {
        this.score = score;
    }

}
