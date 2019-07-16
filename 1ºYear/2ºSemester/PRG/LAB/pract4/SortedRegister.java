package pract4;

import java.io.PrintWriter; 
import java.util.InputMismatchException;
import java.util.Scanner;
/**
 * Class SortedRegister
 * 
 * @author (PRG. ETSINF - UPV) 
 * @version (2017/18)
 */
public class SortedRegister {

    private static int[] DAYS = {0, 31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
    private int year;
    private int[][] m;

    /** Creates a SortedRegister object for dataYear.
     *  @param int dataYear
     */
    public SortedRegister(int dataYear) {
        this.year = dataYear;
        boolean leap = isLeap(this.year);
        m = new int[13][]; 
        for (int month = 1; month <= 12; month++) {
            int numD = this.DAYS[month];
            if (month == 2 && leap) { numD++; }
            m[month] = new int[numD + 1];
        }    
    }    
    
    /** Sort data read from Scanner s.
     *  Precondition: each line from s must contain the format:
     *        day month count
     *  where day and month must be integers corresponding to a correct date
     *  and count must be > 0.
     *  The count must be aggregated in the register of the day and month.
     *  If any error or wrong format, the method must throw an exception and end.
     *
     *  @param s Scanner with the data
     *  @throws InputMismatchException if a non-integer value is read from s.
     *  @throws ArrayIndexOutOfBoundsException if an incorrect date is read from s.
     *  @throws IllegalArgumentException if a negative count is read from s.
     */
    public void add(Scanner s) {
        /**try{
            while (s.hasNext()) {
                int day = s.nextInt();
                int month = s.nextInt();
                int amount = s.nextInt();
                if (day < 0 || month < 0 || amount < 0) {throw new IllegalArgumentException("Negative value");}
                this.m[month][day] += amount;
            }
        } catch (IllegalArgumentException IAE){
            System.out.println(IAE.getMessage());
        }
        */
       
        while (s.hasNext()) {
            int day = s.nextInt();
            int month = s.nextInt();
            int amount = s.nextInt();
            if (day <= 0 || month <= 0 || amount <= 0) {throw new IllegalArgumentException("Negative value");}
            this.m[month][day] += amount;
        }
    } 
    
    /** Registered data with counts > 0 are written in p, line-by-line
     *  with the format
     *        day  month  count
     *  sorted in chronological order.
     *  @param p Printwriter stream where sorted data is written.
     */
    public void save(PrintWriter p) {
        for (int month = 1; month <= 12; month++) {
            for (int day = 1; day < this.m[month].length; day++) {
                int totalAmount = this.m[month][day];
                if (totalAmount > 0) {
                    p.printf("%5d %5d %5d \n", day, month, totalAmount);
                }
            }
        }
    }

    /** Sort data read from Scanner s.
     *  Wrong data is filtered, an error report is printed.
     *  Precondition:
     *      The accepted and non-filtered data follows this format:
     *        day month count
     *  where day and month must be integers 
     *  where day and month must be integers corresponding to a correct date
     *  and count must be > 0.
     *  The count must be aggregated in the register of the day and month.
     *  Wrong lines are reported via err indicating the line number.
     *
     *  @param s Scanner data source
     *  @param err PrintWriter stream where report errors
     */
    public void add(Scanner s, PrintWriter err) {
        // TO BE COMPLETED
        
        int n = 0;
        
        while (s.hasNext()) {
            try{
                n++;
                int day = s.nextInt();
                int month = s.nextInt();
                int amount = s.nextInt();
                if (day <= 0 || month <= 0 || amount <= 0) {throw new IllegalArgumentException("Line " + n + ": negative count");}
                this.m[month][day] += amount;
            } catch (IllegalArgumentException IAE){
                err.println(IAE.getMessage());
            } catch (InputMismatchException IME){
                err.println("Line " + n + ": incorrect format");
            } catch (ArrayIndexOutOfBoundsException AIOOBE){
                err.println("Line " + n + ": wrong date");
            } finally {
                s.nextLine();
            }
        }
    }
    
    /** Chech if a leap year
     *  @param year int
     *  @return true iff is a leap year.
     */
    private static boolean isLeap(int year) {
        return (year % 4 == 0 && year % 100 != 0) || year % 400 == 0;
    }
}