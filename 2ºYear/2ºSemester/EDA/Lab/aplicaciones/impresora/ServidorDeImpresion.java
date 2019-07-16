package aplicaciones.impresora;

/** Interfaz ServidorDeImpresion: especifica la velocidad de la
 *  impresora asociada a un un servidor de impresion (print server)
 *  y los metodos que este ha de implementar **independientemente**   
 *  de si usa una Cola o una Cola de Prioridad para gestionar los
 *  trabajos en espera que almacena.
 *  
 *  @author (profesores EDA)
 *  @version (Curso 2018-2019)
 */

public interface ServidorDeImpresion {
    // Velocidad de la impresora asociada a un servidor, o
    // numero de paginas por minuto que esta puede imprimir
    // asociada al servidor
    public static final int PAGINAS_POR_MINUTO = 30;
    
    /** Incluye un nuevo trabajo en espera t en un ServidorDeImpresion.
     *  @param t   Trabajo
     */
    void insertar(Trabajo t);
    
    /** Comprueba si hay algun trabajo en espera en un ServidorDeImpresion.
     *  @return boolean
     */
    boolean hayTrabajos();
    
    /** SII hayTrabajos(): devuelve el Trabajo de un ServidorDeImpresion
     *  que va a ser impreso.
     *  @return Trabajo
     */
    Trabajo getTrabajo();
    
   /** SII hayTrabajos(): elimina de un ServidorDeImpresion el 
    *  trabajo que va a ser impreso y devuelve el tiempo que 
    *  este tardara en imprimirse, en base a la velocidad de la impresora.
    *  @return int (seg.)
    */
    int imprimirTrabajo();
}
