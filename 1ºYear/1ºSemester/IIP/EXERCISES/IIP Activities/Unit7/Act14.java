package Unit7;
import java.util.*;

public class Act14{  
    public static void main(String [] args){
        Scanner kbd = new Scanner(System.in).useLocale(Locale.US);
        int[] v;
        v = new int[10];
        int aux;
        int i;
        for(i = 0; i < 10; i++){
            System.out.println("Write a number to the array: ");
            aux = kbd.nextInt();
            v[i] = aux;
        }
        System.out.println("The index of the first three consecutive"+
        " numbers is: "+threeConsecutive(v));
    }
    public static int threeConsecutive(int [] v){
        int r = -1;
        int i;
        boolean end = false;
        
        for(i = 0; i < (v.length - 2) && end == false;i++){
            if (((v[i] == v[i + 1] + 1) && (v[i] == v[i + 2] + 2)) ||
            ((v[i] == v[i + 1] - 1) && (v[i] == v[i + 2] - 2))){
                r = i;
                end = true;
            }
        }
        return r;
    }
}