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
        if(empty()){
            return "{}";
        }

        StringBuffer sb = new StringBuffer();
        
        sb.append("{ ");
        NodeInt n1 = this.top;
        sb.append(n1.getValue());
        n1 = n1.getNext();

        while(n1 != null){
            sb.append( ", " + n1.getValue());
            n1 = n1.getNext();
        }

        sb.append(" }");

        return sb.toString();
    }

    @Override
    public boolean equals( Object o )
    {
        if ( o instanceof StackIntLinked ) {
            
            StackIntLinked other = (StackIntLinked)o;

            boolean stillEqual = this.size == other.size;
            
            NodeInt n1 = this.top;
            NodeInt n2 = other.top;

            while(stillEqual && n1 != null){
                stillEqual =  n1.getValue() == n2.getValue(); 
                n1 = n1.getNext();
                n2 = n2.getNext();
            }
            
            return stillEqual;

        } else {
            return false;
        }
    }
}
