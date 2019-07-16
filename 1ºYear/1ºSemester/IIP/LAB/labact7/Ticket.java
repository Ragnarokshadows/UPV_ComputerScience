package labact7;

import labact4.TimeInstant;

/** Clase Ticket: defines data for parking a vehicle
 *  in a parking: plate number, enter time, and location
 *  (floor number and space number)
 *  @author IIP
 *  @version Year 2017/2018
 */
public class Ticket {
    private String plate;
    private TimeInstant enterHour;    
    private int floor;
    private int space;
    
    /** Create a Ticket for a car with a given plate
     *  and enter time, without location (floor and space are -1)
     *  @param plt String, the plate number.
     *  @param entH TimeInstant, enter hour into the parking.      
     */
    public Ticket(String plt, TimeInstant entH) {
        /* TO COMPLETE */
        plate = plt;
        enterHour = entH;
        floor = -1;
        space = -1;
    }

    /** Returns plate
     *  @return String, plate number.
     */
    public String getPlate() { return plate; }

    /** Returns enter hour.
     *  @return TimeInstant, enter hour.
     */
    public TimeInstant getEnterHour() { return enterHour; }    

    /** Returns floor.
     *  @return int, floor. 
     */
    public int getFloor() { return floor; }
    
    /** Returns space number.
     *  @return int, space. 
     */
    public int getSpace() { return space; }
        
    /** Updates floor.
     *  @param fl int, floor.
     */
    public void setFloor(int fl) { floor = fl; }
    
    /** Updates space number.
     *  @param spc int, space.
     */
    public void setSpace(int spc) { space = spc; }
    
    /** Returns a String that represents Ticket data in the
     *  following format: <br>
     *  - When the ticket HAS NOT location: <pre>
     *    "Plate: PLATE - Enter time: ENTERHOUR" 
     *    </pre>
     *  - When the ticket HAS location: <pre>
     *    "Plate: PLATE - Enter time: ENTERHOUR - Floor: FLOOR - Space number: SPACE"
     *    </pre>
     *  @return String, Ticket representation.
     */
    public String toString() {
        String res = "Plate: " + plate 
            + " - Enter time: " + enterHour;
        /* TO COMPLETE */
        if (space != -1 && floor != -1){
            res = res + " - Floor: " + floor + " - Space number: " + space;
        }
        return res;
    }
}
