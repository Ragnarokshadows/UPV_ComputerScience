package aplicaciones.editorPredictivo;

import librerias.estructurasDeDatos.jerarquicos.ABB;
import librerias.estructurasDeDatos.lineales.LEGListaConPI;
import librerias.estructurasDeDatos.modelos.ListaConPI;
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.IOException;

/** Clase EditorPredictivo: es un Arbol Binario de Busqueda <br>
 * de elementos de tipo String, que representan las palabras <br> 
 * de un idioma. Esta clase proporciona los metodos necesarios <br>
 * para la gestion de un editor predictivo. <br>
 *  
 *  @version Febrero 2019
 */

public class EditorPredictivo extends ABB<String> {

    /**
     * Constructor de un EditorPredictivo vacio
     */
    public EditorPredictivo() {
        super();
    }
    
    /**
     * Constructor de un Editor Predictivo a partir de las palabras 
     * que, en orden alfabetico, contiene un fichero de texto a 
     * partir de su segunda linea, pues en su primera linea aparece 
     * el numero de palabras que almacena.
     * @param nombreFichero Nombre del fichero de texto que contiene 
     *                      las palabras a partir de las que se creara 
     *                      el editor ordenadas alfabeticamente, junto 
     *                      con su numero.
     */
    public EditorPredictivo(String nombreFichero) {   
        super();
        try {         
            Scanner fPalabras = new Scanner(new File(nombreFichero), "UTF-8"); 
            int talla = fPalabras.nextInt(); fPalabras.nextLine();
            String[] palabras = new String[talla];
            int nLinea = 0;
            while (fPalabras.hasNext() && nLinea < talla) {
                palabras[nLinea] = fPalabras.nextLine().toLowerCase().trim(); 
                nLinea++;
            }
            fPalabras.close();
            this.raiz = construirEquilibrado(palabras, 0, talla - 1);           
        } catch (FileNotFoundException eChecked) {
            System.out.println("El fichero " + nombreFichero 
                + " no es accesible para lectura, comprueba "
                + "su correcta ubicaci\u00f3n");
        }
    }
        
    /** Anyade la palabra nueva a un Editor Predictivo.
     *  @param  nueva  Palabra a anyadir a las que ya tiene 
     *                 un Editor Predictivo.
     */
    public void incluir(String nueva) { 
        this.insertar(nueva.toLowerCase().trim()); 
    }

    /**
     * Guarda en orden alfabetico las palabras de un Editor Predictivo 
     * a partir de la segunda linea de un fichero de texto, escribiendo 
     * su numero en la primera linea.
     * @param nombreFichero Nombre del fichero de texto donde se guardaran 
     *                      la talla y palabras en orden alfabetico de un 
     *                      Editor Predictivo.
     */
    public void guardar(String nombreFichero) {
        try { 
            PrintWriter fPalabras = new PrintWriter(nombreFichero, "UTF-8");
            String[] palabras = this.toArrayInOrden();
            fPalabras.println(palabras.length);
            for (int i = 0; i < palabras.length; i++) {
                fPalabras.println((String) palabras[i]);
            }
            fPalabras.close();
        } catch (IOException eChecked) {
            System.out.println("Error guardando el fichero " + nombreFichero 
                + ": " + eChecked);
        }
    }
    
    /**
     * Devuelve una ListaConPI con, como maximo, los n siguientes 
     * sucesores de prefijo; en el primer lugar de esta lista figurara 
     * el propio prefijo siempre y cuando sea ya una palabra del editor.
     * @param  prefijo  Prefijo a partir del que se buscan los "n" 
     *                  siguientes sucesores
     * @param  n        Numero maximo de sucesores a recuperar
     * @return ListaConPI<String>   Lista Con Punto de Interes con los 
     *                  sucesores de prefijo obtenidos; su primer elemento 
     *                  es el propio prefijo siempre y cuando sea ya una 
     *                  palabra del editor.
     */
    public ListaConPI<String> recuperarSucesores(String prefijo, int n) {       
	    //COMPLETAR
	    ListaConPI<String> result = new LEGListaConPI<String>();
	    String x = recuperar(prefijo);
	    boolean end = false;
	    int contador = n;
	    
	    if(x != null){
	       result.insertar(x);
	       contador--;
	    }
	    x = prefijo;
	    while(contador > 0 && !end){
	        x = sucesor(x);
	        if(x != null && x.startsWith(prefijo)) {
	            result.insertar(x);
	            contador--;
	        } else end = true;
	    }
	    
	    return result;	
    }
}
