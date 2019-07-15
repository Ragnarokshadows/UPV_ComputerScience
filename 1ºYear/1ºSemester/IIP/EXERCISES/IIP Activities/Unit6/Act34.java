package Unit6;
import java.util.*;

public class Act34{
    public static void main(String [] args){
        String r;
        char one;
        Scanner kbd = new Scanner(System.in).useLocale(Locale.US);
        
        System.out.print("Write a string: ");
        r = kbd.nextLine();
        System.out.print("Write a character: ");
        one = kbd.nextLine().charAt(0);
        
        System.out.println(encode(r,one));
    }
    public static String encode(String r, char one){
        int i,length,d;
        String result = "";
        char n = 'a';
        
        length = r.length();
        d = one - 'a';
        for(i = 0; i < length;i++){
            n = (char) (r.charAt(i) + d);
            if(r.charAt(i) < 'a' || r.charAt(i) > 'z'){
                n = (char) (r.charAt(i));
            }
            if(n > 'z'){
                n = (char) ('a' + ((d - 1) - ('z' - r.charAt(i))));
            }
            result = result + n;
        }
        return result;
    }
}