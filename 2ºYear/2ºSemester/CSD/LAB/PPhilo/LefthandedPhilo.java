// CSD Mar 2013 Juansa Sendra


public class LefthandedPhilo extends Philo { //ASSIMETRY
    public LefthandedPhilo(int id, int cycles, int delay, Table table) {
        super(id,cycles,delay,table);
    }
    
    public void run() {
        try {
            table.begin(id);
            for (int i=0; i<cycles; i++) {
                table.takeL(id); delay(); table.takeR(id);
                table.eat(id); randomDelay();
                table.dropR(id); table.dropL(id);
                table.ponder(id); randomDelay();
            }
            table.end(id);
        } 
        catch (InterruptedException ex) {}
    }
}