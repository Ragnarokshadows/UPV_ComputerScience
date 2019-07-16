package pract5;

import java.io.File;
import java.util.Scanner;
import java.io.FileNotFoundException;

/**
 * ComparTexts 
 * Class - ready to be executed from command line.
 * Returns the union or the intersection of the sets of
 * words corresponding to the text files passed as parameters.
 *
 * @author (PRG. ETSINF. UPV)
 * @version (Academic year 2017/2018)
 */
public class CompareTexts
{
    private final static String USE = "Use: java CompareTexts <-i|-u> nomFitx1 nomFitx2"; 
    private final static String ERR1 = "Wrong access to file: ";
        
    /**
     * Args:
     * 1) "-u" or "-i" for union or intersection respectively
     * 2) Filename of the first file.
     * 3) Filename of the second file.
     * 
     * The result is shown on standard output.
     */
    public static void main( String[] args )
    {
        boolean err = args.length != 3 
            || !(args[0].equals("-u") || args[0].equals("-i"));
        
        if ( err ) {
            System.out.println( USE );
            System.exit(-1);
        } 
        
        String filename1 = args[1];
        String filename2 = args[2];
                
        switch( args[0] ) {
            case "-u": union( filename1, filename2 ); break;
            case "-i": intersection( filename1, filename2 ); break;
            default: System.out.println(USE); System.exit(-1);
        }        
    } // del main
        
    /**
     * Writes on standard output the result of merging the sets of words
     * corresponding to the text files whose names are in
     * <code>filename1</code>  and <code>filename2</code> .
     * 
     * @param filename1 String, name of the first file.
     * @param filename2 String, name of the second file.
     */
    public static void union( String filename1 , String filename2 )
    {
        try{
            File file1 = new File(filename1);
            File file2 = new File(filename2);
            Scanner one = new Scanner(file1);
            Scanner two = new Scanner(file2);
            
            StringSet f1 = readSet(one);
            StringSet f2 = readSet(two);
            
            System.out.println(f1.union(f2));
            
            one.close();
            two.close();
        } catch (FileNotFoundException fnfe){
            System.err.println("Wrong access to file: "  + fnfe.getMessage());
        }
    }
    
    /**
     * Writes on standard output the intersection of the sets of words
     * corresponding to the text files whose names are in
     * <code>filename1</code>  and <code>filename2</code> .
     * 
     * @param filename1 String, name of the first file.
     * @param filename2 String, name of the second file.
     */    
    public static void intersection( String filename1, String filename2 )
    {
        try{
            File file1 = new File(filename1);
            File file2 = new File(filename2);
            Scanner one = new Scanner(file1);
            Scanner two = new Scanner(file2);
            
            StringSet f1 = readSet(one);
            StringSet f2 = readSet(two);
            
            System.out.println(f1.intersection(f2));
            
            one.close();
            two.close();
        } catch (FileNotFoundException fnfe){
            System.err.println("Wrong access to file: "  + fnfe.getMessage());
        }
    }
    
    /**
     * Devuelve el ConjuntoString de las palabras
     * encontradas en el Scanner s segun los separadores
     * dados, por defecto, en ParseString (ParseString.SEPARADORES).
     * @param s Scanner.
     * @return el conjunto de palabras leidas del Scanner s.
     */
    private static StringSet readSet( Scanner s )
    {
        StringSet result = new StringSet();
        s.useDelimiter(ParseString.DELIMITERS);
        
        while (s.hasNext()){
            result.add(s.next());
        }
        
        return result;
    }
}
