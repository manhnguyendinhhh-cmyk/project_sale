
package salesmanagement.util;

import java.util.Scanner;
public class DataInput {
    public static int getIntegerNumber(String displayMessage) {
        Scanner sc = new Scanner(System.in);
        while (true) {
            System.out.print(displayMessage);
            String strInput = sc.nextLine();
            
            if (strInput.matches("\\d{1,10}")) {
                return Integer.parseInt(strInput); 
            } else {
                System.out.println("Error: Invalid number! Please enter integers (0-9) only.");
            }
        }
    }

    
    public static String getString(String displayMessage) {
        String strInput;
        Scanner sc = new Scanner(System.in);
        System.out.print(displayMessage);
        strInput = sc.nextLine();
        return strInput;
    }

    
    public static double getDoubleNumber(String displayMessage) {
        double number = 0;
        String strInput;
        Scanner sc = new Scanner(System.in);
        
        System.out.print(displayMessage);
        strInput = sc.nextLine();
        
        if (strInput.matches("[0-9]{1,10}(\\.[0-9]+)?") == true) {
            number = Double.parseDouble(strInput);
        } else {
            System.out.println("Invalid double number!");
        }
        return number;
    }
}
