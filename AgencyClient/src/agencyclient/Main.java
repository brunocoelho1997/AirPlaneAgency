package agencyclient;

import java.util.Properties;
import java.util.Scanner;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import logic.AgencyManagerRemote;
import logic.Config;
import logic.NoPermissionException;
import logic.TAirlineDTO;
import logic.TPlaceDTO;
import logic.TPlaceFeedbackDTO;
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
            try{
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
                    case Command.LOGOUT:
                        processLogout();
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
                    case Command.FINDALLAIRLINES:
                        processAirlinesFindAll();
                        break;
                    case Command.ADDAIRLINE:
                        processAddAirline();
                        break;
                    case Command.EDITAIRLINE:
                        processEditAirline();
                        break;
                    case Command.REMOVEAIRLINE:
                        processRemoveAirline();
                        break;
                    case Command.FINDALLPLACES:
                        processPlacesFindAll();
                        break;
                    case Command.ADDPLACE:
                        processAddPlace();
                        break;
                    case Command.EDITPLACE:
                        processEditPlace();
                        break;
                    case Command.REMOVEPLACE:
                        processRemovePlace();
                        break;
                    case Command.FINDALLMYPLACESFEEDBACKS:
                        processMyPlacesFeedbackFindAll();
                        break;
                    case Command.ADDPLACEFEEDBACK:
                        processAddPlaceFeedback();
                        break;
                    default:
                        System.out.println("Command not found. Type help to get a command list.");

                        break;

                }
            }catch(Exception e){
                System.err.println("Error - " + e);
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
    
    private static void processLogout(){
        boolean result;

        result = sAgencyManager.logout();
        if(!result)
            System.out.println("A problem occurred. The system didn't finish de task.");
        else
            System.out.println("You have done logout with success!");
    }
            
    private static void processAcceptUser() {
        Scanner sc = new Scanner(System.in);
        TUserDTO userDTO;
        String username;
        boolean result = false;
        int op;
        
        System.out.println("Username:");
        username = sc.nextLine();
        
        userDTO = sAgencyManager.getTUserDTO(username);
                
        if(userDTO == null)
        {
            System.out.println("Not found a user with that name. Try again.");
            return;
        }
        
        System.out.println("Do you want accept the user " + userDTO + " ? [1/0]");
        op = Integer.parseInt(sc.nextLine());
        
        if(op==0)
            return;
        
        sAgencyManager.acceptUser(userDTO);
        
        if(!result)
            System.out.println("The user has been accepted with success.");
        else
            System.out.println("An error has occurred..");

        
    }
    
    //-------------------------------------------------------
    //planes
    private static void processPlanesFindAll() throws NoPermissionException{
        
        System.out.println("All planes in the system:");
        
        for(TPlaneDTO planeDTO : sAgencyManager.findAllPlanes())
            System.out.println(planeDTO);
    }
    
    private static void processAddPlane() throws NoPermissionException{
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
    
    private static void processEditPlane() throws NoPermissionException{
        Scanner sc = new Scanner(System.in);
        TPlaneDTO planeDTO;
        boolean result;
        int id;
        

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
    }
    
    private static void processRemovePlane() throws NoPermissionException{
        Scanner sc = new Scanner(System.in);
        TPlaneDTO planeDTO;
        boolean result;
        int id;
        int op;
        
        
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
    }
    
    //----------------------------------------------------
    //airlines
    private static void processAirlinesFindAll() throws NoPermissionException{
        
        System.out.println("All airlines in the system:");
        
        for(TAirlineDTO airlineDTO : sAgencyManager.findAllAirlines())
            System.out.println(airlineDTO);
    }
    
    private static void processAddAirline() throws NoPermissionException{
        Scanner sc = new Scanner(System.in);
        TAirlineDTO airlineDTO = new TAirlineDTO();
        boolean result;
        
        System.out.println("Airline Name:");
        airlineDTO.setAirlineName(sc.nextLine());
        System.out.println("Phone Number:");
        airlineDTO.setPhoneNumber(sc.nextLine());
        
        result = sAgencyManager.addAirline(airlineDTO);
        if(!result)
            System.out.println("A problem occurred. The system didn't add the airline.");
        else
            System.out.println("Airline added with sucess!");
    }
    
    private static void processEditAirline() throws NoPermissionException{
        Scanner sc = new Scanner(System.in);
        TAirlineDTO airlineDTO;
        boolean result;
        int id;
        

        System.out.println("Airline Id:");
        id = Integer.parseInt(sc.nextLine());
        airlineDTO = sAgencyManager.findAirline(id);

        if(airlineDTO == null)
        {
            System.out.println("Not found an airline with that id. Try again.");
            return;
        }

        System.out.println("Airline: " + airlineDTO);

        System.out.println("New Airline Name:");
        airlineDTO.setAirlineName(sc.nextLine());
        System.out.println("New Phonenumber:");
        airlineDTO.setPhoneNumber(sc.nextLine());

        result = sAgencyManager.editAirline(airlineDTO);
        if(!result)
            System.out.println("A problem occurred. The system didn't edit the airline.");
        else
            System.out.println("Airline edited with success!");
    }
    
    private static void processRemoveAirline() throws NoPermissionException{
        Scanner sc = new Scanner(System.in);
        TAirlineDTO airlineDTO;
        boolean result;
        int id;
        int op;
        
        
        System.out.println("Airline Id:");
        id = Integer.parseInt(sc.nextLine());
        airlineDTO = sAgencyManager.findAirline(id);

        if(airlineDTO == null)
        {
            System.out.println("Not found a airline with that id. Try again.");
            return;
        }


        System.out.println("Do you want remove the airline: " + airlineDTO + "? [1/0]");
        op = Integer.parseInt(sc.nextLine());

        if(op == 0)
            return;

        result = sAgencyManager.removeAirline(airlineDTO);
        if(!result)
            System.out.println("A problem occurred. The system didn't remove the airline.");
        else
            System.out.println("Airline removed with success!");
    }
    
    //----------------------------------------------------
    //places
    private static void processPlacesFindAll() throws NoPermissionException{
        
        System.out.println("All places in the system:");
        
        for(TPlaceDTO placeDTO : sAgencyManager.findAllPlaces())
            System.out.println(placeDTO);
    }
    
    private static void processAddPlace() throws NoPermissionException{
        Scanner sc = new Scanner(System.in);
        TPlaceDTO placeDTO = new TPlaceDTO();
        boolean result;
        
        System.out.println("Country:");
        placeDTO.setCountry(sc.nextLine());
        System.out.println("City:");
        placeDTO.setCity(sc.nextLine());
        System.out.println("Address:");
        placeDTO.setAddress(sc.nextLine());
        
        result = sAgencyManager.addPlace(placeDTO);
        if(!result)
            System.out.println("A problem occurred. The system didn't add the place.");
        else
            System.out.println("Place added with sucess!");
    }
    
    private static void processEditPlace() throws NoPermissionException{
        
    }
    
    private static void processRemovePlace() throws NoPermissionException{
        
    }
    
    
    //places feedback
    private static void processAddPlaceFeedback() throws NoPermissionException {
        Scanner sc = new Scanner(System.in);
        TPlaceFeedbackDTO placeFeedbackDTO = new TPlaceFeedbackDTO();
        int placeid;
        TPlaceDTO tPlaceDTO = null;
        boolean result;
        
        
        
        System.out.println("Place Id:");
        placeid = Integer.parseInt(sc.nextLine());
        tPlaceDTO = sAgencyManager.findPlace(placeid);

        if(tPlaceDTO == null)
        {
            System.out.println("Not found a place with that id. Try again.");
            return;
        }

        System.out.println("Place: " + tPlaceDTO);
        
        System.out.println("Score: ");
        placeFeedbackDTO.setScore(Integer.parseInt(sc.nextLine()));
        
        
        result = sAgencyManager.addFeedbackToPlace(tPlaceDTO, placeFeedbackDTO);
        if(!result)
            System.out.println("A problem occurred. The system didn't added the feedback.");
        else
            System.out.println("Feedback added with success!");
        
    }

    private static void processMyPlacesFeedbackFindAll() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    //----------------------------------------------------
    //Auxiliar methods
    private static void printCommandList(){
        System.out.println("\n-- Help --");
        
        //users
        System.out.println(Command.SIGNIN + " - Sign in");
        System.out.println(Command.SIGNUP + " - Sign up");
        System.out.println(Command.LOGOUT + " - Logout");
        System.out.println("asguest - Enter as guest");

        //planes        
        System.out.println(Command.FINDALLPLANES + " - List All Planes");
        System.out.println(Command.ADDPLANE + " - Add a new plane");
        System.out.println(Command.EDITPLANE + " - Edit a plane");
        System.out.println(Command.REMOVEPLANE + " - Remove a plane");
        
        //airlines        
        System.out.println(Command.FINDALLAIRLINES + " - List All airlines");
        System.out.println(Command.ADDAIRLINE + " - Add a new airline");
        System.out.println(Command.EDITAIRLINE + " - Edit an airline");
        System.out.println(Command.REMOVEAIRLINE + " - Remove an airline");
        
        //places        
        System.out.println(Command.FINDALLPLACES + " - List All places");
        System.out.println(Command.ADDPLACE + " - Add a new place");
        System.out.println(Command.EDITPLACE + " - Edit a place");
        System.out.println(Command.REMOVEPLACE + " - Remove a place");
        
        
        //places feedback
        System.out.println(Command.FINDALLMYPLACESFEEDBACKS + " - List All my feedbacks of places");
        System.out.println(Command.ADDPLACEFEEDBACK + " - Add a new feedback of a place");
        System.out.println(Command.EDITPLACEFEEDBACK + " - Edit a feedback of a place");
        System.out.println(Command.REMOVEPLACEFEEDBACK + " - Remove feedback of a place");
        
        System.out.println(Command.EXIT + " - Exit");
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