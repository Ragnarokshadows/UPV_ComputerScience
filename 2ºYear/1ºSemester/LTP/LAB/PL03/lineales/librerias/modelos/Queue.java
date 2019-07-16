package PL03.lineales.librerias.modelos;

/**
 * interface Queue<T>
 * it defines the TAD of a generic queue
 * 
 * @author LTP 
 * @version 2018-19
 * @param <T>
 */

public interface Queue<T> {
    // Methods that change the queue' state
    /** Inserts the element at the end of the queue
     *  @param e element to be inserted 
     */
    void enqueue(T e);
    
    /** Queries and extracts the first element,
     *  only if the queue is not empty 
     *  @return the first element
     */
    T dequeue();
    
    // Methods that query the queue' state
    /** Queries the number of elements of the queue
     *  @return the number of elements
     */
    int size();
    
    /** Queries the first element, in order of insertion,
     *  only if the queue is not empty 
     *  @return the first element
     */
    T first();
    
    /** Verifies if the queue is empty
     *  @return true iif the queue is empty
     */
    boolean isEmpty();
}