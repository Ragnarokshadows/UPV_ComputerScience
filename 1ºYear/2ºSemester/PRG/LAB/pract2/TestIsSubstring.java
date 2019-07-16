package pract2;

public class TestIsSubstring {
    public static void main(String [] args){
        String [] strings = {"", "recursion", "cursi", "123456789", "pecur"};
        
        //CASE 1: a and b are empty
        System.out.print("Case 1 ('" + strings[0] + "' '" + strings[0] + "'):");
        testIsSubstring(strings[0], strings[0]);
        
        //CASE 2: a empty
        System.out.print("Case 2 ('" + strings[0] + "' '" + strings[1] + "'):");
        testIsSubstring(strings[0], strings[1]);
        
        //CASE 3: b empty
        System.out.print("Case 3 ('" + strings[1] + "' '" + strings[0] + "'):");
        testIsSubstring(strings[1], strings[0]);
        
        //CASE 4: a longer than b
        System.out.print("Case 4 ('" + strings[1] + "' '" + strings[2] + "'):");
        testIsSubstring(strings[1], strings[2]);
        
        //CASE 5: a and b equally long and a is contained in b
        System.out.print("Case 5 ('" + strings[1] + "' '" + strings[1] + "'):");
        testIsSubstring(strings[1], strings[1]);
        
        //CASE 6: a and b equally long and a is not contained in b
        System.out.print("Case 6 ('" + strings[3] + "' '" + strings[1] + "'):");
        testIsSubstring(strings[3], strings[1]);
        
        //CASE 7: a shorter than b and a is contained in b
        System.out.print("Case 7 ('" + strings[2] + "' '" + strings[1] + "'):");
        testIsSubstring(strings[2], strings[2]);
        
        //CASE 8: a shorter than b and a is not contained in b
        System.out.print("Case 8 ('" + strings[4] + "' '" + strings[1] + "'):");
        testIsSubstring(strings[4], strings[1]);
    }
    
    private static void testIsSubstring(String a, String b){
        System.out.println(" " + PRGString.isSubstring(a, b));
        System.out.println("The correct answer is: " + b.contains(a) + "\n");
    }
}
