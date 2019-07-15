package Unit6;
import java.util.*;

public class Act14{
    public static void main(String [] args){
        int n,r;
        Scanner kbd = new Scanner(System.in).useLocale(Locale.US);
        
        System.out.print("Write a integer number: ");
        n = kbd.nextInt();
        r = inverse(n);
        System.out.println(r);
    }
    public static int inverse(int n){
        int r = 0;
        while(n != 0){
            r = r * 10;
            r = r + n%10;
            n = n/10;
        }
        return r;
    }
}