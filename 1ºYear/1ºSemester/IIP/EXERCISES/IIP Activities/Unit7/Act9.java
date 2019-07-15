package Unit7;
import java.util.*;

public class Act9{
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
        
        System.out.println("The maximum number is: "+maxStored(v));
    }
    public static int maxStored(int [] v){
        int res = v[0];
        int i;
        for(i = 1; i < v.length; i++){
            if (v[i] > res){
                res = v[i];
            }
        }
        return res;
    }
}