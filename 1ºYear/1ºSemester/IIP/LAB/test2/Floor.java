package test2;

import labact4.TimeInstant;
import labact7.Ticket;
/** Floor Class: represents the occupacion of a floor in the parking
 *  by using the tickets associated to the parked vehicles in that floor
 *  @author IIP
 *  @version Year 2017/2018
 */
public class Floor {
    private Ticket[] spaces;
    private int freeSpaces;
    private int numFloor;

    /** Creates a Floor given number of floor and number of spaces.
     *  Floor initially is empty (without vehicles).
     *  @param numF int, number of floor, numF >= 0.
     *  @param numSpaces int, number of spaces, numSpaces > 0.
     */
    public Floor(int numF, int numSpaces) {
        spaces = new Ticket[numSpaces];
        freeSpaces = numSpaces;
        numFloor = numF;        
    }

    /** Returns number of minutes that passed from the time that the vehicle
     *  entered in the space until a given exit time, updating the floor.
     *  @param spc int, space number. 
     *    Precondition: 0 <= spc < spaces.length and spaces[spc] != null.
     *  @param exitT TimeInstant, exit time. 
     *    Precondition: posterior to enter time of the vehicle.
     *  @return int, number of minutes the vehicle was in the parking.
     */
    public int leave(int spc, TimeInstant exitT) {
        int minNum = exitT.toMinutes() - spaces[spc].getEnterHour().toMinutes();
        spaces[spc] = null;
        freeSpaces++;
        return minNum;
    }

    /** Frees all spaces occupied by those vehicles whose plate contains a given
     *  letter sequence (lettersPlate), returning the total number of minutes 
     *  that those vehicles were on the floor until a given exit time (exitT).
     *  @param lettersPlate String, the sequence of letters in a plate.
     *  @param exitT TimeInstant, exit time.
     *  Precondition: posterior to enter hour of all the vehicles in the floor.
     *  @return int, total number of minutes that passed.
     */
    public int freeUpSpace(String lettersPlate, TimeInstant exitT) {        
        int totMin = 0;    
        
        for (int i = 0;i < spaces.length;i++){
            if (spaces[i] != null && spaces[i].getPlate().contains(lettersPlate)){
                totMin = totMin + leave(i, exitT);
            }
        }
        
        return totMin;
    }   
    
}
