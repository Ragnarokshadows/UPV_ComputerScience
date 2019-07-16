package labact4;

/**
 * Class TimeInstant. 
 * 
 *  @author (IIP. Grado en Informatica. ETSINF, UPV) 
 *  @version (Year 2017-18)
 */
public class TimeInstant {

    // ATTRIBUTES:
    /** Integer var to store the attribute for hours.
     *  It must pertain to the range <code>[0..23]</code>. */
    // TO COMPLETE
    /** Integer var to store the attribute for minutes.
     *  It must pertain to the range <code>[0..59]</code>. */
    // TO COMPLETE
    private int h;
    private int m;

    // CONSTRUCTORS:
    /**
     *  <code>TimeInstant</code> corresponding to <code>hh</code> 
     *  hours and <code>mm</code> minutes.
     *  <p> Precondition: <code>0<=hh<24, 0<=mm<60 </code> </p>
     */
    // TO COMPLETE
    public TimeInstant(int hh, int mm) {
        h = hh;
        m = mm;
    }
    /**
     * <code>TimeInstant</code> (hours and minutes) from current 
     * UTC (universal coordinated time).
     */
    // TO COMPLETE
    public TimeInstant() {
        long tMinTotal = System.currentTimeMillis() / (60 * 1000);
        int tMinCurrent = (int) (tMinTotal % (24 * 60));
        h = tMinCurrent / 60;
        m = tMinCurrent % 60;
    }
    // CONSULTORS AND MODIFYERS:
    /** Returns hours of current TimeInstant object. */ 
    // TO COMPLETE
    public int getH() {
        return  h;
    }
    /** Returns minutes of current TimeInstant object. */ 
    // TO COMPLETE
    public int getM() {
        return m;
    }
    /** Modifies hours of current TimeInstant object. */ 
    // TO COMPLETE
    public void setH(int hh) {
        h = hh;
    }
    /** Modifies minutes of current TimeInstant object. */ 
    // TO COMPLETE
    public void setM(int mm) {
        m = mm;
    }
    // OTHER METHODS:
    /** Returns current TimeInstant object in "hh:mm" format.
     */
    // TO COMPLETE
    public String toString() {
        String mm = "0" + m;
        String hh = "0" + h;
        if (mm.length() == 3) {
            mm = mm.substring(1);
        }
        if (hh.length() == 3) {
            hh = hh.substring(1);
        }
        String timeInstant = hh + ":" + mm;
        return timeInstant;
    }
    /** Returns true only if o is TimeInstant that concides in hours
     *  and minutes with current TimeInstant.
     */
    // TO COMPLETE
    public boolean equals(Object o) {
        return o instanceof TimeInstant 
            && this.h == ((TimeInstant) o).h 
            && this.m == ((TimeInstant) o).m;
    }
    /** Returns number of minutes from 
     *  00:00 until current TimeInstant object
     */
    // TO COMPLETE
    public int toMinutes() {
        int hMin = (h * 60) + m;
        return hMin;
    }
    /** Compares chronologically current TimeInstant object and ti 
     *  parameter. Result is the difference between the conversion
     *  obained with <code>toMinutes</code> for current and parameter
     *  object, giving:
     *      - negative when current TimeInstant is previous to 
     *        <code>ti</code>
     *      - zero if they are equal
     *      - positive when current TimeInstant is posterior to 
     *        <code>ti</code>
     */
    // TO COMPLETE
    public int compareTo(TimeInstant ti) {
        return (this.toMinutes() - ti.toMinutes());
    }
    // EXTRA ACTIVITY:
    /** Returns a TimeInstant from its textual description 
     *  in a <code>String</code> with format "<code>hh:mm</code>".
     */
    // TO COMPLETE
  
}
