package Unit7;
import java.util.*;

public class Act16{
    public static void main(String [] args){
        Scanner kbd = new Scanner(System.in).useLocale(Locale.US);
        int[] v;
        v = new int[10];
        int aux;
        int i,x;
        for(i = 0; i < 10; i++){
            System.out.println("Write a number to the array: ");
            aux = kbd.nextInt();
            v[i] = aux;
        }
        System.out.println("Write the number x: ");
        x = kbd.nextInt();
        
        System.out.println("The sum of elements on the array is greater"+
        " than "+x+" : "+sumGreaterThan(v, x));
    }
    public static boolean sumGreaterThan(int [] v, int x){
        boolean r;
        int i,counter = 0,aux = 0;
        for(i = 0; i < v.length && aux <= x; i++,counter++){
            aux = aux + v[i];
        }
        if (aux <= x){r = false;}
        else{
            r = true;
            System.out.println("The number of positions of the array that is "+
            "needed is: "+counter);
        }
        return r;
    }
}
