package labact3;

import java.util.Scanner;

/**
 *  Class LabAct3.
 *  A first class with data input from keyboard, 
 *  and use of operations with int, long, Math, and String.
 *  It has three compilation errors.
 *  @author (IIP. Grado en Informatica. ETSINF, UPV) 
 *  @version (Year 2017-18)
 */

public class LabActivity3 {

    public static void main(String[] args) {
        Scanner kbd = new Scanner(System.in);
        System.out.println("Reading hour from keyboard.");
        System.out.print("   -> Input the hour (between 0 and 23): ");
        int h = kbd.nextInt();
        System.out.print("   -> Input the minutes (between 0 and 59): ");
        int m = kbd.nextInt();
        String mm = "0" + m;
        String hh = "0" + h;
        if (mm.length() == 3) {
            mm = mm.substring(1);
        }
        if (hh.length() == 3) {
            hh = hh.substring(1);
        }
        System.out.println("Inputted hour: " + hh + ":" + mm); 
        long tMinTotal = System.currentTimeMillis() / (60 * 1000);
        int tMinCurrent = (int) (tMinTotal % (24 * 60));
        int currentHour = tMinCurrent / 60;
        int currentMin = tMinCurrent % 60;
        String cMin = "0" + currentMin;
        String cHour = "0" + currentHour;
        if (cMin.length() == 3) {
            cMin = cMin.substring(1);
        }
        if (cHour.length() == 3) {
            cHour = cHour.substring(1);
        }
        System.out.println("Current hour: " + cHour + ":" + cMin + "(UTC time)");
        int hMin = (h * 60) + m;
        int max = Math.max(hMin, tMinCurrent);
        int min = Math.min(hMin, tMinCurrent);
        System.out.println("Difference in minutes between the two hours: " + (max - min) + " (" + ((max - min) / 60) + "h " + ((max - min) % 60) + "min)");
        boolean previous = hMin > tMinCurrent;
        boolean palindrome = hh.charAt(0) == mm.charAt(1) && hh.charAt(1) == mm.charAt(0);
        System.out.println("Is previous hour " + hh + ":" + mm + " to hour " + cHour + ":" + cMin + "? " + previous);
        System.out.println("Is palindrome the hour " + hh + ":" + mm + "? " + palindrome);
    }    
 
}
