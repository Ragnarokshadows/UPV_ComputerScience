package librerias.estructurasDeDatos.modelos;

 

 

/**
 * Modelo Cola, ofrece una gestion FIFO (First In First Out) de la 
 * coleccion de datos
 * 
 * @author Profesores de EDA 
 * @version 2017
 * @param <E> tipo de datos de la estructura
 */

public interface Cola<E> {
    /** Inserta el dato x al final de la cola */
    void encolar(E x);
  
    /** SII !esVacia(): elimina y devuelve el primer dato */
    E desencolar();
  
    /** SII !esVacia(): devuelve el primer dato de la cola */
    E primero();
  
    /** Comprueba si la cola esta o no vacia */
    boolean esVacia();
}
