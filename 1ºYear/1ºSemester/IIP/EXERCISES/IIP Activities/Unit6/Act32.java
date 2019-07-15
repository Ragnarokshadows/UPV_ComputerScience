package Unit6;
import java.util.*;

public class Act32{
    public static void main(String [] args){
        Scanner kbd = new Scanner(System.in).useLocale(Locale.US);
        String s;
        
        System.out.print("Write a word: ");
        s = kbd.nextLine();
        
        shiftToLeft(s);
    }
    public static void shiftToLeft(String s){
        String one,two,r;
        int n = s.length(),i;
        
        for(i = 0;i <= n-1;i++){
            one = s.substring(0,i);
            two = s.substring(i,n);
            r = two + one;
            System.out.println(r);
        }
    }
}