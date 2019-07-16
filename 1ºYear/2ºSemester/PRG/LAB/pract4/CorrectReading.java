package pract4;

import java.util.InputMismatchException;
import java.util.Locale;
import java.util.Scanner;
/**
 * Class CorrectReading.
 * 
 * @author (PRG. ETSINF - UPV)
 * @version  (2017/18)
 */
public class CorrectReading {
    
    /** There are not objects of this class. */
    private CorrectReading() { }
    
    /**
     * Reads from a Scanner object and returns a double value 
     * checking that it is > 0.
     * @param keyboard Scanner object for reading from.
     * @param message String for asking the user for the value.
     * @return double, a non-negative double value.
     */    
    public static double nextDoublePositive(Scanner keyboard, String message) {
        double value = 0.0;
        boolean someError = true;
        
        do{
            try{
                do {
                    System.out.print(message);
                    value = keyboard.nextDouble();    
                } while (value < 0.0);
                someError = false;
            } catch(InputMismatchException IME){
                System.out.println("Please, write a double");
            } finally {
                keyboard.nextLine();
            }
        }while(someError);
        
        return value;
    }

    /**
     * Reads from a Scanner object and returns an integer value. 
     * @param keyboard Scanner object for reading from.
     * @param message String for asking to the user for the value.
     * @return int, the integer value user entered.
     */
    public static int nextInt(Scanner keyboard, String message) {
        int value = 0;
        boolean someError = true; 
        do {
            try {
                System.out.print(message);
                value = keyboard.nextInt();
                someError = false;
            } catch (InputMismatchException e) {
                System.out.println("Please, type a correct integer! ...");
            } finally {
                keyboard.nextLine();
            }
        } while (someError);
        return value;
    }

    /**
     * Reads from a Scanner object and returns an integer value 
     * in the range <code>[ lowerBound .. upperBound ]</code> 
     * where <code>Integer.MIN_VALUE <= lowerBound</code> and 
     * <code>upperBound <= Integer.MAX_VALUE</code>.
     * @param keyboard Scanner object for reading from.
     * @param message String for asking to the user for the value.
     * @param lowerBound int lower bound of the value to be read and accepted.
     * @param upperBound int upper bound of the value to be read and accepted.
     * @return int, the integer number entered by the user.
     */    
    public static int nextInt(Scanner keyboard, String message, 
                              int lowerBound, int upperBound) {
        int value = 0; 
        boolean someError = true;
        
        do{
            try{
                System.out.print(message);
                value = keyboard.nextInt(); 
                if (value < lowerBound || value > upperBound) { throw new IllegalArgumentException("Please write an integer in the range!!");}
                else{someError = false;}
            } catch (IllegalArgumentException IAE){
                System.out.println(IAE.getMessage());
            } catch (InputMismatchException IME) {
                System.out.println("Please, type a correct integer! ...");
            } finally {
                keyboard.nextLine();
            }
        }while(someError);

        return value;
    }  
}