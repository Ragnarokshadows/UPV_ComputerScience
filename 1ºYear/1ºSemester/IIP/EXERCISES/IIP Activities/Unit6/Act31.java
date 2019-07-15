package Unit6;
import java.util.*;

public class Act31{
    public static void main(String [] args){
        Scanner kbd = new Scanner(System.in).useLocale(Locale.US);
        String s;
        
        System.out.print("Write a word: ");
        s = kbd.nextLine();
        
        shiftToLeft(s);
    }
    public static void shiftToLeft(String s){
        String r = s;
        char one;
        int n = s.length(),i;
        
        System.out.println(s);
        for(i = 1;i <= n-1;i++){
            one = r.charAt(0);
            r = r + one;
            r = r.substring(1,n + 1);
            System.out.println(r);
        }
    }
}