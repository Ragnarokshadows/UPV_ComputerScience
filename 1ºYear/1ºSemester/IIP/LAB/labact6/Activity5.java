package labact6;
// Import class Graph2D (graph2D package).
import graph2D.Graph2D;
// Import class Color (java.awt package) for
// changing colors of represented elements.
import java.awt.Color;

public class Activity5 {  
    public static void sqrtAndLog() {
        
        double xMin = -1.0;
        double xMax = 15.0;
        double yMin = -3.0;
        double yMax = +4.0;

        
        Graph2D gd1 = new Graph2D(xMin, xMax , yMin, yMax);
                                    
        double delta = (xMax - xMin) / Graph2D.INI_WIDTH;      
        for (double x = xMin; x <= xMax; x = x + delta) {
            if (x >= 0){
                double y = IIPMath.sqrt(x);
                gd1.drawPoint(x, y, Color.GREEN, 2);
            }
        }
        for (double x = xMin; x <= xMax; x = x + delta) {
            if (x > 0){
                double y = IIPMath.log(x);
                gd1.drawPoint(x, y, Color.BLUE, 3);
            }
        }        
    }
}