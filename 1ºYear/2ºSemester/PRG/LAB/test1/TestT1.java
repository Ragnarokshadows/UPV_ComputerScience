package test1;
/**
 * Class TestT1: Turn 1 - Exercise 1 - First lab test.
 * 
 * @author PRG 
 * @version Year 2017-18
 */
public class TestT1 {
    /**
     * Returns a String resulting from replacing 
     * all occurrences of a in the String s with b. 
     */
    public static String replace(String s, char a, char b) {   
        /* TO COMPLETE */
        
        return replace(s, a, b, 0);    
    } 
    
    public static String replace(String s, char a, char b, int pos){
        String res = "";
        
        if(pos > s.length() -1){return res;}
        else{
            if (s.charAt(pos) == a){return res + b + replace(s, a, b, pos + 1);}
            else{return res + s.charAt(pos) + replace(s, a, b, pos + 1);}
        }
    }
}