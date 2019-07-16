package librerias.estructurasDeDatos.deDispersion;

 

import librerias.estructurasDeDatos.modelos.ListaConPI;
/**
 * Clase para probar el c??digo de los m??todos histo y deviacionTipica
 * 
 * @author (EDA) 
 * @version (Febrero 2016)
 */
public class TestTablaHash2 {
    
    public static void main(String [] args) {
        boolean fc = TestTablaHash1.testFactorCarga();
        boolean rehash = TestTablaHash1.testRehashing();
        boolean claves = TestTablaHash1.testClaves();       
        if (!fc || !rehash || !claves) {
            System.out.println("-( Vuelve a pasar el test 1!");
            return;
        }
        boolean desv = testDesvTipica();
        if (!desv)  {
            System.out.println("-( desviacionTipica no es correcto.");
        }
        
        boolean histo = testHisto();
        if (!histo) {
            System.out.println("-( histograma no es correcto.");
        }
        
        if (desv && histo) {
            System.out.println(" -)-) Codigo de TablaHash correcto!");
        }
    }

   
     /**
     * Comprueba el metodo histograma de TablaHash
     * @return  True si el metodo es correcto
     */    
    protected static boolean testHisto() {
        try {
            System.out.print("... Test histograma: "); 
            TablaHash<String, Integer> th = new TablaHash<String, Integer>(40); 
            for (int i = 0; i < 40; i++) { th.insertar("N" + i, i); }
            String histogr = th.histograma(), miHistogr = miHistograma(th);
            String[] histo = histogr.split("\n"), miHisto = miHistogr.split("\n");
            boolean ok = true;
            if (histo.length != miHisto.length) { 
                System.out.println("Debe tener 10 lineas (una para cada longitud)");
                ok = false;
            }
            else {
                ok = true;
                for (int i = 0; i < histo.length && ok; i++) {
                    if (!histo[i].trim().equals(miHisto[i].trim())) {
                        ok = false;
                        System.out.print("Error en la longitud " + i);
                        System.out.println("... deber??a ser " + miHisto[i] 
                            + " y es " + histo[i]); 
                    }
                }
            }
            if (ok) {
                TablaHash<Integer, Integer> thi = new TablaHash<Integer, Integer>(40); 
                for (int i = 0; i < 10; i++) { thi.insertar(new Integer(i * 53), i); }
                histogr = thi.histograma(); miHistogr = miHistograma(thi);
                histo = histogr.split("\n"); miHisto = miHistogr.split("\n");
                ok = true;
                if (histo.length != miHisto.length) { 
                    System.out.println("Debe tener 10 lineas (una para cada longitud)");
                    ok = false;
                }
                else {
                    ok = true;
                    for (int i = 0; i < histo.length && ok; i++) {
                        if (!histo[i].trim().equals(miHisto[i].trim())) {
                            ok = false;
                            System.out.print("Error en la longitud " + i);
                            System.out.println("... debería ser " + miHisto[i] 
                                + " y es " + histo[i]); 
                        }
                    }
                }
            }
            if (ok) { System.out.println("ok!"); }
            return ok;
        } catch (Exception e) {
            System.out.println(e);
            return false;
        }
    }
    
    public static String miHistograma(TablaHash t) {
        String res = "";
        int[] histo = new int[10];
        for (int i = 0; i < t.elArray.length; i++) {
            int cont = t.elArray[i].talla();
            if (cont >= histo.length) { cont = histo.length - 1; }
            histo[cont]++;
        }
        for (int i = 0; i < histo.length; i++) {
            res += i + "\t" + histo[i] + "\n";
        }        
        return res;        
    }

    
    protected static boolean testDesvTipica() {
        System.out.print("... Test desviacionTipica: "); 
        TablaHash<String, Integer> th = new TablaHash<String, Integer>(40); 
        for (int i = 0; i < 40; i++) { th.insertar("N" + i, i); }
        double tuDT = th.desviacionTipica();
        //System.out.println(tuDT);
        //double miDT = 0.6702762138993063; //desviacionTipica(th);
        double miDT = miDesviacionTipica(th);
        boolean res = (Math.abs(tuDT - miDT) < 1E-8);
        if (res) { System.out.println("ok!"); }
        return res;
    }
    
    
    private final static double miDesviacionTipica(TablaHash t) {
        double lMedia = (double) (t.talla) / t.elArray.length;
        double suma = 0.0;
        for (int i = 0; i < t.elArray.length; i++) {
            double di = t.elArray[i].talla() - lMedia; 
            suma += di * di;
        }
        return Math.sqrt(suma / t.elArray.length);
    }

}
