package test2;
/**
 * IIPMath class. Exercise 1 - Turn 1
 *
 * @author IIP
 * @version Second partial - Year 2017/18
 */
public class IIPMath {
    /** Returns the number of terms that must be calculated 
     *  of the series:
     *     a_1 = Square root of 2 
     *     a_i = Square root of (2 * a_(i-1))
     *  until obtaining one with a value greater than or 
     *  equal to 2 - epsilon.
     *  PRECONDITION: 0 < epsilon
     */    
    public static int numTerms(double epsilon) {  
        int i = 1;
        double a = Math.sqrt(2);
        
        while(a < 2 - epsilon){
            i++;
            a = Math.sqrt(2 * a);
        }
        
        return i;
    }    
}