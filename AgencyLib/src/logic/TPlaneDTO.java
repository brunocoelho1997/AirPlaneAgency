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
public class TPlaneDTO implements Serializable{
    private int id;
    private String planeName;
    private int planeLimit;

    public TPlaneDTO() {
    }

    public TPlaneDTO(int id, String planeName, int planeLimit) {
        this.id = id;
        this.planeName = planeName;
        this.planeLimit = planeLimit;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPlaneName() {
        return planeName;
    }

    public void setPlaneName(String planeName) {
        this.planeName = planeName;
    }

    public int getPlaneLimit() {
        return planeLimit;
    }

    public void setPlaneLimit(int planeLimit) {
        this.planeLimit = planeLimit;
    }

    @Override
    public String toString() {
        return "TPlaneDTO{" + "id=" + id + ", planeName=" + planeName + ", planeLimit=" + planeLimit + '}';
    }
    
}
