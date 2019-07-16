package PL01;


/**
 * class Circle.
 * 
 * @author LTP 
 * @version 2018-19
 */

public class Circle extends Figure {
    private double radius;

    public Circle(double x, double y, double r) {
        super(x, y);
        radius = r;
    }
    
    public boolean equals(Object o) {
        if (!(o instanceof Circle)) return false;
        
        return super.equals(o) && radius == ((Circle) o).radius;
    }

    public String toString() {
        return "Circle:\n\t" +
            super.toString() +
            "\n\tRadius: " + radius;
    }
    
    public double area(){
        return Math.PI * Math.pow(radius, 2);
    }
    
    public double perimeter(){
        return 2 * Math.PI * radius;
    }
}