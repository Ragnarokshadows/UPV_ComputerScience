// CSD feb 2013 Juansa Sendra
//     dic 2016 Mario Gonzalez, Fernando Alvarruiz

import javax.swing.*;

public class Log {
    JList<State> stateJList;
    DefaultListModel<State> stateList;
    int nk, ni;

    public Log(JList<State> states, int k, int i) {
        nk = k;
        ni = i;
        stateJList = states;
        stateList = (DefaultListModel<State>) states.getModel();
        
        // Set initial state
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                stateList.addElement(new State(nk, ni));
            }
        });
    }

    public void begin()         { step(getID(),0);}
    public void end()           { step(getID(),-2);}

    public void waitingToSwim() { step(getID(),1);}
    public void swimming()      { step(getID(),2);}
    public void waitingToRest() { step(getID(),3);}
    public void resting()       { step(getID(),0);}

    private int getID() {return Integer.parseInt(Thread.currentThread().getName());}

    private void  step(final int x, final int n) {
        // given that Swing components are not thread-safe, we use a method that creates an asynchronous task to update the
        // component within the Event Dispatcher Thread
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                if (stateList.lastElement().get(x)!=n)
                    stateList.addElement(new State(stateList.lastElement(),x,n));
            }
        });
    }
    
    public boolean checkstate(int type) {
        class StateListReader implements Runnable {
            public State[] states;
            public void run() {
                states = new State[stateList.getSize()];
                stateList.copyInto(states);
            }
        };
        StateListReader reader = new StateListReader();
        
        // Read state list (executed in the Event Dispatcher Thread for thread-safety)
        try { SwingUtilities.invokeAndWait(reader); }
        catch (Exception e) { e.printStackTrace(); }
        
        State[] states = reader.states;
        
        boolean bErr = false;
        for (int i=1; i<states.length; i++) {
            int e = states[i].test();
            if ( e>0 && e<=type ) bErr = true;
            if (states[i].dyn_test(states[i-1])!=0) { 
                bErr = true;
                System.out.println("Log's internal error in line: "+i);
            }
        }
        return bErr;
    }
}
