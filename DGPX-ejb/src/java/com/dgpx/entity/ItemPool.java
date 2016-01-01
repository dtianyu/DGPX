/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dgpx.entity;

import com.lightshell.comm.BaseEntityWithOperate;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
@Table(name = "itempool")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ItemPool.getRowCount", query = "SELECT COUNT(i) FROM ItemPool i"),
    @NamedQuery(name = "ItemPool.findAll", query = "SELECT i FROM ItemPool i"),
    @NamedQuery(name = "ItemPool.findById", query = "SELECT i FROM ItemPool i WHERE i.id = :id"),
    @NamedQuery(name = "ItemPool.findByExamctgyId", query = "SELECT i FROM ItemPool i WHERE i.examcategory.id = :examctgyid"),
    @NamedQuery(name = "ItemPool.findByItemctgyId", query = "SELECT i FROM ItemPool i WHERE i.itemcategory.id = :itemctgyid"),
    @NamedQuery(name = "ItemPool.findByItemctgyIdAndKnowledgeId", query = "SELECT i FROM ItemPool i WHERE i.itemcategory.id = :itemctgyid AND i.knowledge.id = :knowledgeid"),
    @NamedQuery(name = "ItemPool.findByKnowledgeId", query = "SELECT i FROM ItemPool i WHERE i.knowledge.id = :knowledgeid"),
    @NamedQuery(name = "ItemPool.findByQuestion", query = "SELECT i FROM ItemPool i WHERE i.question = :question"),
    @NamedQuery(name = "ItemPool.findByStatus", query = "SELECT i FROM ItemPool i WHERE i.status = :status")})
public class ItemPool extends BaseEntityWithOperate {

    @JoinColumn(name = "examctgyid", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private ExamCategory examcategory;
    @JoinColumn(name = "itemctgyid", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private ItemCategory itemcategory;
    @JoinColumn(name = "knowledgeid", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Knowledge knowledge;
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
    @Size(max = 200)
    @Column(name = "remark")
    private String remark;

    public ItemPool() {
    }

    public ItemPool(boolean key1, boolean key2, boolean key3, boolean key4, boolean key5, boolean key6, boolean key7, boolean key8, String status) {
        this.key1 = key1;
        this.key2 = key2;
        this.key3 = key3;
        this.key4 = key4;
        this.key5 = key5;
        this.key6 = key6;
        this.key7 = key7;
        this.key8 = key8;
        this.status = status;
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

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
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
        if (!(object instanceof ItemPool)) {
            return false;
        }
        ItemPool other = (ItemPool) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.dgpx.entity.ItemPool[ id=" + id + " ]";
    }

    /**
     * @return the examcategory
     */
    public ExamCategory getExamcategory() {
        return examcategory;
    }

    /**
     * @param examcategory the examcategory to set
     */
    public void setExamcategory(ExamCategory examcategory) {
        this.examcategory = examcategory;
    }

    /**
     * @return the itemcategory
     */
    public ItemCategory getItemcategory() {
        return itemcategory;
    }

    /**
     * @param itemcategory the itemcategory to set
     */
    public void setItemcategory(ItemCategory itemcategory) {
        this.itemcategory = itemcategory;
    }

    /**
     * @return the knowledge
     */
    public Knowledge getKnowledge() {
        return knowledge;
    }

    /**
     * @param knowledge the knowledge to set
     */
    public void setKnowledge(Knowledge knowledge) {
        this.knowledge = knowledge;
    }

    /**
     * @return the answer
     */
    public String getAnswer() {
        return answer;
    }

    /**
     * @param answer the answer to set
     */
    public void setAnswer(String answer) {
        this.answer = answer;
    }

}
