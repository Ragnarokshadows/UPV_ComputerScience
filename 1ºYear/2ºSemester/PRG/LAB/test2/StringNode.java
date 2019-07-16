package test2;

/**
 * Class StringNode.
 *
 * @author PRG
 * @version Academic year 2017/18
 */
public class StringNode {    
    protected String data;
    protected StringNode next; 
 
    /** 
     * Creates a node with value <code>s</code> and 
     * next set to null.
     * @param s String. The value for the new node.
     */
    StringNode(String s) {  
        this.data = s;
        this.next = null;
    } 
    
    /** 
     * Creates a node with value <code>s/code> and 
     * next set to <code>n</code>.
     * @param s String. The value for the new node.
     * @param n StringNode. Next node in the sequence.
     */
    StringNode(String s, StringNode n) {
        this.data = s;
        this.next = n;
    }        
}
