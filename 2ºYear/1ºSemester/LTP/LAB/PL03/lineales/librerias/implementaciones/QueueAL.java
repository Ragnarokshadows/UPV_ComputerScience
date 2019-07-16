package PL03.lineales.librerias.implementaciones;

import PL03.lineales.librerias.modelos.Queue;
import java.util.ArrayList;

/**
 * class QueueAL<T>
 * It is the implementation of a Queue<T> 
 * using a generic ArrayList.
 * 
 * @author LTP 
 * @version 2018-19
 * @param <T>
 */

public class QueueAL<T> implements Queue<T> {
    ArrayList<T> theArray;
    
    /** The object constructor */
    public QueueAL() {
        theArray = new ArrayList<T>();  
    } 

    /** It returns the content of the queue 
     *  according to the following format:
     *   <- item0 <- item1 <- item2 <- ... <- itemN 
     *  where N = size() - 1
     *  and each item is concatenated 
     *  according to the format defined in its type
     *  @return the content of the queue 
     */
    public String toString() {
        String s = "";
        for ( T e : theArray ) { 
            s += " <- " + e; 
        }
        /** An alternative loop */
        // for (int k = 0; k < size(); k++) {
        //     s += " <- " + theArray.get(k);
        // }
        return s;
    }
  
    // Implementation of the operations defined 
    // in the Queue<T> interface

    // Methods that change the queue' state
    /** Inserts the element at the end of the queue
     *  @param e element to be inserted 
     */  
    public void enqueue(T e) {
        theArray.add(e);
    } 
  
    /** Queries and extracts the first element,
     *  only if the queue is not empty 
     *  @return the first element
     */
    public T dequeue() {
        if (!theArray.isEmpty()){
            return theArray.remove(0);
        }
        else {
            return null;
        }
    } 
  
    // Methods that query the queue' state
    /** Queries the number of elements of the queue
     *  @return the number of elements
     */
    public int size() {
        return theArray.size();
    } 
  
    /** Queries the first element, in order of insertion,
     *  only if the queue is not empty 
     *  @return the first element
     */
    public T first() {
        return theArray.get(0);
    } 
  
    /** Verifies if the queue is empty
     *  @return true iif the queue is empty
     */
    public boolean isEmpty() {
        return theArray.isEmpty(); 
    } 
}