package Unit7;
import java.util.*;

public class Act22{
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
        
        int[]res = sumArray(v,w);
        for(i = 0; i < 5; i++){
            System.out.print(res[i] + " ");
        }
    }
    public static int[] sumArray(int [] v, int [] w){
        int aux = (int) Math.max(v.length, w.length);
        int[] res;
        res = new int[aux];
        int i;
        
        for (i = 0;i < aux;i++){
            if (i >= v.length){
                res[i] = w[i];
            }
            else{
                if (i >= w.length){
                    res[i] = v[i];
                }
                else{
                    res[i] = v[i] + w[i];
                }
            }
        }
        return res;
    }
}
