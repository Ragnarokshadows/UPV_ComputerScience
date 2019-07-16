// CSD Mar 2013 Juansa Sendra



import java.awt.*;

public class Box extends Canvas {
    final int R=90, r=30;
    final double PI=Math.PI;
    double[] dx, dy;
    State e;

    public Box() {
        dx=new double[20]; dy=new double[20]; e= new State();
        for (int i=0; i<20; i++) {double x=(PI*i)/10; dx[i]=Math.cos(x); dy[i]=Math.sin(x);}
    }
    private int px(int d, int i) {return (int)Math.floor(3*R+d*dx[i]);}
    private int py(int d, int i) {return (int)Math.floor(3*R-d*dy[i]);}
    private void fork(Graphics g, int ang, int d) {
        g.setColor(Color.black); g.drawLine(px(d,ang),py(d,ang),px(d+r,ang),py(d+r,ang));
    }
    private void waitForks(Graphics g, int ang, int d) {circ(g,px(d,ang),py(d,ang),r/4,Color.red);}
    private void circ(Graphics g, int x, int y, int r, Color col) {
        g.setColor(col);         g.fillOval(x-r,y-r,2*r,2*r); 
        g.setColor(Color.black); g.drawOval(x-r,y-r,2*r,2*r);
    }
    private int wL(int x) {return(24-4*x)%20;}
    private int wR(int x) {return(26-4*x)%20;}
    private int R(int x) {return(26-4*x)%20;}
    private int L(int x) {return(24-4*x)%20;}
    public void State(State e) {this.e=e; repaint();}
    public void paint(Graphics g) {
        g.setColor(Color.white); g.fillRect(0,0,6*R,6*R);
        g.setColor(Color.black); g.drawRect(1,1,6*R-2,6*R-2);
        circ(g, 3*R, 3*R, R, Color.lightGray); //table
        int d1=R+r+10, d2=R+(3*r)+20, near=R/2+10, far=R+20;
        for (int i=0; i<5; i++) { //philos
            int ang=(25-4*i)%20;
            int x1=px(d1,ang), x2=px(d2,ang), y1=py(d1,ang), y2=py(d2,ang);
            switch(e.get(i)) {
            case  0:                        break; //inactive
            case  1: circ(g,x2,y2,r,Color.gray);  break; //ponder
            case  2: circ(g,x2,y2,r,Color.red);   break; //wtalk
            case  3: circ(g,x1,y1,r,Color.red);   break; //talk
            case  4: circ(g,x1,y1,r,Color.red); waitForks(g,wL(i),far+10);  break; //wtakeL
            case  5: circ(g,x1,y1,r,Color.red); waitForks(g,wR(i),far+10);  break; //wtakeR
            case  6: circ(g,x1,y1,r,Color.red); waitForks(g,wR(i),far+10); waitForks(g,wL(i), far+10); break; //wtakeLR
            case  7: circ(g,x1,y1,r,Color.red); fork(g,R(i),far); break; //talkR
            case  8: circ(g,x1,y1,r,Color.red); fork(g,L(i),far); break; //talkL
            case  9: circ(g,x1,y1,r,Color.red); waitForks(g,wL(i),far+10); fork(g,R(i),far); break; // wtakeLhasR
            case 10: circ(g,x1,y1,r,Color.red); waitForks(g,wR(i),far+10); fork(g,L(i),far); break; // wtakeRhasL
            case 11: circ(g,x1,y1,r,Color.green); fork(g,R(i),far); fork(g,L(i),far); break; //eat
            case 12: circ(g,x1,y1,r,Color.white); break; //rest
            case 13: circ(g,x1,y1,r,Color.white); fork(g,L(i),far); //restL
            }
        }
        for (int i=0; i<5; i++) if (e.free(i)) fork(g,(23-4*i)%20, near); //free forks
    }
    public Dimension getPreferredSize() {return new Dimension(6*R,6*R);}
}