// CSD feb 2013 Juansa Sendra


import java.awt.*;

public class Box extends Canvas {
    State e;
    int w=500, h=100, rsep=40;
    public Box(int nad) {rsep=(w-100)/nad;}
    void rect(Graphics g, Color c, int i, int pos) {
        g.setColor(c); g.fillRect(100+i*rsep,pos*h/2,rsep-5,50);
    }
    public void state0(int n, int i) {e=new State(n,i);  repaint();}
    public void state (State stat)   {e=new State(stat); repaint();}
    public void paint(Graphics g) {
        g.setColor(Color.gray);  g.fillRect(0, 0, w, h);
        g.setColor(Color.black); g.drawRect(0, 0, w, h);
        g.setColor(Color.blue);  g.fillRect(0, h, w, h);
        g.setColor(Color.black); g.drawRect(0, h, w, h);
        g.drawString("resting",10,20); g.drawString("waits to swim",10,h-20);
        g.drawString("waits to rest",10,(3*h)/2-20); g.drawString("swimming",10,2*h-20);
        if (e==null) return;
        for (int i=0; i<e.e.length; i++) {
            switch (e.get(i)) {
            case 0: rect(g,(i<e.K?Color.red:Color.green),i,0); break;
            case 1: rect(g,(i<e.K?Color.red:Color.green),i,1); break;
            case 2: rect(g,(i<e.K?Color.red:Color.green),i,3); break;
            case 3: rect(g,(i<e.K?Color.red:Color.green),i,2); break;
            default:
            }
        }
    }
    public Dimension getPreferredSize(){return new Dimension(w, 2*h);}
}