/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logic.LogsManagement;

import logic.TLogDTO;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Singleton;
import logic.DTOFactory;
import logic.UsersManagement.TUser;
import logic.UsersManagement.TUserFacadeLocal;

@Singleton
public class LogsManager implements LogsManagerLocal {
    
    private static final String DATE_COLUMN = "datelog";
    
    @EJB
    TLogFacadeLocal logFacade;
    
    @EJB
    TUserFacadeLocal userFacade;
    
    public LogsManager() {
        // Do nothing
    }

    @Override
    public List<TLogDTO> getLogs(int lines) {
        List<TLog> retrievedList;
        
        if (lines > 0) {
            retrievedList = logFacade.findLast(lines, DATE_COLUMN);
            Collections.reverse(retrievedList);
        
        } else {
            retrievedList = logFacade.findAll();
        }
    
        List<TLogDTO> logList = new ArrayList<>();
        for (TLog log : retrievedList) {
            logList.add(DTOFactory.getTLogDTOFromTLog(log));
        }
        
        return logList;
    }

    @Override
    public TLogDTO getLogsByUser(String username) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public boolean addLog(TLogDTO log) {
        TUser user = userFacade.find(log.getUser().getId());
        if (user == null) {
            return false;
        }
        
        
        TLog newLog = DTOFactory.getTLogFromTLogDTO(log);
        newLog.setUserid(user);
        
        logFacade.create(newLog);
        return true;
    }
}