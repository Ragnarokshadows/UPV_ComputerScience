package linear;

import java.util.*;
import linear.NodeInt;

/**
 * Class StackIntLinked: Stack of data type int. Linked implementation.
 */
public class StackIntLinked
{
    private NodeInt top;
    private int     size;
    
    /**
     * Creates an empty stack.
     */
    public StackIntLinked() // T(n) \in \Theta(1)
    {
        size=0;        
        top=null;
    }

    /**
     * Puts the value on the top of the stack.
     * @param value, int, the value to be stacked.
     */
    public void push( int value ) // T(n) \in \Theta(1)
    {
        top = new NodeInt( value, top );
        ++size;
    }

    /**
     * Extracts the value on the top of the stack and returns this element.
     * @return int, the value on the top of the stack.
     * @throws Exception if the stack is empty.
     */
    public int pop() // T(n) \in \Theta(1)
        throws Exception
    {
        if ( empty() ) throw new Exception( "Stack underflow!" );

        int temp = top.getValue();

        top = top.getNext();
        --size;

        return temp;
    }

    /**
     * Returns the value on the top of the stack.
     * @return int, the value on the top of the stack.
     * @throws Exception if the stack is empty.
     */
    public int top() // T(n) \in \Theta(1)
        throws Exception
    {
        if ( empty() ) throw new Exception( "Stack underflow!" );

        return top.getValue();
    }

    /**
     * Checks if the stack is empty.
     * @return boolean, true if it is empty, false otherwise.
     */
    public boolean empty() // T(n) \in \Theta(1)
    {
        return 0 == size();
    }

    /**
     * Returns the size of the stack.
     * @return int, the size of the stack.
     */
    public int size() // T(n) \in \Theta(1)
    {
        return size;
    }
}