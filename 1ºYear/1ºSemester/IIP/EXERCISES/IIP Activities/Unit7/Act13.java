package Unit7;
import java.util.*;

public class Act13{
    public static void main(String [] args){
        Scanner kbd = new Scanner(System.in).useLocale(Locale.US);
        int[] v;
        v = new int[10];
        int aux;
        int i;
        for(i = 0; i < 10; i++){
            System.out.println("Write a number to the array: ");
            aux = kbd.nextInt();
            v[i] = aux;
        }
        System.out.println("The array is ascendant sorted: "+ascSorted(v));
    }
    public static boolean ascSorted(int [] v){
        boolean r = true;
        int i;
        
        for(i = 0; i < (v.length - 1) && r == true;i++){
            if (v[i] > v[i + 1]){
                r = false;
            }
        }
        return r;
    }
}