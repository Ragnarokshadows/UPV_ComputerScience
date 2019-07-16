package librerias.estructurasDeDatos.lineales;

 

 

import librerias.estructurasDeDatos.modelos.Cola;

/** Implementa el interfaz Cola mediante un array
 * @author (profesores EDA ) 
 * @version 2017
 * @param <E> tipo de datos de la estructura
 */
public class ArrayCola<E> implements Cola<E> {
    // Atributos de Clase   
    protected static final int CAPACIDAD_POR_DEFECTO = 50;
    // Atributos de Instancia
    protected E[] elArray;
    protected int fin, primero, talla;
  
    // Constructor
    @SuppressWarnings("unchecked")
    public ArrayCola() {
        elArray = (E[]) new Object[CAPACIDAD_POR_DEFECTO];
        talla = 0; primero = 0; fin = -1;
    }
    
    /* inserta x al final de la Cola */
    public void encolar(E x) {
        if (talla == elArray.length) { duplicarCola(); }
        fin = incrementa(fin);
        elArray[fin] = x; 
        talla++;
    }
    
    /* SII !esVacia(): elimina el primer dato de la Cola */
    public E desencolar() {
        E elPrimero = elArray[primero];
        primero = incrementa(primero); 
        talla--;
        return elPrimero;
    }
    
    /* SII !esVacia(): devuelve el primer dato de la Cola */
    public E primero() {
        return elArray[primero];
    }

    /* comprueba si la Cola esta o no vacia */
    public boolean esVacia() {
        return (talla == 0);
    }
  
    /* incrementa la posicion de forma circular */
    private int incrementa(int indice) {
        return (indice + 1) % elArray.length;
    //         if (++indice == elArray.length) indice = 0;
    //         return indice;
    }
    
    /** 
     * duplica la capacidad del array
     */ 
    @SuppressWarnings("unchecked")
    private void duplicarCola() {
        E[] nuevo = (E[]) new Object[elArray.length * 2];
        for (int i = 0; i < talla; i++) {
            nuevo[i] = elArray[primero];
            primero = incrementa(primero);
        }
        elArray = nuevo;
        primero = 0;
        fin = talla - 1;
    }
}
