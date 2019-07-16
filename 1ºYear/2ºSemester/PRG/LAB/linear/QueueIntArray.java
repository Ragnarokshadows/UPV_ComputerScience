package linear;

import java.util.*;

public class QueueIntArray
{
    private int []  data;
    private int     size;
    private int     first;
    private int     last;

    public QueueIntArray()
    {
        this( 100 );
    }
    public QueueIntArray( int initialSize )
    {
        size = 0;
        data = new int [initialSize];
    }

    public boolean add( int value )
        throws IllegalStateException
    {
        if ( size == data.length ) throw new IllegalStateException( "Full queue!" );

        last = (last+1) % data.length;
        data[last] = value;

        ++size;

        return true;
    }
    public boolean offer( int value )
    {
        if ( size == data.length ) return false;

        last = (last+1) % data.length;
        data[last] = value;

        ++size;

        return true;
    }
    public int element()
        throws IllegalStateException
    {
        if ( isEmpty() ) throw new IllegalStateException( "Empty queue!" );

        return data[first];
    }
    public int peek()
        throws IllegalStateException
    {
        if ( isEmpty() ) throw new IllegalStateException( "Empty queue!" );

        return data[first];
    }
    public int remove()
        throws IllegalStateException
    {
        if ( isEmpty() ) throw new IllegalStateException( "Empty queue!" );

        int temp = data[first];
        first = (first+1) % data.length;
        --size;
        return temp;
    }
    public int poll()
        throws IllegalStateException
    {
        if ( isEmpty() ) throw new IllegalStateException( "Empty queue!" );

        int temp = data[first];
        first = (first+1) % data.length;
        --size;
        return temp;
    }

    public int size()
    {
        return size;
    }
    public boolean isEmpty()
    {
        return 0 == size();
    }

    @Override
    public boolean equals( Object o )
    {
        if ( o instanceof QueueIntArray ) {
            
            QueueIntArray other = (QueueIntArray)o;

            boolean stillEqual = this.size == other.size;

            for( int i=0; i < this.size && stillEqual; i++ ) {
                stillEqual = this.data[i] == other.data[i];
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

        for( int i = first; i < size; i++ )
            sb.append( " " + data[i] );

        return sb.toString();
    }

    public QueueIntArray clone()
    {
        QueueIntArray newQueue = new QueueIntArray( this.data.length );

        for( int i=0; i < this.size; i++ ) {
            newQueue.data[i] = this.data[ (this.first+i) % this.data.length  ];
        }
        newQueue.size = this.size;
        newQueue.first = 0;
        newQueue.last = this.size-1;

        return newQueue;
    }
}
