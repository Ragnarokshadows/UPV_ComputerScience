package aplicaciones.impresora;

/** Clase Trabajo: representa un trabajo a imprimir.
 *  ATRIBUTOS: 
 *      TIENE UN titulo (String)
 *      TIENE UN numero de paginas (int)
 *      TIENE UN instante de envio a imprimir en segundos (int)
 *      
 *  @author (profesores EDA)
 *  @version (Curso 2018-2019)
 */
 
public class Trabajo implements Comparable<Trabajo>{
    
    private String titulo;
    private int numPaginas;
    private int envio;
    
    /** Crea un Trabajo de titulo t, numero de paginas n
     *  y enviado a imprimir en el intante de envio e.
     *  @param t    String
     *  @param n    int
     *  @param e    int (seg.)
     */
    public Trabajo(String t, int n, int e) {
        titulo = t;
        numPaginas = n;
        envio = e;
    }
    
    /** Devuelve el titulo de un Trabajo.
     *  @return String
     */
    public String getTitulo() { return titulo; }
    
    /** Devuelve el numero de paginas de un Trabajo.
     *  @return int
     */
    public int getNumPaginas() { return numPaginas; }
    
    /** Devuelve el instante de envio a impresion de un Trabajo.
     *  @return int (seg.)
     */
    public int getEnvio() { return envio; }
    
    /** Devuelve el valor int que resulta de comparar el numero de
     *  paginas de un documento (this) con las de otro. Dicho valor
     *  sera...
     *  ** NEGATIVO si un documento (this) tiene MENOS paginas que el otro,
     *     i.e. si su impresion es MAS prioritaria que la del otro
     *  ** POSITIVO si un documento (this) tiene MAS paginas que otro, 
     *     i.e. si su impresion es MENOS prioritaria que la del otro
     *  ** CERO si ambos documentos tienen el mismo numero de paginas
     *  @param otro  Trabajo  
     *  @return int resultado de la comparacion de un documento (this) con otro 
     */
    public int compareTo(Trabajo otro) { 
        /*COMPLETAR*/
        int res = res = this.numPaginas - otro.numPaginas;
        
        return res;
    }
    
    /** Devuelve el String que representa un Trabajo en un cierto 
     *  formato texto.
     *  @return String
     */
    public String toString() {
        return titulo + " (" + numPaginas + " pag.) Envio: " + envio;
    }
}
