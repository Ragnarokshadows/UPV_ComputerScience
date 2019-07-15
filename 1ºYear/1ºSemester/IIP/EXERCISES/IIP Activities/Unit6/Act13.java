package Unit6;
import java.util.*;

public class Act13{
    public static void main(String [] args){
        int x,aux,i;
        Scanner kbd = new Scanner(System.in).useLocale(Locale.US);
        
        System.out.print("Write a number: ");
        x = kbd.nextInt();
        aux = x;
        
        for(i = 1;i < aux; i++){
            x = x + aux;
        }
        System.out.println("The square of "+aux+" = "+x);
    }
}