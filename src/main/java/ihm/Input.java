/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ihm;

import java.util.Scanner;

/**
 *
 * @author adech
 */
public class Input {
    public String message;
    public Output output = new Output();

    public int InputInt(String message) {
        Scanner scanner_input = new Scanner(System.in);
        this.output.Output(message);
        Integer user_choice;
        user_choice = Integer.parseInt(scanner_input.nextLine());
        
        return user_choice;
    }
    
    public String InputString(String message) {
        Scanner scanner_input = new Scanner(System.in);
        this.output.Output(message);
        String user_choice;
        user_choice = scanner_input.nextLine();
        
        return user_choice;
    }
}
