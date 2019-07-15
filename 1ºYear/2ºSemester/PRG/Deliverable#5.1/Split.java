import java.util.*;
import java.io.File;
import linear.*;
import java.io.FileNotFoundException;

/**
 * Class Split: Receives the input data and introduces it in a stack, then calls the 
 * method split. To use this program you have to use the command: 
 * 'java Split -input data.in >data.out' or
 * 'data.in | java Split'
 */
public class Split
{
    public static void main( String [] args )
    {
        String inputFilename = null;
        
        for( int i=0; i < args.length; i++ ) {

            if ( "-input".equals( args[i] ) ) {
                inputFilename = args[i+1];
            }
        }

        Scanner input = null;
        
        try{
            if ( inputFilename != null )
                input = new Scanner( new File( inputFilename ) );
            else
                input = new Scanner( System.in );
            
            StackIntLinked original = new StackIntLinked();
    
            while( input.hasNext() ) {
                original.push(input.nextInt());
            }
    
            if ( inputFilename != null ) input.close();
            
            split(original);            
        } catch (FileNotFoundException fnfe){
            System.err.println("The file does not exist or was not found");
        } catch (InputMismatchException ime){
            System.err.println("Incorrect data type");
        } catch (Exception e){
            System.err.println(e.getMessage());
        }
    }
    
    /**
     * Splits the original stack into two stacks, one of positive numbers and
     * the other one of negative. Then, it shows the content of these two stacks.
     * @throws Exception if the stack is empty.
     */
    private static void split(StackIntLinked original)
    throws Exception
    {
        StackIntLinked positive = new StackIntLinked();
        StackIntLinked negative = new StackIntLinked();
        int temp;
    
        while(!original.empty()){
            temp = original.pop();
                
            if (temp >= 0) positive.push(temp);
            else negative.push(temp);
        }
            
        for(; !positive.empty() || !negative.empty(); System.out.println()) {
            if (negative.empty()) {
                System.out.printf(" %10s ", " ");
            } else {
                System.out.printf(" %10d ", negative.pop());
            }
    
            if (positive.empty()) {
                System.out.printf(" %10s ", " ");
            } else {
                System.out.printf(" %10d ", positive.pop());
            }
        }
    }
}