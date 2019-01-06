/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logic;

/**
 *
 * @author bruno
 */
public class TSeatDTO {
    private Integer id;
    private String luggage;
    private Boolean auctioned;
    private Double price;
    private TTripDTO trip;
    private TUserDTO user;

    public TSeatDTO() {
    }

    public TSeatDTO(Integer id, String luggage, Boolean auctioned, Double price, TTripDTO trip, TUserDTO user) {
        this.id = id;
        this.luggage = luggage;
        this.auctioned = auctioned;
        this.price = price;
        this.trip = trip;
        this.user = user;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getLuggage() {
        return luggage;
    }

    public void setLuggage(String luggage) {
        this.luggage = luggage;
    }

    public Boolean getAuctioned() {
        return auctioned;
    }

    public void setAuctioned(Boolean auctioned) {
        this.auctioned = auctioned;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public TTripDTO getTrip() {
        return trip;
    }

    public void setTrip(TTripDTO trip) {
        this.trip = trip;
    }

    public TUserDTO getUser() {
        return user;
    }

    public void setUser(TUserDTO user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "TSeatDTO{" + "id=" + id + ", luggage=" + luggage + ", auctioned=" + auctioned + ", price=" + price + ", trip=" + trip + ", user=" + user + '}';
    }
    
    
}
