package Unit6.Test1516;
import java.util.*;

public class Act1{
    public static void main(String [] args){
        Scanner kbd = new Scanner(System.in).useLocale(Locale.US);
        double x;
        double n;
        
        System.out.print("Write the value of x: ");
        x = kbd.nextDouble();
        System.out.print("Write the value of n: ");
        n = kbd.nextDouble();
        
        System.out.println("The result is: "+sin(x,n));
    }
    public static double sin(double x,double n){
        double res = x;
        int i;
        double t = x;
        
        for(i = 1;i <= n;i++){
            t = (-t) * (x * x) / ((2 * i) * (2 * i +1));
            res = res + t;
        }
        return res;
    }
}