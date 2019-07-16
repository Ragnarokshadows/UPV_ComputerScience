package aplicaciones.biblioteca;

import librerias.estructurasDeDatos.modelos.Map;
import librerias.estructurasDeDatos.modelos.ListaConPI;
import librerias.estructurasDeDatos.deDispersion.TablaHash;
import librerias.estructurasDeDatos.lineales.LEGListaConPI;
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;

/**
 * Clase que genera las referencias cruzadas del vocabulario en un conjunto 
 * de libros.
 * Es decir, analiza los documentos para obtener información que permita  
 * consultar de forma eficiente en que lineas de que documentos aparece una 
 * determinada palabra.
 * Permite la siguiente funcionalidad:
 * 1.- Construir un Indexador a partir de un listado con los nombres de los
 *     documentos y el numero aproximado de terminos
 * 2.- Actualizar las referencias cruzadas (el Indexador) cuando se analiza un nuevo documento
 * 2.- Consultar en que lineas de que documentos aparece una determinada palabra
 * 
 * @author (EDA) 
 * @version (Curso 2017-2018)
 */
public class Indexador {
    /** DIR_LIBROS contiene la ubicacion de los textos.
     *  Por defecto se encuentran en $HOME/asigDSIC/libros/TXT.
     *  Se debe cambiar si la ubicacion es diferente.
     */
    protected static String DIR_LIBROS = File.separator 
                                       + "labos" + File.separator 
                                       + "asignaturas" + File.separator 
                                       + "ETSINF" + File.separator 
                                       + "eda" + File.separator 
                                       + "libros" + File.separator 
                                       + "TXT" + File.separator;
    
    /** DIR_LISTAS_LIBROS contiene la ubicacion de las listas de libros a analizar.
     *  Por defecto se encuentran en el paquete aplicaciones.indices de eda.
     *  Se debe cambiar si la ubicacion es diferente.
     */
    protected static String DIR_LISTAS_LIBROS = "aplicaciones" + File.separator
                                              + "biblioteca" + File.separator;
    
    /** SEPARADORES contiene la expresion regular que define los separadores de palabras.
     *  Debe cambiarse si procede.
     */
    protected final static String SEPARADORES = "[[ ]*|[,]*|[\\.]*|[\t]*|[:]*|[;]*|[(]*|[)]*|[/]*|[!]*|[?]*|[¿]*|[“]*|[”]*|[+]*]+";
    
    /** TALLA_VOCABULARIO contiene una estimacion del numero de terminos a tratar.
     *  Debe cambiarse si procede.
     */
    protected final static int TALLA_VOCABULARIO = 120000;
    
    /** Map que representa para cada termino (clave) la lista de indices (titulo y linea) en 
     *  la que aparece el termino.
     */
    //COMPLETAR: Atributo de tipo Map
    protected TablaHash<String, ListaConPI<Indice>> indices;
    
    /** Tiempo para construir el Map.
     */
    protected double tmpCarga;
    
    /** Construye un indexador a partir de los textos cuyos nombres se encuentran
     *  en listaLibros.
     *  @param   String listaLibros, nombre del fichero que contiene la lista de documentos
     *  @param   int tallaAprox, numero aproximado de terminos a considerar
     *  @throws  FileNotFoundException si no se encuentra algun fichero 
     */ 
    public Indexador(String listaLibros, int tallaAprox) throws FileNotFoundException { 
        boolean res = true; 
        Scanner fich = new Scanner(new File(DIR_LISTAS_LIBROS + listaLibros));
        System.out.println("Cargando libros...");

        
        // COMPLETAR:
        // Inicializar el atributo Map utilizando como tipo dinamico
        // la clase TablaHash, cuyo constructor requiere como argumento
        // el numero aproximado de datos (tallaAprox)
        
        indices = new TablaHash(tallaAprox);
        while (fich.hasNext()) {
            String nombreLibro = fich.next();
            String fichLibro = DIR_LIBROS + nombreLibro;
            res = cargarLibro(fichLibro, SEPARADORES);
        }
        tmpCarga = tmpCarga / 1000000.0;
        System.out.println("Terminos indexados (tamaño del Map) = " + indices.talla());
        System.out.printf("Tiempo de carga = %10.2f mseg.\n", tmpCarga);
        if (!res) { throw new FileNotFoundException(); }
    }
    
    /** Actualiza el Indexador con los datos del documento nombreLibro que
     *  se encuentra en el directorio dir. Si no existe el documento, no hace nada.
     *  @param   String fichLibro, nombre del fichero que contiene el documento
     *  @param   String dir, nombre del directorio donde se encuentra el documento
     *  @return  boolean, true si el libro se ha leido con exito y falso en caso contrario
     */ 
    public boolean cargarLibro(String fichLibro, String separadores)  {
        boolean res = true;     
        try {            
            Scanner libro = new Scanner(new File(fichLibro));            
            int posSep = fichLibro.lastIndexOf(File.separator);
            String titulo = fichLibro.substring(posSep + 1);
            System.out.println("Cargando ..." + titulo);
            int numLin = 0;
            long tmp = 0, t1 = 0, t2 = 0;
            while (libro.hasNext()) {
                String linea = libro.nextLine().toLowerCase();
                String[] words = linea.split(separadores);
                numLin++;
                Indice ind = new Indice(titulo, numLin);
                for (int i = 0; i < words.length; i++) {
                    String clave = words[i];
                    if (esTermino(clave)) {
                        t1 = System.nanoTime();
                        // COMPLETAR:
                        // añadir el nuevo par titulo, numLin (ind) al Map
                        // la clave puede haber aparecido antes o no.
                        ListaConPI<Indice> e = indices.recuperar(clave);
                        if (e == null){
                            e = new LEGListaConPI<Indice>();
                        }
                        e.insertar(ind); 
                        indices.insertar(clave, e);
                        t2 = System.nanoTime();
                    }
                    tmp += (t2 - t1);
                }
            }
            tmpCarga += tmp;
        } catch (FileNotFoundException e) {
            System.err.println("Error " + fichLibro + " no se encuentra");
            res = false;        
        }
        return res;
    }
       
    /**
     * Devuelve una ListaConPI con la representación textual
     * del libro y pagina (Indice) en los que aparece el termino
     * @param   String a buscar
     * @return ListaConPI<String>
     */
    public ListaConPI<String> indiceDe(String pal) {
        ListaConPI<String> res = new LEGListaConPI<String>();  
        // COMPLETAR
        ListaConPI<Indice> e = indices.recuperar(pal);
        
        if(e != null){
            e.inicio();
            while(!e.esFin()){
                res.insertar(e.recuperar().toString());
                e.siguiente();
            }   
        }
        
        return res;   
    }
    /** Comprueba si el String s es un termino valido
      * es decir, si es una secuencia de letras
      */
    protected static boolean esTermino(String s) {
        for (int i = 0; i < s.length(); i++) {
            if (!Character.isLetter(s.charAt(i))) {
                return false;
            }
        }
        return true;
    }
}
