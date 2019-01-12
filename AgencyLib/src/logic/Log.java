/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logic;

import java.io.Serializable;

public class Log implements Serializable {
    
    private final TUserDTO user;
    private final String msg;
    private final int date;
    
    public Log(TUserDTO user, String msg, int date) {
        this.user = user;
        this.msg = msg;
        this.date = date;
    }

    public TUserDTO getUser() {
        return user;
    }

    public String getMsg() {
        return msg;
    }

    public int getDate() {
        return date;
    }

    @Override
    public String toString() {
        return "Log {" +
                "user=" + user + 
                "msg=" + msg +
                "date=" + date + "}";
    }
}