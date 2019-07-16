// CSD Mar 2013 Juansa Sendra

public class LimitedTable extends RegularTable { //max 4 in dinning-room
    public int table_phil=0;
    
    public LimitedTable(StateManager state) {super(state);}
    
    public synchronized void enter(int id) throws InterruptedException {
        while(table_phil == 4){
            state.wenter(id);
            wait();
        }
        
        table_phil++;
        state.enter(id);
    }
    public synchronized void exit(int id)  {
        state.exit(id);
        table_phil--;
        notifyAll();
    }
}
