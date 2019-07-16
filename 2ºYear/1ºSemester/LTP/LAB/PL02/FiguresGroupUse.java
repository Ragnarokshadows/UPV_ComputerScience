package PL02;


/**
 * class FiguresGroupUse.
 * 
 * @author LTP 
 * @version 2018-19
 */
public class FiguresGroupUse {
    public static void main(String[] a) { 
        FiguresGroup g = new FiguresGroup();
        g.add(new Circle(1.0, 6.0, 6.0));
        g.add(new Rectangle(2.0, 5.0, 10.0, 10.0));
        g.add(new Triangle(3.0, 4.0, 10.0, 2.0));
        g.add(new Circle(4.0, 3.0, 1.0));
        g.add(new Triangle(5.0, 1.0, 1.0, 2.0));
        g.add(new Square(6.0, 7.0, 15));
        g.add(new Rectangle(7.0, 2.0, 1.0, 1.0));
        //g.printArea();
        //System.out.println(g.orderedList());
        g.print('a');
    }
}