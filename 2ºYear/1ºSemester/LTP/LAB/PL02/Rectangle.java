package PL02;


/**
 * class Rectangle.
 * 
 * @author LTP 
 * @version 2018-19
 */

public class Rectangle extends Figure implements ComparableRange, Printable {
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
    
    public int compareToRange(Object o){
        int res = 0;
        double sum,area1,area2;
        
        area1 = this.area();
        area2 = ((Rectangle) o).area();
        sum = area1 + area2;
        
        if(Math.abs(area1 - area2) > (0.1 * sum)){
            res = this.compareTo(o);
        }
        
        return res;
    }
    
    public void print(char c){
        int b = (int) base;
        int h = (int) height;
        for (int i = 0; i < h; i++) {
            for (int j = 0; j < b; j++) {
                System.out.print(c);
            }
            System.out.println();
        }
    }
}