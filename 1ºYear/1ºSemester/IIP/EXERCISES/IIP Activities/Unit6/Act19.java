package Unit6;
import java.util.*;

public class Act19{
    public static void main(String [] args){
        String word;
        Scanner kbd = new Scanner(System.in).useLocale(Locale.US);
        
        System.out.print("Write a word: ");
        word = kbd.nextLine();
        write(word);
    }
    public static void write(String word){
        int length = word.length();
        int i;
        
        for (i = 0;i < length;i++){
            if(i == 0){
                System.out.print(word.charAt(i));
            }
            else{
                System.out.print("-"+word.charAt(i));
            }
        }
    }
}