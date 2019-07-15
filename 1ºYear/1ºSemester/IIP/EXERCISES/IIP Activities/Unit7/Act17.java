package Unit7;
import java.util.*;

public class Act17{   
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
        System.out.println("Write a number x: ");
        x = kbd.nextInt();
        System.out.println("Write a number n: ");
        n = kbd.nextInt();
        
        System.out.println("The index where the subsequence starts is: "+
        subsequence(v, x, n));
    }
    public static int subsequence(int [] v, int x, int n){
        int i, aux = 0;
        int r = -1, j;
        boolean end1 = false,end2;
        for(i = 0; i < v.length - n && end1 == false; i++){
            end2 = false;
            for(j = 0;j < n && end2 == false;j++){
                r = i;
                if(v[i + j] > x){
                    end1 = true;
                    end2 = false;
                }
                else{
                    r = -1;
                    end1 = false;
                    end2 = true;
                }
            }
        }
        return r;
    }
}