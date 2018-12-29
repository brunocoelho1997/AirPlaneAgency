/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logic;

import java.io.Serializable;
import java.util.Collection;

/**
 *
 * @author bruno
 */
public class TPlaceDTO implements Serializable{
    
    private Integer id;
    private String country;
    private String city;
    private String address;
    private Collection<TPlaceFeedbackDTO> tPlacefeedbackDTOCollection;

    public TPlaceDTO() {
    }

    public TPlaceDTO(Integer id, String country, String city, String address, Collection<TPlaceFeedbackDTO> tPlacefeedbackDTOCollection) {
        this.id = id;
        this.country = country;
        this.city = city;
        this.address = address;
        this.tPlacefeedbackDTOCollection = tPlacefeedbackDTOCollection;
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

    public Collection<TPlaceFeedbackDTO> gettPlacefeedbackDTOCollection() {
        return tPlacefeedbackDTOCollection;
    }

    public void settPlacefeedbackDTOCollection(Collection<TPlaceFeedbackDTO> tPlacefeedbackDTOCollection) {
        this.tPlacefeedbackDTOCollection = tPlacefeedbackDTOCollection;
    }

    @Override
    public String toString() {
        return "TPlaceDTO{" + "id=" + id + ", country=" + country + ", city=" + city + ", address=" + address + ", tPlacefeedbackDTOCollection=" + tPlacefeedbackDTOCollection + '}';
    }
}
