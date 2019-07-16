// CSD Mar 2013 Juansa Sendra

public class BothOrNoneTable extends RegularTable { //both or none
    public BothOrNoneTable(StateManager state) {super(state);}
    
    public synchronized void takeLR(int id) throws InterruptedException{
        while (!state.rightFree(id) || !state.leftFree(id)) {
            state.wtakeLR(id);
            wait();
        }
        state.takeLR(id);
    }
}
