package Unit7;
import java.util.*;

public class Act30{
    public static void main(String [] args){
        Scanner kbd = new Scanner(System.in).useLocale(Locale.US);
        int[] v;
        v = new int[5];
        int aux;
        int i,j;
        for(i = 0; i < 5; i++){
            System.out.println("Write a number to the array: ");
            aux = kbd.nextInt();
            v[i] = aux;
        }
        System.out.println("Write a number i: ");
        i = kbd.nextInt();
        System.out.println("Write a number j: ");
        j = kbd.nextInt();
        
        System.out.println(sum(v,i,j));
    }
    public static int sum(int[]a, int i, int j){
        int sum = 0;
        
        while (i <= j){
            if (i == j){
                sum = sum + a[i];
            }
            if (i != j && a[i] == a[j]){
                sum = sum + a[i] * 2;
            }
            i++;
            j--;
        }
        
        return sum;
    }
}