package librerias.estructurasDeDatos.lineales;
import librerias.estructurasDeDatos.modelos.ListaConPI;

public class LEGListaConPIOrdenada<E extends Comparable> 
    extends LEGListaConPI<E>
    implements ListaConPI<E> 
{
    public void insertar(E e){
        NodoLEG nuevo = new NodoLEG(e);
        inicio();
        
        while( !esFin() && recuperar().compareTo(nuevo.dato) < 0 ){
            /*
             * Negative if this goes before the argument
             * Zero if the same
             * Positive if this goes after the argument
             */
            siguiente();
        }
        nuevo.siguiente = ant.siguiente;    // join nuevo with element at the PI
        ant.siguiente = nuevo;              // join nuevo with element before PI
        if (ant  ==  ult) { ult = nuevo; }  // Update last element if needed
        ant = nuevo;    // the element we just inserted goes behind the element at the PI
        talla++;        // increase size
    }

}
