package librerias.estructurasDeDatos.modelos;

/**
 * Modelo de una Cola de Prioridad, o de Busqueda Dinamica 
 * del Elemento de maxima prioridad en una Coleccion
 * 
 * @version Febrero 2018
 * @param <E> tipo de datos de la estructura,  
 * RESTRINGIDO POR Comparable 
 */

public interface ColaPrioridad<E extends Comparable<E>> {

    // Metodos Modificadores del estado de una Cola de Prioridad (CP)
    /** Atendiendo a su prioridad, inserta el Elemento e en una CP */
    void  insertar(E e);
    /** SII !esVacia(): 
     *  obtiene y elimina el Elemento con maxima prioridad de una CP */
    E  eliminarMin();
    
    // Metodos Consultores del estado de una Cola de Prioridad (CP)
    /** SII !esVacia(): 
     *  obtiene el Elemento con maxima prioridad de una CP */
    E  recuperarMin();
    /** Comprueba si una CP esta vacia */
    boolean esVacia();
}
