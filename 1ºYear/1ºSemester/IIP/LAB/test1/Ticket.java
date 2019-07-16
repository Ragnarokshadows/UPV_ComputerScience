package test1;

import labact4.TimeInstant;
/** 
 *  Class Ticket. Lab Activity 5.
 *  
 *  @author IIP
 *  @version Year 2017/18
 */
public class Ticket {
    // Definition of public constant class attibutes 
    // for calculating the ticket price
    // Base price for the tickets
    public static final double BASE_PRICE = 7.6;
             
    // The rest of constants defined in the Ticket class of
    // Lab Activity 5 are not needed in the method 
    // proportionalPrice so, for simplicity, they have been removed 

    // Definition of private instance attributes    
    private String title;
    private String theater;
    private TimeInstant sessionStart;    
    
    /** Creates a ticket with title t, theather th, session hours 
     *  and minutes sh:sm
     */
    public Ticket(String t, String th, int sh, int sm) {
        title = new String(t); 
        theater = new String(th); 
        sessionStart = new TimeInstant(sh, sm);
    }
    
    /** Get movie title */
    public String getTitle() { return title; }
    
    /** Get theather name */
    public String getTheater() { return theater; }
    
    /** Get session start */
    public TimeInstant getSessionStart() { return sessionStart; }
    
    /** Set to t the movie title */
    public void setTitle(String t) { title = new String(t); }
    
    /** Set to th the theather name */
    public void setTheater(String th) { theater = new String(th); }
    
    /** Set to sh the session start */
    public void setSessionStart(TimeInstant sh) { sessionStart = sh; }
    
    /** Returns a String with ticket data */
    public String toString() {
        return "\"" + title + "\", projected in " + theater + ", at " 
            + sessionStart + "\nBase price: " + BASE_PRICE + " euros";
    }
    
    /** Checks if o is a ticket with the same data than current ticket */
    public boolean equals(Object o) {
        return (o instanceof Ticket) 
            && this.title.equals(((Ticket) o).title) 
            && this.theater.equals(((Ticket) o).theater) 
            && this.sessionStart.equals(((Ticket) o).sessionStart);
    }

    /**
     * Returns price for Ticket this, according to the following:
     * When arrival time is previous or equal to session start, 
     * the Ticket price is the base price
     * When arrival time is posterior to session start,
     * Ticket price is the base price with a discount
     * of 0.1 euros for each minute that passed from session
     * start until arrival time
     * When discount is greater than or equal to base price
     * Ticket price must be zero
     */
    public double proportionalPrice(TimeInstant arrivalTime) {
        double res = BASE_PRICE;  // 7.60 euros        
        
        /* TO COMPLETE */
        int x = this.sessionStart.compareTo(arrivalTime);
        if (x >= 0){res = BASE_PRICE;}
        else{
            res = BASE_PRICE - 0.10*(arrivalTime.toMinutes() - sessionStart.toMinutes());
            if(res >= BASE_PRICE){res = 0.0;}
        }
        return res;
    } 
}