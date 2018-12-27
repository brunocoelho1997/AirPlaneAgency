/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logic.TripsManagement.TFeedback;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author bruno
 */
@Entity
@Table(name = "t_feedback")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TFeedback.findAll", query = "SELECT t FROM TFeedback t")
    , @NamedQuery(name = "TFeedback.findById", query = "SELECT t FROM TFeedback t WHERE t.id = :id")
    , @NamedQuery(name = "TFeedback.findByScore", query = "SELECT t FROM TFeedback t WHERE t.score = :score")})
public class TFeedback implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @Column(name = "score")
    private int score;

    public TFeedback() {
    }

    public TFeedback(Integer id) {
        this.id = id;
    }

    public TFeedback(Integer id, int score) {
        this.id = id;
        this.score = score;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
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
        if (!(object instanceof TFeedback)) {
            return false;
        }
        TFeedback other = (TFeedback) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "logic.TripsManagement.TFeedback.TFeedback[ id=" + id + " ]";
    }
    
}
