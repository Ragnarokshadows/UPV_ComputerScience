package Unit6.Test1314;
import java.util.*;

public class Act1{
    public static void main(String [] args){
        Scanner kbd = new Scanner(System.in).useLocale(Locale.US);
        double eps;
        
        System.out.print("Write the value of eps: ");
        eps = kbd.nextDouble();
        
        System.out.println("The result is: "+pi(eps));
    }
    public static double pi(double eps){
        double t = 1;
        double res = 1;
        int n = 1;
        
        while(2 * t > eps){
            t = t * n / (2 * n + 1);
            res = res + t;
            n++;
        }
        return 2 * res;
    }
}