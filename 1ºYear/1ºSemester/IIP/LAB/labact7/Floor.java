package labact7;

import labact4.TimeInstant;

/** Floor Class: represents the occupacion of a floor in the parking
 *  by using the tickets associated to the parked vehicles in that floor
 *  @author IIP
 *  @version Year 2017/2018
 */
public class Floor {
    // Attributes definition
    // numFloor: integer that keeps the floor number
    // spaces: array of Ticket that represents floor occupancy
    // freeSpaces: integer that indicates how many free spaces are present
    /* TO COMPLETE */
    private int numFloor;
    private Ticket[]spaces;
    private int freeSpaces;

    /** Creates a Floor given number of floor and number of spaces.
     *  Floor initially is empty (without vehicles).
     *  @param numF int, number of floor, numF >= 0.
     *  @param numSpaces int, number of spaces, numSpaces > 0.
     */
    /* TO COMPLETE */
    public Floor(int nnumFloor,int numSpaces){
        numFloor = nnumFloor;
        spaces = new Ticket[numSpaces];
        freeSpaces = numSpaces;
    }
    /** Returns floor number
     *  @return int, number of the floor in the parking.
     */
    /* TO COMPLETE */
    public int getFloor(){
        return numFloor;
    }

    /** Returns number of free spaces.
     *  @return int, number of free spaces in the floor.
     */  
    /* TO COMPLETE */
    public int getFreeSpaces(){
        return freeSpaces;
    }

    /** Returns true if floor is full, or false otherwise.
     *  @return boolean, true for full floor (without free spaces),
     *  false otherwise.
     */
    /* TO COMPLETE */
    public boolean isFull(){
        boolean res = false;
        if(freeSpaces == 0){
            res = true;
        }
        return res;
    }

    /** Returns first free space (lowest nomber) in the floor, or -1 when
      * no free spaces.
      * @return int, number of first free space (lowest number) in the floor
      * or -1 when no free spaces.
      */
    // Uses isFull()
    /* TO COMPLETE */
    public int firstFree(){
        int res = -1;
        int i = 0;
        boolean end = false;
        if (isFull() == false){
            while(i < spaces.length && end == false){
                if (spaces[i] == null){
                    res = i;
                    end = true;
                }
                i++;
            }
        }
        return res;
    }
    
    /** When free spaces, associates the ticket to the first free floor
     *  (that of lowest number).
     *  @param t Ticket, ticket of the vehicle to be parked.
     *    Precondition: Ticket not associated to any space.
     */
    // Uses firstFree()
    /* TO COMPLETE */
    public void park(Ticket t){
        int r = firstFree();
        if (r != -1){
            t.setSpace(r);
            freeSpaces = freeSpaces -1;
            spaces[r]= t;
        }
    }
       
    /** Checks if a vehicle, given its plate, is on the floor.
     *  @param plt String, plate number of the searched vehicle.
     *  @return Ticket, the ticket associated to the vehicle if found,
     *  null otherwise.
     */
    /* TO COMPLETE */
    public Ticket searchTicket(String t){
        int i = 0;
        boolean end = false;
        Ticket ti = null;
        while (i < spaces.length && end == false){
            if(spaces[i]!=null){
                if (spaces[i].getPlate().equals(t)){
                    end = true;
                    ti = spaces[i];
                }
            }
            i++;
        }
        return ti;
    }

    /** Returns number of minutes that passed from the time that the vehicle
     *  entered in the space until a given exit time, updating the floor.
     *  @param spc int, space number. 
     *    Precondition: 0 <= spc < spaces.length and spaces[spc] != null.
     *  @param exitT TimeInstant, exit time. 
     *    Precondition: posterior to enter time of the vehicle.
     *  @return int, number of minutes the vehicle was in the parking.
     */
    /* TO COMPLETE */
    public int leave(int spc, TimeInstant exitT){
        int res = 0;
        
        int sMin = spaces[spc].getEnterHour().toMinutes();
        int eMin = exitT.toMinutes();
        
        res = eMin - sMin;
        spaces[spc] = null;
        freeSpaces++;
        
        return res;
    }

    /** Takes out all the vehicles in the floor and returns the total number
     *  of minutes that the vehicles stayed in the floor until a given exit time.
     *  @param exitT TimeInstant, exit time.
     *    Precondition: posterior to enter hour of all the vehicles in the floor.
     *  @return int, total number of minutes that passed.
     */
    // Uses leave(int, TimeInstant)
    /* TO COMPLETE */
    public int emptyFloor(TimeInstant exitT){
        int res = 0;
        int i;
        
        for(i = 0;i < spaces.length;i++){
            if (spaces[i] != null){
                res = res + leave(i, exitT);
            }
        }
        
        return res;
    }
  
    /** Returns a String with the occupancy of the floor, where 'X' is occupied, 
      * and ' ' is free. <br>
      * Format: <pre> FLOOR (in 3 positions), blank space, occupancy 
      * ("  X" or "   "), blank space, ..., occupancy ("  X" or "   "), 
      * blank space, '\n'</pre>
      * Format example: (Floor 2 with 5 spaces, occupied 0, 2, 3 and 4): 
      * <pre> "--2---X-------X---X---X-" </pre>
      * - was used for representing blank space.
      * @return String, representation of floor occupancy.
      */
    public String toString() {        
        String res = String.format("%3d ", numFloor);
        for (int i = 0; i < spaces.length; i++) {
            if (spaces[i] == null) { res += "    "; }
            else { res += "  X "; }
        }
        res += "\n";
        return res;
    }

}
