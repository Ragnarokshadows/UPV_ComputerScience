package pract1;

// Imports class Graph2D from package graph2D
import graph2D.Graph2D;
// Imports class Color from package java.awt for changing
// the colour of the figures to be drawn.
import java.awt.Color;
import java.awt.Font;

/**
 * The class <code>FigRecSimple</code> contains the
 * basic components for recursively drawing figures.
 * 
 * @author (PRG - DSIC - ETSINF, pmarques) 
 * @version (academic year 2017 - 2018)
 */
public class FigRecSimple {
    
    // This declaration avoids the default constructor
    // to be available.
    private FigRecSimple() { }

    /**
      * Draws a serie of squares where ones are included in others.
      * The number of squares is given by the parameter
      * <code>initialOrder</code>.
      * PRECONDITION: <code>initialOrder >= 0</code>
      * @param initialOrder Number of squares to be drawn.
      */
    public static void figRec(int initialOrder) {

        // Window title
        String tit = "Nested squares 1. Orden: " + initialOrder;

        // Actual ranges of the axes in the drawing canvas
        double minX = -0.1; double maxX = 1.1;
        double minY = -0.1; double maxY = 1.1;
        
        Graph2D gd = new Graph2D(minX, maxX, minY, maxY);
        gd.setTitle(tit); 
        
        // Initial call to the method that draws 
        // squares, with the first of lateral length 1
        // and indicating the initial order of the whole
        // figure.
        figRec(gd, initialOrder, 1.0);
    }
    
    /**
     * Draws a square of order n and lateral length of l.
     * @param g: the drawing canvas, an object of the class Graph2D
     * @param n: order of the figure with nested squares
     * @param l: lateral length of the square
     * PRECONDITION: n >= 0 
     */
    private static void figRec(Graph2D g, int ord, double l) {
        // Trivial case n == 0, nothing is done
        if (ord >= 1) {
            // The squares have his upper left corner at (0.0, 1.0)
            // The squares are drawn in black with the border with set to 2
            g.drawRect(0.0, 1.0, l, l, Color.BLACK, 2);
            
            // Compute the lower right corner
            double xID = l; double yID = 1.0 - l;
            // and draow an string indicating the order in blue
            g.setForegroundColor(Color.BLUE);
            g.drawString(xID, yID, " " + ord);
            
            // Each new square has lateral length a 10% less than
            // the previous one
            figRec(g, ord - 1, l * 0.9);
        }
    }

    public static void main( String [] args )
    {
        figRec( 3 );
        figRec( 5 );
    }
}
