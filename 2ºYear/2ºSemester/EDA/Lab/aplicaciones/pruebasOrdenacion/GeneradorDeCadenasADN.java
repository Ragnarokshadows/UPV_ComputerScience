package aplicaciones.pruebasOrdenacion;

import java.util.Random;

/**
 * GeneradorDeCadenasADN genera CadenaADN con un numero dado  
 * de bases nitrogenadas iniciales iguales.
 * 
 * @author (EDA) 
 * @version (Curso 2018-2019)
 */
public class GeneradorDeCadenasADN {
    private static final String ATGCU = "ATGCU";
    private String prefijo; // SubString inicial comun a los String generados
    private Random rand;
    
    
    /**
     * Constructor del GeneradorDeCadenas, que permite generar objetos de tipo CadenaADN 
     * inicializando una cadena de n bases nitrogenadas que será prefijo común a todas las cadenas 
     * generadas posteriormente.
     */    
    public GeneradorDeCadenasADN(int n) {
        rand = new Random();
        prefijo = "";
        for (int i = 0; i < n; i++) {            
           prefijo += baseNitrogenada(rand.nextInt(4)); 
        }
    }
    
    /** Método para convertir el número entre 0 y 4 en la 
     * inicial de la base nitrogenada correspondiente.
     */
    private char baseNitrogenada(int a) { return ATGCU.charAt(a); }
    
    
    /**
     * Devuelve una CadenaADN con sus n primeros bases nitrogenadas
     * identicas, aunque elegidas al azar.
     */
    public CadenaADN generar() {
        String s = "";   
        int longitudCadenaAdicional = rand.nextInt(2 * prefijo.length());
        for (int i = 0; i < longitudCadenaAdicional; i++) {
            s += baseNitrogenada(rand.nextInt(4));
        }
        return new CadenaADN(this.prefijo + s);
    }
}
