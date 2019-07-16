package PL01;


/**
 * class FiguresGroup.
 * 
 * @author LTP 
 * @version 2018-19
 */

public class FiguresGroup {
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
}