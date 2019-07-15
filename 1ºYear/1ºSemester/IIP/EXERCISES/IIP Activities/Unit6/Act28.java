package Unit6;
import java.util.*;

public class Act28{
    public static void main(String [] args){
        char one,two;
        Scanner kbd = new Scanner(System.in).useLocale(Locale.US);
        
        System.out.print("Write a character: ");
        one = kbd.nextLine().charAt(0);
        System.out.print("Write another character: ");
        two = kbd.nextLine().charAt(0);
        
        charactersFrom(one,two);
    }
    public static void charactersFrom(char one,char two){
        char i;
        for(i = one;i <= two;i++){
            System.out.println(i);
        }
    }
}