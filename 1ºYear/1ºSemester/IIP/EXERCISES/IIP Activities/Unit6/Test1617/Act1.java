package Unit6.Test1617;
import java.util.*;

public class Act1{
    public static void main(String [] args){
        Scanner kbd = new Scanner(System.in).useLocale(Locale.US);
        double z;
        double eps;
        
        System.out.print("Write the value of z: ");
        z = kbd.nextDouble();
        System.out.print("Write the value of eps: ");
        eps = kbd.nextDouble();
        
        System.out.println("The result is: "+log(z,eps));
    }
    public static double log(double z, double eps){
        double x = z - 1;
        double res = 0;
        double t = x;
        int n = 1;
        
        while(Math.abs(t) >= eps){
            res = res + t;
            n++;
            t = (-1) * t * x * (n - 1) / n;
        }
        return res;
    }
}