package Unit7;

import java.awt.Point;

public class Act33{
    public class Polygon{
        private Point [] points;
        private int nPoints;
    
        public Polygon (int np){
            points = new Point[np];
            nPoints = 0;
        }
        
        public void addVertex(Point p){
            if (nPoints < points.length){
                points[nPoints] = p;
                nPoints++;
            }
        }
        
        public double perimeter(){
            double per = 0,dist;
            int i;
            if (nPoints < points.length){
                per = 0;
            }
            
            else{
                for (i = 1; i < points.length; i++){
                    dist = Math.sqrt(Math.pow(points[i].x - points[i - 1].x,2) +
                    Math.pow(points[i].y - points[i - 1].y,2));
                    per = per + dist;
                }
            
                dist = Math.sqrt(Math.pow(points[0].x - 
                    points[points.length - 1].x,2) +
                    Math.pow(points[0].y - points[points.length - 1].y,2));
                
                per = per + dist;
            }
            return per;
        }
    }
}