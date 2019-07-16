// CSD feb 2015 Juansa Sendra
//     dic 2016 Mario Gonzalez, Fernando Alvarruiz

import javax.swing.*;

import java.awt.*;
import java.awt.event.*;

import javax.swing.event.*;

public class PPool extends JPanel implements ActionListener {	
    private int K,I,KI,CAP;
    private	DefaultListModel<State> states;
    private JList<State> text;
    private Box box;
    private StateRenderer sr;
    JRadioButton[] typeRadioButtons;
    public Log log;
    private Light luz;

    private static int integer(String[] v, int i, int def, int a, int b) {
        int n= (i>=v.length)? def: Integer.parseInt(v[i]);
        return (n<a || n>b)?def:n;
    }

    private void simulate(String s)  {
        final int TYPE=Integer.parseInt(s.substring(4)); sr.inic(TYPE);
        
        Swimmer[] sw= new Swimmer[K+I];

        luz.red();

        Pool p=null;
        Log log=new Log(text,K,I);
        try {
            Class<?> c=Class.forName(s);
            p=(Pool)c.newInstance();
            p.setLog(log);
            p.init(KI,CAP);
            box.state0(K,I);
            System.out.print("Simulation of pool "+TYPE+" ... ");
        }
        catch (Exception e) { e.printStackTrace();}
        for (int i=0; i<K+I; i++) sw[i]= i<K? new Kid(i,p): new Instructor(i,p);

        try {
            for (int i=0; i<K+I; i++) sw[i].start();
            // wait for the instructors to finish - it is possible to have unfinished kids 
            for (int i=K; i<K+I; i++) sw[i].join();
        } 
        catch (InterruptedException e) {}

        // Interrupt all the kids and wait for them to finish
        for (int i=0; i<K; i++) sw[i].interrupt();
        try {
            for (int i=0; i<K; i++) sw[i].join();
        }
        catch (Exception e) {e.printStackTrace();}

        // Check for error in state sequence
        boolean bErr = log.checkstate(TYPE);
        System.out.println("done");
        if (bErr) { luz.yellow(); System.out.println("Pool "+TYPE+" has not fullfilled its requirements");}
        else	luz.green();

        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                enableTypeRadioButtons(true);
            }
        });
    }

    private void enableTypeRadioButtons(boolean f) {

        for(int i=0;i<5;i++) { typeRadioButtons[i].setEnabled(f);}

    }

    public PPool(int k, int i) {
        K=k; I=i; KI=k/i; CAP=(k+i)/2;

        typeRadioButtons = new JRadioButton[5];

        typeRadioButtons[0] =	new JRadioButton("Pool0: free access");
        typeRadioButtons[1] =	new JRadioButton("Pool1: kids cannot be alone");
        typeRadioButtons[2] =	new JRadioButton("Pool2: max kids/instructor");
        typeRadioButtons[3] =	new JRadioButton("Pool3: max capacity");
        typeRadioButtons[4] =	new JRadioButton("Pool4: kids cannot enter if there are instructors waiting to rest");

        enableTypeRadioButtons(false);
        JLabel working=new JLabel("");
        ButtonGroup poolType= new ButtonGroup();
        for (int j=0; j<typeRadioButtons.length; j++) {
            poolType.add(typeRadioButtons[j]);
            typeRadioButtons[j].addActionListener(this);
            typeRadioButtons[j].setActionCommand("Pool"+j);
        }
        JPanel ptype= new JPanel(new GridLayout(0,1));
        for (int j=0; j<typeRadioButtons.length; j++) {
            ptype.add(typeRadioButtons[j]);
        }
        ptype.add(new JLabel(""));
        JPanel etiq= new JPanel(new GridLayout(0,1));
        etiq.add(new JLabel(K+" kids", JLabel.CENTER));
        etiq.add(new JLabel(I+" instructors", JLabel.CENTER));
        etiq.add(new JLabel("Max "+KI+" kids per instructor", JLabel.CENTER));
        etiq.add(new JLabel("Pool capacity = "+CAP+" Swimmers", JLabel.CENTER));
        etiq.add(working);
        box = new Box(K+I);
        states=new DefaultListModel<State>(); 
        text = new JList<State>(states);
        sr= new StateRenderer();
        text.setCellRenderer(sr);
        text.setVisibleRowCount(24);
        text.addListSelectionListener(new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent e) {
                int i=text.getSelectedIndex();
                if (i>=0) states.get(i).draw(box);
            }
        });
        JPanel p=new JPanel(), q=new JPanel();

        luz = new Light(); 
        luz.green();

        enableTypeRadioButtons(true);
        p.setLayout(new GridLayout(0,1)); p.add(etiq); p.add(ptype);

        p.add(luz);

        q.setLayout(new GridLayout(0,1)); q.add(p); q.add(box);
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

    public Dimension getPreferredSize() {return new Dimension(1000,800);}

    public static void main(String[] args) {
        // 1st arg  = number of kids    (interval [5..20], default 7)
        // 2nd arg  = number of instructors     (interval [2..5],   default 3)
        final int nkids=integer(args,0,7,5,20);
        final int ninst=integer(args,1,3,2,5);
        
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                JFrame frame = new JFrame("CSD: Shared Pool");
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                PPool ppool = new PPool(nkids, ninst);
                frame.setContentPane(ppool);
                frame.pack(); frame.setVisible(true);
            }
        });
    }
}
