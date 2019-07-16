package examExample;

/**
 * Class TimeInstant. 
 * 
 *  @author (IIP. Grado en Informatica. ETSINF, UPV) 
 *  @version (Year 2017-18)
 */
public class TimeInstant {

    // ATTRIBUTES:
    private int h;
    private int m;

    // CONSTRUCTORS:
    /**
     *  <code>TimeInstant</code> corresponding to <code>hh</code> 
     *  hours and <code>mm</code> minutes.
     *  <p> Precondition: <code>0<=hh<24, 0<=mm<60 </code> </p>
     */
    public TimeInstant(int hh, int mm) {
        this.h = hh;
        this.m = mm;
    }
   
    /**
     * <code>TimeInstant</code> (hours and minutes) from current 
     * UTC (universal coordinated time).
     */
    public TimeInstant() { 
        long tMinTotal =  System.currentTimeMillis() / (60 * 1000);
        int tMinCurrent = (int) (tMinTotal % (24 * 60));
        this.h = tMinCurrent / 60;
        this.m = tMinCurrent % 60; 
    }
    
    // CONSULTORS AND MODIFYERS:
    /** Returns hours of current TimeInstant object. */ 
    public int getH() { return this.h; }

    /** Returns minutes of current TimeInstant object. */ 
    public int getM() { return this.m; }
   
    /** Modifies hours of current TimeInstant object. */ 
    public void setH(int hh) { this.h = hh; }
   
    /** Modifies minutes of current TimeInstant object. */ 
    public void setM(int mm) { this.m = mm; }
   
    // OTHER METHODS:
    /** Returns current TimeInstant object in "hh:mm" format. */
    public String toString() {
        String hh = "0" + this.h;
        hh = hh.substring(hh.length() - 2);
        String mm = "0" + this.m;
        mm = mm.substring(mm.length() - 2);
        return hh + ":" + mm;
    }
   
    /** Decrements in one minute the current TimeInstant object. */
    public void decrement1Min() {
       /* TO COMPLETE */
       if (this.m > 0){
           this.m = m - 1;
       }
       else{
           if (this.h > 0){
               this.h = h - 1;
               this.m = 59;
           }
           else{
               this.h = 23;
               this.m = 59;
           }
        }
    }
  
}
