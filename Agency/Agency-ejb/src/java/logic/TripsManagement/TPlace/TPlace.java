/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logic.TripsManagement.TPlace;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import logic.TripsManagement.TPlaceFeedback.TPlacefeedback;

/**
 *
 * @author bruno
 */
@Entity
@Table(name = "t_place")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TPlace.findAll", query = "SELECT t FROM TPlace t")
    , @NamedQuery(name = "TPlace.findById", query = "SELECT t FROM TPlace t WHERE t.id = :id")
    , @NamedQuery(name = "TPlace.findByCountry", query = "SELECT t FROM TPlace t WHERE t.country = :country")
    , @NamedQuery(name = "TPlace.findByCity", query = "SELECT t FROM TPlace t WHERE t.city = :city")
    , @NamedQuery(name = "TPlace.findByAddress", query = "SELECT t FROM TPlace t WHERE t.address = :address")})
public class TPlace implements Serializable {

    @OneToMany(mappedBy = "placeid")
    private Collection<TPlacefeedback> tPlacefeedbackCollection;

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @Column(name = "country")
    private String country;
    @Basic(optional = false)
    @Column(name = "city")
    private String city;
    @Basic(optional = false)
    @Column(name = "address")
    private String address;

    public TPlace() {
    }

    public TPlace(Integer id) {
        this.id = id;
    }

    public TPlace(Integer id, String country, String city, String address) {
        this.id = id;
        this.country = country;
        this.city = city;
        this.address = address;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
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
        if (!(object instanceof TPlace)) {
            return false;
        }
        TPlace other = (TPlace) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "TPlace{" + "tPlacefeedbackCollection=" + tPlacefeedbackCollection + ", id=" + id + ", country=" + country + ", city=" + city + ", address=" + address + '}';
    }

    

    @XmlTransient
    public Collection<TPlacefeedback> getTPlacefeedbackCollection() {
        return tPlacefeedbackCollection;
    }

    public void setTPlacefeedbackCollection(Collection<TPlacefeedback> tPlacefeedbackCollection) {
        this.tPlacefeedbackCollection = tPlacefeedbackCollection;
    }
    
}
