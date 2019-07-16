package labact7;

import labact4.TimeInstant;
import java.util.Scanner;
import java.util.Locale;

/** 
 *  ParkingManager class: parking manager.
 *  @author IIP
 *  @version Year 2017/2018
 */
public class ParkingManager {
    
    /**
     * Shows a menu with options on the screen and
     * reads from keyboard a valid option. 
     * @param kbd Scanner, represents the keyboard.
     * @return int, valid option.
     */ 
    private static int menu(Scanner kbd) {
        int op;
        do {
            System.out.println("\nParking manager");
            System.out.println("==================");
            System.out.println("1. Park");
            System.out.println("2. Leave");
            System.out.println("3. Search");
            System.out.println("4. Show occupance");
            System.out.println("5. Empty parking");
            System.out.println("0. Exit");
            System.out.println();
            System.out.print("Choose an option: ");
            op = kbd.nextInt();
        } while (op < 0 || op > 5);
        kbd.nextLine();
        return op;
    }
  
    /**
     * Read from keyboard a valid time.
     * @param kbd Scanner, represents the keyboard.
     * @return TimeInstant, valid time.
     */
    private static TimeInstant readTimeInstant(Scanner kbd) {
        int h, m;
        do {
            System.out.println("Give me a valid time: "); 
            System.out.print("  Hours: "); h = kbd.nextInt();
            System.out.print("  Minutes: "); m = kbd.nextInt();
        } while (h < 0 || h > 23 || m < 0 || m > 59);
        kbd.nextLine();
        return new TimeInstant(h, m);
    }
    
    /**
     * Read from keyboard a plate number.
     * @param kbd Scanner, represents the keyboard.
     * @return String, plate.
     */
    private static String readPlate(Scanner kbd) {
        System.out.print("Give me a plate number: "); 
        String plt = kbd.nextLine();        
        return plt;
    }

    /**
     * Main method.
     * @param args String[].     
     */
    public static void main(String [] args) {
        final String MSG_IS_NOT = "Vehicle not present in the parking";
        final String MSG_IS = "Vehicle already present in the parking";
        final String MSG_FULL = "No free spaces in the parking";
        
        Scanner kbd = new Scanner(System.in).useLocale(Locale.US);
        // Parking p = new Parking(4, 0.015);
        Parking p = new Parking("parkingIIP.txt");
        int op, pref;
        String plate;
        TimeInstant h;
        Ticket t;

        do {
            op = menu(kbd);
            switch(op) {
                case 1: // Park
                    if (!p.isFull()) {                       
                        plate = readPlate(kbd);
                        t = p.searchTicket(plate);
                        if (t == null) {
                            h = readTimeInstant(kbd);
                            do {
                                System.out.print("Give me a preferred ");
                                System.out.print("floor (0-");
                                System.out.print((p.getNumFloor() - 1));
                                System.out.print("): ");
                                pref = kbd.nextInt();
                            } while (pref < 0 || pref >= p.getNumFloor());
                            t = new Ticket(plate, h);
                            p.park(t, pref);
                            System.out.println(t);
                        }
                        else { System.out.print(MSG_IS); }
                    }
                    else { System.out.print(MSG_FULL); }
                    break;
                    
                case 2: // Leave
                    plate = readPlate(kbd);
                    t = p.searchTicket(plate);
                    if (t == null) { System.out.println(MSG_IS_NOT); }
                    else {                        
                        do {
                            System.out.print("Vehicle entered at ");
                            System.out.print(t.getEnterHour());
                            System.out.print(" and must leave "); 
                            System.out.println("after that hour.");
                            h = readTimeInstant(kbd);
                        } while (t.getEnterHour().toMinutes() >= h.toMinutes());
                        System.out.println(t);                        
                        System.out.printf(Locale.US, 
                            "Fare: %.2f euros\n", p.leave(t, h));
                    }
                    break;
                    
                case 3: // Search                    
                    plate = readPlate(kbd);
                    t = p.searchTicket(plate);
                    if (t == null) { System.out.println(MSG_IS_NOT); }
                    else { System.out.println(t); }
                    break;
                    
                case 4: // Show occupancy
                    System.out.println(p);
                    break;
                    
                case 5: // Empty parking
                    System.out.print("Empty parking. Remaining total fare : ");
                    System.out.printf(Locale.US, "%.2f\n", p.emptyParking());
                    break;
                    
                case 0: // Finish
                    System.out.println("Bye!"); 
                    break;
                    
                default: // Checkstyle
                    break;
            }
        } while (op != 0);
    }
}
