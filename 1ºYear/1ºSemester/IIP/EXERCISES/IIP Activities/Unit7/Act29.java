package Unit7;
import java.util.*;

public class Act29{
    public static void main(String [] args){
        Scanner kbd = new Scanner(System.in).useLocale(Locale.US);
        int[] v;
        v = new int[5];
        int aux;
        int i;
        for(i = 0; i < 5; i++){
            System.out.println("Write a number to the array: ");
            aux = kbd.nextInt();
            v[i] = aux;
        }
        
        System.out.println(sum(v));
    }
    public static int sum(int [] a){
        int i,j;
        int sum = 0;
        
        for (i = 0, j = a.length - 1;i <= j;i++,j--){
            if (i == j){
                sum = sum + a[i];
            }
            if (i != j && a[i] == a[j]){
                sum = sum + a[i] * 2;
            }
        }
        
        return sum;
    }
}