package librerias.estructurasDeDatos.lineales;

import librerias.estructurasDeDatos.modelos.ListaConPI;

/** Implementa la interfaz ListaConPI mediante una LEG...
 *  (a) Con Nodo ficticio cabecera 
 *  (b) Una referencia al primer Nodo  
 *  (c) Una referencia al ultimo Nodo
 *  (d) Para representar el Punto de Interes, una referencia 
 *      al Nodo anterior al que ocupa el punto de interes
 *  (e) Un int talla que representa la talla de la LEG
 *
 *  @version Febrero 2019
 *  @param <E> tipo de datos de la estructura
 */ 

public class LEGListaConPI<E> implements ListaConPI<E> {
    
    protected NodoLEG<E> pri, ant, ult; 
    protected int talla;
    
    /** Crea una ListaConPI vacia */
    public LEGListaConPI() {
        pri = new NodoLEG<E>(null);
        ult = pri; ant = pri;
        talla = 0;
    }
    
    /** Inserta e en una ListaConPI antes del Elemento  
     *  que ocupa su PI, que permanece INalterado
     *  
     *  @param e un Elemento de tipo generico E
     */
    public void insertar(E e) {
        NodoLEG<E> nuevo = new NodoLEG<E>(e); talla++;
        // Se inserta nuevo DETRAS de ant,
        // i.e. ANTES del Dato que ocupa el PI
        nuevo.siguiente = ant.siguiente;
        ant.siguiente = nuevo;
        // OJO: al insertar en fin de Lista... actualizar ult!
        if (ant  ==  ult) { ult = nuevo; } // equivale a: ult = ant.siguiente; 
        // OJO: tras la insercion el PI de la Lista permanece inalterado
        ant = nuevo; // equivalentemente, ant = ant.siguiente;
    }
    
    /** SII !esFin(): 
     *  Elimina de una ListaConPI el Elemento  
     *  que ocupa su PI, que permanece INalterado */  
    public void eliminar() {
        talla--;
        // OJO: al eliminar el ultimo Elemento... actualizar ult!; 
        // el PI esta en fin()
        if (ant.siguiente == ult)  { ult = ant; }
        ant.siguiente = ant.siguiente.siguiente;
    }
    
    /** Situa el PI de una Lista en su inicio,   
     *  sobre su primer Elemento si no esta vacia */
    public void inicio() { ant = pri; }
    
    /** SII !esFin(): 
     *  Situa sobre el siguiente Elemento el PI de una Lista */
    public void siguiente() { ant = ant.siguiente; }
    
    /** Situa el PI de una Lista en su fin, detras de su ultimo 
     *  Elemento si no esta vacia */
    public void fin() { ant = ult; }
    
    /** SII !esFin(): 
     *  Devuelve el Elemento que ocupa el PI de una Lista
     *  
     *  @return E, el Elemento que ocupa el PI de una Lista
     */
    public E recuperar() { return ant.siguiente.dato; }
    
    /** Comprueba si el PI de una Lista esta en su fin
     * 
     *  @return true si el PI de la Lista esta en su fin 
     *          y false en caso contrario
     */
    public boolean esFin() { return ant == ult; }
    
    /** Comprueba si una ListaConPI esta vacia
     * 
     *  @return true si la ListaConPI esta vacia 
     *          y false en caso contrario
     */
    public boolean esVacia() { return pri == ult; }
    
    /** Devuelve la talla de una ListaConPI
     * 
     *  @return int, el numero de Elementos de la Lista
     */
    public int talla() { return talla; }
    
    /** Devuelve el String con los Elementos de una ListaConPI 
     *  en orden de insercion
     *  
     *  @return String que contiene los Elementos de la Lista,  
     *                 en el formato del estandar de Java
     */
    public String toString() {
        // NOTA: se usa la clase StringBuilder, en lugar de String, 
        // por motivos de eficiencia 
        StringBuilder s = new StringBuilder();
        s.append("[");
        NodoLEG<E> aux = pri.siguiente; 
        for (int i = 0, j = talla - 1; i < j; i++, aux = aux.siguiente) {
            s.append(aux.dato.toString() + ", ");
        }
        if (talla != 0) { 
            s.append(aux.dato.toString() + "]"); 
        }
        else { s.append("]"); }
        return s.toString();
    }
}
