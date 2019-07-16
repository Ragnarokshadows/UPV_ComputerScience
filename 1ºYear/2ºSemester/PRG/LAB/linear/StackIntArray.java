package linear;

import java.util.*;

public class StackIntArray
{
    private int [] data;
    private int    indexOfTop;
    
    public StackIntArray() // T(n) \in \Theta(1)
    {
        this( 100 );
    }
    public StackIntArray( int initialSize ) // T(n) \in \Theta(1)
    {
        indexOfTop = -1;
        data = new int [ initialSize ];
    }
    public void push( int value ) // T(n) \in \Theta(1)
        throws Exception
    {
        if ( indexOfTop == data.length-1 ) throw new Exception( "Stack overflow!" );

        data[++indexOfTop] = value;
    }
    public int pop() // T(n) \in \Theta(1)
        throws Exception
    {
        if ( empty() ) throw new Exception( "Stack underflow!" );

        return data[indexOfTop--];
    }
    public int peek() // T(n) \in \Theta(1)
        throws Exception
    {
        if ( empty() ) throw new Exception( "Stack underflow!" );

        return data[indexOfTop];
    }
    public int top() // T(n) \in \Theta(1)
        throws Exception
    {
        if ( empty() ) throw new Exception( "Stack underflow!" );

        return data[indexOfTop];
    }
    public boolean empty() // T(n) \in \Theta(1)
    {
        return 0 == size();
    }
    public int size() // T(n) \in \Theta(1)
    {
        return indexOfTop+1;
    }

    @Override
    public String toString()
    {
        StringBuffer sb = new StringBuffer();

        if(empty()){
            return "{}";
        }

        sb.append( "{ " );

        for( int i = indexOfTop; i >= 0; i-- )
            if(i == indexOfTop){
                sb.append(data[i]);
            }
            else{
                sb.append( ", " + data[i] );
            }

        sb.append( " }" );

        return sb.toString();
    }

    @Override
    public boolean equals( Object o )
    {
        if ( o instanceof StackIntArray ) {
            
            StackIntArray other = (StackIntArray)o;

            boolean stillEqual = this.size() == other.size();

            for( int i=0; i < this.size() && stillEqual; i++ ) {
                stillEqual = this.data[i] == other.data[i];
            }
            return stillEqual;

        } else {
            return false;
        }
    }
}
