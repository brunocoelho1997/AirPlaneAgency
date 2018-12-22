package agencyclient;

import java.util.Properties;
import java.util.Scanner;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import logic.AgencyManagerRemote;
import logic.Config;
import logic.TPlaneDTO;
import logic.TUserDTO;

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
                case Command.ACCEPTUSER:
                    processAcceptUser();
                    break;
                case Command.FINDALLPLANES:
                    processPlanesFindAll();
                    break;
                case Command.ADDPLANE:
                    processAddPlane();
                    break;
                case Command.EDITPLANE:
                    processEditPlane();
                    break;
                case Command.REMOVEPLANE:
                    processRemovePlane();
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
        TUserDTO userDTO = new TUserDTO();
        boolean result;
        
        do{
            System.out.println("Type of the User:");
            System.out.println("0- Operator");
            System.out.println("1- Client");
            System.out.print("Type: ");
            userDTO.setUsertype(Integer.parseInt(sc.nextLine()));
        }while(userDTO.getUsertype() != Config.CLIENT && userDTO.getUsertype() != Config.OPERATOR);
        
        System.out.println("Username:");
        userDTO.setUsername(sc.nextLine());
        System.out.println("Password:");
        userDTO.setPassword(sc.nextLine());

        if(userDTO.getUsertype()==Config.CLIENT)
        {
            System.out.println("Name:");
            userDTO.setClientName(sc.nextLine());
        }
        
        result = sAgencyManager.signUp(userDTO);
        if(!result)
            System.out.println("Username already exists or passwords are different.");
        else
            System.out.println("Sign up with sucess! Now you can sign in, " + userDTO.getUsername() + ".");
    }
    
    private static void processAcceptUser() {
        Scanner sc = new Scanner(System.in);
        TUserDTO userDTO;
        boolean result;
        
        System.out.println("Username:");
        
        
        /*
        
        sAgencyManager.acceptUser(userDTO);
        
        result = sAgencyManager.signUp(userDTO);
        if(!result)
            System.out.println("Username already exists or passwords are different.");
        else
            System.out.println("Sign up with sucess! Now you can sign in, " + userDTO.getUsername() + ".");

        */
    }
    
    private static void processPlanesFindAll(){
        
        System.out.println("All planes in the system:");
        
        for(TPlaneDTO planeDTO : sAgencyManager.findAll())
            System.out.println(planeDTO);
    }
    
    private static void processAddPlane(){
        Scanner sc = new Scanner(System.in);
        TPlaneDTO planeDTO = new TPlaneDTO();
        boolean result;
        
        System.out.println("Plane Name:");
        planeDTO.setPlaneName(sc.nextLine());
        System.out.println("Plane Limit:");
        planeDTO.setPlaneLimit(Integer.parseInt(sc.nextLine()));
        
        result = sAgencyManager.addPlane(planeDTO);
        if(!result)
            System.out.println("A problem occurred. The system didn't add the plane.");
        else
            System.out.println("Plane added with sucess!");
    }
    
    private static void processEditPlane(){
        Scanner sc = new Scanner(System.in);
        TPlaneDTO planeDTO;
        boolean result;
        int id;
        
        try
        {
            System.out.println("Plane Id:");
            id = Integer.parseInt(sc.nextLine());
            planeDTO = sAgencyManager.findPlane(id);
            
            if(planeDTO == null)
            {
                System.out.println("Not found a plane with that id. Try again.");
                return;
            }
            
            System.out.println("Plane: " + planeDTO);
            
            System.out.println("New plane Name:");
            planeDTO.setPlaneName(sc.nextLine());
            System.out.println("New plane Limit:");
            planeDTO.setPlaneLimit(Integer.parseInt(sc.nextLine()));
            
            result = sAgencyManager.editPlane(planeDTO);
            if(!result)
                System.out.println("A problem occurred. The system didn't edit the plane.");
            else
                System.out.println("Plane edited with success!");
        
        }catch(Exception e){
            System.err.println("Error - " + e);
        }
    }
    
    private static void processRemovePlane(){
        Scanner sc = new Scanner(System.in);
        TPlaneDTO planeDTO;
        boolean result;
        int id;
        int op;
        
        try
        {
            System.out.println("Plane Id:");
            id = Integer.parseInt(sc.nextLine());
            planeDTO = sAgencyManager.findPlane(id);
            
            if(planeDTO == null)
            {
                System.out.println("Not found a plane with that id. Try again.");
                return;
            }
            
            
            System.out.println("Do you want remove the plane: " + planeDTO + "? [1/0]");
            op = Integer.parseInt(sc.nextLine());
            
            if(op == 0)
                return;
            
            result = sAgencyManager.removePlane(planeDTO);
            if(!result)
                System.out.println("A problem occurred. The system didn't remove the plane.");
            else
                System.out.println("Plane removed with success!");
        
        }catch(Exception e){
            System.err.println("Error - " + e);
        }
    }
    
    private static void printCommandList(){
        System.out.println("\n-- Help --");
        
        //users
        System.out.println("signin - Sign in");
        System.out.println("signup - Sign up");
        System.out.println("asguest - Enter as guest");

        //planes        
        System.out.println("findallplanes - List All Planes");
        System.out.println("addplane - Add a new plane");
        System.out.println("editplane - Edit a plane");
        System.out.println("removeplane - Remove a plane");
        
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