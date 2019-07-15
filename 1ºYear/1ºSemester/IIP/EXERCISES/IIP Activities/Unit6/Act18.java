package Unit6;
import java.util.*;

public class Act18{
    public static void main(String [] args){
        int x, i = -1;
        Scanner kbd = new Scanner(System.in).useLocale(Locale.US);
        
        do{
            System.out.print("Write an integer number: ");
            x = kbd.nextInt();
            i++;
        }while(x != 0);
        
        System.out.println("The maximun number of numbers inputted is: "+i);
    }
}