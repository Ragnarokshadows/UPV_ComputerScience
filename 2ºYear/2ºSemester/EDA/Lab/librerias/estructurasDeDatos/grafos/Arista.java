package librerias.estructurasDeDatos.grafos;

/** Clase Arista: representa una arista de un grafo.<br> 
 *  
 *  @version Mayo 2018
 */
 
public class Arista implements Comparable<Arista>{
    
    // UNA Arista TIENE UN vertice origen y UN vertice destino:
    /*COMPLETAR*/
    protected int origen;
    protected int destino;

    // UNA Arista TIENE UN peso:
    /*COMPLETAR*/
    protected double peso;
    
    /** Crea una arista (v, w) con peso p.
      *
      * @param v  Vertice origen
      * @param w  Vertice destino
      * @param p  Peso
     */
    public Arista(int v, int w, double p) {
        // COMPLETAR
        origen = v;
        destino = w;
        peso = p;
    }

    /** Devuelve el vertice origen de una arista.
      *
      * @return int vertice origen
     */
    public int getOrigen() {    
        // COMPLETAR 
        return origen;
    }
    
    /** Devuelve el vertice destino de una arista.
      *
      * @return int vertice destino
     */
    public int getDestino() {  
        // COMPLETAR
        return destino;
    }
    
    /** Devuelve el peso de una arista.
      *
      * @return double Peso de la arista
     */
    public double getPeso() {
        // COMPLETAR 
        return peso;
    }
    
    /** Devuelve un String que representa una arista
      * en formato (origen, destino, peso)
      *
      * @return  String que representa la arista
     */
    public String toString() {
        // COMPLETAR    
        return "(" + origen + ", " + destino + ", " + peso + ")";
    }
    
    public int compareTo(Arista a){ 
        return (int) Math.signum(this.peso - a.peso);
    }
}