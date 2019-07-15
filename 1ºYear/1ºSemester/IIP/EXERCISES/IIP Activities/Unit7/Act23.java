package Unit7;
import java.util.*;

public class Act23{
    public static void main(String [] args){
        Scanner kbd = new Scanner(System.in).useLocale(Locale.US);
        String[] v;
        v = new String[6];
        String aux;
        int i;
        for(i = 0; i < 6; i++){
            System.out.println("Write a word to the array: ");
            aux = kbd.nextLine();
            v[i] = aux;
        }
        
        System.out.println("The array is palindrome: " + palindrome(v));
    }
    public static boolean palindrome(String[]v){
        int i = 0;
        int length = v.length - 1;
        boolean end = false;
        boolean res = false;
        
        while(i <= (length / 2) && end == false){
            if(v[i].equals(v[length - i])){
                res = true;
            }
            else{
                res = false;
                end = true;
            }
            i++;
        }
        return res;
    }
}
