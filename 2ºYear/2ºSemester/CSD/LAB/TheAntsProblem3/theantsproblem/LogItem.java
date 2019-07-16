//CSD 2016 Ana Garcia
package theantsproblem;

//Items in trace
public class LogItem {
    public static final int PUT = 0; // operation code: put ant in territory
    public static final int MOVE = 1; // operation code: move ant in territory
    public static final int TAKE = 2; // operation code: ant leaves territory
    public static final int END = 3; // operation code: last ant leaves
                                     // territory
    public static final int WAIT = 1; // state code: ant is waiting to move
    public static final int OK = 0; // state code: ant is moving
    public static final int OUT = 2; // state code: ant is leaving board
    public static final int WAITINS = 3; // state code: ant is waiting outside
                                         // board

    int cod_op; // 0:put, 1:move, 2:take, 3:end
    int idH; // ant identifier
    int PosX;
    int PosY; // current ant position
    int State; // 0:OK in board, 1:waiting in board, 2:out, 3:waiting outside
               // board
    String Evento;

    public LogItem(int c, int id, int x, int y, int s, String e) {
        this.cod_op = c;
        this.idH = id;
        this.PosX = x;
        this.PosY = y;
        this.State = s;
        this.Evento = e;
    }
}
