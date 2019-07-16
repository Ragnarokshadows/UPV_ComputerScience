// CSD Mar 2013 Juansa Sendra


public class BothOrNonePhilo extends Philo { //Both or None. Table3
    public BothOrNonePhilo(int id, int cycles, int delay, Table table) {
        super(id,cycles,delay,table);
    }
    
    public void run() {
        try {
            table.begin(id);
            for (int i=0; i<cycles; i++) {
                table.takeLR(id); delay();
                table.eat(id); randomDelay();
                table.dropR(id); table.dropL(id);
                table.ponder(id); randomDelay();
            }
            table.end(id);
        } 
        catch (InterruptedException ex) {}
    }
}