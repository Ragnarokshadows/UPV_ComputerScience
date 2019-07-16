// CSD feb 2013 Juansa Sendra


import java.util.*;

public abstract class Swimmer extends Thread {
    final int DELAY=60;
    Random rd=new Random();
    Pool pool;
    protected void delay() throws InterruptedException { Thread.sleep(DELAY+rd.nextInt(DELAY));}

    public Swimmer(int id0, Pool p) {super(""+id0); pool=p;}
    public void run() {
        try {
            pool.begin(); delay();
            for (int i=0; i<6 && !this.isInterrupted(); i++) {
                swims(); delay(); 
                rests(); delay();
            }
            pool.end();
        }catch (InterruptedException ex) {
        }
    }
    abstract void swims() throws InterruptedException;
    abstract void rests() throws InterruptedException;
}
