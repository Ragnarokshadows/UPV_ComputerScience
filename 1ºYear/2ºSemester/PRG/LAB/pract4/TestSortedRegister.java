package pract4;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.time.Year;
import java.util.InputMismatchException;
import java.lang.ArrayIndexOutOfBoundsException;
import java.util.Scanner;
/**
 * Class TestSortedRegister. Test for class SortedRegister.
 * 
 * @author (PRG) 
 * @version (2017/18)
 */
public class TestSortedRegister {

    public static void main(String[] args) throws FileNotFoundException {
        Scanner keyB = new Scanner(System.in);
        String msg = "Year for recover data (maximum 10 years ago): ";
        int currentY = Year.now().getValue();
        int year = CorrectReading.nextInt(keyB, msg, 
                                          currentY - 10, currentY);   
        Scanner in = null;
        PrintWriter out = null, err = null;
        System.out.print("Name of the file to be sorted: "); 
        String nameIn = keyB.next();
        File f = new File(nameIn);
        in = new Scanner(f);
        f = new File("result.out");
        File log = new File("result.log");
        out = new PrintWriter(f);
        err = new PrintWriter(log);
        
        System.out.println("Sorting options:" + "\n" + "1.- Reject the file if contains errors." + "\n" +
        "2.- Filter the wrong lines.");
        int res = keyB.nextInt();
        
        if (res == 1){
            testUnreportedSort(year, in, out);   
        }
        else{
            testReportedSort(year, in, out, err);   
        }
        
        in.close();
        out.close();
        err.close();
    }
  
    /** Method for checking add(Scanner) and save(PrintWriter) from
     *  SortedRegister. */
    public static void testUnreportedSort(int year, Scanner in, PrintWriter out) {
        try{
            SortedRegister c = new SortedRegister(year);
            c.add(in); 
            c.save(out);
        } catch (IllegalArgumentException IAE) {
            System.out.println("Wrong file: negative value for the count.");
        } catch (InputMismatchException IME) {
            System.out.println("Wrong file: incorrect format for an int.");
        } catch (ArrayIndexOutOfBoundsException AIOBE){
            System.out.println("Wrong file: non-valid date.");
        }
    }
    
    public static void testReportedSort(int year, Scanner in, PrintWriter out, PrintWriter err){
        SortedRegister c = new SortedRegister(year);
        c.add(in, err);
        c.save(out);
    }
}