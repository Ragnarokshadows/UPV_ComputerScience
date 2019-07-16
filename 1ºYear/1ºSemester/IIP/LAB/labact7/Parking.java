package labact7;

import labact4.TimeInstant;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Locale;
import java.util.Scanner;

/** Parking class: represents the occupancy of the floors in a parking 
 *  along with its fee in euros per minute.
 *  @author IIP
 *  @version Year 2017/2018
 */
public class Parking {
    /** Number of spaces by floor . */
    public static final int FLOOR_SPACES = 5;
    // Definition of instance attributes
    /* TO COMPLETE */
    private Floor[]floors;
    private double fee;

    /** Creates a parking given floors number and
     *  fee in euros per minute.
     *  Initially, parking is empty.
     *  @param numFl int, number of floors, numFl > 0.      
     *  @param f double, fee in euros per minuto, f > 0.
     */
    /* TO COMPLETE */
    public Parking(int numFl, double f){
        int i;
        fee = f;
        floors = new Floor[numFl];
        for (i = 0;i < numFl;i++){
            floors[i] = new Floor(i,FLOOR_SPACES);
        }
    }

    /** Creates a parking from data in a file, whose name is
     *  given as paramter.<br>
     *  File format:
     *  <pre>
     *  floors 
     *  fee
     *  floor plate hours minutes
     *  ...
     *  floor plate hours minutes
     *  </pre>
     *  Data is correct (not repeated vehicles or spaces,
     *  floor and times correct). 
     *  @param fileName String, name of the file with the data.
     */
    public Parking(String fileName) {
        final String SEP = java.io.File.separator;
        final String DIR = this.getClass().getPackage().getName();   // "labact7"
        Scanner in = null;
        try {
            in = new Scanner(new File(DIR + SEP + fileName)).useLocale(Locale.US);
            int numFl = in.nextInt(); 
            double f = in.nextDouble();

            floors = new Floor[numFl];
            for (int i = 0; i < floors.length; i++) {
                floors[i] = new Floor(i, FLOOR_SPACES);
            }            
            fee = f;
            
            while (in.hasNext()) {
                int fl = in.nextInt(); 
                String pl = in.next(); 
                int h = in.nextInt(); 
                int m = in.nextInt();
                Ticket t = new Ticket(pl, new TimeInstant(h, m));
                t.setFloor(fl);
                floors[fl].park(t);
            }
        } catch (FileNotFoundException e) {
            System.out.println("\n***ERROR***: " 
                + "Could not access to file " + fileName);
        } finally {
            if (in != null) { in.close(); }
        }          
    }

    /** Returns number of floors.
     *  @return int, number of floors of the parking.
     */
    /* TO COMPLETE */
    public int getNumFloor(){
        return floors.length;
    }

    /** Returns fee.
     *  @return double, fee of the parking in euros per minute.
     */
    /* TO COMPLETE */
    public double getFee(){
        return fee;
    }

    /** Updates fee.
     *  @param f double, new fee 
     *  (in euros per minute), f > 0.
     */
    /* TO COMPLETE */
    public void setFee(double f){
        if (f > 0){fee = f;}
    }

    /** Checks if the parking is full.
     *  @return boolean, true if full, false otherwise.
     */
    // Uses isFull() from Floor
    /* TO COMPLETE */
    public boolean isFull(){
        boolean res = true;
        boolean end = false;
        int i = 0;
        
        while(i < floors.length && end == false){
            if (floors[i].isFull() == false){
                end = true;
                res = false;
            }
            i++;
        }
        
        return res;
    }

    /** Given a Ticket associated to a vehicle, parks the vehicle
     *  in the lowest floor with free spaces, in the space with lowest
     *  number.
     *  Precondition: parking with free spaces and vehicle not present.
     *  @param t Ticket, ticket of the vehicle to park.
     */
    // Uses isFull() and park(Ticket) from Floor and
    // setFloor(int) from Ticket
    /* TO COMPLETE */
    public void park(Ticket t){
        int i = 0;
        boolean end = false;
        
        while(i < floors.length && end == false){
            if (floors[i].isFull() == false){
                end = true;
            }
            else{i++;}
        }
        t.setFloor(i);
        floors[i].park(t);
    }
    
    /** Given a Ticket associated to a vehicle and a preferred floor number,
     *  parks the vehicle in that floor if there are free spaces; otherwise,
     *  in the closest floor with free spaces; otherwise, follows the strategy
     *  described in the statement.
     *  Precondition: parking with free spaces and vehicle not present.
     *  @param t Ticket, ticket of the vehicle to park.       
     *  @param pref int, preferred floor.
     */
    // Uses ifFull() and park(Ticket) from Floor and
    // setFloor(int) from Ticket
    /* TO COMPLETE */
    public void park(Ticket t, int pref){
        int i = 0;
        boolean end = false;
        if (pref < floors.length && pref >= 0){
            if (floors[pref].isFull() == true){
                while(i < floors.length && end == false){
                    i++;
                    if (((pref - i) >= 0) && floors[pref - i].isFull() == false){
                        end = true;
                        i=pref-i;
                    }
                    if (((pref + i) < floors.length) && floors[pref + i].isFull() == false &&  
                    end == false){
                        end = true;
                        i=pref+i;
                    }
                }
            }
            else{i = pref;}
            t.setFloor(i);
            floors[i].park(t);
        }
    }

    /** Checks if the vehicle of given plate number is in the parking.
     *  @param plt String, plate of the vehicle to search for.
     *  @return Ticket, associated to the vehicle with that plate
     *  if found, or null when not found.
     */
    // Uses searchTicket(String) from Floor
    /* TO COMPLETE */
    public Ticket searchTicket(String plt){
        Ticket res = null;
        int i = 0;
        boolean end = false;
        
        while(i < floors.length && end == false){
            if (floors[i].searchTicket(plt) != null){
                end = true;
                res = floors[i].searchTicket(plt);
            }
            i++;
        }
        
        return res;
    }
    
    /** The vehicle associated to the given Ticket leaves the
     *  parkng; given an exit time, it returns the fare to be
     *  paid.
     *  @param t Ticket, associated to the vehicle that is leaving.
     *       Precondition: always present.
     *  @param exitT TimeInstant, exit time for the vehicle.
     *       Precondition: posterior to enter hour.
     *  @return double, fare to be paid in euros.
     */
    // Uses leave(int, TimeInstant) from Floor
    /* TO COMPLETE */
    public double leave(Ticket t, TimeInstant exitT){
        int floor = t.getFloor();
        int space = t.getSpace();
        int min = floors[floor].leave(space, exitT);
        double res;
        
        res = min * fee;
        
        return res;
    }

    /** Empties the parking, taking out all vehicles, supposing
     *  that current time is 23:59, and returning the total fare.
     *  @return double, total in euros to be paid for all
     *  the vehicles that leave the parking.
     */
    // Uses emptyFloor(TimeInstant) from Floor
    /* TO COMPLETE */
    public double emptyParking(){
        double res;
        int sumMin = 0;
        int i;
        TimeInstant ti = new TimeInstant(23,59);
        
        for (i = 0;i < floors.length;i++){
            sumMin = sumMin + floors[i].emptyFloor(ti);
        }
        res = sumMin * fee;
        
        return res;
    }
  
    /** Returns a String that represents the occupancy of the parking,
     *  with 'X' occupied, and ' ' free.
     *  Must place a first line with the corresponding space numbers. <br>
     *  For example: the following String represents a parking with 
     *  3 floors and 5 spaces per floor, where occupied spaces are:
     *  on floor 0, spaces 0, 1, and 3;
     *  on floor 1, spaces 1, 2, and  4;
     *  on floor 2, spaces 0 and 1.
     *  <pre>
     *          "      0   1   2   3   4 
     *             0   X   X       X    
     *             1       X   X       X
     *             2   X   X             " </pre>
     *  @return String, parking representation.
     */
    // Uses toString() from Floor
    /* TO COMPLETE */
    public String toString(){
        int i;
        String res = "    ";
        
        for (i = 0;i < FLOOR_SPACES;i++){
            res = res + "  " + i + " ";
        }
        res = res + "\n";
        for (i = 0;i < floors.length;i++){
            res = res + floors[i];
        }
        
        return res;
    }
}
