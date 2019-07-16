package pract5;

/**
 * Class StringSet.
 * Implementation of a set of strings by means of a
 * linked sequence as internal representation.
 * The linked sequence is maintained sorted
 * lexicographically.
 *
 * @author (PRG. ETSINF. UPV)
 * @version (Academic year 2017/2018)
 */
public class StringSet
{
    private StringNode first;
    private int size;
    
    /**
     * Creates an empty set.
     */
    public StringSet()
    {
        first = null;
        size = 0;
    }

    /**
     * Adds <code>s</code> into the set.
     * If <code>s</code> is already in the set,
     * the set remains untouched.
     *
     * @param s String. Element to be inserted.
     */
    public void add( String s )
    {
        StringNode current = this.first;
        StringNode previous = null;
        int rc = -1;
    
        while(current != null && rc < 0){
            rc = current.getData().compareTo(s);
            
            if (rc < 0){
                previous = current;
                current = current.getNext();
            }
        }
        if (rc != 0){
            if (previous != null) {
                previous.setNext(new StringNode(s, current));
            }
            else this.first = new StringNode(s, current);
            
            size++;
        } 
    }
    
    /**
     * Checks if <code>s</code> belongs to the set.
     *
     * @param s String.
     * @return true iff <code>s</code> is in the set.
     */
    public boolean contains( String s )
    {
        StringNode current = this.first;
        StringNode previous = null;
        int rc = -1;
    
        while(current != null && rc < 0){
            rc = current.getData().compareTo(s);
            
            if (rc < 0){
                previous = current;
                current = current.getNext();
            }
        }
        
        return rc == 0;
    }

    /**
     * Removes <code>s</code> from the set.
     * If <code>s</code> is not in the set,
     * the set remains untouched.
     *
     * @param s String.
     */
    public void remove( String s )
    {
        StringNode current = this.first;
        StringNode previous = null;
        int rc = -1;
    
        while(current != null && rc < 0){
            rc = current.getData().compareTo(s);
            
            if (rc < 0){
                previous = current;
                current = current.getNext();
            }
        }
        
        if(rc == 0){
            if(previous != null){
                previous.setNext(current.getNext());
            }
            else {
                first = current.getNext();
            }
            size--;
        }
    }
    
    /**
     * Returns the size of the set.
     * @return the size of the set.
     */
    public int size() {
        return size;
    }
    
    /**
     * Returns a new set as the intersection of both
     * this and other.
     *
     * @param other StringSet.
     * @return the intersection as a set.
     */
    public StringSet intersection( StringSet other )
    {
        StringSet result = new StringSet();
        StringNode n1 = this.first; // current node to check of this set
        StringNode n2 = other.first; // current node to check of the other set
        StringNode last = null; // last node of new set, initialised to null
        while( n1 != null && n2 != null ) {
            if(n1.getData().equals(n2.getData())){
                if (last == null){
                    last = new StringNode(n1.getData());
                    result.first = last;
                }
                else {
                    last.setNext(new StringNode(n1.getData()));
                    last = last.getNext();
                }
                
                result.size++;
                
                n1 = n1.getNext();
                n2 = n2.getNext();
            }
            else {
                if (n1.getData().compareTo(n2.getData()) < 0) n1 = n1.getNext();
                else n2 = n2.getNext();
            }
        }
        
        return result;
    }
    
    /**
     * Returns the union of both sets, this and other.
     *
     * @param other StringSet.
     * @return the union of both sets.
     */
    public StringSet union( StringSet other )
    {
        StringSet result = new StringSet();
        StringNode n1 = this.first; // current node to check of this set
        StringNode n2 = other.first; // current node to check of the other set
        StringNode last = null; // last node of new set, initialised to null
        while( n1 != null && n2 != null ) {
            if(n1.getData().equals(n2.getData())){
                if (last == null){
                    last = new StringNode(n1.getData());
                    result.first = last;
                }
                else {
                    last.setNext(new StringNode(n1.getData()));
                    last = last.getNext();
                }
                
                n1 = n1.getNext();
                n2 = n2.getNext();
            }
            else {
                if (n1.getData().compareTo(n2.getData()) < 0) {
                    if (last == null){
                        last = new StringNode(n1.getData());
                        result.first = last;
                    }
                    else{
                        last.setNext(new StringNode(n1.getData()));
                        last = last.getNext();
                    }
                    
                    n1 = n1.getNext();
                }
                else {
                    if (last == null){
                        last = new StringNode(n2.getData());
                        result.first = last;
                    }
                    else{
                        last.setNext(new StringNode(n2.getData()));
                        last = last.getNext();
                    }
                    
                    n2 = n2.getNext();
                }
            }
            
            result.size++;
        }
        
        while (n1 == null && n2 != null) {
            if (last == null){
                last = new StringNode(n2.getData());
                result.first = last;
            }
            else{
                last.setNext(new StringNode(n2.getData()));
                last = last.getNext();
            }
                    
            n2 = n2.getNext();
            result.size++;
        }
        
        while (n2 == null && n1 != null) {
            if (last == null){
                last = new StringNode(n1.getData());
                result.first = last;
            }
            else{
                last.setNext(new StringNode(n1.getData()));
                last = last.getNext();
            }
                    
            n1 = n1.getNext();
            result.size++;
        }
        
        return result;
    }
    
    /**
     * Returns the list of strings in the set in lexicographical order
     * and each word in a line.
     * If the empty set returns an empty string.
     *
     * @return list of all the words in the set.
     */
    public String toString()
    {
        StringBuffer result = new StringBuffer();
        StringNode current = this.first;
        while( current != null ) {
            result.append( current.getData() );
            result.append( "\n" );
            current = current.getNext(); 
        }
        return result.toString();
    }
}
