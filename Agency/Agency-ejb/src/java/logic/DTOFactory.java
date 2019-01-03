/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logic;

import logic.LogsManagement.TLog;
import logic.LogsManagement.TLogDTO;
import logic.UsersManagement.TUser;


public class DTOFactory {
    
    /* From DB object to DTO */
    
    public static TUserDTO getTUserDTOFromTUser(TUser user){
        TUserDTO tUserDTO = new TUserDTO();
        tUserDTO.setUsername(user.getUsername());
        tUserDTO.setPassword(user.getPassword());
        tUserDTO.setAccepted(user.getAccepted());
        tUserDTO.setUsertype(user.getUsertype());
        tUserDTO.setId(user.getId());
        
        if(user.getUsertype() == Config.CLIENT)
        {
            tUserDTO.setBalance(user.getBalance());
            tUserDTO.setClientName(user.getClientname());
        }
        return tUserDTO;
    }
    
    public static TLogDTO getTLogDTOFromTLog(TLog log) {
        TUserDTO user = getTUserDTOFromTUser(log.getUserid());
        return new TLogDTO(user, log.getMsg(), log.getDatelog());
    }
    
    /* From DTO to DB object */
    
    public static TUser getTUserFromTUserDTO(TUserDTO userDTO) {
        TUser newUser = new TUser();
        
        newUser.setUsername(userDTO.getUsername());
        newUser.setPassword(userDTO.getPassword());
        newUser.setUsertype(userDTO.getUsertype());
        newUser.setAccepted(false);
        newUser.setBalance((double) 0);
        
        if(userDTO.getUsertype()==Config.CLIENT)
        {
            newUser.setBalance(userDTO.getBalance());
            newUser.setClientname(userDTO.getClientName());
        }
        
        return newUser;
    }
    
    public static TLog getTLogFromTLogDTO(TLogDTO log) {        
        TLog newLog = new TLog();
        newLog.setMsg(log.getMsg());
        newLog.setDatelog(log.getDate());
        return newLog;
    }
    
}