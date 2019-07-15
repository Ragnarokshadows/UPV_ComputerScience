package Unit6;
import java.util.*;

public class Act26{
    public static void main(String [] args){
        int a,n;
        Scanner kbd = new Scanner(System.in).useLocale(Locale.US);
        
        System.out.print("Write a number a: ");
        a = kbd.nextInt();
        System.out.print("Write a number n: ");
        n = kbd.nextInt();
        
        System.out.println("The result is: "+multiplication(a,n));
    }
    public static int multiplication(int a, int n){
        int result = 0;
        while(n > 0){
            if (n % 2 != 0){
                result = result + a;
            }
            a = a * 2;
            n = n / 2;
        }
        return result;
    }
}