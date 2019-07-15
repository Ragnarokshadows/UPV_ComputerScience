package Unit7;
import java.util.*;

public class Act19{
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
        
        System.out.println("The index of the last odd number is: "+lastOdd(v));
    }
    public static int lastOdd(int [] v){
        int index = -1;
        int i;
        boolean end = false;
        
        for (i = v.length - 1;i >= 0 && end == false;i--){
            if (v[i] % 2 != 0){
                end = true;
                index = i;
            }
        }
        return index;
    }
}