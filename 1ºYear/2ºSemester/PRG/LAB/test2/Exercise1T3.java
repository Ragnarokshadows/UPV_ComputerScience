package test2;

import java.util.InputMismatchException;
import java.util.Scanner;
/**
 * Second Lab Test - Exercise 1 - Turn 3.
 * 
 * @author PRG 
 * @version Academic year 2017/18
 */
public class Exercise1T3 {
    
    /** There are not objects of this class. */
    private Exercise1T3() { }
    
    /** Returns how many integers have been read from the sc file.
     *  Considering that the file contains a data in each line and 
     *  that this data can have format errors that the nextInt()
     *  method detects and warns throwing the InputMismatchException
     *  exception, you have to:
     *  Modify the following code so that, instead of returning
     *  the number of valid integers, return the total number of 
     *  data with formatting errors that exist in the file.
     */  
    public static int calculate(Scanner sc) {
        int cont = 0;        
        while (sc.hasNext()) {
            try{
                int val = sc.nextInt();
            } catch(InputMismatchException e){
                cont++;
            } finally {
                sc.nextLine();
            }
        }
        return cont;
    }
}