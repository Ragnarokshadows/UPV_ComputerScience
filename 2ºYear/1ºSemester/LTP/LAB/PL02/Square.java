package PL02;


/**
 * class Square.
 * 
 * @author LTP 
 * @version 2018-19
 */

public class Square extends Rectangle {
    public Square(double x, double y, double b) {
        super(x, y, b, b);
    }
    
    public boolean equals(Object o) {
        if (!(o instanceof Square)) return false;
        
        return super.equals(o);
    }

    public String toString() {
        return "Square:\n\t" +
            super.toString(1);
    }
    
    public double area(){
        return super.area();
    }
    
    public double perimeter(){
        return super.perimeter();
    }
}