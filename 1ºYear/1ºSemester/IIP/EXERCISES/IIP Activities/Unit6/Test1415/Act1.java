package Unit6.Test1415;
import java.util.*;

public class Act1{
    public static void main(String [] args){
        Scanner kbd = new Scanner(System.in).useLocale(Locale.US);
        double eps;
        
        System.out.print("Write the value of eps: ");
        eps = kbd.nextDouble();
        
        System.out.println("The result is: "+goldenRatio(eps));
    }
    public static double goldenRatio(double eps){
        double c,b,a;
        double diff,res;
        
        c = 1.0;
        b = 1.0;
        diff = 1;
        while(Math.abs(diff) >= eps){
            a = c + b;
            diff = b / c - a / b;
            c = b;
            b = a;
        }
        return b / c;
    }
}