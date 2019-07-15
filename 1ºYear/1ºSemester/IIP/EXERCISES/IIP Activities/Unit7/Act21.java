package Unit7;
import java.util.*;

public class Act21{
    public static void main(String [] args){
        Scanner kbd = new Scanner(System.in).useLocale(Locale.US);
        int[] v;
        v = new int[5];
        int[] w;
        w = new int[5];
        int aux;
        int i,x,n;
        for(i = 0; i < 5; i++){
            System.out.println("Write a number to the array: ");
            aux = kbd.nextInt();
            v[i] = aux;
        }
        for(i = 0; i < 5; i++){
            System.out.println("Write a number to the other array: ");
            aux = kbd.nextInt();
            w[i] = aux;
        }
        
        System.out.println("The escalar product of v and w is: " + escalar(v,w));
    }
    public static int escalar(int [] v, int [] w){
        int res = 0;
        int i;
        
        for (i=0;i < v.length;i++){
            res = res + (v[i] * w[i]);
        }
        
        return res;
    }
}