import java.util.Random;

// CSD Mar 2013 Juansa Sendra
//     Ene 2017 Fernando Alvarruiz


public class Philo extends Thread { //not prevents deadlocks. Table0
    protected int id, cycles, msegDelay;
    protected Table table;
    private Random x=new Random();
    private final int randomVariability=80, randomBase=200;

    public Philo(int id, int cycles, int delay0, Table table) {
        this.id=id; this.cycles=cycles; this.table=table; msegDelay=delay0;
    }

    protected void delay() throws InterruptedException {Thread.sleep(msegDelay);}

    protected void randomDelay() throws InterruptedException {
        Thread.sleep(randomBase*(100+x.nextInt(randomVariability))/100);
    }

    public void run() {
        try {
            table.begin(id);
            for (int i=0; i<cycles; i++) {
                table.takeR(id); delay(); table.takeL(id);
                table.eat(id); randomDelay();
                table.dropR(id); table.dropL(id);
                table.ponder(id); randomDelay();
            }
            table.end(id);
        } 
        catch (InterruptedException ex) {}
    }
}
