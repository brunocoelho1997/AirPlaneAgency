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
                case Command.GET_BY_PLACE:
                    processGetByPlace(input);
                    break;
                    
                case Command.EXIT:
                    return;
            }
        }
    }           

    private static void processGetByPlace(String input) {
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
}