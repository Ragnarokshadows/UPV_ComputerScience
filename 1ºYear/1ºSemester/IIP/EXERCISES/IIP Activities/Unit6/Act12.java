package Unit6;
import java.util.*;

public class Act12{
    public static void main(String [] args){
        int m,s,x,i;
        i = 1;
        Scanner kbd = new Scanner(System.in).useLocale(Locale.US);
        
        System.out.println("Write the minuend: ");
        m = kbd.nextInt();
        System.out.println("Write the subtrahend: ");
        s = kbd.nextInt();
        x = m;
        
        while(i <= s){
            x--;
            i++;
        }
        System.out.println("The result is: "+x);
    }
}