package linear;

import java.util.*;

public class ListIntLinked
{
    private NodeInt first;
    private NodeInt last;
    private NodeInt cursor;
    private int     size;


    public ListIntLinked()
    {
        size = 0;
        cursor = null;
        first = null;
        last = null;
    }

    public boolean empty() // T(n) \in \Theta(1)
    {
        return size() == 0;
    }
    public boolean isEmpty() // T(n) \in \Theta(1)
    {
        return size() == 0;
    }
    public int search( int x ) // T(n) \in O(n)
    {
        NodeInt node = first;
        int position = 0;

        while( node != null ) {
            if ( node.getValue() == x ) return position;

            ++position;
            node = node.getNext();
        }

        return -1;
    }
    public int size() // T(n) \in Theta(1)
    {
        return size;
    }
    public void begin() // T(n) \in Theta(1)
    {
        cursor = isEmpty() ? null : first;
    }
    public void end() // T(n) \in Theta(1)
    {
        cursor = last;
    }
    public void next() // T(n) \in Theta(1)
        throws NoSuchElementException
    {
        if ( cursor == null )
            throw new NoSuchElementException( "What are you doing Dave?" );

        cursor = cursor.getNext();
    }
    public void previous() // T(n) \in Theta(1)
        throws NoSuchElementException
    {
        if ( cursor == null )
            throw new NoSuchElementException( "What are you doing Dave?" );

        cursor = cursor.getPrevious();
    }
    public boolean isEnd() // T(n) \in \Theta(1)
    {
        return cursor == null;
    }
    public int get() // T(n) \in \Theta(1)
        throws NoSuchElementException
    {
        if ( cursor == null )
            throw new NoSuchElementException( "What are you doing Dave?" );

        return cursor.getValue();
    }
    public void remove() // T(n) \in Theta(n)
        throws NoSuchElementException
    {
        if ( cursor == null )
            throw new NoSuchElementException( "What are you doing Dave?" );

        if ( 1 == size() ) { // first is equal to last
            first = last = cursor = null;
            size = 0;
        } else if ( cursor == first ) {
            first = cursor = first.getNext();
            first.setPrevious(null);
            --size;
        } else if ( cursor == last ) {
            last = cursor = last.getPrevious();
            last.setNext(null);
            --size;
        } else { // in the middle
            cursor.getNext().setPrevious( cursor.getPrevious() );
            cursor.getPrevious().setNext( cursor.getNext() );
            cursor = cursor.getNext();
            --size;
        }
    }
    public void insert( int x ) // T(n) \in Theta(1)
    {
        if ( isEmpty() ) {

            first = last = new NodeInt( x );
            size = 1;

        } else if ( cursor == null ) {

            NodeInt node = new NodeInt( last, x, null );
            last.setNext( node );
            last = node;
            ++size;

        } else {
            // Here we are in the case when cursor is not null

            NodeInt node = new NodeInt( cursor.getPrevious(), x, cursor ); // (1,2,2.b)

            if ( node.getPrevious() != null )
                node.getPrevious().setNext( node );
            else
                first = node;
                
            node.getNext().setPrevious( node );
            ++size;
            cursor = node;
        }
    }

    @Override
    public String toString()
    {
        StringBuffer sb = new StringBuffer();

        sb.append( "{" );
        for( NodeInt node = first; node != null ; node = node.getNext() ) {
            if ( node == first ) {
                sb.append( " " + node.getValue() );
            } else {
                sb.append( ", " + node.getValue() );
            }
        }
        sb.append( " }" );

        return sb.toString();
    }

    @Override
    public boolean equals( Object o )
    {
        if ( o instanceof ListIntLinked ) {

            ListIntLinked other = (ListIntLinked)o;

            if  ( this.size == other.size ) {

                NodeInt n1 = this.first;
                NodeInt n2 = other.first;

                while( n1 != null && n1.getValue() == n2.getValue() ) {
                    n1 = n1.getNext();
                    n2 = n2.getNext();
                }

                return null == n1;

            } else {
                return false;
            }

        } else {
            return false;
        }
    }

    public void sort()
    {
        if ( size() <= 1 ) return;

        NodeInt temp = mergeSort( first );

        first = temp; // Update the reference to the first element
        last = temp;  // Update the reference to the last element by traversing the list
        while( last != null && last.getNext() != null ) last=last.getNext();
    }
    private NodeInt mergeSort( NodeInt p )
    {
        if ( p == null || p.getNext() == null ) return p; // Trivial case

        NodeInt left  = null, firstLeft  = null;
        NodeInt right = null, firstRight = null;

        int i=0;
        // Do the split
        while( p != null ) {

            if ( (i % 2) == 0 ) {
                if ( left != null ) {
                    left.setNext( p );
                    p.setPrevious( left );
                } else {
                    firstLeft = p;
                    p.setPrevious(null);
                }
                left = p;
                p = p.getNext();
                left.setNext(null);
            } else {
                if ( right != null ) {
                    right.setNext( p );
                    p.setPrevious( right );
                } else {
                    firstRight = p;
                    p.setPrevious(null);
                }
                right = p;
                p = p.getNext();
                right.setNext(null);
            }

            i++;
        }

        left  = mergeSort( firstLeft );
        right = mergeSort( firstRight );

        return naturalMerge( left, right );
    }
    private NodeInt naturalMerge( NodeInt l, NodeInt r ) 
    {
        if ( l == null ) return r;
        if ( r == null ) return l;

        NodeInt f = null,
                c = null,
                t = null;

        while( l != null && r != null ) {
            
            if ( l.getValue() <= r.getValue() ) {
                // Extract from l
                t = l;
                l = l.getNext();
                if ( l != null ) l.setPrevious(null);
            } else {
                // Extract from r
                t = r;
                r = r.getNext();
                if ( r != null ) r.setPrevious(null);
            }
            t.setNext(null);

            // Connect the node to the new linked sequence
            if ( f == null ) {
                // If it was empty
                f = c = t; // No comment, this is trivial
            } else {
                // If it was not empty
                c.setNext( t ); // Connect the last till now with the new element to be inserted
                t.setPrevious( c ); // Connect the new element to be inserted with the last till now
                c = t; // Set c to be the new last
            }
        }

        if ( l != null ) {
            c.setNext(l); l.setPrevious(c);
        } else if ( r != null ) {
            c.setNext(r); r.setPrevious(c);
        }

        return f;
    }
}