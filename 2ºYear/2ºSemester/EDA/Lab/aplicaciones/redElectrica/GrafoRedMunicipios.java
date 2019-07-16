package aplicaciones.redElectrica;

import librerias.estructurasDeDatos.grafos.GrafoNoDirigido;
import librerias.estructurasDeDatos.grafos.Grafo;
import librerias.estructurasDeDatos.grafos.Arista;
import librerias.estructurasDeDatos.grafos.Adyacente;
import java.util.Scanner;
import librerias.estructurasDeDatos.modelos.ListaConPI;
import librerias.estructurasDeDatos.modelos.Map;
import librerias.estructurasDeDatos.lineales.LEGListaConPI;
import librerias.estructurasDeDatos.deDispersion.TablaHash;
import java.io.File;
import java.io.FileNotFoundException;

/** GrafoRedMunicipios: grafo etiquetado que representa una red  
 *  electrica interurbana mediante...
 ** Un conjunto de vertices etiquetados por los nombres 
 *  de los municipios de la red 
 ** Un conjunto de aristas etiquetadas por los millones 
 *  de euros que costaria la renovacion del tendido electrico 
 *  entre cada par de municipios de la red (vertices). 
 * 
 * @version (Curso 2017/18)
 */    

public class GrafoRedMunicipios {
    
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
                                              
    // UN GrafoRedMunicipios TIENE UN Grafo gRM para representar
    // una Red electrica existente entre varios Municipios (gRM)
    protected Grafo gRM;   
    
    // UN GrafoRedMunicipios TIENE UN Map que asocia cada uno de 
    // sus vertice (int) con aquel Municipio de una red cuyo nombre 
    // lo etiqueta
    protected Map<Integer, Municipio> verticesAMunicipios; 
    
    // UN GrafoRedMunicipios TIENE UN Map que asocia cada uno de 
    // los Municipios de una red con aquel de sus vertice (int)
    // etiquetado con el nombre del Municipio
    protected Map<Municipio, Integer> municipiosAVertices;     
    
    // UN GrafoRedMunicipios TIENE UN Map adyKruskal que asocia cada uno
    // de los Municipios de una red con todos los que le son Adyacentes 
    // en una subred de coste minimo, i.e. en la subRed que define un  
    // Arbol de Recubrimiento Minimo del grafo
    // COMPLETAR: declarar el Map adyKruskal con claves de tipo 
    //            Muncipio y valores ListaConPI<Municipio> 
    protected Map<Municipio, ListaConPI<Municipio>> adyKruskal;
    
    /** Construye el grafo que representa una red electrica interurbana  
      * como un GrafoNoDirigido, a partir de los datos contenidos en 2 
      * ficheros de texto cuyos nombres empiezan por un mismo prefijo 
      * nomFich: uno contiene datos de los municipios de la red y otro 
      * los datos de la lineas de tendido que los conectan, incluidos
      * sus costes de renovacion. 
      * 
      * Al mismo tiempo que se construye el grafo, se construyen
      * sus Map verticesAMunicipios y municipiosAVertices, 
      * implementados eficientemente mediante sendas Tablas Hash.
     */    
    public GrafoRedMunicipios(String nomFich) {
        verticesAMunicipios = new TablaHash<Integer, Municipio>(MAX_MUNICIPIOS);
        municipiosAVertices = new TablaHash<Municipio, Integer>(MAX_MUNICIPIOS);
        
        String nomFichMunicipios = DIR_FICHEROS + nomFich + "_municipios.txt";
        try {         
            Scanner fent = new Scanner(new File(nomFichMunicipios), "UTF-8"); 
            int vertice = 0;
            while (fent.hasNext()) {
                String linea = fent.nextLine();
                String[] lA = linea.split(";");
                String nombre = lA[0].toLowerCase();
                int poblacion = Integer.parseInt(lA[1]);
                double extension = Double.parseDouble(lA[2]);
                int posX = Integer.parseInt(lA[3]);
                int posY = Integer.parseInt(lA[4]);
                Municipio m = new Municipio(nombre, poblacion, extension, 
                                            posX, posY);
                verticesAMunicipios.insertar(vertice, m);
                municipiosAVertices.insertar(m, vertice);
                vertice++;
            }
            fent.close();
            gRM = new GrafoNoDirigido(verticesAMunicipios.talla());
            cargarAristas(nomFich);
        } catch (java.io.IOException eChecked) {
            System.out.println(NO_ACC_MSG);
        }
    }
    // Inserta en el grafo las aristas con pesos que hay representadas 
    // en el fichero con los datos de las lineas de tendido electrico 
    // que conectan los municipios de una red, incluidos los costes de 
    // su renovacion. Para ello usa el Map municipiosAVertices 
    private void cargarAristas(String nomFich) {  
        String nomFichCostes = DIR_FICHEROS  + nomFich + "_costes.txt";
        try {
            Scanner f = new Scanner(new File(nomFichCostes), "UTF-8");
            while (f.hasNext()) {
                String linea = f.nextLine();
                String[] datosCostes = linea.split(";");
                if (datosCostes.length != 3) {
                    System.out.println(NO_FOR_MSG);
                    break;
                }
                String mOrigen = datosCostes[0].trim().toLowerCase();
                String mDestino = datosCostes[1].trim().toLowerCase();
                double coste = Double.parseDouble(datosCostes[2]);
                int vOrigen =  getVertice(new Municipio(mOrigen)),
                    vDestino = getVertice(new Municipio(mDestino));
                gRM.insertarArista(vOrigen, vDestino, coste);
            }
        } catch (FileNotFoundException e) {
            System.err.println(NO_FDIS_MSG);
        }
    }
    
    /** Devuelve el numero de municipios de una red electrica, o 
      * numero de vertices del grafo que la representa.
      * @return int
     */    
    public int numVertices() { return gRM.numVertices(); }
    
    /** Devuelve el numero de lineas de tendido electrico de una red,
      * o numero de aristas del grafo que la representa.
      * @return int
     */    
    public int numAristas() { return gRM.numAristas(); }

    /** Devuelve el vertice de un grafo asociado al municipio m 
      * de una red electrica, -1 si m no esta en la red.
      * 
      * Es un metodo consultor del Map municipiosAVertices:
      * @param m    Un municipio de la red
      * @return int Valor de la clave m en municipiosAVertices,
      *             -1 si la clave no esta en el Map
     */
    public int getVertice(Municipio m) {
        Integer vertice = municipiosAVertices.recuperar(m); 
        if (vertice == null) { return -1; }
        return vertice.intValue();
    }
    
    /** Devuelve el municipio de una red electrica asociado al 
      * vertice v del grafo que la representa, null si v no esta
      * en el intervalo [0, numMunicipios() - 1].
      * 
      * Es un metodo consultor del Map verticesAMunicipios:
      * @param v Un vertice del grafo que representa la red
      * @return Municipio Valor de la clave v en verticesAMunicipios,
      *                   null si v no es un vertice del grafo
     */    
    public Municipio getMunicipio(int v) { 
        return verticesAMunicipios.recuperar(v);
    }

    /** Devuelve la lista de adyacentes a un vertice v, null si
      * v no esta en el intervalo [0, numMunicipios() - 1].
      * @param v Vertice del grafo
      * @return ListaConPI<Adyacente> al vertice v, null si v
      *                               no es un vertice del grafo
     */  
    public ListaConPI<Adyacente> adyacentesDe(int v) {
        return gRM.adyacentesDe(v);
    }
    
    /** Si existe, calcula las lineas de tendido electrico de    
      * una subred de coste minimo de una red interurbana, i.e. 
      * las aristas que definen un Arbol de Recubrimiento Minimo
      * del grafo que representa a la red, y devuelve su coste.  
      * Ademas, construye el Map adyKruskal, que asocia a cada  
      * Municipio de la red todos sus Adyacentes en dicho Arbol.
      * 
      * Si no existe la subred de coste minimo, devuelve -1.0;
      * 
      * @return double Coste del Arbol de Recubrimiento Minimo, 
      *                -1 si no existe
     */
    public double crearAdyKruskal() {      
        
        // PASO 1. Usando el metodo kruskal de la clase Grafo, 
        // obtener el conjunto de aristas que definen un Arbol  
        // de Recubrimiento Minimo del grafo que representa la
        // red electrica interurbana (gRM)
        // COMPLETAR
        Arista[] mST = gRM.kruskal();
        
        // PASO 2. Si kruskal devuelve null, devolver -1.0
        // COMPLETAR
        if (mST == null) {
            return -1.0;
        }
        
        // PASO 3. Si kruskal devuelve un conjunto de aristas, 
        // recorrerlo para calcular su coste (optimo) y, al
        // mismo tiempo, construir el Map adyKruskal con ayuda 
        // del Map verticesAMunicipios
        // COMPLETAR
        double costeMST = 0.0;
        adyKruskal = new TablaHash<Municipio, ListaConPI<Municipio>> (mST.length + 1);
        for (int i = 0; i < mST.length; i++) {
            costeMST += mST[i].getPeso();
            Municipio clave = getMunicipio(mST[i].getOrigen());
            ListaConPI<Municipio> valor = adyKruskal.recuperar(clave);
            if (valor == null) {
                valor = new LEGListaConPI<Municipio>();
            }
            valor.insertar(getMunicipio(mST[i].getDestino()));
            adyKruskal.insertar(clave, valor);
        }
              
        // (c) Devolver el coste total del tendido electrico, i.e.
        //     la suma de los costes de las aristas que forman el MST
        // COMPLETAR
        return costeMST;
    }
}