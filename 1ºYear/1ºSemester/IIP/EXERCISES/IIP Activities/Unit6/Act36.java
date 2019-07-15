package Unit6;
import java.util.*;

public class Act36{
    public static void main(String [] args){
        int n;
        Scanner kbd = new Scanner(System.in).useLocale(Locale.US);
        
        System.out.print("Write a number: ");
        n = kbd.nextInt();
        
        aZ(n);
    }
    public static void aZ(int n){
        String r = "";
        int j,z,i;
        if (n >= 0){
            for(j = 0;j < n;j++){
                r = r + "A";
            }
            for(z = 0;z < n;z++){
                r = r + "Z";
            }
            System.out.println(r);
            for(i = 0;i < n;i++){
                r = "Z" + r;
                r = r.substring(0,(2*n));
                System.out.println(r);
            }   
        }
        else{
            n = (int) Math.abs(n);
            for(j = 0;j < n;j++){
                r = r + "A";
            }
            for(z = 0;z < n;z++){
                r = r + "Z";
            }
            System.out.println(r);
            for(i = 0;i < n;i++){
                r = r + "A";
                r = r.substring(1,((2 * n) + 1));
                System.out.println(r);
            }   
        }
    }
}