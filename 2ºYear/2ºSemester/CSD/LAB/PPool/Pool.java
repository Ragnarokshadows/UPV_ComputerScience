// CSD feb 2015 Juansa Sendra

public abstract class Pool {
    Log log;

    public abstract void init(int ki, int cap);
    public abstract void kidSwims() throws InterruptedException;
    public abstract void kidRests() throws InterruptedException;
    public abstract void instructorSwims() throws InterruptedException;
    public abstract void instructorRests() throws InterruptedException;

    public void setLog(Log l) { log = l;}
    public void begin() { log.begin();}
    public void end() { log.end();}

}
