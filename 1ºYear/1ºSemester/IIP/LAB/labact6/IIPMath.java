package labact6;
/**
 * Class IIPMath implements some mathematical operations:
 * IIPMath.sqrt(double) and IIPMath.log(double).
 *
 * @author (IIP-PRG-DSIC-ETSINF)
 * @version (Year 2017-18)
 */
public class IIPMath {
    
    public final static double LOG_2 = 0.6931471805599453;
    
    /** No objects can be created for this datatype (utility class) */
    private IIPMath() { }    
    
    /**
     * Calculate square root of x with a precision epsilon
     * @param x. Value.
     * @param epsilon. Error
     * @return double. natural logarithm of x with a maximum error eps
     */

    public static double sqrt(double x, double epsilon) {
      // TO COMPLETE
      double t = (1 + x) / 2;
      double res = t + 1;
      
      while ((res - t) >= epsilon){
          res = t;
          t = (t + (x / t)) / 2;
      }
      return t;
    }            

    /**
     * Calculate square root of x with a precision 1e-15.
     * @param x. Value.
     * @return double. natural logarithm of x with a maximum error 1e-15
     */
    public static double sqrt(double x) {    
      // TO COMPLETE
      return sqrt(x,1e-15);
    }  
                    
    /* ******************************************************** */
    /* ******************************************************** */
    /* ******************************************************** */
                
    /** Returns log(z), 1/2 <= z < 1, with an error epsilon > 0.
     * @param z. Value.
     * @param epsilon. Error
     * @return double. natural logarithm of z with a maximum error eps
     */
    public static double logBase(double z, double epsilon) {
      // TO COMPLETE
      double y = (1 - z) / (1 + z);
      double u = y;
      double res = 0.0;
      int k = 1;
      
      while (u >= epsilon){
          res += u;
          u = u * y * y * (2 * k - 1) / (2 * k + 1);
          k++;
      }
      return (-2) * (res + u); 
    }
            
    /** Returns log(x), x > 0, with error epsilon > 0.
     * C@param x. Value.
     * @param epsilon. Error
     * @return double. natural logarithm of x with a maximum error eps
     */
    public static double log(double x, double epsilon) {
      // TO COMPLETE
      double res = 0.0;
      int m;
      double z = x;
      if (x >= 1.0/2.0 && x < 1){
          res = logBase(x, epsilon);
      }
      if (x >= 1){
          for(m = 0;z >= 1;m++){
              z = z / 2.0;
          }
              
          res = m * LOG_2 + logBase(z,epsilon);
      }
      if (x < 1.0/2.0){
          for(m = 0; z < 1.0/2.0;m++){
              z = z * 2.0;
          }

          res = (-1) * m * LOG_2 + logBase(z,epsilon);
      }
      return res;
    }
   
    /** Returns log(x), x > 0, with error 1e-15.
     * @param x. Value.
     * @return double. natural logarithm of x with a maximum error 1e-15
     */
    public static double log(double x) {    
      // TO COMPLETE
      return log(x,1e-15);
    }
}
