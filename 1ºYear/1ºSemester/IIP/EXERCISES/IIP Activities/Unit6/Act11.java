package Unit6;
import java.util.*;

public class Act11{
    public static void main(String [] args){
        int a,b,x,i;
        i = 0;
        Scanner kbd = new Scanner(System.in).useLocale(Locale.US);
        
        System.out.print("Write an integer number a>=0: ");
        a = kbd.nextInt();
        System.out.print("Write an integer number b>0: ");
        b = kbd.nextInt();
        x = a;
        
        while(x >= b){
            x = x - b;
            i++;
        }
        System.out.println("The quotient is: "+i);
        System.out.println("The reminder is: "+x);
    }
}