package labact6;
import java.util.Scanner;
import java.util.Locale;
/**
 * IIPMathTest let the user compare IIPMath: sqrt and log methods 
 * vs those in Math standard library.
 * 
 * @author (IIP-PRG-DSIC-ETSINF)
 * @version (curs 2017-18)
 */
public class TestIIPMath {
    
    /** It is not possible to create new objects for this class. */
    private TestIIPMath() { }    
    
    public static void main(String[] args) {
        
        Scanner tcl = new Scanner(System.in).useLocale(Locale.US);
        
        System.out.println("Comparison of sqrt and log (IIPMath vs Math).");
        System.out.println("0 or negative value to finish.\n");
        
        System.out.print("Valor: ");
        double val = tcl.nextDouble();        
        
        while (val > 0) { 
            
            double xr1 = IIPMath.sqrt(val);
            double xr2 = Math.sqrt(val); 
            System.out.printf(Locale.US, 
                    "%14s %+1.16e \n %14s %+1.16e %8s %1.3g%n",
                    "SQRT-- IIPMath:", xr1, "Math:", xr2, "|Diff|:",
                    Math.abs(xr1 - xr2));
            
            double xl1 = IIPMath.log(val);
            double xl2 = Math.log(val);  
          
            System.out.printf(Locale.US,
                   "%14s %+1.16e \n %14s %+1.16e %8s %1.3g%n",
                   "LOG -- IIPMath:", xl1, "Math:", xl2, "|Diff|:",
                   Math.abs(xl1 - xl2));
                
            System.out.printf("%1$6s %2$20s %3$18s%n", "------", 
                              "--------------------------------",
                              "-----------------");
            
            System.out.print("Val: ");
            val = tcl.nextDouble();
        }
    }
         
            
}
