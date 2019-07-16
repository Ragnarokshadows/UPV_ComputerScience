package librerias.estructurasDeDatos.grafos;

import librerias.estructurasDeDatos.modelos.ListaConPI; 
import librerias.estructurasDeDatos.lineales.LEGListaConPI;

/** Implementacion de un grafo Dirigido (Ponderado o no) mediante 
 *  Listas de Adyacencia.<br>
 * 
 * @version Diciembre 2019
 */
public class GrafoDirigido extends Grafo { 
    
    protected int numV, numA;
    protected ListaConPI<Adyacente>[] elArray;
    
    /** Crea un grafo Dirigido vacio con nV vertices. 
     *  @param nV  Numero de vertices del grafo
     */
    @SuppressWarnings("unchecked")
    public GrafoDirigido(int nV) {
        super(true);
        numV = nV; numA = 0;
        elArray = new ListaConPI[numV]; 
        for (int i = 0; i < numV; i++) {
            elArray[i] = new LEGListaConPI<Adyacente>();
        }
    }
    
    /** Devuelve el numero de vertices de un grafo. 
      *  @return  int Numero de vertices
     */
    public int numVertices() { return numV; }

    /** Devuelve el numero de aristas de un grafo
      * @return     Numero de aristas 
     */
    public int numAristas() { return numA; }
    
    /** Comprueba si la arista (i,j) esta en un grafo.
      * @param i    Vertice origen
      * @param j    Vertice destino
      * @return boolean true si (i,j) esta y false en caso contrario
     */    
    public boolean existeArista(int i, int j) {
        ListaConPI<Adyacente> l = elArray[i]; 
        boolean esta = false;
        for (l.inicio(); !l.esFin() && !esta; l.siguiente()) {
            if (l.recuperar().destino == j) { esta = true; }
        }
        return esta;   
    }
    
    /** Devuelve el peso de la arista (i,j) de un grafo, 0 si dicha arista 
      * no esta en el grafo.
      * @return double Peso de la arista (i,j), 0 si no existe
     */
    public double pesoArista(int i, int j) {
        ListaConPI<Adyacente> l = elArray[i];
        for (l.inicio(); !l.esFin(); l.siguiente()) {
            if (l.recuperar().destino == j) {
                return l.recuperar().peso;
            }
        }
        return 0.0;
    }
    
    /** Si no esta, inserta la arista (i, j) en un grafo no Ponderado 
      * (al final de la Lista de adyacentes a i).
      * @param i    Vertice origen
      * @param j    Vertice destino
     */       
    public void insertarArista(int i, int j) {
        if (!existeArista(i, j)) { 
            elArray[i].insertar(new Adyacente(j, 1)); 
            numA++; 
        }
    }

    /** Si no esta, inserta la arista (i, j) de peso p en un grafo Ponderado 
      * (al final de la Lista de adyacentes a i).
      * @param i    Vertice origen
      * @param j    Vertice destino
      * @param p    Peso de (i, j)
     */  
    public void insertarArista(int i, int j, double p) {
        if (!existeArista(i, j)) { 
            elArray[i].insertar(new Adyacente(j, p)); 
            numA++; 
        }
    }
        
    /** Devuelve una Lista Con PI que contiene los adyacentes al vertice i.
      * @param i Vertice del que se obtienen los adyacentes
      * @return ListaConPI con los vertices adyacentes a i
     */ 
    public ListaConPI<Adyacente> adyacentesDe(int i) {
        return elArray[i];
    }
}

