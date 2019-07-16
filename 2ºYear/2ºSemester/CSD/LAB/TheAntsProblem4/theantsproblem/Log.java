//CSD 2016 Ana Garcia
package theantsproblem;

import java.util.ArrayList;

public class Log {
    private ArrayList<LogItem> Trace;
    private int next;

    public Log(int size) {
        this.Trace = new ArrayList<LogItem>(size);
        this.next = 0;
    }

    public synchronized void writeLog(int op, int hid, int cx, int cy, int state, String s) {
        // System.out.println(s);
        Trace.add(next, new LogItem(op, hid, cx, cy, state, s));
        next++;
    }

    public synchronized LogItem readLog(int pos) {
        if (pos < Trace.size())
            return (LogItem) Trace.get(pos);
        else
            return (LogItem) null;
    }

    public synchronized int getsize() {
        return Trace.size();
    }
}
