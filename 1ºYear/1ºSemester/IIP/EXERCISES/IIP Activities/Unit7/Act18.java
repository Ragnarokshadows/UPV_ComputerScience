package Unit7;
import java.util.*;

public class Act18{
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
        
        System.out.println("The number of ceros is: "+ceros(v));
    }
    public static int ceros(int [] v){
        int counter = 0;
        int i = v.length -1;
        
        while (i >= 0 && v[i] == 0){
            counter++;
            i--;
        }
        return counter;
    }
}