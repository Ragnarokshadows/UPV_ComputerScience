package librerias.estructurasDeDatos.modelos;

/**
 * Modelo de una Lista Con Punto de Interes (PI), 
 * o de Acceso Secuencial a los Elementos de una
 * Coleccion
 *  
 * @version Febrero 2019
 * @param <E> tipo de los datos de Lista
 */

public interface ListaConPI<E> {

    // Metodos Modificadores del estado de una Lista
    /** Inserta e en una Lista antes del Elemento que ocupa su PI, 
     *  que permanece inalterado */
    void insertar(E e);
    
    /** SII !esFin(): 
     *  elimina de una Lista el Elemento que ocupa su PI, 
     *  que permanece inalterado */
    void eliminar();
    
    // Metodos Modificadores del estado del PI de una Lista
    /** Situa el PI de una Lista en su inicio */
    void inicio();
    
    /** SII !esFin(): 
     *  avanza el PI de una Lista */
    void siguiente();
    
    /** Situa el PI de una Lista en su fin */
    void fin();
    
    // Metodos Consultores del estado de una Lista
    /** SII !esFin(): 
     *  obtiene el Elemento que ocupa el PI de una Lista */
    E recuperar();
    
    /** Comprueba si el PI de una Lista esta en su fin */
    boolean esFin();
    
    /** Comprueba si una Lista Con PI esta vacia */
    boolean esVacia();
    
    /** Devuelve la talla, o numero de elementos, de una Lista */
    int talla();
}