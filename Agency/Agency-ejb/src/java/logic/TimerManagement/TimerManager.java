/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logic.TimerManagement;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.ejb.Singleton;
import javax.ejb.Timeout;
import javax.ejb.Timer;
import javax.ejb.TimerConfig;
import javax.ejb.TimerService;
import logic.Config;

/**
 *
 * @author bruno
 */
@Singleton
public class TimerManager implements TimerManagerLocal {

    @Resource
    private TimerService timerService;
    
    private int date;
    private long timerDuration;
    static String timerName = "IntervalTimer_Info";
    
    @PostConstruct
    private void init() {
        this.date = 0;
        this.timerDuration = Config.DEFAULT_TIMER / 1000;
        timerService.createTimer(1000, secondsToMiliSeconds(Config.DEFAULT_TIMER), timerName);
    }
    
    @Timeout
    public void incrementDate(Timer timer) {
        date++;
    }

    @Override
    public int getDate() {
        return date;
    }
    @Override
    public boolean setDate(int date) {
        this.date = date;
        return true;
    }

    @Override
    public boolean setDurationTimer(long durationSeconds) {
        
        if(!stopTimer())
            return false;
        this.timerDuration = durationSeconds;        
        this.timerService.createTimer(1000, secondsToMiliSeconds(durationSeconds), timerName);
        return true;
    }
    
    @Override
    public String getTimerInformation() {
        
        String info = new String();
        
        info += "\n-------Timer-------\n";
        info += "\nAtual Date: " + date;
        info += "\nAtual Duration: " + timerDuration + " (seconds)";
        info += "\nTimer Remaining: " + getTimer(timerName).getTimeRemaining();
        info += "\n-----------------\n";

        return info;
    }
    
    
    //auxiliar methods
    public boolean stopTimer(){
        for(Object obj : timerService.getTimers()) {
            Timer t = (Timer)obj;
            if (t.getInfo().equals(timerName)) {
                t.cancel();
                return true;
            }
        }
        return false;
    }
    
    public Timer getTimer(String timerName){
        for(Object obj : timerService.getTimers()) {
            Timer t = (Timer)obj;
            if (t.getInfo().equals(timerName)) {
                return t;
            }
        }
        return null;
    }
    
    private long secondsToMiliSeconds(long seconds){
        return seconds * 1000;
    }

    
    
}
