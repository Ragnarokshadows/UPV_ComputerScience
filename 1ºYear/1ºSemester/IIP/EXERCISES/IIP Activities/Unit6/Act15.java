package Unit6;
import java.util.*;

public class Act15{
    public static void main(String [] args){
        int n,r,result;
        Scanner kbd = new Scanner(System.in).useLocale(Locale.US);
        
        System.out.print("Write a positive integer number greater than 0: ");
        n = kbd.nextInt();
        
        result = integerPart(n);
        r = countDigits(n);
        
        System.out.println("The integer part of log("+n+") is: "+result);
        System.out.println("The digits of "+n+" is: "+r);
    }
    public static int integerPart(int n){
        int i,r = 0;
        
        for (i = 0; n !=0; i++){
            r = i;
            n = n/10;
        }
        
        return r;
    }
    public static int countDigits(int n){
        int r = 0;
        r = (int) Math.floor(Math.log10((double) n));
        r = r + 1;
        return r;
    }
}