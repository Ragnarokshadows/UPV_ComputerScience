// CSD feb 2015 Juansa Sendra

public class Pool2 extends Pool{ //max kids/instructor
    //Atributes to know how much swimmers of both types are swimming
    private int kids=0;
    private int instructors=0;
    //ki = kids / instructors
    private int ki;
    
    public void init(int ki, int cap)           {
        this.ki = ki;
    }
    
    public synchronized void kidSwims() throws InterruptedException {
        while (kids + 1 > ki * instructors){
            log.waitingToSwim();
            wait();
        }
        kids++;
        log.swimming();
    }
    
    public synchronized void kidRests()      {
        log.resting(); 
        kids--;
        notifyAll();
    }
    
    public synchronized void instructorSwims()   {
        instructors++;
        log.swimming();
        notifyAll();
    }
    
    public synchronized void instructorRests() throws InterruptedException{
        while ((instructors - 1)*ki < kids){
            log.waitingToRest();
            wait();
        }
        instructors--;
        log.resting(); 
    }
}
