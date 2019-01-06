/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logic;

import java.util.Collection;

/**
 *
 * @author bruno
 */
public class TPurchaseDTO {
    private Integer id;
    private Collection<TSeatDTO> tSeatCollection;
    private TUserDTO user;

    public TPurchaseDTO() {
    }

    public TPurchaseDTO(Integer id, Collection<TSeatDTO> tSeatCollection, TUserDTO user) {
        this.id = id;
        this.tSeatCollection = tSeatCollection;
        this.user = user;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Collection<TSeatDTO> gettSeatCollection() {
        return tSeatCollection;
    }

    public void settSeatCollection(Collection<TSeatDTO> tSeatCollection) {
        this.tSeatCollection = tSeatCollection;
    }

    public TUserDTO getUser() {
        return user;
    }

    public void setUser(TUserDTO user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "TPurchaseDTO{" + "id=" + id + ", tSeatCollection=" + tSeatCollection + ", user=" + user + '}';
    }
    
    
}
