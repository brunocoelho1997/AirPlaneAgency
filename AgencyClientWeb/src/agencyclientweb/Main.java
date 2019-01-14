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
    
    private static int INVALID_VALUE = -1;
    private static ClientRS sClient;
    
    public static void main(String[] args) {
        sClient = new ClientRS();
        
        doListenCommands();
    }
    
    private static void doListenCommands() {
        Scanner sc = new Scanner(System.in);

        while (true) {
            System.out.println("Insert command:");
            System.out.print("> ");
            
            String input = sc.nextLine();
            String command = input.split(" ")[0];

            switch (command) {
                case Command.GET_BY_ORIGIN:
                    processGetByOrigin(input);
                    break;
                    
                case Command.GET_BY_DESTINY:
                    processGetByDestiny(input);
                    break;
                
                case Command.GET_CUSTOM:
                    processGetCustom();
                    break;
                    
                case Command.EXIT:
                    return;
            }
        }
    }           

    private static void processGetByOrigin(String input) {
        String[] inputs = input.split(" ");
        
        List<Flight> flights;
        if (inputs.length == 1) {
            flights = sClient.getAllFlights();
            
        } else {
            flights = sClient.getFlights(inputs[1]);
        }
        
        System.out.println("\n" + flights.size() + " flighs found:");
        for (Flight flight : flights) {
            System.out.println(flight);
        }
        
        System.out.println("");
    }
    
    private static void processGetByDestiny(String input) {
        String[] inputs = input.split(" ");
        
        List<Flight> flights;
        if (inputs.length == 1) {
            flights = sClient.getAllFlights();
            
        } else {
            flights = sClient.getFlights(inputs[1]);
        }
        
        System.out.println("\n" + flights.size() + " flighs found!");
        for (Flight flight : flights) {
            System.out.println(flight);
        }
        
        System.out.println("");
    }

    private static void processGetCustom() {
        Scanner sc = new Scanner(System.in);
        
        System.out.println("Origin (empty to discard):");
        System.out.print("> ");
        String origin = processInput(sc.nextLine());
        
        System.out.println("Destiny (empty to discard):");
        System.out.print("> ");
        String destiny = processInput(sc.nextLine());
        
        System.out.println("Price (empty to discard):");
        int price = processIntInput(sc.nextLine());
        
        System.out.println("Searching with parameteres:");
        System.out.println("+ origin = " + origin);
        System.out.println("+ destiny = " + destiny);
        System.out.println("+ price = " + price);
        
        System.out.println("\n\n");
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
}