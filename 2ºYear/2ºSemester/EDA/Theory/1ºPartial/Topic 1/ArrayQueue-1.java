package librerias.estructurasDeDatos.lineales;

import librerias.estructurasDeDatos.modelos.*;

public class ArrayQueue<E> implements Queue<E> {
    protected E theArray[];
    protected static final int DEFAULT_SIZE = 30000;
    protected int finalC, principioC, size;
   
    @SuppressWarnings("unchecked")
    public ArrayQueue(){
        theArray = (E[]) new Object[DEFAULT_SIZE];
        size = 0; principioC = 0; finalC = 0;
    }
    
    public void enqueue(E e){
        if ( talla==theArray.length ) duplicateCircularArray();
        theArray[finalC] = e;
        finalC = increase(finalC); size++;
    }
  
    @SuppressWarnings("unchecked")
    protected void duplicateCircularArray(){
        E newArray[] = (E[]) new Object[theArray.length*2];
        for ( int i=0; i<size; i++, principioC=increase(principioC) )
            newArray[i] = theArray[principioC];
        theArray = newArray; principioC = 0; finalC = size;
    }
    
    protected int increase(int indice){
        if ( ++indice==theArray.length) indice = 0;
        return indice;
    }
    
    public E dequeue(){
        E theFirst = theArray[principioC];
        principioC = increase(principioC); size--;
        return theFirst 
    }
    
    public E first(){ return theArray[principioC]; }
    
    public boolean isEmpty(){ return ( size==0 ); }
    
    public String toString(){
        String res = ""; int aux = principioC;
        for ( int i=0; i<size; i++, aux=increase(aux) )
              res += theArray[aux].toString()+"|";
        return res;
    }

}
