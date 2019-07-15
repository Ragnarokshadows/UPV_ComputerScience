package Unit7;
import java.util.*;

public class Act26{
    public static void main(String [] args){
        Scanner kbd = new Scanner(System.in).useLocale(Locale.US);
        int[] a;
        a = new int[5];
        int[] b;
        b = new int[10];
        int i, aux;
        
        for(i = 0; i < 5; i++){
            System.out.println("Write a number to the array: ");
            aux = kbd.nextInt();
            a[i] = aux;
        }
        for(i = 0; i < 10; i++){
            System.out.println("Write a number to the other array: ");
            aux = kbd.nextInt();
            b[i] = aux;
        }
        
        sumInB(a, b);
        
        for(i = 0; i < 10; i++){
            System.out.print(b[i] + " ");
        }
        System.out.println("");
    }
    public static void sumInB(int[]a, int[]b){
        int n = (int) Math.min(a.length, b.length);
        int i;
        
        for (i = 0;i < n;i++){
            b[i] = b[i] + a[i];
        }
    }
}