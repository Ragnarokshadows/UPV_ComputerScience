// CSD Mar 2013 Juansa Sendra
//     Ene 2017 Fernando Alvarruiz

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.event.*;

public class PPhilo extends JPanel implements ActionListener {
    private DefaultListModel<State> states;
    private JList<State> text;
    private Box box;
    private static int delay;
    JRadioButton[] typeRadioButtons;

    void simulate(String s) {
        final int cycles=4;
        int i, type=Integer.parseInt(s.substring(5)); System.out.println(type+"");
        StateManager sman= new StateManager(text);
        Thread[] philo=new Philo[5];
        Table t; 
        switch (type) {
        case 0: 
            t = new RegularTable(sman);
            for (i=0; i<5; i++) philo[i]=new Philo(i,cycles,delay,t); 
            break;
        case 1: 
            System.out.println("Assimetry (last/but last).");
            t = new RegularTable(sman);
            for (i=0; i<4; i++) philo[i]=new Philo(i,cycles,delay,t); 
            philo[i]=new LefthandedPhilo(i,cycles,delay,t);
            break;
        case 2: 
            System.out.println("Assimetry (even/odd).");
            t = new RegularTable(sman);
            for (i=0; i<5; i++){ 
                if(i%2==0){
                    philo[i]=new Philo(i,cycles,delay,t);
                } else {
                    philo[i]=new LefthandedPhilo(i,cycles,delay,t);
                }          
            } 
            break;
        case 3: 
            System.out.println("Both or None.");
            t = new BothOrNoneTable(sman);
            for (i=0; i<4; i++) philo[i]=new Philo(i,cycles,delay,t);
            philo[i]=new BothOrNonePhilo(i,cycles,delay,t);
            break;
        case 4: 
            System.out.println("Limited capacity Dining Room.");
            t = new LimitedTable(sman);
            for (i=0; i<5; i++) philo[i]=new LimitedPhilo(i,cycles,delay,t);
            break;
        }
        for (i=0; i<5; i++) philo[i].start();
        
        // Wait until the end of the simulation
        sman.await();
        System.out.println(sman.deadlock()?" DEADLOCK":"   OK");
        
        // If deadlock, interrupt all the philosophers 
        if (sman.deadlock())
            for (i=0; i<5; i++) philo[i].interrupt();
        
        // Wait for the philosophers to finish
        try {
            for (i=0; i<5; i++) philo[i].join();
        }
        catch (Exception e) {e.printStackTrace();}

        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                enableTypeRadioButtons(true);
            }
        });
    }
    
    public PPhilo() {
        typeRadioButtons = new JRadioButton[5];
        typeRadioButtons[0] = new JRadioButton("Deadlock not prevented");
        typeRadioButtons[1] = new JRadioButton("Assimetry (last/but last)");
        typeRadioButtons[2] = new JRadioButton("Assimetry (even/odd)");
        typeRadioButtons[3] = new JRadioButton("Both or None");
        typeRadioButtons[4] = new JRadioButton("Limited capacity Dining Room");

        ButtonGroup typeTable= new ButtonGroup();
        for (int j=0; j<typeRadioButtons.length; j++) {
            typeTable.add(typeRadioButtons[j]);
            typeRadioButtons[j].addActionListener(this);
            typeRadioButtons[j].setActionCommand("Table"+j);
        }
        JPanel ptype= new JPanel(new GridLayout(0,1));
        for (int j=0; j<typeRadioButtons.length; j++) {
            ptype.add(typeRadioButtons[j]);
        }

        states=new DefaultListModel<State>(); 
        text = new JList<State>(states);
        box = new Box();
        text.setCellRenderer(new StateRenderer());
        text.setVisibleRowCount(30);
        text.addListSelectionListener(new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent e) {
                int i=text.getSelectedIndex();
                if (i>=0) states.get(i).draw(box);
            }
        });
        JPanel q=new JPanel(); 
        q.setLayout(new BorderLayout()); q.add(ptype, BorderLayout.PAGE_START); q.add(box, BorderLayout.PAGE_END);
        setLayout(new BorderLayout());
        add(q, BorderLayout.LINE_START);
        add(new JScrollPane(text), BorderLayout.LINE_END);
    }
    
    public void actionPerformed(ActionEvent e) {
        enableTypeRadioButtons(false);
        states.clear();
        final String action = e.getActionCommand();
        new Thread()  { 
            public void run() { simulate(action);}
        }.start();
    }
    
    private void enableTypeRadioButtons(boolean f) {
        for(int i=0;i<5;i++) { typeRadioButtons[i].setEnabled(f);}
    }
    
    private static int integer(String[] v, int i, int def, int a, int b) {
        int n= (i>=v.length)? def: Integer.parseInt(v[i]);
        return (n<a || n>b)?def:n;
    }

    public static void main(String[] args) {
        delay=integer(args,0,10,1,10);
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                JFrame frame = new JFrame("CSD: 5 Philosophers");
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setContentPane(new PPhilo());
                frame.pack(); frame.setVisible(true);
            }
        });
    }
}