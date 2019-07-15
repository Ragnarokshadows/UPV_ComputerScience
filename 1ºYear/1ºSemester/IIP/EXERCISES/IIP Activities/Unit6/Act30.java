package Unit6;
import java.util.*;

public class Act30{
    public static void main(String [] args){
        String r;
        Scanner kbd = new Scanner(System.in).useLocale(Locale.US);
        
        System.out.print("Write a word: ");
        r = kbd.nextLine();
        
        System.out.println("The word is palindrome: "+palindrome(r));
    }
    public static boolean palindrome(String r){
        boolean result = true;
        int i,j,length;
        
        length = r.length() - 1;
        for(i = length, j = 0; (j <= i) && (result == true);i--,j++){
            if(r.charAt(i) != r.charAt(j)){
                result = false;
            }
        }
        return result;
    }
}