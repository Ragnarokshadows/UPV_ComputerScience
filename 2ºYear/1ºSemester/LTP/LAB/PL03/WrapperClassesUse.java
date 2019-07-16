package PL03;


/**
 * class WrapperClassesUse.
 * 
 * @author LTP 
 * @version 2018-19
 */

public class WrapperClassesUse {        
    public static void main(String[] args) {            
        // Assignment of wrapper variables to elementary types 
        byte b = new Byte((byte) 56);
        short s = new Short((short) 167);
        int i = new Integer(123456);
        long l = new Long(12345678);
        float f = new Float(1.56);
        double d = new Double(56789.9);
        char c = new Character('c');
        boolean boo = new Boolean(true);
        
        // Writing elementary variables
        System.out.println("byte b = " + b);
        System.out.println("short s = " + s);
        System.out.println("int i = " + i);
        System.out.println("long l = " + l);
        System.out.println("float f = " + f);
        System.out.println("double d = " + d);
        System.out.println("char c = " + c);
        System.out.println("boolean boo = " + boo);
               
	// Assignment of elementary values to wrapper variables
	Byte eB = 56;
	Short eS = 167;
	Integer eI = 123456;
	Long eL = (long) 12345678;
        Float eF = (float) 1.56;
        Double eD = 56789.9;
        Character eC = 'c';
        Boolean eBoo = true;
            
        // Writing wrapper variables
	System.out.println("Byte B = " + eB);
        System.out.println("Short S = " + eS);
        System.out.println("Integer I = " + eI);
        System.out.println("Long L = " + eL);
        System.out.println("Float F = " + eF);
        System.out.println("Double D = " + eD);
        System.out.println("Character C = " + eC);
        System.out.println("Boolean Boo = " + eBoo);
    }    
}