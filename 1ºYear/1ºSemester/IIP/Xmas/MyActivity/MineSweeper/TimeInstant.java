package MyActivity;

public class TimeInstant{
    // ATTRIBUTES:
    private int h;
    private int m;

    // CONSTRUCTORS:
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
    
    public int toMinutes() {
        int hMin = (h * 60) + m;
        return hMin;
    }
}
