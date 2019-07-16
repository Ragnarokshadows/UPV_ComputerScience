// CSD feb 2015 Juansa Sendra

public class Pool3 extends Pool{ //max capacity
    //Atributes to know how much swimmers of both types are swimming
    private int kids=0;
    private int instructors=0;
    //ki = kids / instructors
    private int ki;
    //capacity
    private int cap;
    
    public void init(int ki, int cap)           {
        this.ki = ki;
        this.cap = cap;
    }
    
    public synchronized void kidSwims() throws InterruptedException {
        while ((kids + instructors + 1 > cap) || (kids + 1 > ki * instructors)){
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
    
    public synchronized void instructorSwims() throws InterruptedException  {
        while (kids + instructors + 1 > cap){
            log.waitingToSwim();
            wait();
        }
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
        notifyAll();
    }
}
