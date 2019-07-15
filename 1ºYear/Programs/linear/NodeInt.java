package linear;


public class NodeInt
{
    private int     value;
    private NodeInt next;
    private NodeInt previous;

    public NodeInt( int v )
    {
        this( null, v, null );
    }
    public NodeInt( int v, NodeInt n )
    {
        this( null, v, n );
    }
    public NodeInt( NodeInt p, int v, NodeInt n )
    {
        previous = p;
        value = v;
        next = n;
    }

    public int     getValue() { return value; }
    public NodeInt getNext()  { return next; }
    public NodeInt getPrevious()  { return previous; }

    public void setValue( int v ) { value = v; }
    public void setNext( NodeInt n ) { next = n; }
    public void setPrevious( NodeInt p ) { previous = p; }
}