package linear;

/**
 * Class NodeInt: it represents a node of a linked sequence
 * which contains an int data type and a link to the next node.
 */

public class NodeInt
{
    private int value;
    private NodeInt next;
    // private NodeInt previous;

    /**
     * Creates a node with an int v and whithout the link to
     * the next node.
     * @param v int, the value of the new node.
     */
    public NodeInt( int v )
    {
        this( v, null );
    }

    /**
     * Creates a node with an int v, linked to a node n that
     * already exists or not if its null.
     * @param v int, the value of the new node.
     * @param s NodeInt, the link to the next node.
     */
    public NodeInt( int v, NodeInt n )
    {
        value = v;
        next = n;
    }

    /**
     * Gets the parameter value.
     * @return int, the parameter value of the class.
     */
    public int     getValue() { return value; }
    
    /**
     * Gets the parameter next.
     * @return NodeInt, the parameter next of the class.
     */
    public NodeInt getNext()  { return next; }
    // public NodeInt getPrevious()  { return previous; }

    /**
     * Sets a new value to the parameter value.
     * @param v, int, the new value to the parameter value.
     */
    public void setValue( int v ) { value = v; }

    /**
     * Sets a new link to the parameter next.
     * @param n, NodeInt, the new link to the parameter next.
     */
    public void setNext( NodeInt n ) { next = n; }
    // public void setPrevious( NodeInt p ) { previous = p; }
}