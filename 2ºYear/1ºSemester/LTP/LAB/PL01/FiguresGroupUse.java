package PL01;


/**
 * class FiguresGroupUse.
 * 
 * @author LTP 
 * @version 2018-19
 */
public class FiguresGroupUse {
    public static void main(String[] args) {
        FiguresGroup g = new FiguresGroup();
        FiguresGroup f = new FiguresGroup();
        FiguresGroup h = new FiguresGroup();
        
        Circle c = new Circle(10, 5, 3.5);
        Triangle t = new Triangle(10, 5, 6.5, 32);
        Rectangle r = new Rectangle(10, 5, 6.5, 32);
        Square s = new Square(10, 5, 6.5);
        
        g.add(c);
        System.out.println("The area of the circle is : " + c.area());
        g.add(t);
        System.out.println("The area of the triangle is : " + t.area());
        g.add(r);
        System.out.println("The area of the rectangle is : " + r.area());
        g.add(s);
        System.out.println("The area of the square is : " + s.area());
        System.out.println(g);
        
        f.add(t);
        f.add(c);
        f.add(r);
        f.add(s);
        
        h.add(c);
        h.add(t);
        h.add(t);
        h.add(c);
        
        System.out.printf("\nG is equal to F: %b\n", g.equals(f));
        System.out.printf("G is equal to H: %b\n", g.equals(h));
        System.out.printf("F is equal to H: %b\n", f.equals(h));
    }
}