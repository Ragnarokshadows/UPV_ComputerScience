package PL02;
import java.util.*;


/**
 * class FiguresGroup.
 * 
 * @author LTP 
 * @version 2018-19
 */

public class FiguresGroup implements Printable{
    private static final int NUM_FIGURES = 10;
    private Figure[] figuresList = new Figure[NUM_FIGURES];
    private int numF = 0;
    
    public void add(Figure f) { figuresList[numF++] = f; }
    
    public String toString() {
        String s = "";
        for (int i = 0; i < numF; i++) {
            s += "\n" + figuresList[i];
        }
        return s;
    }

    private boolean found(Figure f) {
        for (int i = 0; i < numF; i++) {
            if (figuresList[i].equals(f)) return true;
        }
        return false;
    }

    private boolean included(FiguresGroup g) {
        for (int i = 0; i < g.numF; i++) {
            if (!found(g.figuresList[i])) return false;
        }
        return true;
    }
    
    public boolean equals(Object o) {
        if (!(o instanceof FiguresGroup)) { return false; }
        FiguresGroup f = (FiguresGroup) o;
        
        return this.included(f) && f.included(this);
    }
    
    public double area(){
        double total = 0;
        
        for(int i = 0;i < numF;i++){
            total += figuresList[i].area();
        }
        
        return total;
    }
    
    public Figure greatestFigure(){
        Figure great = null;
        
        for(int i = 0;i < numF;i++){
            if (i == 0){
                great = figuresList[i];
            }
            else{
                if (great.area() < figuresList[i].area()){
                    great = figuresList[i];
                }
            }
        }
        
        return great;
    }
    
    public List orderedList(){
        List res = new LinkedList();
        
        if(numF > 0){
            res.add(figuresList[0]);
        }
        
        for (int i = 1; i < numF; i++) {
            int j = res.size() - 1; 
            while (j >= 0 && figuresList[i].compareTo(res.get(j)) < 0) {
                j--;
            }
            res.add(j + 1,figuresList[i]);
        }
        
        return res;
    }
    
    public void print(char c) {
        for (int i = 0; i < numF; i++) {
            if (figuresList[i] instanceof Printable){
                ((Printable) figuresList[i]).print(c);
            }
        }
    }
    
    public void printArea(){        
        for(int i = 0;i < numF;i++){
            System.out.println("Area: " + figuresList[i].area());
        }
    }
}