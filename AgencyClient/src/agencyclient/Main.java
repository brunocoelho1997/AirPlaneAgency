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
            System.out.print(">");
            String command = sc.nextLine();
            switch (command) {
                case Command.SIGNIN:
                    processSignIn();
                    break;
                case Command.SIGNUP:
                    processSignUp();
                    break;
                case Command.HELP:
                    printCommandList();
                    break;
                case Command.EXIT:
                    listenCommand = false;
                    break;
                    
                default:
                    System.out.println("Command not found. Type help to get a command list.");

                    break;
                    
            }            
        }
    }

    private static void processSignIn(){
        Scanner sc = new Scanner(System.in);
        String username, password;
        boolean result;
        
        System.out.println("Username:");
        username = sc.nextLine();
        System.out.println("Password:");
        password = sc.nextLine();

        result = sAgencyManager.signIn(username, password);
        if(!result)
            System.out.println("Username or Password wrong.");
        else
            System.out.println("Wellcome to the Agency, " + username + ".");
    }
    
    private static void processSignUp(){
        Scanner sc = new Scanner(System.in);
        String username, password, passwordConfirmation, name;
        boolean result;
        
        System.out.println("Username:");
        username = sc.nextLine();
        System.out.println("Password:");
        password = sc.nextLine();
        System.out.println("Password Confirmation:");
        passwordConfirmation = sc.nextLine();
        System.out.println("Name:");
        name = sc.nextLine();
        
        

        result = sAgencyManager.signUp(username, password, passwordConfirmation, name);
        if(!result)
            System.out.println("Username already exists or passwords are different.");
        else
            System.out.println("Sign up with sucess! Now you can sign in, " + name + ".");
    }
    
    private static void printCommandList(){
        System.out.println("\n-- Help --");
        System.out.println("signin - Sign in");
        System.out.println("signup - Sign up");
        System.out.println("asguest - Enter as guest");
        System.out.println("exit - Exit");
        System.out.println("----------------");
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