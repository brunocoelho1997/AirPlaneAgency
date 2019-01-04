/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logic.LogsManagement;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import logic.UsersManagement.TUser;

@Entity
@Table(name = "t_log")
@NamedQueries({
    @NamedQuery(name = "TLog.findAll", query = "SELECT t FROM TLog t")
    , @NamedQuery(name = "TLog.findById", query = "SELECT t FROM TLog t WHERE t.id = :id")
    , @NamedQuery(name = "TLog.findByMsg", query = "SELECT t FROM TLog t WHERE t.msg = :msg")
    , @NamedQuery(name = "TLog.findByDatelog", query = "SELECT t FROM TLog t WHERE t.datelog = :datelog")})
public class TLog implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @Column(name = "msg")
    private String msg;
    @Basic(optional = false)
    @Column(name = "datelog")
    private int datelog;
    @JoinColumn(name = "userid", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private TUser userid;

    public TLog() {
    }

    public TLog(Integer id) {
        this.id = id;
    }

    public TLog(Integer id, String msg, int datelog) {
        this.id = id;
        this.msg = msg;
        this.datelog = datelog;
    }
    
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getDatelog() {
        return datelog;
    }

    public void setDatelog(int datelog) {
        this.datelog = datelog;
    }

    public TUser getUserid() {
        return userid;
    }

    public void setUserid(TUser userid) {
        this.userid = userid;
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
        if (!(object instanceof TLog)) {
            return false;
        }
        TLog other = (TLog) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "logic.TLog[ id=" + id + " ]";
    }
    
}
