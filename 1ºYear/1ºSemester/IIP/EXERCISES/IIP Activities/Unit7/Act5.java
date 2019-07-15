package Unit7;
import java.util.*;

public class Act5{
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
        multBetween(v,b,e);
        
        for(i = 0; i < 10; i++){
            System.out.print(v[i]);
            System.out.print(" ");
        }
    }
    public static void multBetween(int [] v, int b, int e){
        int i;
        for(i = b;i <= e; i++){
            v[i] = v[i] * 2;
        }
    }
}