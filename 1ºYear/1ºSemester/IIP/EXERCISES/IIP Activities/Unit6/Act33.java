package Unit6;
import java.util.*;

public class Act33{
    public static void main(String [] args){
        Scanner kbd = new Scanner(System.in).useLocale(Locale.US);
        String s;
        
        System.out.print("Write a word: ");
        s = kbd.nextLine();
        
        shiftToLeft(s);
    }
    public static void shiftToLeft(String s){
        int n = s.length(),i,j,k;
        
        for(i = 0;i <= n-1;i++){
            for(j = i; j <= n-1;j++){
                System.out.print(s.charAt(j));
            }
            for(k = 0; k <= i-1;k++){
                System.out.print(s.charAt(k));
            }
            System.out.println("");
        }
    }
}