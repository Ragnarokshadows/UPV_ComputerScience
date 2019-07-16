package aplicaciones.pruebasOrdenacion;


/**
 * Objeto CadenaADN, contiene un array de caracteres e implementa a Complarable.
 * 
 * @author (EDA) 
 * @version (Curso 2018-2019)
 */
public class CadenaADN implements Comparable<CadenaADN> {
    /* Atributo que contiene la secuencia de bases nitrogenadas representadas por una letra */
    private char[] dna;

    /**
     * Constructor de objetos de tipo CadenaADN
     * Dado un String con los caracteres que representan las bases nitrogenadas, 
     * los guarda en un array de caracteres de la longitud adecuada.
     */
    public CadenaADN(String s) {
        dna = s.toCharArray();
    }

    /**
     * Método de comparación de Cadenas de ADN, la comparación se realiza de la manera 
     * usual a como se haría con Strings
     * 
     * @param  CadenaADN
     * @return entero de valor negativo si la cadena this es anterior lexicográficamente a 
     * la cadena otra, positivo si es mayor y cero si es idéntica.
     */
    public int compareTo(CadenaADN otra)     {
        int comp = 0;
        int i = 0;
        while (i < dna.length && i < otra.dna.length && comp == 0) {
            comp = dna[i] - otra.dna[i++];
        }
        if (comp == 0) { return dna.length - otra.dna.length; }
        else { return comp; }
    }
}
