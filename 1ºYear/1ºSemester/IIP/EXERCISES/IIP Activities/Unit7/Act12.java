package Unit7;
import java.util.*;

public class Act12{
    public static void main(String [] args){
        Scanner kbd = new Scanner(System.in).useLocale(Locale.US);
        int[] v;
        v = new int[10];
        int aux;
        int i,x,n;
        for(i = 0; i < 10; i++){
            System.out.println("Write a number to the array: ");
            aux = kbd.nextInt();
            v[i] = aux;
        }
        System.out.println("Write a number x: ");
        x = kbd.nextInt();
        System.out.println("Write a number n: ");
        n = kbd.nextInt();
        
        System.out.println("The number of numbers lower by "+x+ " in "+
        "positions previous to "+n+" is: "+lowerPreviousTo(v,x,n));
    }
    public static int lowerPreviousTo(int [] v, int x, int n){
        int res = 0;
        int i;
        if (n <= 0){res = 0;}
        if (n > v.length){n = v.length;}
        for(i = 0; i < n; i++){
            if (v[i] < x){
                res++;
            }
        }
        return res;
    }
}