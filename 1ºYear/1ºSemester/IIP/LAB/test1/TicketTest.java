package test1;

import java.util.Scanner;
import labact4.TimeInstant;
/**
 *  Class TicketTest: program class that uses TicketTeacher class.
 * 
 *  @author IIP 
 *  @version Year 2017-18
 */
public class TicketTest {
    public static void main(String[] args) {
        // 1. Create a ticket (of TicketTeacher class) 
        //    for movie "It", theater "Babel", session at 18:30.
        
        /* TO COMPLETE */
        TicketTeacher t = new TicketTeacher("It","Babel", 18, 30);
        // Read hours and minutes
        Scanner kbd = new Scanner(System.in);
        System.out.print("   -> Input hour (between 0 and 23): ");
        int h = kbd.nextInt();
        System.out.print("   -> Input minutes (between 0 and 59): ");
        int m = kbd.nextInt();
        // 2. After reading data for hours and minutes from the keyboard:
        // (a) Checks that they are correct (0<=h<24 and 0<=m<60) 
        // (b) If data is correct, create a TimeInstant object using that 
        //     data which represents the arrival time, call to method
        //     proportionalPrice(TimeInstant) with that time as parameter 
        //     and show on the screen the calculated price preceded by the 
        //     message "Price is: ".
        // (c) If data is not correct, show on the screen the message
    	//     "Incorrect data"
        
    	/* TO COMPLETE */  
    	if (0 <= h && h < 24 && 0 <= m && m<60){
    	    TimeInstant arrivalTime = new TimeInstant(h, m);
    	    double price = t.proportionalPrice(arrivalTime);
    	    System.out.println("Price is: "+price);
    	}
    	else{
    	    System.out.println("Incorrect data");
    	}
    }
}
