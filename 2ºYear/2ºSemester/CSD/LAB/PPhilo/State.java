// CSD Mar 2013 Juansa Sendra


public class State {
    //              0        1       2      3     4       5       6        7      8      9           10          11   12    13
    //states philo: inactive ponder, wtalk, talk, wtakeL, wtakeR, wtakeLR, talkR, talkL, wtakeLhasR, wtakeRhasL, eat, rest, restL
    final int[] e; final boolean[] f; //5 States philo, 5 States fork (free)

    public State() {e=new int[5]; f=new boolean[5]; for (int i=0; i<5; i++) {e[i]=0; f[i]=true;}}
    public State(State est) {e=new int[5]; f=new boolean[5]; for (int i=0; i<5; i++) {e[i]=est.e[i]; f[i]=est.f[i];}}
    public State(State est, int idx, int x, boolean[] f) {this(est); e[idx]=x; for (int i=0; i<5; i++) this.f[i]=f[i];}
    public String toString() {
        String s="   .P..*..T.*T..T**T*.T][T.*T][T*[E].R.[R.";
        StringBuffer sb=new StringBuffer();
        for (int i=4; i>=0; i--) sb.append(s.substring(3*e[i],3*e[i]+3));
        return sb.toString();
    }
    public void draw(Box b) {b.State(this);}
    public int get(int i) {return e[i];}
    public boolean free(int i) {return f[i];}
}


