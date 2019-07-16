// CSD feb 2015 Juansa Sendra

public class Pool1 extends Pool {   //no kids alone
    //Atributes to know how much swimmers of both types are swimming
    private int kids=0;
    private int instructors=0;
    
    public void init(int ki, int cap)           {
    }
    
    public synchronized void kidSwims() throws InterruptedException {
        while (instructors == 0){
            log.waitingToSwim();
            wait();
        }
        kids++;
        log.swimming();
    }
    
    public synchronized void kidRests()      {
        kids--;
        log.resting(); 
        notifyAll();
    }
    
    public synchronized void instructorSwims()   {
        instructors++;
        log.swimming();
        notifyAll();
    }
    
    public synchronized void instructorRests() throws InterruptedException{
        while (instructors == 1 && kids > 0){
            log.waitingToRest();
            wait();
        }
        instructors--;
        log.resting(); 
    }
}
