package librerias.util;

 
/** 
 *  La clase Ordenacion contiene:
 *  - La implementacion de los algoritmos de ordenacion in-situ DyV
 *    Quick Sort y Merge Sort. 
 *  - Un metodo para comprobar si dos arrays genericos son iguales.
 *  
 * @author (EDA) 
 * @version (Curso 2018-2019)
 */

public class Ordenacion {
    
    // QUICK SORT -------------------------------------------------
    /** 
     *  Algoritmo de ordenacion quicksort (Hoare, 1963). 
     *  Utiliza el algoritmo de particion debido a Weiss, 
     *  con mediana de 3 para el calculo del pivote.
     *  
     *  @param a Sus elementos implementan la interfaz Comparable
     */
    public static <T extends Comparable<T>> void quickSort(T[]  a) {
        quickSort(a, 0, a.length - 1);
    }
   
    // Ordena el array a[izq, der] por quickSort, izq <= der
    private static <T extends Comparable<T>> void quickSort(T[] a, 
                                                            int izq, int der) {
        if (izq < der) {
            T pivot = mediana3(a, izq, der);
            int i = izq;
            int j = der - 1;
            for ( ; i < j;) {
                while (pivot.compareTo(a[++i]) > 0) { ; }
                while (pivot.compareTo(a[--j]) < 0) { ; }
                intercambiar(a, i, j);
            }
            intercambiar(a, i, j);        // Deshacer el ultimo cambio
            intercambiar(a, i, der - 1);  // Restaurar el pivote
            // PIVOTE ORDENADO -->
            quickSort(a, izq, i - 1);     // Ordenar rec. los elementos menores
            quickSort(a, i + 1, der);     // Ordenar rec. los elementos mayores
        }
    }

    // Intercambia los elementos ind1 e ind2 del array a
    private static <T> void intercambiar(T[] a, int ind1, int ind2) {
        T tmp = a[ind1];    
        a[ind1] = a[ind2];
        a[ind2] = tmp;   
    }
 
    // Tras calcular la Mediana de 3 del subArray a[izq, der], 
    // devuelve el valor del pivote
    private static <T extends Comparable<T>> T mediana3(T[] a, 
                                                        int izq, int der) {    
        int c = (izq + der) / 2;   
        if (a[c].compareTo(a[izq]) < 0)   { intercambiar(a, izq, c); }
        if (a[der].compareTo(a[izq]) < 0) { intercambiar(a, izq, der); }
        if (a[der].compareTo(a[c]) < 0)   { intercambiar(a, c, der); }
        // ocultar el pivote en la posicion der-1
        intercambiar(a, c, der - 1);
        return a[der - 1];
    }

    // MERGE SORT --------------------------------------------   
    // VERSION 1 (vista en teoria):   
    /**
     * Ordena ascendentemente el array v.
     * 
     * @param v  Sus elementos deben implementar la interfaz Comparable
     */
    public static <T extends Comparable<T>> void mergeSort1(T[] v) {
        mergeSort1(v, 0, v.length - 1);
    }
    
    /**
     * SII i<=f: ordena ascendentemente el subarray v[i, f].
     * 
     * @param v  Sus elementos implementan la interfaz Comparable
     * @param i  Extremo inferior del intervalo a ordenar
     * @param f  Extremo superior del intervalo a ordenar
     */
    private static <T extends Comparable<T>> void mergeSort1(T[] v, 
                                                             int i, int f) {
        if (i < f) {
            int m = (i + f) / 2;
            mergeSort1(v, i, m);
            mergeSort1(v, m + 1, f);
            merge1(v, i, f, m);
        }
    }        
    
    /**
     * Mezcla internamente los subarrays v[i, m] y v[m + 1, f], 
     * ambos ordenados Asc.
     * 
     * @param v  Sus elementos implementan la interfaz Comparable
     * @param i  Extremo inferior del intervalo a mezclar
     * @param f  Extremo superior del intervalo a mezclar
    */
    @SuppressWarnings("unchecked")
    private static <T extends Comparable<T>> void merge1(T[] v, 
                                                         int i, int f, int m) {
        int a = i, b = m + 1, k = 0;
        T[] aux = (T[]) new Comparable[f - i + 1];
        while (a <= m && b <= f) {
            if (v[a].compareTo(v[b]) < 0) { aux[k++] = v[a++]; }
            else                          { aux[k++] = v[b++]; }
        }
        while (a <= m) { aux[k++] = v[a++]; }
        while (b <= f) { aux[k++] = v[b++]; }
        
        for (a = i, k = 0; a <= f; a++, k++) { v[a] = aux[k]; }
    }   
    
    // MERGE SORT --------------------------------------------   
    // VERSION 2 
    /**
     * Ordena ascendentemente el array v.
     * 
     * @param v  Sus elementos deben implementar la interfaz Comparable
     */
    public static <T extends Comparable<T>> void mergeSort2(T[] v) {
        /*COMPLETAR*/
        T[] result = mergeSort2(v, 0, v.length - 1);
        for(int i = 0;i < v.length;i++){
            v[i] = result[i];
        }
    }
    
    /**
     * SII i<=f: devuelve un array con los elementos del subarray 
     * v[i, f] ordenados Asc. 
     * 
     * @param v  Sus elementos implementan la interfaz Comparable
     * @param i  Extremo inferior del intervalo a ordenar
     * @param f  Extremo superior del intervalo a ordenar
     * @return T[], el array resultante de ordenacion de v[i, f]
     */
    private static <T extends Comparable<T>> T[] mergeSort2(T[] v,
                                                             int i, int f) {
        /*COMPLETAR*/
        if(i == f) { 
            T[] v1 = (T[]) new Comparable[1];
            v1[0]=v[i];
            return v1;
        }
        if(f - i == 1) {
            T[] v2 = (T[]) new Comparable[2];
            if(v[i].compareTo(v[f]) < 0){
                v2[0]=v[i];
                v2[1]=v[f];
            } else {
                v2[1]=v[i];
                v2[0]=v[f];
            }
            return v2;
        }
        if (i < f) {
            int middle = (i + f) / 2;
            T[] v1 = mergeSort2(v, i, middle);
            T[] v2 = mergeSort2(v, middle + 1, f);
            return merge2(v1, v2);
        }
        
        return v;
    }        
    
    /**
     * Devuelve el array mezcla de v1 y v2, dos arrays ordenados Asc.
     * 
     * @param v1  Sus elementos implementan la interfaz Comparable
     * @param v2  Sus elementos implementan la interfaz Comparable
     * @return T[], el array resultante de la fusion de v1 y v2
     */
    @SuppressWarnings("unchecked")
    private static <T extends Comparable<T>> T[] merge2(T[] v1, T[] v2) {
        /*COMPLETAR*/
        T[] res = (T[]) new Comparable[v1.length + v2.length];
        int i = 0, j = 0, k = 0;
        while (i < v1.length && j < v2.length) {
            if (v1[i].compareTo(v2[j]) < 0) res[k++] = v1[i++];
            else res[k++] = v2[j++];
        }
        for (int r = i; r < v1.length; r++) res[k++] = v1[r];
        for (int r = j; r < v2.length; r++) res[k++] = v2[r];
        return res;
    }
    
    // Metodo auxiliar --------------------------------------------
    /** 
     *  Comprueba si los arrays a y b son iguales elemento a elemento.
     *  
     *  @param a  Sus elementos implementan la interfaz Comparable
     *  @return boolean, el resultado de la comprobacion
     */
    public static <T extends Comparable<T>> boolean sonIguales(T[] a, T[] b) {
        boolean iguales = true;
        if (a.length != b.length) { iguales = false; }
        else {
            for (int i = 0; i < a.length && iguales; i++) {
                iguales = (a[i].compareTo(b[i]) == 0);
            }
        }    
        return iguales;
    }    
}