package librerias.aplicaciones;

import librerias.implementaciones.*;
import librerias.modelos.*;

public class QueueApp {
    public static void main(String[] args) { 
        Queue<Integer> c = new QueueAC<Integer>();
        for (int i = 1; i <= 15; i++) {
            c.enqueue(i);  
        }
        System.out.println("Initial queue:\n" + c);
        
        int toRemove = 10;
        for (int i = 0; i < toRemove; i++) {
            c.dequeue();
        }
        System.out.println("Queue after dequeing " 
            + toRemove + " items:\n" + c);
     
        int toInsert = 25;
        for (int i = 1; i <= toInsert; i++) {
            c.enqueue(i);
        }
        System.out.println("Queue after queing "
            + toInsert + " items:\n" + c);   
    }
}