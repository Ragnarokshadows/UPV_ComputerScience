package labact5;

import java.util.Scanner;
import java.util.Locale;

/** Clase TicketSale.
 *  Lab Activity 5 - IIP - ETSINF-UPV.
 *  
 *  @author
 *  @version Year 2016/17
 */
public class TicketSale {

    public static void main(String [] args) {
        Scanner kbd = new Scanner(System.in).useLocale(Locale.US);
        
        // Vars for ticket creation
        Ticket t;
        String title, theater;
        int h, m;
        
        // Vars for final price calculation
        int age;
        boolean hol = false, holeve = false, watche = false, client = false;
        
        // Aux vars
        String ans;
        
        // Ask for ticket features and creates it
        System.out.println("Ticket features: ");
        System.out.print("   Title: ");
        title = kbd.nextLine();
        System.out.print("   Theater: ");
        theater = kbd.nextLine();
        System.out.print("   Hour: ");
        h = kbd.nextInt(); kbd.nextLine();
        System.out.print("   Minutes: ");
        m = kbd.nextInt(); kbd.nextLine();
        
        // Create ticket
        t = new Ticket(title, theater, h, m);
        
        // Ask for client and day features
        System.out.print("Your age? ");
        age = kbd.nextInt(); kbd.nextLine();
        
        if (age < 65) {
            System.out.print("It is watcher's day? "); 
            ans = kbd.next(); ans = ans.toUpperCase();
            if (ans.equals("YES")) { watche = true; }
            else {
                System.out.print("It is holyday? "); 
                ans = kbd.next(); ans = ans.toUpperCase();
                if (ans.equals("YES")) { 
                    hol = true;
                    System.out.print("Are you client? "); 
                    ans = kbd.next(); ans = ans.toUpperCase();
                    if (ans.equals("YES")) { client = true; }
                }
                else {
                    System.out.print("It is holydayeve? "); 
                    ans = kbd.next(); ans = ans.toUpperCase();
                    if (ans.equals("YES")) { 
                        holeve = true;
                        System.out.print("Are you client? "); 
                        ans = kbd.next(); ans = ans.toUpperCase();
                        if (ans.equals("YES")) { client = true; }
                    }
                }
            }
        }
        
        // Calculate final price
        double fPrice = t.finalPrice(age, watche, hol, holeve, client);
        
        System.out.println("The final price for the ticket is: " + fPrice + " euros");
    }

}
