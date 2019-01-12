/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logic.LogsManagement;

import java.io.Serializable;
import logic.Log;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.ejb.Singleton;
import javax.inject.Inject;
import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSConnectionFactory;
import javax.jms.JMSContext;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.ObjectMessage;
import javax.jms.Queue;
import javax.jms.Session;
import logic.Config;
import logic.NoPermissionException;
import logic.TUserDTO;
import logic.UsersManagement.UsersManagerLocal;

@Singleton
public class LogsManager implements LogsManagerLocal {
  
    @EJB
    UsersManagerLocal userManager;
    
    @Inject
    @JMSConnectionFactory("jms/MyConnFactory")
    private JMSContext jmsContext;
    
    @Resource(lookup = "jms/MyConnFactory")
    private ConnectionFactory connectionFactory;
    
    @Resource(mappedName = "jms/MyQueue")
    Queue logsQueue;
    
    public LogsManager() {
        // Do nothing
    }
    
    @Override
    public void sendLogMessage(String username, String msg, int date) throws NoPermissionException {        
        if (username == null || username.isEmpty())
                throw new NoPermissionException(Config.MSG_NO_PERMISSION_LOG);
        
        
        TUserDTO userDTO = userManager.getTUserDTO(username);
        
        System.out.println("sendLogMessage: user retrieved:" + userDTO);
        
        if(userDTO == null)
            throw new NoPermissionException(Config.MSG_NO_PERMISSION_LOG);
        
        Log log = new Log(userDTO, msg, date);
        ObjectMessage message = jmsContext.createObjectMessage(log);
        jmsContext.createProducer().send(logsQueue, message);
    }

    @Override
    public List<Log> getLogs(String username) throws NoPermissionException {
        userManager.verifyPermission(username, Config.OPERATOR);

        List<Log> logs = new ArrayList<>();
        Connection connection;
        try {
            connection = connectionFactory.createConnection();
            Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            
            MessageConsumer consumer = session.createConsumer(logsQueue);
            connection.start();
            
            while (true) {
                Message message = consumer.receive(1000);
                if (message != null) {
                    if (!(message instanceof ObjectMessage)) {
                        System.out.println("[LOGS] getLogs - not an ObjectMessage");
                        continue;
                    }
                    
                    Serializable log = ((ObjectMessage) message).getObject();
                    if (!(log instanceof Log)) {
                        System.out.println("[LOGS] getLogs - not a TLogDTO");
                        continue;
                    }

                    logs.add((Log) log);
                    System.out.println("[LOGS] getLogs - Retrieved message: " + message);
                    
                } else {
                    System.out.println("[LOGS] getLogs - No more messages");
                    break;
                }
            }

        } catch (JMSException ex) {
            System.out.println("[LOGS] getLogs - Logs retrieving failed: " + ex);
        }
        
        return logs;
    }
}