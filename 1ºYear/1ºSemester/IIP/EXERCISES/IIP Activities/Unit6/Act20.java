package Unit6;

import java.util.*;

public class Act20{
    public static void main(String [] args){
        int n,i;
        Scanner kbd = new Scanner(System.in).useLocale(Locale.US);
        
        System.out.print("Write a number higher than 0: ");
        n = kbd.nextInt();
        String r = "*";
        
        for (i=1; i<=n; i++){
            System.out.println(r);
            r = r + "*";
        }
    }
}