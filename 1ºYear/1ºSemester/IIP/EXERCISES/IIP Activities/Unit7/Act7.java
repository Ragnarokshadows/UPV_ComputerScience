package Unit7;
import java.util.*;

public class Act7{
    public static void main(String [] args){
        Scanner kbd = new Scanner(System.in).useLocale(Locale.US);
        int[] v;
        v = new int[10];
        int x;
        int i,b,e;
        for(i = 0; i < 10; i++){
            System.out.println("Write a number to the array: ");
            x = kbd.nextInt();
            v[i] = x;
        }
        System.out.println("Write a number b: ");
        b = kbd.nextInt();
        System.out.println("Write a number e: ");
        e = kbd.nextInt();
        toRightBetween(v,b,e);
        
        for(i = 0; i < 10; i++){
            System.out.print(v[i]);
            System.out.print(" ");
        }
    }
    public static void toRightBetween(int [] v, int b, int e){
        int i,j,length;
        length = v.length;
        int[] r;
        r = new int[length];
        for(i=0; i < v.length;i++){
            r[i] = v[i];
        }
        for(i = b + 1;i <= e; i++){
            v[i] = r[i - 1];
        }
        v[b] = r[e];
    } 
}