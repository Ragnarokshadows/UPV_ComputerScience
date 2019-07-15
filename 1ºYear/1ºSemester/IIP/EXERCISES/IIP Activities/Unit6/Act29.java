package Unit6;
import java.util.*;

public class Act29{
    public static void main(String [] args){
        String r;
        Scanner kbd = new Scanner(System.in).useLocale(Locale.US);
        
        System.out.print("Write a string: ");
        r = kbd.nextLine();
        
        System.out.println("Number of changes: "+changeToCapital(r));
    }
    public static int changeToCapital(String r){
        int x = 0,i,length,d;
        String result = "";
        char n;
        
        length = r.length();
        d = 'a' - 'A';
        for(i = 0; i < length;i++){
            if(r.charAt(i) <= 'z' && r.charAt(i) >= 'a'){
                n = (char) (r.charAt(i) -  d);
                x++;
            }
            else{
                n = r.charAt(i);
            }
            result = result + n;
        }
        System.out.println(result);
        return x;
    }
}