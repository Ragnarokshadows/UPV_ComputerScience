package pract2;

/**
 * Clas PRGString: clase of tools with methods for working with Strings.
 * 
 * @author PRG - ETSINF - DSIC - UPV
 * @version Academic Year 2017-2018
 */
public class PRGString {
    
    /** Objects of this class cannot be created. */
    private PRGString() { }
    
    /**
     * Returns the number of occurrences of the letter 'a' in the String given as parameter.
     * @param s String to be explored to find occurrences of the letter 'a'.
     * @return int
     */
    public static int countA(String s) {
        // Trivial case: Empty String
        if (s.length() == 0) { return 0; }
        // General case: Non empty String. Check with the next substring.
        else if (s.charAt(0) == 'a') { return 1 + countA(s.substring(1)); }
        else { return countA(s.substring(1)); }
    }

    /**
     * Returns the number of occurrences of the letter 'a' in
     * the String given as parameter from the specified position.
     * @param s String to be explored to find occurrences of the letter 'a'.
     * @param pos starting position of 's' where the substring starts.
     * @return int
     * PRECONDITION: pos >= 0
     */
    public static int countA(String s, int pos) {
        // Trivial case: Empty string
        if (pos >= s.length()) { return 0; }
        // General case: Non empty String. Check with the next substring.
        else if (s.charAt(pos) == 'a') { return 1 + countA(s, pos + 1); }
        else { return countA(s, pos + 1); }
    }

    /**
     * Returns the number of occurrences of the letter 'a' in
     * @param s String to be explored to find occurrences of the letter 'a'.
     * @return int
     */
    public static int countA2(String s) {
        // Trivial case: Empty String
        if (s.length() == 0) { return 0; }
        // General case: Non emtpy String. Check with the next substring.
        else if (s.charAt(s.length() - 1) == 'a') {
            return 1 + countA2(s.substring(0, s.length() - 1));
        } else { return countA2(s.substring(0, s.length() - 1)); }
    }

    /**
     * Check whether 'a' is prefix of 'b'.
     * -- TO BE COMPLETED --
     * Returns a boolean that express if 'a' is prefix of 'b'.
     * @param a String that represents the prefix.
     * @param b String where could be or not 'a' a prefix.
     * @return boolean
     */
    public static boolean isPrefix(String a, String b) {
        /* TO BE COMPLETED */
        /**if (a.length() > b.length()){return false;}
        else if (a.length() == 0){return true;}
        else if (a.charAt(0) != b.charAt(0)){return false;}
        else{return isPrefix(a.substring(1), b.substring(1));}
        */
       
        return isPrefix(a, b, 0); 
    }
    
    public static boolean isPrefix(String a, String b, int i){
        if (a.length() > b.length()){return false;}
        else if (i >= a.length()){return true;}
        else if (b.charAt(i) != a.charAt(i)){return false;}
        else {return isPrefix(a, b, i + 1);}
    }
    
    /**public static boolean isPrefix(String a, String b, int i, int j){
        if (a.length() > b.length()){return false;}
        else if (i >= a.length()){return true;}
        else if (b.charAt(j) != a.charAt(i)){return false;}
        else {return isPrefix(a, b, i + 1, j + 1);}
    }
    */

    /**
     * Check whether 'a' is contained in 'b'.
     * -- TO BE COMPLETED --
     * Returns a boolean that express if 'a' is contained in 'b'.
     * @param a String that represents the substring.
     * @param b String where could be or not 'a' contained.
     * @return boolean
     */
    public static boolean isSubstring(String a, String b) {
        /* TO BE COMPLETED */
        if (a.length() > b.length()){return false;}
        else if (isPrefix(a,b)){return true;}
        else {return isSubstring(a, b.substring(1));}
       
        //return isSubstring(a, b, 0);
    }
    
    /**public static boolean isSubstring(String a, String b, int i){
        if (a.length() > b.length()){return false;}
        else if (i > b.length()) {return false;}
        else if (isPrefix(a, b, i, 0)){return true;}
        else {return isSubstring(a, b, i + 1);}
    }
    */
}