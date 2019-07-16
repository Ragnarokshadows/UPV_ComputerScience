package linear;

import java.util.*;

public class QueueIntLinked
{
    private NodeInt first;
    private NodeInt last;
    private int     size;

    public QueueIntLinked()
    {
        size = 0;
        first = last = null;
    }

    public boolean add( int value )
        throws IllegalStateException
    {
        if ( isEmpty() ) {
            first = last = new NodeInt( value );
        } else {
            last.setNext( new NodeInt( value ) );
            last = last.getNext();
        }
        ++size;

        return true;
    }
    public boolean offer( int value )
    {
        return add( value );
    }
    public int element()
        throws IllegalStateException
    {
        if ( isEmpty() ) throw new IllegalStateException( "Empty queue!" );

        return first.getValue();
    }
    public int peek()
        throws IllegalStateException
    {
        if ( isEmpty() ) throw new IllegalStateException( "Empty queue!" );

        return first.getValue();
    }
    public int remove()
        throws IllegalStateException
    {
        if ( isEmpty() ) throw new IllegalStateException( "Empty queue!" );

        int temp = first.getValue();

        first = first.getNext();
        if ( --size == 0 ) last = null;

        return temp;
    }
    public int poll()
        throws IllegalStateException
    {
        if ( isEmpty() ) throw new IllegalStateException( "Empty queue!" );

        return remove();
    }

    public int size()
    {
        return size;
    }
    public boolean isEmpty()
    {
        return 0 == size();
        //return first == null;
    }

    @Override
    public boolean equals( Object o )
    {
        if ( o instanceof StackIntArray ) {
            
            QueueIntLinked other = (QueueIntLinked)o;

            boolean stillEqual = this.size == other.size;
            
            NodeInt n1 = this.first;
            NodeInt n2 = other.first;

            while(stillEqual && n1 != null && n1.getValue() == n2.getValue()){
                n1 = n1.getNext();
                n2 = n2.getNext();
            }
            
            return stillEqual;

        } else {
            return false;
        }
    }

    @Override
    public String toString()
    {
        StringBuffer sb = new StringBuffer();
        NodeInt n1 = this.first;

        while(n1 != null){
            sb.append( " " + n1.getValue());
            n1 = n1.getNext();
        }

        return sb.toString();
    }

    public QueueIntLinked clone()
    {
        return null;
    }
}
