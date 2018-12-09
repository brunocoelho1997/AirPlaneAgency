package agencyclient;

import java.util.Properties;
import java.util.Scanner;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import logic.AgencyManagerRemote;

public class Main {
    
    private static AgencyManagerRemote sAgencyManager;
    
    public static void main(String[] args) {
        initRemoteReferences();
        
        doListenCommands();
    }
    
    private static void doListenCommands() {
        Scanner sc = new Scanner(System.in);
        boolean listenCommand = true;

        while (listenCommand) {
            System.out.println("TODO: Listen command....");

            String command = sc.nextLine();
            switch (command) {
                case Command.EXIT:
                    listenCommand = false;
                    break;
                    
                default:
                    break;
                    
            }            
        }
    }

    private static void initRemoteReferences() {
        Properties prop = new Properties();

        prop.setProperty("java.naming.factory.initial",
                "com.sun.enterprise.naming.SerialInitContextFactory");

        prop.setProperty("java.naming.factory.url.pkgs",
                "com.sun.enterprise.naming");

        prop.setProperty("java.naming.factory.state",
                "com.sun.corba.ee.impl.presentation.rmi.JNDIStateFactoryImpl");

        prop.setProperty("org.omg.CORBA.ORBInitialHost", "192.168.56.175");
        prop.setProperty("org.omg.CORBA.ORBInitialPort", "3700");

        InitialContext ctx = null;
        try {
           ctx = new InitialContext(prop);
        
        } catch (NamingException e) {
           System.out.println(e.getMessage());
           System.exit(1);
        }

        String agencyManagerClassName = "java:global/Agency/Agency-ejb/AgencyManager!logic.AgencyManagerRemote";

        try {
           sAgencyManager = (AgencyManagerRemote) ctx.lookup(agencyManagerClassName);
        
        } catch (NamingException e) {
           System.out.println(e.getMessage());
        }
    }
    
}