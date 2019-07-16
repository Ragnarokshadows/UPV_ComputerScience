package aplicaciones.biblioteca;

import librerias.estructurasDeDatos.deDispersion.TablaHash;
import java.util.Scanner;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.File;
import java.io.FileNotFoundException;

/**
 * Clase programa para comprobar la efectividad de las 4 funciones de dispersion propuestas sobre cadenas
 * de caracteres. 
 * Como datos utiliza los terminos que se encuentran en los documentos listados en el fichero lista10.txt
 * Como resultados, muestra por pantalla el factor de carga y la desviacion tipica para las 4 funciones
 * y genera ficheros de texto en la carpeta res con los histogramas correspondientes.
 * 
 * @author (EDA) 
 * @version (Marzo 2018)
 */
public class EvaluaFuncionDispersion {
    public final static int NUM_TERMINOS = 23000;
    public final static String NOM_DIR = Indexador.DIR_LISTAS_LIBROS + "res";
                                         // Indexador.DIR_LISTAS_LIBROS + File.separator + "res";
    public final static String NOM_FICH = NOM_DIR + File.separatorChar + "histo"; 
    public final static String EXT = ".txt";
    
    public static void main(String[] args) {
        int fdis = 0;
        try {
            File dir = new File(NOM_DIR); dir.mkdir();
            for (fdis = 0; fdis < Termino.NOMFDIS.length; fdis++) {  
                //1.- Construir la Tabla Hash a partir de los terminos encontrados en los
                //    libros de la lista lista10.txt usando la funcion de hashing fdis
                TablaHash<Termino, Termino> tab = construirTabla("lista10.txt", fdis);
                //2.- Mostrar por pantalla los valores de factor de carga y desviacion tipica
                System.out.printf("Funcion de dispersion: %s\n", Termino.NOMFDIS[fdis]);
                System.out.printf("\tFactor de carga = %4.3f\n", tab.factorCarga());
                System.out.printf("\tDesv. Tipica = %4.3f\n", tab.desviacionTipica());    
                //3.- Generar el histograma y guardarlo en el directorio res de la carpeta
                //    aplicaciones/biblioteca con el nombre del hashing precedido de histo y 
                //    acabado en .txt: histoSimple.txt, histoWeiss.txt, etc.
                PrintWriter pw = new PrintWriter(new File(NOM_FICH + Termino.NOMFDIS[fdis] + EXT));
                pw.println(tab.histograma());
                pw.close();
            }
        } catch (FileNotFoundException e) {
            System.err.println("Problemas con el fichero..." + NOM_FICH + fdis + EXT); 
        } catch (IOException e) {
            System.err.println("No se encontro el fichero " + "lista10.txt");
        }
    }
   
   // Construye la Tabla Hash a partir de los terminos encontrados en todos los documentos que incluye el fichero
    // listaLibros, utilizando la funcion de dispersion fdis.
    private static TablaHash<Termino, Termino> construirTabla(String listaLibros, int fdis) throws FileNotFoundException {  
        boolean leido = true; 
        Scanner lista = new Scanner(new File(Indexador.DIR_LISTAS_LIBROS + listaLibros));
        System.out.println("Analizando libros...");
        
        //String dirHome = System.getProperty("user.home");
        
        TablaHash<Termino, Termino> res = new TablaHash<Termino, Termino>(NUM_TERMINOS);
        while (lista.hasNext()) {
            String nombreLibro = lista.next();
            leido = extraerTerminos(Indexador.DIR_LIBROS + nombreLibro, res, fdis);
        }       
        System.out.println("Numero de terminos = " +  res.talla());
        if (!leido) { throw new FileNotFoundException(); }
        return res;
    }
    
    // Incluye en la tabla t, utilizando la funcion de dispersion fdis todos los terminos encontrados en el
    // documento nomLibro
    private static boolean extraerTerminos(String nomLibro, TablaHash<Termino, Termino> t, int fdis)  {
    boolean res = true;     
        try {            
            Scanner libro = new Scanner(new File(nomLibro)); 
            int posSep = nomLibro.lastIndexOf(File.separator);
            String titulo = nomLibro.substring(posSep + 1);
            System.out.println("... " + titulo);
            while (libro.hasNext()) {
                String linea = libro.nextLine().toLowerCase();
                String[] words = linea.split(Indexador.SEPARADORES);
                for (int i = 0; i < words.length; i++) {                   
                    if (Indexador.esTermino(words[i])) {
                        Termino clave = new Termino(words[i], fdis);
                        Termino valor = t.recuperar(clave);
                        if (valor == null) { 
                            t.insertar(clave, clave);
                        }
                    }
                }
            }
        } catch (FileNotFoundException e) {
            System.err.println("Error: " + nomLibro + " no se encuentra");
            res = false;     
        }
        return res;
    }
}