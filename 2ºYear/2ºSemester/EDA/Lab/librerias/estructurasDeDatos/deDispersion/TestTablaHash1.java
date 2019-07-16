package librerias.estructurasDeDatos.deDispersion;

 
import librerias.estructurasDeDatos.modelos.ListaConPI;
/**
 * Clase para probar el c??digo de los m??todos:
 * factor de carga, rehashing y claves
 * 
 * @author (EDA) 
 * @version (Febrero 2016)
 */
public class TestTablaHash1 {
    
    public static void main(String [] args) {   
        String corr = "\n\tCorr??gelo antes de seguir comprobando.";
        boolean rehash = false;
        boolean claves = false;
        boolean fc = testFactorCarga();              
        if (!fc) {
            System.out.println("\t-( factorCarga no es correcto." + corr);
        }
        else {
            rehash = testRehashing();
            if (!rehash) {
                System.out.println("\t-( rehashing no es correcto." + corr);
            }
            else {
                claves = testClaves();
                if (!claves) {
                    System.out.println("\t-( claves no es correcto." + corr);
                }
            }
        }
//         if (fc && rehash && claves) {
//             System.out.println("-) Codigo de la practica correcto!");
//         }
    }

 /**
     * Comprueba el metodo factorCarga de TablaHash
     * @return  True si el metodo es correcto
     */    
    protected static boolean testFactorCarga() {
        System.out.print("... Test factor de carga: ");
        boolean ok = true; String res = "";
        TablaHash<Integer, Integer> t = new TablaHash<Integer, Integer>(10);
        for (int i = 0; i < 5; i++) {
            t.insertar(new Integer(i), 0);
        }
        int longArray = TablaHash.siguientePrimo((int) (10 / 0.75));
        if (t.factorCarga() != ((double) (t.talla()) / longArray)) {
            ok = false;
            res += "Factor de Carga Incorrecto";          
        }
        else { res += "ok!"; }
        System.out.println(res);
        return ok;
    }    
    
    
    /**
     * Comprueba el metodo rehashing de TablaHash
     * @return  True si el metodo es correcto
     */    
    protected static boolean testRehashing() {
        System.out.print("... Test rehashing: ");        
        boolean ok = true; String res = ""; 
        TablaHash<Integer, Integer> t = new TablaHash<Integer, Integer>(10);
        int t1 = t.elArray.length;
        int nrH1 = 1 + (int) (t1 * TablaHash.FACTOR_CARGA);
        for (int i = 0; i < nrH1 - 1; i++) {
            t.insertar(new Integer(i), 0);
        }
        t.insertar(new Integer(29), 0);
        if (t.talla() != nrH1) { // Talla incorrecta
            ok = false;  res += "\n\tTalla incorrecta"; 
        } 
        else {
            int t2 = t.elArray.length, t2Ref =  nuevaCapacidad(t1);
            if (t2 != t2Ref) { // Talla incorrecta tras el rehashing
                ok = false; res += "\n\tTama??o incorrecto del nuevo array"; 
            }   
            else {
                for (int i = 0; i < nrH1 - 1 && ok; i++) {
                    Integer oi = t.recuperar(new Integer(i));
                    if (oi == null) { // No se han copiado todos los datos 
                        ok = false;  
                        res += "\n\tNo se han copiado todos los datos";
                    } 
                }
                if (ok) {
                    Integer oi = t.recuperar(new Integer(29));
                    if (oi == null) { // No se han copiado todos los datos 
                        ok = false;  
                        res += "\n\tNo se han copiado todos los datos"; 
                    }
                }
            }
        }
        if (res.equals("")) { res = "ok!"; }
        System.out.println(res);
        return ok;
    }
    
    /**
     * Comprueba el metodo claves de TablaHash
     * @return  True si el metodo es correcto
     */    
    protected static boolean testClaves() {
        System.out.print("... Test claves: "); 
        boolean ok = true;
        TablaHash<String, Integer> th = new TablaHash<String, Integer>(100);
        for (int i = 0; i < 40; i++) { th.insertar("N" + i, i); }
       
        ListaConPI<String> lista = th.claves();
        int[] res = new int[40];
        
        for (lista.inicio(); !lista.esFin(); lista.siguiente()) {
            String s = lista.recuperar();
            if (s.startsWith("N")) {
                try {
                    int n = Integer.parseInt(s.substring(1));
                    if (n < 0 || n >= 40) { ok = false; }
                    else { res[n] = 1; }
                } catch (NumberFormatException e) { ok = false; }
            } else { ok = false; }
        }
        if (ok) {
            for (int i = 0; i < res.length; i++) {
                if (res[i] != 1) { ok = false; }
            }
        }
        if (ok) { System.out.println("ok!"); }
        else { System.out.println("incorrecto."); } 
        return ok;
    }
    
    private static int nuevaCapacidad(int old) {
        return siguientePrimo(old * 2);
    }
    // Devuelve un numero primo MAYOR o IGUAL a n, i.e. el primo que sigue a n
    public static final int siguientePrimo(int n) {
        int nn = n;
        if (nn % 2 == 0) { nn++; }
        for ( ; !esPrimo(nn); nn += 2) { ; } 
        return nn;
    } 
    // Comprueba si n es un numero primo
    protected static final boolean esPrimo(int n) {
        for (int i = 3; i * i <= n; i += 2) {
            if (n % i == 0) { return false; } // n NO es primo
        }
        return true; // n SI es primo
    } 

}
