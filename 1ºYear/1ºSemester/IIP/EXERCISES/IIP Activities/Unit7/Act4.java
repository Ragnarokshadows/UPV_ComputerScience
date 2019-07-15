package Unit7;
import java.util.*;

public class Act4{
    public static void main(String [] args){
        Scanner kbd = new Scanner(System.in).useLocale(Locale.US);
        int[] v;
        v = new int[10];
        int x,r;
        int i = 0;
        for(i = 0; i < 10; i++){
            System.out.println("Write a number to the array: ");
            x = kbd.nextInt();
            v[i] = x;
        }
        System.out.println("Write a number: ");
        r = kbd.nextInt();
        System.out.println("The number of occurrences is: "+ocurrence(v,r));
    }
    public static int ocurrence(int[] v, int x){
        int i = v.length;
        int j;
        int counter = 0;
        for(j = 0; j < i;j++){
            if (v[j] == x){
                counter++;
            }
        }
        return counter;
    }
}