package librerias.estructurasDeDatos.lineales;

/** Clase friendly que representa un Nodo de una LEG
 *  TIENE UN:
 *  - dato, el elemento que contiene el Nodo
 *  - siguiente, la referencia al siguiente Nodo de la LEG
 *  
 *  @version Febrero 2019
 *  @param <E> tipo de datos de la estructura
 */

class NodoLEG<E> {
    
    protected E dato;
    protected NodoLEG<E> siguiente;
   
    /** Crea un NodoLEG que contiene al Elemento e  
     *  y al que sigue el NodoLEG s
     *  
     *  @param e un Elemento de tipo generico E
     *  @param s un NodoLEG
     */
    NodoLEG(E e, NodoLEG<E> s) {
        this.dato = e;
        this.siguiente = s;
    }
   
    /** Crea un NodoLEG que contiene al Elemento e  
     *  y al que NO sigue ninguno
     *  
     *  @param e un Elemento de tipo generico E
     */
    NodoLEG(E e) { this(e, null); } 
}