package librerias.estructurasDeDatos.modelos;

/** Representa las clases -subconjuntos disjuntos- que define una relacion
 *  de equivalencia en un conjunto de cardinal n 
 *** Los elementos del conjunto se representan mediante enteros en  
 *   el intervalo [0, n-1], pues su tipo concreto NO interviene en 
 *   la defincion de las clases
 *   
 *** Los elementos de una clase se representan mediante uno SOLO de sus 
 *   miembros, cualquiera de ellos, pues todos son equivalentes. Este 
 *   elemento recibe el nombre de identificador de la clase
 *   
 *** Las clases se definen dinamicamente a partir de las n triviales, 
 *   cada una formada por un elemento del conjunto
 *      
 *  Ademas de Union-Find Set, otros nombres que recibe este Modelo son 
 *  Estructura de Particion (Disjoint-Set en Ingles) y Merge-Find Set
 *   
 *  @version Diciembre 2018
 */

public interface UFSet {
    
    /** Devuelve el identificador de la clase de equivalencia  
     *  -subconjunto- al que pertence i, tras realizar una 
     *  compresion de caminos.
     */
    int find(int i);
    
    /** PRECONDICION: claseI != claseJ AND claseI = find(i) && claseJ = find(j)
     *  Une las clases de equivalencia -subconjuntos- con 
     *  identificadores claseI y claseJ mediante combinacion 
     *  por rango.
     */
    void union(int claseI, int claseJ);
}