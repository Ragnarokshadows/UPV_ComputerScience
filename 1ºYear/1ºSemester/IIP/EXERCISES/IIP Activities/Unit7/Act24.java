package Unit7;
import java.util.*;

public class Act24{
    public static void main(String [] args){
        Scanner kbd = new Scanner(System.in).useLocale(Locale.US);
        double[] v;
        v = new double[2];
        double[] w;
        w = new double[5];
        double aux;
        int i;
        for(i = 0; i < 2; i++){
            System.out.println("Write a number to the array: ");
            aux = kbd.nextDouble();
            v[i] = aux;
        }
        for(i = 0; i < 5; i++){
            System.out.println("Write a number to the other array: ");
            aux = kbd.nextDouble();
            w[i] = aux;
        }
        
        System.out.println("The first ine is a prefix: " + prefix(v,w));
    }
    public static boolean prefix(double[]v, double[]w){
        boolean res = false;
        int i = 0;
        boolean end = false;
        if (v.length <= w.length){
            while (i < v.length && end == false){
                if (v[i] == w[i]){
                    res = true;
                }
                else{
                    res = false;
                    end = true;
                }
                i++;
            }
        }
        
        return res;
    }
}
