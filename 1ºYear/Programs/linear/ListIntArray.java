package linear;

import java.util.*;

public class ListIntArray
{
    private int [] data;
    private int    size;
    private int    cursor; // The interest point


    public ListIntArray()
    {
        this( 100 ); // calls the other constructor with a value for the parameter 'initialSize'
    }
    public ListIntArray( int initialSize )
    {
        data = new int [initialSize];
        size = 0;
        cursor = -1;
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
        int position = -1;

        for( int i=0; i < size && position < 0; i++ )
            if ( x == data[i] ) position=i;

        return position;
    }
    public int size() // T(n) \in Theta(1)
    {
        return size;
    }
    public void begin() // T(n) \in Theta(1)
    {
        cursor = isEmpty() ? -1 : 0;
    }
    public void end() // T(n) \in Theta(1)
    {
        cursor = size-1;
    }
    public void next() // T(n) \in Theta(1)
    {
        ++cursor;
    }
    public void previous() // T(n) \in Theta(1)
    {
        --cursor;
    }
    public boolean isEnd() // T(n) \in \Theta(1)
    {
        return cursor >= size;
    }
    public int get() // T(n) \in Theta(1)
        throws NoSuchElementException
    {
        if ( cursor < 0 || cursor >= size )
            throw new NoSuchElementException( "What are you doing Dave?" );

        return data[cursor];
    }
    public void remove() // T(n) \in Theta(n)
        throws NoSuchElementException
    {

        if ( cursor < 0 || cursor >= size )
            throw new NoSuchElementException( "What are you doing Dave?" );

        for( int i=cursor+1; i < size; i++ ) data[i-1] = data[i];
        // for( int i=cursor; i < size-1; i++ ) data[i] = data[i+1];

        data[--size] = 0; // data[--size] = null; when working with objects

        if ( size == cursor ) --cursor;
        
    }
    public void insert( int x ) // T(n) \in Theta(n)
        throws NoSuchElementException
    {
        if ( size() == data.length ) {
            int [] newData = new int [ data.length + 100 ];

            for( int i=0; i < data.length; i++ ) newData[i] = data[i];

            data = newData;
        }

        if ( cursor < 0 || cursor >= size ) {

            data[size++] = x;

        } else {
            
            for( int i=size; i > cursor; i-- ) data[i] = data[i-1];
            data[cursor] = x;

            ++size;
        }
    }

    @Override
    public String toString()
    {
        StringBuffer sb = new StringBuffer();

        for( int i=0; i < size; i++ ) sb.append( " " + data[i] );

        return sb.toString();
    }

    @Override
    public boolean equals( Object o )
    {
        if ( o instanceof ListIntArray ) {

            ListIntArray other = (ListIntArray)o;

            boolean stillEqual = this.size == other.size;
            for( int i=0; stillEqual && i < this.size; i++ )
                stillEqual = this.data[i] == other.data[i];

            return stillEqual;
        } else {
            return false;
        }
    }
}