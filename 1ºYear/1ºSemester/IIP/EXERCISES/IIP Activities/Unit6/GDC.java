package Unit6;

public class GDC{
    public static int gdc(int a,int b){
        while(a != b){
            if(a > b){
                a = a - b;
            }
            else{
                b = b - a;
            }
        }
        return a;
    }
}