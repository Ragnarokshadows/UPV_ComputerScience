package Unit7;
import java.util.*;

public class Act20{
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
        
        System.out.println("The sum of all elements after the"+
        " first odd: "+sumAfterOdd(v));
    }
    public static int sumAfterOdd(int [] v){
        int res = 0;
        int i;
        boolean end = false;
        
        for (i = 0;i < v.length  && end == false;i++){
            if (v[i] % 2 != 0){
                end = true;
                i++;
                while(i < v.length ){
                    res = res + v[i];
                    i++;
                }
            }
        }
        return res;
    }
}