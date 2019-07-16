package pract2;

public class TestIsPrefix {
    public static void main(String [] args){
        String [] strings = {"", "recursion", "rec", "123456789", "pecur", "recurso", "remursi"};
        
        //CASE 1: a and b are empty
        System.out.print("Case 1 ('" + strings[0] + "' '" + strings[0] + "'):");
        testIsPrefix(strings[0], strings[0]);
        
        //CASE 2: a empty
        System.out.print("Case 2 ('" + strings[0] + "' '" + strings[1] + "'):");
        testIsPrefix(strings[0], strings[1]);
        
        //CASE 3: b empty
        System.out.print("Case 3 ('" + strings[1] + "' '" + strings[0] + "'):");
        testIsPrefix(strings[1], strings[0]);
        
        //CASE 4: a longer than b
        System.out.print("Case 4 ('" + strings[1] + "' '" + strings[2] + "'):");
        testIsPrefix(strings[1], strings[2]);
        
        //CASE 5: a and b equally long and a is prefix of b
        System.out.print("Case 5 ('" + strings[1] + "' '" + strings[1] + "'):");
        testIsPrefix(strings[1], strings[1]);
        
        //CASE 6: a and b equally long and a is not a prefix    
        System.out.print("Case 6 ('" + strings[3] + "' '" + strings[1] + "'):");
        testIsPrefix(strings[3], strings[1]);
        
        //CASE 7: a shorter than b and a is prefix of b
        System.out.print("Case 7 ('" + strings[2] + "' '" + strings[1] + "'):");
        testIsPrefix(strings[2], strings[2]);
        
        //CASES (8,9,10): a shorter than b and a is not prefix of b:
        //CASE 8: -fails the first character
        System.out.print("Case 8 ('" + strings[4] + "' '" + strings[1] + "'):");
        testIsPrefix(strings[4], strings[1]);
        
        //CASE 9: -fails the last character
        System.out.print("Case 9 ('" + strings[5] + "' '" + strings[1] + "'):");
        testIsPrefix(strings[5], strings[1]);
        
        //CASE 10: -fails any intermediate character
        System.out.print("Case 10 ('" + strings[6] + "' '" + strings[1] + "'):");
        testIsPrefix(strings[6], strings[1]);
    }
    
    private static void testIsPrefix(String a, String b){
        System.out.println(" " + PRGString.isPrefix(a, b));
        System.out.println("The correct answer is: " + b.startsWith(a) + "\n");
    }
}
