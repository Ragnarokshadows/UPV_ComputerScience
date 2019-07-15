package linear;

import java.util.*;

public class StackIntLinked
{
    private NodeInt top;
    private int     size;
    
    public StackIntLinked() // T(n) \in \Theta(1)
    {
        size=0;        
        top=null;
    }

    public void push( int value ) // T(n) \in \Theta(1)
    {
        top = new NodeInt( value, top );
        ++size;
    }

    public int pop() // T(n) \in \Theta(1)
        throws Exception
    {
        if ( empty() ) throw new Exception( "Stack underflow!" );

        int temp = top.getValue();

        top = top.getNext();
        --size;

        return temp;
    }

    public int top() // T(n) \in \Theta(1)
        throws Exception
    {
        if ( empty() ) throw new Exception( "Stack underflow!" );

        return top.getValue();
    }

    public boolean empty() // T(n) \in \Theta(1)
    {
        return 0 == size();
    }

    public int size() // T(n) \in \Theta(1)
    {
        return size;
    }

    @Override
    public String toString()
    {
        if ( empty() ) {
            return "{}";
        } else {

            StringBuffer sb = new StringBuffer();

            sb.append( "{ " );
            NodeInt n = this.top;
            sb.append( n.getValue() );
            n = n.getNext();
            while( n != null ) {
                sb.append( ", " + n.getValue() );
                n = n.getNext();
            }
            sb.append( " }" );

            return sb.toString();
        }
    }

    @Override
    public boolean equals( Object o )
    {
        if ( o instanceof StackIntLinked ) {

            StackIntLinked other = (StackIntLinked)o;

            boolean continueCheck = this.size() == other.size();

            NodeInt n1 = this.top;
            NodeInt n2 = other.top;

            while( continueCheck && n1 != null ) {
                
                continueCheck = n1.getValue() == n2.getValue();

                n1 = n1.getNext();
                n2 = n2.getNext();
            }

            return continueCheck;

        } else {
            return false;
        }
    }

    public StackIntLinked clone()
    {
        StackIntLinked s = new StackIntLinked();

        // myClone( this.top, s );

        NodeInt n = this.top;

        s.top = new NodeInt( this.top.getValue() );
        NodeInt ns = s.top;

        n = n.getNext();

        while( n != null ) {
            ns.setNext( new NodeInt( n.getValue() ) );
            ns = ns.getNext();
            n  = n.getNext();
        }
        s.size = this.size;

        return s;
    }
    private void myClone( NodeInt current, StackIntLinked s )
    {
        if ( current == null ) {
            return;
        } else {
            myClone( current.getNext(), s );
            
            // s.push( current.getValue();

            s.top = new NodeInt( current.getValue(), s.top );
            s.size++; 
        }
    }
}