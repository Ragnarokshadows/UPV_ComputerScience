package test2;

/** 
 * Second Lab Test - Exercise 2 - Turn 3.
 * Class StringSetT3. Implementation of a set of strings 
 * by means of a linked sequence as internal representation.
 * The linked sequence is maintained sorted lexicographically.
 * Simplified version of the StringSet class implemented
 * in lab activity 5.
 * @author PRG
 * @version Academic year 2017/18
 */
public class StringSetT3 {    
    private StringNode first;
    private int size;
    
    /**
     * Creates an empty set.
     */
    public StringSetT3() {
        this.first = null;
        this.size = 0;
    }

    /**
     * Adds <code>s</code> into the set.
     * If <code>s</code> is already in the set,
     * the set remains untouched.
     * @param s String. Element to be inserted.
     */
    public void add(String s) {
        StringNode aux = this.first;
        StringNode prev = null;
        int compare = -1; 
        while (aux != null && compare < 0) {
            compare = aux.data.compareTo(s); 
            if (compare < 0) {
                prev = aux;
                aux = aux.next;
            } 
        }
        if (compare != 0) { 
            StringNode node = new StringNode(s, aux);
            if (aux == this.first) {
                this.first = node;
            } else { prev.next = node; }
            this.size++;
        }
    }
    
    /**
     * Returns the list of strings in the set in 
     * lexicographical order and each word in a line.
     * If the empty set returns an empty string.
     * @return list of all the words in the set.
     */
    public String toString() {
        String result = "";
        StringNode aux = this.first;
        while (aux != null) {
            result += aux.data + "\n"; 
            aux = aux.next; 
        }
        return result;
    } 
    
    /**
     * Removes the word s from the set, returning as a result 
     * the following word of s.
     * If s does not belong to the set or is the last one 
     * (it has no following), the set does not change and 
     * returns null.
     * @param s String.
     * @return String, the following word of s or null 
     * if it does not exist.
     */
    public String removeAndNext(String s) {        
        String res = null;
        
        StringNode current = this.first;
        StringNode previous = null;
        int rc = -1;
    
        while(current != null && rc < 0){
            rc = current.data.compareTo(s);
            
            if (rc < 0){
                previous = current;
                current = current.next;
            }
        }
        
        if(rc == 0){
            if (current.next != null){
                if(previous != null){
                    previous.next = current.next;
                }
                else {
                    first = current.next;
                }
                
                res = current.next.data;
                size--;
            }
        }
        
        return res;
    }
}
