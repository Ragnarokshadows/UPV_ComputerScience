package pract5;

/**
 * ParseString helps to split strings into words
 * according to a set of delimiters.
 *
 * @author (PRG. ETSINF. UPV)
 * @version (Academic year 2017/2018)
 */
public class ParseString
{
    // Delimiters by default:
    public static final String DELIMITERS = "[\\p{Space}\\p{Punct}\\p{Digit}¡¿]+";
    
    private String delimiters;

    /**
     * Creates an object of the class ParseString with the
     * default delimiters.
     */
    public ParseString()
    {
        this.delimiters = DELIMITERS;
    }        
    
    /**
     * Creates an object of the class ParseString with a
     * delimiters expression provided by the user.
     *
     * @param delim String. Delimiters to use.
     */
    public ParseString( String delim )
    {
        this.delimiters = delim;
    }    
    
    /**
     * Returns an array with the words of <code>s</code>
     * split according to delimiters.
     * @param s String. The string to be split.
     * @return the array with the words.
     */
    public String[] split( String s )
    {
        return (s.trim()).split( this.delimiters );
    }
}
