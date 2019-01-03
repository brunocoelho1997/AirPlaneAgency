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
    
    private int time;
    private long timerDuration;
    static String timerName = "IntervalTimer_Info";
    
    @PostConstruct
    private void init() {
        timerService.createTimer(1000, Config.DEFAULT_TIMER, timerName);
    }
    
    
    public TimerManager() {
        this.time = 0;
    }
    
    @Timeout
    public void incrementDate(Timer timer) {
        time++;
    }
    

    @Override
    public int getDate() {
        return time;
    }

    @Override
    public boolean setDurationTimer(long durationMinuts) {
        
        long durationSeconds = 60 * durationMinuts;
        
        if(!stopTimer())
            return false;
        
        this.timerDuration = durationSeconds;
        
        this.timerService.createTimer(1000, durationSeconds, timerName);
        
        return true;
    }
    
    @Override
    public String getTimerInformation() {
        
        String info = new String();
        
        info += "\n-------Timer-------\n";
        info += "Atual Duration: " + timerDuration;
        info += "\nTimer Remaining: " + getTimer(timerName).getTimeRemaining();
        info += "-----------------\n";

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

    
}
