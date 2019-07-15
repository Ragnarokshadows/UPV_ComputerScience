package Unit6;
import java.util.*;

public class Act9{
    public static void main(String [] args){
        int c,b,a,n,i;
        Scanner kbd = new Scanner(System.in).useLocale(Locale.US);
        c = 0;
        b = 1;
        a = 1;
        
        System.out.print("Write a number: ");
        n = kbd.nextInt();
        
        for(i=0;i<n;i++){
            c = b;
            b = a;
            a = c + b;
        }
        System.out.println(c);
    }
}