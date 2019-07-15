package Unit6;
import java.util.*;

public class Act16{
    public static void main(String [] args){
        int n,b,r,result;
        Scanner kbd = new Scanner(System.in).useLocale(Locale.US);
        
        System.out.print("Write a positive integer number greater than 0: ");
        n = kbd.nextInt();
        System.out.print("Write the base of the log: ");
        b = kbd.nextInt();
        
        result = integerPart(n,b);
        
        System.out.println("The integer part of log("+n+") in base("+b+") is: "+result);
    }
    public static int integerPart(int n, int b){
        int r = 0;
        while ( n!= 0){
            n = n / b;
            r++;
        }
        return r - 1;
    }
}