package Unit7;
import java.util.*;

public class Act31{
    public static void main(String [] args){
        Scanner kbd = new Scanner(System.in).useLocale(Locale.US);
        char[] v;
        v = new char[3];
        char[] w;
        w = new char[10];
        String aux;
        int i;
        for(i = 0; i < 3; i++){
            System.out.println("Write a char to the array: ");
            aux = kbd.nextLine();
            v[i] = aux.charAt(0);
        }
        for(i = 0; i < 10; i++){
            System.out.println("Write a char to the other array: ");
            aux = kbd.nextLine();
            w[i] = aux.charAt(0);
        }
        
        System.out.println(detect(v,w));
    }
    public static boolean detect(char[]s1, char[]s2){
        int i = 0, j = 0, aux = 0;
        boolean end = false, res = false;
        
        while (i < s2.length && end == false){
            if (s2[i] == s1[j]){
                aux++;
                j++;
            }
            if (j >= s1.length){
                end = true;
            }
            i++;
        }
        
        return end;
    }
}