package Unit7;
import java.util.*;

public class Act25{
    public static void main(String [] args){
        Scanner kbd = new Scanner(System.in).useLocale(Locale.US);
        char[] v;
        v = new char[10];
        String aux;
        int i;
        for(i = 0; i < 10; i++){
            System.out.println("Write a number to the array: ");
            aux = kbd.nextLine();
            v[i] = aux.charAt(0);
        }
        
        notToYes(v);
        
        for(i = 0; i < 10; i++){
            System.out.println(v[i] + " ");
        }
    }
    public static void notToYes(char [] v){
        int i;
        
        for (i = 0;i < v.length - 2;i++){
            if (v[i] == 'n' && v[i + 1] == 'o' && v[i + 2] == 't'){
                v[i] = 'y';
                v[i + 1] = 'e';
                v[i + 2] = 's';
            }
        }
    }
}