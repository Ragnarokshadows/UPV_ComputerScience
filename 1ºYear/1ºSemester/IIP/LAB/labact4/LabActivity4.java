package labact4;
import java.util.Scanner;
public class LabActivity4 {

    public static void main(String[] args) {
        Scanner kbd = new Scanner(System.in);
        System.out.println("Reading hour from keyboard.");
        System.out.print("   -> Input the hour (between 0 and 23): ");
        int h = kbd.nextInt();
        System.out.print("   -> Input the minutes (between 0 and 59): ");
        int m = kbd.nextInt();
        TimeInstant ti1 = new TimeInstant(h,m);
        TimeInstant ti2 = new TimeInstant();
        System.out.println("Inputted hour: " + ti1); 
        System.out.println("Current hour: " + ti2);
        System.out.println("Difference in minutes between the two hours: " + Math.abs(ti1.compareTo(ti2)));
        boolean previous = ti1.toMinutes() > ti2.toMinutes();
        String mm = "0" + m;
        String hh = "0" + h;
        if (mm.length() == 3) {
            mm = mm.substring(1);
        }
        if (hh.length() == 3) {
            hh = hh.substring(1);
        }
        boolean palindrome = hh.charAt(0) == mm.charAt(1) && hh.charAt(1) == mm.charAt(0);
        System.out.println("Is previous hour " + ti1 + " to hour " + ti2 + "? " + previous);
        System.out.println("Is palindrome the hour " + ti1 + "? " + palindrome);
    }    
 
}
