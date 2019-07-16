package prueba1;

import librerias.estructurasDeDatos.modelos.Map;
import librerias.estructurasDeDatos.deDispersion.TablaHash;

import librerias.estructurasDeDatos.modelos.ListaConPI;
import librerias.estructurasDeDatos.lineales.LEGListaConPI;

import aplicaciones.biblioteca.Indice;

import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import java.awt.Color;
import java.awt.Font;

/**
 * IndexadorEx: version de la clase Indexador (Practica 3 - S1) para el Primer Parcial de Lab. 
 * @author (EDA) 
 * @version (Curso 2018-2019)
 */

public class IndexadorEx {
    
    protected final static String SEPARADORES = " +";
    protected Map<String, ListaConPI<Indice>> indices;
    
    /**
     * Dada una lista de palabras l, devuelve una lista con aquellas palabras de l
     * que aparecen una sola vez en los documentos indexados en su Map indices; si  
     * ninguna palabra de l cumple tal condicion o l esta vacia, el metodo debe 
     * devolver una lista vacia.
     */ 
    public ListaConPI<String> palsFrecuenciaUno(ListaConPI<String> l) {
        // COMPLETAR
        ListaConPI<String> result = new LEGListaConPI<String>();
        ListaConPI<Indice> aux;
        String palabra;
        
        if(!l.esVacia()){
            for(l.inicio();!l.esFin();l.siguiente()){
                palabra = l.recuperar();
                aux = indices.recuperar(palabra);
                if (aux != null && aux.talla() == 1){
                    result.insertar(palabra);
                }
            }
        }
        
        return result;
    }
    
    /** Construye un indexador a partir de un libro dados su titulo, contenido y 
     *  una estimacion del numero maximo de terminos que puede contener.
     *  
     *  @param   String titulo, titulo del libro
     *  @param   String contenido, contenido del libro
     *  @param   int tallaAprox, estimacion del numero maximo de terminos en el libro
     */ 
    public IndexadorEx(String titulo, String contenido, int tallaAprox) { 
        Scanner libro = new Scanner(contenido);
        indices = new TablaHash<String, ListaConPI<Indice>>(tallaAprox);
        int numLin = 0;
        while (libro.hasNext()) {
            String linea = libro.nextLine().toLowerCase();
            String[] words = linea.split(SEPARADORES);
            numLin++;
            Indice nuevo = new Indice(titulo, numLin);
            for (int i = 0; i < words.length; i++) {
                if (esTermino(words[i])) {
                    String clave = words[i];
                    ListaConPI<Indice> valor = indices.recuperar(clave);
                    if (valor == null) { valor = new LEGListaConPI<Indice>(); }
                    valor.insertar(nuevo);
                    indices.insertar(clave, valor);
                }
            }
        }
    }
    
    /**
     * Devuelve una ListaConPI con la representacion textual
     * del libro y pagina (Indice) en los que aparece una palabra
     * 
     * @param   String pal, palabra a buscar
     * @return  ListaConPI<String>
     */
    public ListaConPI<String> indiceDe(String pal) {
        ListaConPI<String> res = new LEGListaConPI<String>();  
        ListaConPI<Indice> valor = indices.recuperar(pal.toLowerCase());    
        if (valor != null) {
            for (valor.inicio(); !valor.esFin(); valor.siguiente()) { 
                String e = valor.recuperar().toString();
                res.insertar(e);
            }
        }
        return res;
    }
    
    /** Comprueba si el String s es un termino valido
     *  es decir, si es una secuencia de letras
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
