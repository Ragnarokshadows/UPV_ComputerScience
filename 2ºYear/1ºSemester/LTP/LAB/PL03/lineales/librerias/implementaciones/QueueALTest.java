package PL03.lineales.librerias.implementaciones;

import static org.junit.Assert.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * class QueueALTest.
 *
 * @author LTP
 * @version 2018-19
 */

public class QueueALTest {    

    QueueAL<Integer> q;
    
    /**
     * Default constructor for test classqACTest
     */
    public QueueALTest() { }

    /**
     * Sets up the test fixture.
     *
     * Called before every test case method.
     */
    @Before
    public void setUp() {
        q = new QueueAL<Integer>();
    }  
    
    @Test
    public void firstTest() {   
        assertEquals(q.size(), 0);
        for (int i = 0; i < 15; i++) {
            q.enqueue(new Integer(i));
            assertEquals(q.first(), new Integer(0));
        }
    }
    
    @Test
    public void isEmptyTest() {   
        assertEquals(q.size(), 0);
        assertTrue(q.isEmpty());
        q.enqueue(new Integer(1));
        assertEquals(q.size(), 1);
        assertFalse(q.isEmpty());
        q.enqueue(new Integer(2));
        assertFalse(q.isEmpty());
        q.dequeue();
        q.dequeue();
        assertTrue(q.isEmpty());
        assertEquals(q.size(), 0);
    }
    
    @Test
    public void simpleTest() {   
        assertEquals(q.size(), 0);
        q.enqueue(new Integer(1));
        assertEquals(q.size(), 1);
        assertEquals(q.first(), new Integer(1));
        q.enqueue(new Integer(2));
        assertEquals(q.size(), 2);
        assertEquals(q.first(), new Integer(1));
        assertEquals(q.dequeue(), new Integer(1));
        assertEquals(q.dequeue(), new Integer(2));
        assertEquals(q.size(), 0);      
    }
    
    @Test
    public void enqueueTest() {   
        assertEquals(q.size(), 0);
        // sabemos que el Max es 10, asi que enqmos 15 elementos
        for (int i = 0; i < 15; i++)  q.enqueue(new Integer(i));
        assertEquals(q.size(), 15);
        assertEquals(q.dequeue(), new Integer(0));
        assertEquals(q.dequeue(), new Integer(1));
        assertEquals(q.dequeue(), new Integer(2));
        assertEquals(q.dequeue(), new Integer(3));
        assertEquals(q.dequeue(), new Integer(4));
        assertEquals(q.dequeue(), new Integer(5));
        assertEquals(q.dequeue(), new Integer(6));
        assertEquals(q.dequeue(), new Integer(7));
        assertEquals(q.dequeue(), new Integer(8));
        assertEquals(q.dequeue(), new Integer(9));
        assertEquals(q.dequeue(), new Integer(10));
        assertEquals(q.dequeue(), new Integer(11));
        assertEquals(q.dequeue(), new Integer(12));
        assertEquals(q.dequeue(), new Integer(13));
        assertEquals(q.dequeue(), new Integer(14));
        assertEquals(q.size(), 0);     
    }
    
    @Test
    public void dequeueTest() {
        for (int i = 0; i < 15; i++) {
            q.enqueue(new Integer(i));
        }
        for (int i = 0; i < 15; i++) {
            assertEquals(q.dequeue(), new Integer(i));
        }
    }
   
    /**
     * Tears down the test fixture.
     *
     * Called after every test case method.
     */
    @After
    public void tearDown() { }
}