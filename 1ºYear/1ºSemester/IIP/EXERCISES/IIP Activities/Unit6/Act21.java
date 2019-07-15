package Unit6;
import java.util.*;

public class Act21{
    public static void main(String [] args){
        int n,aux;
        String r = "**";
        String spaces = "";
        String result = null;
        Scanner kbd = new Scanner(System.in).useLocale(Locale.US);
        
        System.out.print("Write a number higher than 0: ");
        n = kbd.nextInt();
        aux = (2 * n) - 4;
        if (aux < 0){aux = 0;}
        
        for (int j = 0;j < ((2*n)-2);j++){
            spaces = spaces + " ";
        }
        for (int i=1; i<=n; i++){
            result = spaces + r;
            System.out.println(result);
            r = r + "**";
            spaces = spaces.substring(0,aux);
            aux = aux-2;
            if (aux < 0){aux = 0;}
        }
    }
}