/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logic;

import java.io.Serializable;

/**
 *
 * @author bruno
 */
public class TTripDTO implements Serializable{
    private Integer id;
    private Double price;
    private Boolean done;
    private Boolean canceled;
    private TAirlineDTO airlineDTO;
    private TPlaceDTO placeDTO;
    private TPlaneDTO planeDTO;

    public TTripDTO() {
    }

    public TTripDTO(Integer id, Double price, Boolean done, Boolean canceled, TAirlineDTO airlineDTO, TPlaceDTO placeDTO, TPlaneDTO planeDTO) {
        this.id = id;
        this.price = price;
        this.done = done;
        this.canceled = canceled;
        this.airlineDTO = airlineDTO;
        this.placeDTO = placeDTO;
        this.planeDTO = planeDTO;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Boolean getDone() {
        return done;
    }

    public void setDone(Boolean done) {
        this.done = done;
    }

    public Boolean getCanceled() {
        return canceled;
    }

    public void setCanceled(Boolean canceled) {
        this.canceled = canceled;
    }

    public TAirlineDTO getAirlineDTO() {
        return airlineDTO;
    }

    public void setAirlineDTO(TAirlineDTO airlineDTO) {
        this.airlineDTO = airlineDTO;
    }

    public TPlaceDTO getPlaceDTO() {
        return placeDTO;
    }

    public void setPlaceDTO(TPlaceDTO placeDTO) {
        this.placeDTO = placeDTO;
    }

    public TPlaneDTO getPlaneDTO() {
        return planeDTO;
    }

    public void setPlaneDTO(TPlaneDTO planeDTO) {
        this.planeDTO = planeDTO;
    }

    @Override
    public String toString() {
        return "TTripDTO{" + "id=" + id + ", price=" + price + ", done=" + done + ", canceled=" + canceled + ", airlineDTO=" + airlineDTO + ", placeDTO=" + placeDTO + ", planeDTO=" + planeDTO + '}';
    }
}
