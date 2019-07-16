package theantsproblem;

import java.util.Random;
import java.util.concurrent.CyclicBarrier;

public class Ant extends Thread {
    private int id;
    private Territory land;
    private Random rnd;
    private int steps;
    private int posX, posY; // current position
    private int nextPosX, nextPosY; // next position for movement
    private CyclicBarrier b;

    public Ant(int id0, int x0, int y0, Territory t, int stepsAnt, CyclicBarrier barrier) {
        id = id0;
        posX = x0;
        posY = y0;
        land = t;
        rnd = new Random();
        steps = stepsAnt;
        nextPosX = -1;
        nextPosY = -1;
        b = barrier;
    }

    public int getid() {
        return id;
    }

    public void setPosition(int x, int y) {
        posX = x;
        posY = y;
    }

    public int getX() {
        return posX;
    }

    public int getY() {
        return posY;
    }

    public void setX(int x) {
        posX = x;
    }

    public void setY(int y) {
        posY = y;
    }

    public int getNextPosX() {
        return nextPosX;
    }

    public int getNextPosY() {
        return nextPosY;
    }

    public void nextMovement() { // gets next movement, depending on the origin
                                 // and a random number
        switch (rnd.nextInt(4)) {
        // Up
        case 0:
            nextPosX = (posX - 1 >= 0) ? posX - 1 : -1;
            nextPosY = posY;
            break;

        // Down
        case 1:
            nextPosX = (posX + 1 < land.getTam()) ? posX + 1 : -1;
            nextPosY = posY;
            break;

        // Left
        case 2:
            nextPosY = (posY - 1 >= 0) ? posY - 1 : -1;
            nextPosX = posX;
            break;

        // Right
        case 3:
            nextPosY = (posY + 1 < land.getTam()) ? posY + 1 : -1;
            nextPosX = posX;
            break;

        }
    }

    protected void delay(long ms) {
        try {
            Thread.sleep(ms);
        } catch (Exception ex) {
        }
    }

    public void run() {
        Thread.currentThread().setName("Ant " + this.getid()); // set the name
                                                               // of the ant

        land.putAnt(this, getX(), getY());

        // Make the number of moves given by steps. In each movement, you can go up, down, left, or right.
        for (int i = 0; i < steps; i++) {
            delay(rnd.nextInt(5) * 1000); // Wait before moving to another cell
            do {
                nextMovement(); // Get next move
            } while ((getNextPosX() < 0) || (getNextPosY() < 0));
            land.moves(this, getNextPosX(), getNextPosY(), i);
        }
        // The ant is removed from the territory
        land.takeAnt(this);
        try {
            b.await(); // wait in the barrier
        } catch (Exception e) {
        }
    }
}
