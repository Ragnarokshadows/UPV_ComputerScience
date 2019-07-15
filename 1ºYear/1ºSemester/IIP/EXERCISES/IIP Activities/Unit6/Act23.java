package Unit6;

import java.util.*;

public class Act23{
    public static void main(String [] args){
        int n, i, j, x;
        Scanner kbd = new Scanner(System.in).useLocale(Locale.US);
        
        System.out.print("Write a positive number: ");
        n = kbd.nextInt();
        
        for (i=1;i<=n;i++){
            for (j=i;j<=n;j++) {
                x = i+j+2*i*j;
                System.out.println("Pair "+i+","+j+": "+i+"+"+j+"+2*"+i+"*"+j+" is "+x);
            }
        }
    }
}