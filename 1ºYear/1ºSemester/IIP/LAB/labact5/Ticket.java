package labact5;

// Import TimeInstant class in package labact4
import labact4.TimeInstant;
/** Class Ticket.
 *  Lab activity 5 - IIP - ETSINF-UPV.
 *  
 *  @author
 *  @version Year 2017/18.
 */

public class Ticket {

    // Definition of public constant class attibutes for calculating the ticket price

    // Base price for the tickets
    private static double BASE_PRICE = 7.6;

    // Limit for matinee
    public static final TimeInstant MATINEE_LIMIT = new TimeInstant(15, 0);

    // Elderly watcher age (older than 65)
    private static int ELDERLY_AGE = 65;

    // Discount for elderly watcher (pays a 30% of the base price)
    private static double ELDER = 0.3;

    // Discount for watcher's day
    public static final double WATCHER = 0.8;
    // Charge for holiday
    public static final double HOLY = 1.2;
    // Charge for holiday eve
    public static final double HOLYEVE = 1.1;
    // Discount for client card
    public static final double CLIENT = 0.8;
    // Definition of private instance attributes
    private String title;
    private String theather;
    private TimeInstant sessionStart;

    /** Creates a ticket with title t, theater th, session hours 
     *  and minutes sh:sm
     */
    public Ticket(String t, String th, int sh, int sm) {
        title = t;
        theather = th;
        TimeInstant ti = new TimeInstant(sh, sm);
        sessionStart = ti;
    }

    /** Get movie title */
    public String getTitle() { return title; }

    /** Get theather name */
    public String getTheater() { return theather; }

    /** Get session start */
    public TimeInstant getSessionStart() { return sessionStart; }

    /** Set to t the movie title */
    public void setTitle(String t) { title = new String(t); }

    /** Set to th the theather name */
    public void setTheater(String th) { theather = th; }

    /** Set to sh the session start */
    public void setSessionStart(TimeInstant sh) { sessionStart = sh; }

    /** Returns a String with ticket data */
    public String toString() {
        return "\"" + title + "\", projected in " + theather + ", at " 
              + sessionStart + "\nBase price: " + BASE_PRICE + " euros";
    }

    /** Checks if o is a ticket with the same data than current ticket */
    public boolean equals(Object o) {
        return o instanceof Ticket 
            && this.title.equals(((Ticket) o).title)
            && this.theather.equals(((Ticket) o).theather)
            && this.sessionStart == ((Ticket) o).sessionStart;
    }

    /** Calculate and return final price using base price and the discounts 
     * associated to watcher's age, the day of the session (watcher's day, 
     * holiday, holiday eve),  and the possession of client card 
     */
    public double finalPrice(int age, boolean watcherDay, boolean holiday,
                           boolean holidayEve, boolean clientCard) {

        double f = BASE_PRICE;  // Supposition: no discount is applied

        if (age >= ELDERLY_AGE) {
            f = ELDER * f;
        }
        else {
            
            if (watcherDay == true) {
                f = WATCHER * f;
            }
            else {
                if (holiday == true) {
                    f = HOLY * f;
                }
                else {
                    if (holidayEve == true) {
                        f = HOLYEVE * f;
                    }
                }

                if (clientCard == true) {
                    f = CLIENT * f;
                }
            }
        }
        if (sessionStart.getH() < MATINEE_LIMIT.getH()) {
            f = f - 0.50;
        } 
        f = f * 100;
        f = Math.floor(f);
        f = f / 100;
        return f;
    }
}