package pract3;

import java.util.Locale;
import java.util.Random;

/** Class MeasuringLinearSearch: empirical o a posterior analysis of linear search algorithm
 *  @author PRG - ETSInf
 *  @version Curso 2017-2018
 */
public class MeasuringLinearSearch
{
    // Constants to be used when varying the measuring parameters
    public static final int MIN_SIZE = 10000,
                            MAX_SIZE = 100000;
    public static final int STEP_OF_SIZE = 10000,
                            REPETITIONS = 100000,
                            BEST_CASE_REPETITIONS = 2000000;
    public static final double NMS = 1e3;  // ratio microseconds/nanoseconds

    // Method for creating arrays
    private static int[] createArray( int size )
    {
        int[] a = new int[size];

        for (int i = 0; i < size; i++) { a[i] = i; }

        return a;
    }

    public static void measuringLinearSearch()
    {
        long ti = 0, // Initial timestamp
             tf = 0, // Final timestamp
             tt = 0; // Total timestamp

        Random generator = new Random(); // Generator of random numbers

        // Print result header
        System.out.printf( "# Linear Search. Time expressed in microseconds\n" );
        System.out.printf( "#     Size        Worst         Best      Average\n" );
        System.out.printf( "#------------------------------------------------\n" );

        // Este bucle repite el proceso para varias tallas
        for( int size = MIN_SIZE; size <= MAX_SIZE; size += STEP_OF_SIZE ) {

            // Create the array
            int[] a = createArray( size );
      
            // Worst case. When the value to be found is not in the array
            tt = 0;  // Accumulated time at the beginning is zero
            for( int r = 0; r < REPETITIONS; r++ ) {
                ti = System.nanoTime();      // Initial timestamp
                MeasurableAlgorithms.linearSearch( a, -1 );
                tf = System.nanoTime();      // Final timestamp
                tt += (tf - ti);             // Lapse for this run is accumulated
            }
            // Average running time for the worst case
            double worstTime = (double) tt / REPETITIONS;
      
            // Best case. When the value to be found is in the first position of the array
            // WARNING: it is too fast, we use a greater number of repetitions
            tt = 0;  // Accumulated time at the beginning is zero
            for (int r = 0; r < BEST_CASE_REPETITIONS; r++) {
                ti = System.nanoTime();      // Initial timestamp
                MeasurableAlgorithms.linearSearch( a, a[0] );
                tf = System.nanoTime();      // Final timestamp
                tt += (tf - ti);             // Lapse for this run is accumulated
            } 
            // Average running time for the best base
            double bestTime = (double) tt / BEST_CASE_REPETITIONS; 
      
            // Average case. When the value to be found could be in the array at any
            // position, a random value in between 0 and t-1 is used in this case
            tt = 0;  // Accumulated time at the beginning is zero
            for (int r = 0; r < REPETITIONS; r++) {
                int value = generator.nextInt( size ); // Value to be looked for
                ti = System.nanoTime();      // Initial timestamp
                MeasurableAlgorithms.linearSearch( a, value );
                tf = System.nanoTime();      // Final timestamp
                tt += (tf - ti);             // Lapse for this run is accumulated
            }
            // Average running time for the average case
            double averageTime = (double) tt / REPETITIONS;

            // Print results
            System.out.printf( Locale.US, "%10d %12.3f %12.3f %12.3f\n", 
                              size, worstTime / NMS, bestTime / NMS, averageTime / NMS );
        }
    }

    public static void main( String[] args )
    {
        measuringLinearSearch();
    }
}
