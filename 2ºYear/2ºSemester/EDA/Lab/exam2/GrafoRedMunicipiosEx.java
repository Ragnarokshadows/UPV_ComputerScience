package exam2;

import others.librerias.estructurasDeDatos.grafos.GrafoNoDirigido;
import others.librerias.estructurasDeDatos.grafos.Grafo;
import others.librerias.estructurasDeDatos.grafos.Arista;
import others.librerias.estructurasDeDatos.grafos.Adyacente;
import java.util.Scanner;
import others.librerias.estructurasDeDatos.modelos.ListaConPI;
import others.librerias.estructurasDeDatos.modelos.Map;
import others.librerias.estructurasDeDatos.lineales.LEGListaConPI;
import others.librerias.estructurasDeDatos.deDispersion.TablaHash;
import java.io.File;
import java.io.FileNotFoundException;
import others.aplicaciones.redElectrica.Municipio;

/** GrafoRedMunicipios: grafo etiquetado que representa una red  
 *  electrica interurbana mediante...
 ** Un conjunto de vertices etiquetados por los nombres 
 *  de los municipios de la red 
 ** Un conjunto de aristas etiquetadas por los millones 
 *  de euros que costaria la renovacion del tendido electrico 
 *  entre cada par de municipios de la red (vertices). 
 * 
 * @version (Curso 2018/19)
 */    

public class GrafoRedMunicipiosEx {
    
    public final static String DIR_FICHEROS = "aplicaciones" + File.separator
                                              + "redElectrica" + File.separator;
        
    private static final int MAX_MUNICIPIOS = 5000;
    
    private static final String NO_ACC_MSG = "El fichero con los datos de los "
                                             + "municipios no es accesible "
                                             + "para lectura: comprueba su "
                                             + "correcta ubicaci\u00f3n";
    private static final String NO_FOR_MSG = "Formato no v\u00e1lido en "
                                             + "la l\u00ednea: ";
    private static final String NO_FDIS_MSG = "Fichero con datos de las lineas"
                                              + "no encontrado"; 
                                              
    protected Grafo gRM;   
    protected Map<Integer, Municipio> verticesAMunicipios; 
    protected Map<Municipio, Integer> municipiosAVertices;    
    
    /** Constructor */
    public GrafoRedMunicipiosEx() {
        verticesAMunicipios = new TablaHash<Integer, Municipio>(MAX_MUNICIPIOS);
        municipiosAVertices = new TablaHash<Municipio, Integer>(MAX_MUNICIPIOS);
        gRM = new GrafoNoDirigido(0);
    }
    
    /** Consultor del número de vértices */
    public int numVertices() { return gRM.numVertices(); }
    
    /** Consultor del número de aristas */
    public int numAristas() { return gRM.numAristas(); }
    
    /** Consultor de vértice dado el municipio */
    public int getVertice(Municipio m) {
        Integer vertice = municipiosAVertices.recuperar(m); 
        if (vertice == null) { return -1; }
        return vertice.intValue();
    }
    
    /** Consultor del municipio dado el vértice */
    public Municipio getMunicipio(int v) { 
        return verticesAMunicipios.recuperar(v);
    }

    
    public double pesoAristasEnMST(Municipio m) {
        //COMPLETAR
        int v = getVertice(m);
        double sum = 0;
        
        if(v == -1){
            return -2;
        }
        
        Arista[] a = gRM.kruskal();
        if (a == null){
            return -1;
        }
        
        for(int i=0;i<a.length;i++){
            Arista x = a[i];
            if(x.getDestino() == v || x.getOrigen() == v){
                sum += x.getPeso();
            }
        }
        
        return sum;
    }
    
}
