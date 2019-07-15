package Unit6;
import java.util.*;

public class Act22{
    public static void main(String [] args){
        String word1,word2;
        Scanner kbd = new Scanner(System.in).useLocale(Locale.US);
        int length1,length2,z,i,j,x,y;
        String spaces = null;
        
        System.out.print("Write a word: ");
        word1 = kbd.nextLine();
        System.out.print("Write another word: ");
        word2 = kbd.nextLine();
        
        length1 = word1.length();
        length2 = word2.length();
        
        for(i = 0;i < length1;i++){
            for(j = 0; j < length2;j++){
                if (word1.charAt(i) == word2.charAt(j)){
                    spaces = "";
                    for(x = 0;x < i;x++){
                        spaces = spaces + " ";
                    }
                    for(z = 0;z < j;z++){
                        System.out.println(spaces + word2.charAt(z));
                    }
                    System.out.println(word1);
                    for(y = j + 1;y < length2;y++){
                        System.out.println(spaces + word2.charAt(y));
                    }
                    System.out.println("");
                }
            }
        }
    }
}