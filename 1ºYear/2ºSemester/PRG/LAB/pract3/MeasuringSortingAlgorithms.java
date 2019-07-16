package pract3;

import java.util.Locale;
import java.util.Random;

/** Class MeasuringSortingAlgorithms: Empirical analysis of the temporal cost of sorting algorithms
 *  @author PRG - ETSInf
 *  @version Curso 2017-2018
 */
class MeasuringSortingAlgorithms
{
    // Constants to be used when varying the measuring parameters
    public static final int MIN_SIZE = 1000, 
                            MAX_SIZE = 10000;
    public static final int STEP_OF_SIZE = 1000,
                            REPETITIONS = 200;
    public static final double NMS = 1e3;  // ratio microseconds/nanoseconds


    private static final Random generator = new Random(); // Generator of random numbers

  
    /* Creates an array of length size with random values in the range 0, size-1
     * @param size int, size of the array
     * @result int[], created array
     */
    private static int[] createRandomArray( int size )
    { 
        int[] a = {1};
        int i;

        // To be completed by students
        
        a = new int [size];
        
        for (i = 0;i < size;i++){
            a[i] = generator.nextInt();
        }

        return a;
    }
  
    /* Generates an array of length size sorted in ascending order
     * @param size int, size of the array
     * @result int[], created array
     */
    private static int[] createArraySortedInAscendingOrder( int size )
    { 
        int[] a = {1};
        int i;

        // To be completed by students
        
        a = createRandomArray( size );
        
        MeasurableAlgorithms.quickSort(a);
        
        return a;
    }

    /* Generates an array of length size sorted in descending order
     * @param size int, size of the array
     * @result int[], created array
     */
    private static int[] createArraySortedInDescendingOrder( int size )
    {
        int[] a = {1};
        int i, aux = 0, x;

        // To be completed by students
        a = createArraySortedInAscendingOrder( size );
        
        for (i = 0;i < size / 2;i++){
            swap(a, i, size -1 -i);
        }

        return a;
    }
    public static void swap( int [] a, int i, int j )
    {
        int temp = a[i]; a[i] = a[j]; a[j] = temp;
    }

    public static void measuringSelectionSort()
    { 
        // To be completed by students
        /**
        int [] a;
        long tf;
        long ti;
        int i, size;
        
        for (i = 0; i < REPETITIONS;i++) {
            a = createRandomArray( 100 );
            
            ti = System.nanoTime();
            
            MeasurableAlgorithms.selectionSort(a);
            
            tf = System.nanoTime();
            
            System.out.println("The size is " + a.length + " and the elapsed time is " + (tf - ti)/1000 + " microseconds" );
        }
        */
       
        long ti = 0, // Initial timestamp
             tf = 0, // Final timestamp
             tt = 0; // Total timestamp
       
        // Print result header
        System.out.printf( "# SelectionSort. Time in microseconds\n" );
        System.out.printf( "#     Size      Average\n" );
        System.out.printf( "#----------------------\n" );

        // Este bucle repite el proceso para varias tallas
        for( int size = MIN_SIZE; size <= MAX_SIZE; size += STEP_OF_SIZE ) {

            // Create the array
            int[] a = createRandomArray( size );
      
            for (int r = 0; r < REPETITIONS; r++) {
                ti = System.nanoTime();      // Initial timestamp
                MeasurableAlgorithms.selectionSort( a );
                tf = System.nanoTime();      // Final timestamp
                tt += (tf - ti);             // Lapse for this run is accumulated
            }
            
            // Average running time for the average case
            double averageTime = (double) tt / REPETITIONS;
            
            // Print results
            System.out.printf( Locale.US, "%10d %12.3f\n", 
                              size, averageTime / NMS );
        }
    }

    public static void measuringInsertionSort()
    { 
        // To be completed by students
        long ti = 0, // Initial timestamp
             tf = 0, // Final timestamp
             tt = 0; // Total timestamp

        Random generator = new Random(); // Generator of random numbers

        // Print result header
        System.out.printf( "# Insertion Sort. Time expressed in microseconds\n" );
        System.out.printf( "#     Size        Worst         Best      Average\n" );
        System.out.printf( "#------------------------------------------------\n" );

        // Este bucle repite el proceso para varias tallas
        for( int size = MIN_SIZE; size <= MAX_SIZE; size += STEP_OF_SIZE ) {

            // Create the array (worst)
            int[] a = createArraySortedInDescendingOrder( size );
      
            // Worst case
            tt = 0;  // Accumulated time at the beginning is zero
            for( int r = 0; r < REPETITIONS; r++ ) {
                ti = System.nanoTime();      // Initial timestamp
                MeasurableAlgorithms.insertionSort( a );
                tf = System.nanoTime();      // Final timestamp
                tt += (tf - ti);             // Lapse for this run is accumulated
            }
            // Average running time for the worst case
            double worstTime = (double) tt / REPETITIONS;
            
            // Create the array (best)
            a = createArraySortedInAscendingOrder( size );
            
            // Best case
            tt = 0;  // Accumulated time at the beginning is zero
            for (int r = 0; r < REPETITIONS; r++) {
                ti = System.nanoTime();      // Initial timestamp
                MeasurableAlgorithms.insertionSort( a );
                tf = System.nanoTime();      // Final timestamp
                tt += (tf - ti);             // Lapse for this run is accumulated
            } 
            // Average running time for the best base
            double bestTime = (double) tt / REPETITIONS; 
            
            // Create the array (average)
            a = createRandomArray( size );
            
            // Average case
            tt = 0;  // Accumulated time at the beginning is zero
            for (int r = 0; r < REPETITIONS; r++) {
                int value = generator.nextInt( size ); // Value to be looked for
                ti = System.nanoTime();      // Initial timestamp
                MeasurableAlgorithms.insertionSort( a );
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
  
    public static void measuringMergeSort()
    { 
        // To be completed by students
        
        long ti = 0, // Initial timestamp
             tf = 0, // Final timestamp
             tt = 0; // Total timestamp
       
        // Print result header
        System.out.printf( "# MergeSort. Time in microseconds\n" );
        System.out.printf( "#     Size      Average\n" );
        System.out.printf( "#----------------------\n" );

        // Este bucle repite el proceso para varias tallas
        for( int size = 1; size <= Math.pow(2, 19); size = size * 2 ) {

            // Create the array
            int[] a = createRandomArray( size );
      
            for (int r = 0; r < REPETITIONS; r++) {
                ti = System.nanoTime();      // Initial timestamp
                MeasurableAlgorithms.mergeSort( a, 0, a.length - 1 );
                tf = System.nanoTime();      // Final timestamp
                tt += (tf - ti);             // Lapse for this run is accumulated
            }
            
            // Average running time for the average case
            double averageTime = (double) tt / REPETITIONS;
            
            // Print results
            System.out.printf( Locale.US, "%10d %12.3f\n", 
                              size, averageTime / NMS );
        }
    }

    private static void help()
    {
        System.out.println( "Uso: java MeasurigSortingAlgorithms <algorithm_number>" );
        System.out.println( "   Where <algorithm_number> can be: " );
        System.out.println( "   1 -> Insertion Sort" );
        System.out.println( "   2 -> Selection Sort" );
        System.out.println( "   3 -> Merge Sort" );
    }

    public static void main( String[] args )
    {
        if ( args.length != 1 ) {
            help();
        } else {
            try {
                int a = Integer.parseInt(args[0]);
                switch (a) {
                    case 1: measuringInsertionSort(); break;
                    case 2: measuringSelectionSort(); break;
                    case 3: measuringMergeSort(); break;
                    default: help();
                }
            } catch (Exception e) {
                help(); 
            }
        }
    }
}
