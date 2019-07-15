package Unit7;
import java.util.*;

public class Act28{
    public static void main(String [] args){
        Scanner kbd = new Scanner(System.in).useLocale(Locale.US);
        int[] a;
        a = new int[10];
        int aux;
        int i;
        for(i = 0; i < 10; i++){
            System.out.println("Write a number to the array: ");
            aux = kbd.nextInt();
            a[i] = aux;
        }

        System.out.println(digits(a));
    }
    public static int digits(int[]a){
        int i = 0;
        int res = 0;
        boolean end = false;
        
        while (i < a.length - 1 && end == false){
            if (a[i] != a[i + 1]){
                res = (res + a[i]) * 10;
            }
            else{
                res = res + a[i];
                end = true;
            }
            i++;
        }
        return res;
    }
}