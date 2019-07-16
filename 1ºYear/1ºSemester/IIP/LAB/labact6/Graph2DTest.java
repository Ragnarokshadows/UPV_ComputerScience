package labact6;
// Import class Graph2D (graph2D package).
import graph2D.Graph2D;
// Import class Color (java.awt package) for
// changing colors of represented elements.
import java.awt.Color;

/**
 * Graph2DTest simple test for class Graph2D.
 * Draws functions Math.sin(x) and Math.sin(x)/x.
 * 
 * @author (IIP-PRG-DSIC-ETSINF)
 * @version (Year 2017-18)
 */
public class Graph2DTest {
        
    /** Draw sin(x) and sin(x)/x with
     * x in [-1.0: +PI*4] and f(x) in [-1.10 : +1.10].
     * Representation by consecutive dots.
     */    
    public static void test1() {
        
        double xMin = -1.0;
        double xMax = Math.PI * 4;
        double yMin = -1.10;
        double yMax = +1.10;

        
        Graph2D gd1 = new Graph2D(xMin, xMax , yMin, yMax);
                                    
        double delta = (xMax - xMin) / Graph2D.INI_WIDTH;      
        for (double x = xMin; x <= xMax; x = x + delta) {
            double y = Math.sin(x);
            gd1.drawPoint(x, y, Color.BLUE, 2);
        }
        
        // Change drawing color:
        gd1.setForegroundColor(Color.GREEN);
        // Change thickness:
        gd1.setThickness(3);
        for (double x = xMin; x <= xMax; x = x + delta) {
            double y = Math.sin(x) / x;
            gd1.drawPoint(x, y);
            
            // Equivalent way without changing values:
            // gd1.drawPoint(x, y, Color.GREEN, 2);
        }        
    }

}
