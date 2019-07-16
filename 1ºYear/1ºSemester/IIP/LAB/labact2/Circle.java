package labact2;

/** 
 * Class Circle: defines a circle of a given radius, color, center position,
 * with the following functionalities.
 * @author IIP-PRG English group
 * @version 2017-18
 */ 
public class Circle  {
    private double radius; 
    private String color;
    private int centerX, centerY;   
    
    /** Create a Circle of radius 50, black and center in (100,100). */
    public Circle() {
        radius = 50; 
        color = "black"; 
        centerX = 100;  
        centerY = 100; 
    }
    
    /** Create a Circle of radius r, color c and center in (cx,cy). 
     *  @param r double the radius. 
     *  @param col String the color.
     *  @param cx int the abscissa of the center.
     *  @param cy int the ordenate of the center.  
     */
    public Circle(double r, String col, int cx, int cy) {
        radius = r;  
        color = col; 
        centerX = cx; 
        centerY = cy; 
    }

    /** Return radius of Circle. 
     *  @return double, the radius.  
     */
    public double getRadius() { return radius; }    

    /** Return color of Circle.
     *  @return String, the color.   
     */
    public String getColor() { return color; }  

    /** Return abscissa of the center of Circle. 
     *  @return int, the abscissa of the center.   
     */
    public int getCenterX() { return centerX; }  

    /** Return ordenate of the center of Circle. 
     *  @return int, the ordenate of the center.  
     */
    public int getCenterY() { return centerY; }
 
    /** Update radius of Circle to newRadius. 
     *  @param newRadius double the new radius.  
     */
    public void setRadius(double newRadius) { radius = newRadius; }   

    /** Update color of Circle to newColor. 
     *  @param newColor String the new color.  
     */
    public void setColor(String newColor) { color = newColor; }   
 
    /** Update center of Circle to position (cx,cy). 
     *  @param cx int the new abscissa of the center.
     *  @param cy int the new ordenate of the center.
     */
    public void setCenter(int cx, int cy) { centerX = cx; centerY = cy; }
    
    /** Calculate area of Circle.
     *  @return double, the area.  
     */
    public double area() { return Math.PI * radius * radius; }
    
    /** Calculate perimeter of Circle.
     *  @return double, the perimeter. 
     */
    public double perimeter() { return 2 * Math.PI * radius; }
    
    /** Return a String with the components of Circle. 
     *  @return String, the components.   
     */
    public String toString() { 
        String res = "Circle of radius " + radius;
        res += ", color " + color;
        res += " and center (" + centerX + "," + centerY + ")";
        return res; 
    }
        
} // of Circle
