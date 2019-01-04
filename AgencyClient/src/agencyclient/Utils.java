/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package agencyclient;

import logic.LogTypes;
import logic.TLogDTO;

/** Class for utility methods */
public class Utils {
    
    private static final String PURCHASE_TRIP_LOG_MESSAGE = "purchased trip";
    private static final String CREATE_TRIP_LOG_MESSAGE = "created trip";
    private static final String FINISH_TRIP_LOG_MESSAGE = "finished trip";
    
    // logs
    
    public static String getFormattedLogMessage(TLogDTO log) {
        String date = "[" + log.getDate() + "] | ";
        String user = "user=" + log.getUser().getUsername() + " | ";
        String action = "action=" + getActionTextFromLogType(log.getMsg());
        return date + user + action;
    }
    
    public static String getActionTextFromLogType(String logType) {
        switch (logType) {
            case LogTypes.PURCHASE_TRIP:
                return PURCHASE_TRIP_LOG_MESSAGE;
                
            case LogTypes.CREATE_TRIP:
                return CREATE_TRIP_LOG_MESSAGE;
                
            case LogTypes.FINISH_TRIP:
                return FINISH_TRIP_LOG_MESSAGE;
                
            default:
                System.out.println("Unknown log type:" + logType);
                return logType; 
        }
    }
    
}
