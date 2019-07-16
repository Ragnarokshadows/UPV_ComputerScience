package aplicaciones.biblioteca;
/**
 * Clase que representa un indice para una palabra: 
 * titulo y linea del libro donde aparece
 * 
 * @author (EDA) 
 * @version (Curso 2017-2018)
 */
public class Indice {
    private String titulo;
    private int linea;
    public Indice(String nombreLibro,  int noLinea) {
        this.titulo = new String(nombreLibro);
        this.linea = noLinea;
    }

    public String toString() {
        return String.format("%s - %d\n", titulo, linea);
    }
}