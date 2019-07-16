package librerias.estructurasDeDatos.grafos;

/** Clase GrafoNoDirigido: implementacion de un grafo No Dirigido 
 *  (Ponderado o no) mediante Listas de Adyacencia:<br>
 *  un grafo No Dirigido ES UN Grafo Dirigido tal que si la Arista (i, j)  
 *  esta presente en la Lista de Adyacencia de i entonces tambien lo esta
 *  la Arista (j, i) en la de j
 * 
 * @version Diciembre 2019
 */

public class GrafoNoDirigido extends GrafoDirigido {
    
    /** Crea un grafo No Dirigido vacio con numV vertices. 
      * 
      * @param numV  Numero de vertices
     */
    public GrafoNoDirigido(int numV) { 
        super(numV);
        esDirigido = false;
    }
    
    /** Si no esta, inserta la arista (i, j) en un grafo No Dirigido  
      * y No Ponderado; por tanto, tambien inserta la arista (j, i).
      * 
      *  @param i    Vertice origen
      *  @param j    Vertice destino
     */ 
    public void insertarArista(int i, int j) {
        if (!existeArista(i, j)) { 
            elArray[i].insertar(new Adyacente(j, 1)); 
            elArray[j].insertar(new Adyacente(i, 1));
            numA++; 
        }
    }
    
    /** Si no esta, inserta la arista (i, j) de peso p en un grafo  
      * No Dirigido y Ponderado; por tanto, tambien inserta la arista 
      *  (j, i) de peso p.
      *  
      * @param i    Vertice origen
      * @param j    Vertice destino
      * @param p    Peso de (i, j)
     */ 
    public void insertarArista(int i, int j, double p) {
        if (!existeArista(i, j)) { 
            elArray[i].insertar(new Adyacente(j, p)); 
            elArray[j].insertar(new Adyacente(i, p));
            numA++; 
        }
    }
}
