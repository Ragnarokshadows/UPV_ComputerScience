package examExample;

import java.util.Scanner;
/**
 * Class TestTimeInstant. 
 * 
 *  @author (IIP. Grado en Informatica. ETSINF, UPV) 
 *  @version (Year 2017-18)
 */
public class TestTimeInstant {
    public static void main(String[] args) {
        Scanner kbd = new Scanner(System.in);
        System.out.println("Reading time from keyboard.");
        System.out.print("   -> Input hour (between 0 and 23): ");
        int h = kbd.nextInt();
        System.out.print("   -> Input minutes (between 0 and 59): ");
        int m = kbd.nextInt();
        TimeInstant ti = null;
        if (h < 23 && h >=0 && m < 60 && m >=0){
            ti = new TimeInstant(h,m);
        }
        else{
            ti = new TimeInstant();
        }
        /* TO COMPLETE */ 
        ti.decrement1Min();
        System.out.println("After: " + ti);
    }
}
