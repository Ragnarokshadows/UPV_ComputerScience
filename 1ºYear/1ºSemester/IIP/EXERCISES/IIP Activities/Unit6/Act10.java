package Unit6;
import java.util.*;

public class Act10 {
    public static double eVal(double x, double eps) {
        double fv, ti;
        int i;
    
        fv = 1;
        i = 1;
        ti = 1;
        while (ti >= eps) {
            ti = ti * (x/i);
            fv = fv + ti;
            i++;
        }
    
        return fv;
    }
    public static void main(String [] args){
        double x, eps,r,result;
        Scanner kbd = new Scanner(System.in).useLocale(Locale.US);
        
        System.out.print("Write a real number x: ");
        x = kbd.nextDouble();
        System.out.print("Write a small real number epsilon: ");
        eps = kbd.nextDouble();
        
        r = eVal(x,eps);
        result = Math.pow(Math.E,x);
        System.out.println("The value with te method is: "+r);
        System.out.println("The real value is: "+result);
    }
}