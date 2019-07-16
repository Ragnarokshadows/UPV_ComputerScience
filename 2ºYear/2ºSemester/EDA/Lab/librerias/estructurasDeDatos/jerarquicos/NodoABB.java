package librerias.estructurasDeDatos.jerarquicos;

 

/** Clase friendly NodoABB<E>: representa un Nodo de un ABB, por lo que
 *  TIENE UN: 
 *  1.- E dato, que representa el Elemento que ocupa un Nodo de un ABB 
 *  2.- NodoABB<E> izq, un enlace al Hijo Izquierdo de un Nodo de un ABB
 *  3.- NodoABB<E> der, un enlace al Hijo Derecho de un Nodo de un ABB
 *  4.- talla, un entero que representa el tamanyo de un Nodo de un ABB
 *      Un ABB que tiene Nodos con este atributo se denomina ABB con Rango
 *      
 *  @param <E>, el tipo de los datos del ABB
 *  @version Marzo 2017
 */
class NodoABB<E> {
     // atributos
    protected E dato;
    protected int talla;
    protected NodoABB<E> izq;
    protected NodoABB<E> der;
     
    /** Constructor de un nodo sin hijos
      * @param  valor  Dato a almacenar en el nodo
      */
    public NodoABB(E valor) {
        this(valor, null, null);  
    }
     
    /**  Constructor de un nodo con un hijo izquierdo y derecho dado
      * @param  valor Dato a almacenar en el nodo
      * @param  izq   Hijo izquierdo del nodo
      * @param  der   Hijo derecho del nodo
      */
    public NodoABB(E valor, NodoABB<E> izq, NodoABB<E> der) {
        dato = valor; 
        this.izq = izq;
        this.der = der;
        talla = 1;
        if (izq != null) { talla += izq.talla; }
        if (der != null) { talla += der.talla; }
    }
}
