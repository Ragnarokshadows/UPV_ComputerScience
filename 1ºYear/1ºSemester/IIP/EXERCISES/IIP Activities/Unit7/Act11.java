package Unit7;
import java.util.*;

public class Act11{
    public static void main(String [] args){
        Scanner kbd = new Scanner(System.in).useLocale(Locale.US);
        int[] v;
        v = new int[10];
        int x;
        int i;
        for(i = 0; i < 10; i++){
            System.out.println("Write a number to the array: ");
            x = kbd.nextInt();
            v[i] = x;
        }
        
        System.out.println("The number of odd numbers in even"+
        " positions: "+oddInEven(v));
    }
    public static int oddInEven(int [] v){
        int res = 0;
        int i;
        for(i = 0; i < v.length; i = i+2){
            if (v[i] % 2 != 0){
                res++;
            }
        }
        return res;
    }
}