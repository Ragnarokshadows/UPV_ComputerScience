package Unit7;
import java.util.*;

public class Act27{
    public static void main(String [] args){
        Scanner kbd = new Scanner(System.in).useLocale(Locale.US);
        int[] a;
        a = new int[10];
        int aux;
        int i;
        for(i = 0; i < 10; i++){
            System.out.println("Write a number to the array: ");
            aux = kbd.nextInt();
            a[i] = aux;
        }
        
        digits(a);
    }
    public static void digits(int[]a){
        int i = 0;
        boolean end = false;
        
        while (i < a.length - 1 && end == false){
            if (a[i] != a[i + 1]){
                System.out.print(a[i]);
            }
            else{
                System.out.print(a[i]);
                end = true;
            }
            i++;
        }
        System.out.println("");
    }
}