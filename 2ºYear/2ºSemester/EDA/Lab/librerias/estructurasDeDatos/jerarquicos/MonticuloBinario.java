package librerias.estructurasDeDatos.jerarquicos;

import librerias.estructurasDeDatos.modelos.ColaPrioridad;

/** Clase MonticuloBinario: representa un Heap con Raiz en 1.
 * 
 *  Sus caracteristicas son las siguientes:
 *  @param <E>, el tipo de sus elementos es E extends Comparable<E>
 *  ATRIBUTOS (protected para la herencia): 
 *      TIENE UN array con los elementos (E[] elArray)
 *      TIENE UNA talla que indica el numero de elementos
 *      
 * @author (profesores EDA)
 * @version (Curso 2018-2019)
 **/
 
public class MonticuloBinario<E extends Comparable<E>> 
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
    public MonticuloBinario() {
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
    public E recuperarMin() { return elArray[1]; }
    
    /** Inserta el dato e en una CP, atendiendo a su prioridad 
     *  @param e  Elemento a insertar  
     */
    public void insertar(E e) {
        if (talla == elArray.length - 1) { duplicarArray(); }
        int  posActual = ++talla;
        while (posActual > 1 && e.compareTo(elArray[posActual / 2]) < 0) {
            elArray[posActual] = elArray[posActual / 2];
            posActual = posActual / 2;
        }
        elArray[posActual] = e;
    }
    // Duplica la capacidad de elArray
    @SuppressWarnings("unchecked")
    protected void duplicarArray() {
        E[] nuevo = (E[]) new Comparable[elArray.length * 2];
        System.arraycopy(elArray, 1, nuevo, 1, talla);
        elArray = nuevo;
    }  
    
    /** SII !esVacia(): obtiene y borra el dato con 
     *  maxima prioridad de la CP 
     *  @return Elemento con maxima prioridad de la CP
     */
    public E eliminarMin() {
        E elMinimo = recuperarMin();
        elArray[1] = elArray[talla--];
        hundir(1);
        return elMinimo;
    }
    //  Si es necesario, restablece la propiedad de orden 
    //  de un Heap hundiendo el elemento de elArray situado 
    //  en la posicion pos 
    //  @param pos  Posicion del elemento a hundir
    protected void hundir(int pos) {
        int posActual = pos;
        E aHundir = elArray[posActual]; 
        int hijo = posActual * 2; 
        boolean esHeap = false;
        while (hijo <= talla && !esHeap) {
            if (hijo != talla 
                && elArray[hijo + 1].compareTo(elArray[hijo]) < 0) { 
                hijo++; 
            }
            if (elArray[hijo].compareTo(aHundir) < 0) {
                elArray[posActual] = elArray[hijo];
                posActual = hijo; 
                hijo = posActual * 2;
            } else { esHeap = true; }
        }
        elArray[posActual] = aHundir;
    }
}
