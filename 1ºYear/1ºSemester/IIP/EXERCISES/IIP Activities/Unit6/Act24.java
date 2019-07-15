package Unit6;
import java.util.*;

public class Act24{
    public static void main(String [] args){
        int n;
        Scanner kbd = new Scanner(System.in).useLocale(Locale.US);
        
        System.out.print("Write a number: ");
        n = kbd.nextInt();
        
        System.out.println("Is "+n+" a prime number: "+prime(n));
    }
    public static boolean prime(int n){
        boolean r = true;
        int i;
        
        for(i = 2; (i < (n/2)) && (r == true); i++){
            if(n%i == 0){
                r = false;
            }
        }
        return r;
    }
}