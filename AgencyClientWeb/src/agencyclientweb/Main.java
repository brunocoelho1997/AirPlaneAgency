/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package agencyclientweb;

import java.util.Scanner;
import logic.websearch.Flight;

public class Main {
    
    public static void main(String[] args) {
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
        if (inputs.length == 1) {
            System.out.println("Missing second argument [place]");
            return;
        }

        ClientRS client = new ClientRS();
        Flight flight = client.getFlight(Flight.class, inputs[1]);
        System.out.println("flight=" + flight);
    }
    
}