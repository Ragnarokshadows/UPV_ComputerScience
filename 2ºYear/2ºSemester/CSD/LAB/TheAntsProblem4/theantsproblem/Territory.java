package theantsproblem;

import java.util.concurrent.locks.*;
import java.util.concurrent.*;

public class Territory {
    private int tam; // Matrix size
    private boolean occupied[][];
    String description = "Using Barrriers";
    private Log log;
    
    ReentrantLock lock;
    Condition [][] terOccupied;
    CountDownLatch barrier;

    public String getDesc() {
        return description;
    }

    public Territory(int tamT, Log l, int ants) {
        tam = tamT;
        occupied = new boolean[tam][tam];
        terOccupied = new Condition[tam][tam];
        log = l;
        lock = new ReentrantLock();
        barrier = new CountDownLatch(ants);
        
        // Initializing the matrix
        for (int i = 0; i < tam; i++) {
            for (int j = 0; j < tam; j++) {
                occupied[i][j] = false;
                terOccupied[i][j] = lock.newCondition();
            }
        }
    }

    public int getTam() {
        return tam;
    }

    public void putAnt(Ant h, int x, int y) {
        lock.lock();
        try{
            barrier.countDown();
            while (occupied[x][y]) {
                try {
                    // Write in the log: ant waiting
                    log.writeLog(LogItem.PUT, h.getid(), x, y, LogItem.WAITINS,
                            "Ant " + h.getid() + " waiting for [" + x + "," + y + "]");
                    terOccupied[x][y].await();
                } catch (InterruptedException e) {
                }
            }
            occupied[x][y] = true;
            h.setPosition(x, y);
            // Write in the log: ant inside territory
            log.writeLog(LogItem.PUT, h.getid(), x, y, LogItem.OK, "Ant " + h.getid() + " : [" + x + "," + y + "]  inside");
        } catch (Exception e){
        } finally { lock.unlock(); }
        try{
            barrier.await();
        } catch (InterruptedException ie) {}
    }

    public void takeAnt(Ant h) {
        int x = h.getX();
        int y = h.getY();
        lock.lock();
        try{
            occupied[x][y] = false;
            // Write in the log: ant outside territory
            log.writeLog(LogItem.TAKE, h.getid(), x, y, LogItem.OUT, "Ant " + h.getid() + " : [" + x + "," + y + "] out");
            terOccupied[x][y].signalAll();
        } finally { lock.unlock(); }
    }

    public void moves(Ant h, int x1, int y1, int step) {
        int x = h.getX();
        int y = h.getY();
        lock.lock();
        try{
            while (occupied[x1][y1]) {
                try {
                    // Write in the log: ant waiting
                    log.writeLog(LogItem.MOVE, h.getid(), x1, y1, LogItem.WAIT,
                            "Ant " + h.getid() + " waiting for [" + x1 + "," + y1 + "]");
                    terOccupied[x1][y1].await(10, TimeUnit.SECONDS);
                    if(occupied[x1][y1]){
                        do {
                            h.nextMovement(); // Get next move
                            x1=h.getNextPosX();
                            y1=h.getNextPosY();
                        } while ((x1 < 0) || (y1 < 0));
                    }
                } catch (InterruptedException e) {
                }
            }
            occupied[x][y] = false;
            occupied[x1][y1] = true;
            h.setX(x1);
            h.setY(y1);
            // Write in the log: ant moving
            log.writeLog(LogItem.MOVE, h.getid(), x1, y1, LogItem.OK,
                    "Ant " + h.getid() + " : [" + x + "," + y + "] -> [" + x1 + "," + y1 + "] step:" + step);
            terOccupied[x][y].signalAll();
        } catch (Exception e){
        }finally {lock.unlock();}
    }
}
