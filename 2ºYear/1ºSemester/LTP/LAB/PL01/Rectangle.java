package PL01;


/**
 * class Rectangle.
 * 
 * @author LTP 
 * @version 2018-19
 */

public class Rectangle extends Figure {
    private double base;
    private double height;

    public Rectangle(double x, double y, double b, double h) {
        super(x, y);
        base = b;
        height = h;
    }
    
    public boolean equals(Object o) {
        if (!(o instanceof Rectangle)) return false;
        
        return super.equals(o) && base == ((Rectangle) o).base && 
        height == ((Rectangle) o).height;
    }

    public String toString() {
        return "Rectangle:\n\t" +
            super.toString() +
            "\n\tBase: " + base +
            "\n\tHeight: " + height;
    }
    
    public String toString(int i) {
        return super.toString() +
            "\n\tBase: " + base +
            "\n\tHeight: " + height;
    }
    
    public double area(){
        return base * height;
    }
    
    public double perimeter(){
        return base * 2 + height * 2;
    }
}