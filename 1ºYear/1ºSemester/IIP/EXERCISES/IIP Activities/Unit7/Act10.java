package Unit7;
import java.util.*;

public class Act10{
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
        
        System.out.println("The second maximum number is: "+secondMaxStored(v));
    }
    public static int secondMaxStored(int [] v){
        int res = v[0];
        int i;
        int aux = res;
        for(i = 1; i < v.length; i++){
            if (v[i] > res){
                aux = res;
                res = v[i];
            }
        }
        return aux;
    }
}