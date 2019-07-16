package librerias.estructurasDeDatos.jerarquicos;

import librerias.estructurasDeDatos.modelos.ColaPrioridad;


/**
 * Write a description of class MonticuloBinarioR0 here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class MonticuloBinarioR0<E extends Comparable<E>>
    implements ColaPrioridad<E> {
    protected static final int CAPACIDAD_POR_DEFECTO = 10;
    
    // Un Heap es un AB Completo y, por tanto,...
    // TIENE UNA Representacion Implicita
    protected E[] elArray;
    // TIENE UNA talla o numero de nodos
    protected int talla;
    
    /** Crea una Cola de Prioridad (CP) vacia 
     *  con capacidad inicial CAPACIDAD_POR_DEFECTO
     */
    @SuppressWarnings("unchecked")
    public MonticuloBinarioR0() {
        elArray = (E[]) new Comparable[CAPACIDAD_POR_DEFECTO];
        talla = 0;
    }
    
    /** Comprueba si una CP esta vacia o no 
     *  @return boolean que indica si la CP esta vacia
     */
    public boolean esVacia() { return talla == 0; }
        
    /** SII !esVacia(): obtiene el dato con maxima prioridad de la CP 
     *  @return Elemento con maxima prioridad de la CP
     */
    public E recuperarMin() { return elArray[0]; }
    
    /** Inserta el dato e en una CP, atendiendo a su prioridad 
     *  @param e  Elemento a insertar  
     */
    public void insertar(E e) {
        if (talla == elArray.length) { duplicarArray(); }
        int  posActual = talla;
        talla++;
        while (posActual > 0 && e.compareTo(elArray[(posActual - 1) / 2]) < 0) {
            elArray[posActual] = elArray[(posActual - 1)/ 2];
            posActual = (posActual - 1) / 2;
        }
        elArray[posActual] = e;
    }
    // Duplica la capacidad de elArray
    @SuppressWarnings("unchecked")
    protected void duplicarArray() {
        E[] nuevo = (E[]) new Comparable[elArray.length * 2];
        System.arraycopy(elArray, 0, nuevo, 0, talla);
        elArray = nuevo;
    }  
    
    /** SII !esVacia(): obtiene y borra el dato con 
     *  maxima prioridad de la CP 
     *  @return Elemento con maxima prioridad de la CP
     */
    public E eliminarMin() {
        E elMinimo = recuperarMin();
        elArray[0] = elArray[talla-1];
        talla--;
        hundir(0);
        return elMinimo;
    }
    //  Si es necesario, restablece la propiedad de orden 
    //  de un Heap hundiendo el elemento de elArray situado 
    //  en la posicion pos 
    //  @param pos  Posicion del elemento a hundir
    protected void hundir(int pos) {
        int posActual = pos;
        E aHundir = elArray[posActual]; 
        int hijo = posActual * 2 + 1; 
        boolean esHeap = false;
        while (hijo < talla && !esHeap) {
            if (hijo != talla - 1
                && elArray[hijo + 1].compareTo(elArray[hijo]) < 0) { 
                hijo++; 
            }
            if (elArray[hijo].compareTo(aHundir) < 0) {
                elArray[posActual] = elArray[hijo];
                posActual = hijo; 
                hijo = posActual * 2 + 1;
            } else { esHeap = true; }
        }
        elArray[posActual] = aHundir;
    }
}
