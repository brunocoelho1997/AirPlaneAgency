/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package agencyclientweb;

import java.util.List;
import java.util.Scanner;
import logic.websearch.Flight;

public class Main {
    
    private static final int INVALID_VALUE = -1;
    private static ClientRS sClient;
    
    public static void main(String[] args) {
        sClient = new ClientRS();
        
        doListenCommands();
        
        sClient.close();
    }
    
    private static void doListenCommands() {
        Scanner sc = new Scanner(System.in);

        while (true) {
            System.out.println("Insert command:");
            System.out.print("> ");
            
            String input = sc.nextLine();
            String command = input.split(" ")[0];

            switch (command) {
                case Command.GET_ALL:
                    processGetAll();
                    break;
                    
                case Command.GET_BY_ORIGIN:
                    processGetByOrigin(input);
                    break;
                    
                case Command.GET_BY_DESTINY:
                    processGetByDestiny(input);
                    break;
                
                case Command.GET_CUSTOM:
                    processGetCustom();
                    break;
                    
                case Command.HELP:
                    printCommands();
                    break;
                    
                case Command.EXIT:
                    return;
                    
                default:
                    System.out.println("Command not found. Type help to get a command list.");
                    break;
            }
        }
    }
    
    private static void processGetAll() {
        List<Flight> flights = sClient.getAllFlights();
        System.out.println("\n" + flights.size() + " flighs found:");
        for (Flight flight : flights) {
            System.out.println(flight);
        }
        
        System.out.println("");
    }

    private static void processGetByOrigin(String input) {
        String[] inputs = input.split(" ");
        
        List<Flight> flights;
        if (inputs.length == 1) {
            flights = sClient.getAllFlights();
            
        } else {
            flights = sClient.getFlights(inputs[1], null);
        }
        
        printFlightsInfo(flights);
    }
    
    private static void processGetByDestiny(String input) {
        String[] inputs = input.split(" ");
        
        List<Flight> flights;
        if (inputs.length == 1) {
            flights = sClient.getAllFlights();
            
        } else {
            flights = sClient.getFlights(null, inputs[1]);
        }
        
        printFlightsInfo(flights);
    }

    private static void processGetCustom() {
        Scanner sc = new Scanner(System.in);
        
        System.out.println("Origin city (empty to discard):");
        System.out.print("> ");
        String origin = processInput(sc.nextLine());
        
        System.out.println("Destiny city (empty to discard):");
        System.out.print("> ");
        String destiny = processInput(sc.nextLine());
        
        //System.out.println("Price (empty to discard):");
        //int price = processIntInput(sc.nextLine());
        
        System.out.println("Searching with parameteres:");
        
        List<Flight> flights = sClient.getFlights(origin, destiny);
        printFlightsInfo(flights);
    }
    
    private static String processInput(String input) {
        if (input == null || input.trim().isEmpty()) {
            return null;
        }
        
        return input;
    }
    
    private static Integer processIntInput(String input) {
        if (input == null || input.trim().isEmpty()) {
            return INVALID_VALUE;
        }
        
        try {
            int value = Integer.parseInt(input);
            return value;
            
        } catch (NumberFormatException ex) {
            // Do nothing
        }
        
        return INVALID_VALUE;
    }
    
    private static void printCommands() {
        System.out.println("\n-- Help --");
        
        //users
        System.out.println(Command.GET_ALL + " - Get all active trips");
        System.out.println(Command.GET_BY_ORIGIN + " - Get active trips matching origin city");
        System.out.println(Command.GET_BY_DESTINY + " - Get active trips matching destiny city");
        System.out.println(Command.GET_CUSTOM + " - Get active trips mathing custom search");
        System.out.println(Command.EXIT + " - Exit");
        
        System.out.println("----------------");
    }
    
    private static void printFlightsInfo(List<Flight> flights) {
        System.out.println("\n" + flights.size() + " flighs found!");
        for (Flight flight : flights) {
            System.out.println(flight);
        }
        
        System.out.println("");
    }
}