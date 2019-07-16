package pract3;

/** Class MeasurableAlgorithms: class with the methods to be analyzed. It is a tool class.
 *  @author PRG - ETSInf
 *  @version Curso 2017-2018
 */
public class MeasurableAlgorithms
{
    /** Linear Search
     *  @param a int[], array of int
     *  @param e int, value to look for
     *  @return int, position of e in a or -1 if e is not in a
     */
    public static int linearSearch( int[] a, int e )
    {
        int i = 0;
        while( i < a.length && (a[i] != e) ) i++;

        if ( i < a.length )
            return i;
        else
            return -1;
    }

    /** Selection Sort
     *  @param a int[], array of int
     */
    public static void selectionSort( int[] a )
    {
        int posMin, temp;
        for( int i = 0; i < a.length - 1; i++ ) {
            posMin = i;
            for( int j = i + 1; j < a.length; j++ ) {
                if ( a[j] < a[posMin] ) { posMin = j; }
            }
            temp = a[posMin];
            a[posMin] = a[i];
            a[i] = temp;
        }
    }

    /** InsertionSort
     *  @param a int[], array of int
     */
    public static void insertionSort( int[] a )
    {
        int temp;
        for( int i = 1; i < a.length; i++ ) {

            int j = i - 1;
            temp = a[i];

            while( j >= 0 && a[j] > temp ) {

                a[j + 1] = a[j];
                j--;
            }
            a[j + 1] = temp;
        }
    }

    /** mergeSort
     *  @param a int[], array of int
     *  @param left  int, leftmost position of the slice of the array to be sorted
     *  @param right int, rightmost position of the slice of the array to be sorted 
     */ 
    public static void mergeSort( int[] a, int left, int right )
    {
        int half;
        if (left < right) {
            half = (left + right) / 2;
            mergeSort( a, left, half );
            mergeSort( a, half + 1, right );
            naturalMerge( a, left, half, right );
        }
    }

    /** Natural Merge to be used in Merge Sort
     *  @param a int[], array of int
     *  @param left  int, leftmost position of the slice of the array to be sorted
     *  @param half  int, central position of the slice of the array to be sorted 
     *  @param right int, rightmost position of the slice of the array to be sorted
     */ 
    private static void naturalMerge( int[] a, int left, int half, int right )
    {
        int [] temp = new int[right - left + 1];

        int i=left, j=half+1, k=0;

        while( i <= half && j <= right ) {
            
            if ( a[i] < a[j] ) {
                temp[k++] = a[i++];
            } else {
                temp[k++] = a[j++];
            }
        }

        while( i <= half  ) { temp[k++] = a[i++]; }
        while( j <= right ) { temp[k++] = a[j++]; }

        for( i=left,k=0; i <= right; i++ ) {
            a[i] = temp[k++];
        }
    }


    ////////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////
    /////                                                                          /////
    /////  IMPORTANT NOTICE:                                                       /////
    /////                                                                          /////
    /////  The following code corresponds to the Quick Sort algorithm, that is     /////
    /////  out of the scope of the subject PRG corresponding to the first          /////
    /////  academic year.                                                          /////
    /////                                                                          /////
    /////  So, the following code has been placed here to satisfy the curiosity    /////
    /////  of some of the students of the group 1E.                                /////
    /////  This will never appear in the exam.                                     /////
    /////                                                                          /////
    ////////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////

    /** Quick Sort.
     *  Wrapper method.
     *  @param a int[], array of int
     */ 
    public static void quickSort( int [] a )
    {
        quickSort( a, 0, a.length-1 );
    }

    /** Quick Sort.
     *  Recursive version.
     *  @param a int[], array of int
     *  @param left  int, leftmost position of the slice of the array to be sorted
     *  @param right int, rightmost position of the slice of the array to be sorted
     */ 
    private static void quickSort( int [] a, int left, int right )
    {
        /* BEGIN of the optimized version
           If the insertion sort algorithm is used for slices of the array (sub-arrays) 
           whose size is less than or equal to 16 it seems it is more efficient.
           Consider the fact that when the quick sort reaches small instances of the
           problem thanks to splitting sub-arrays repeatedly, it is more probable that
           these slices are practically sorted, in such cases the insertion sort behaves
           close to linear time. Specially when the array was already sorted.
           Additionally, this option is of particular interest in the case there are
           large arrays with repeated values.

           Uncomment the following three lines if you want to use the optimized version
        */
        /*
        if ( right-left+1 <= 16 ) {
            insertionSort( a, left, right );
        } else
        */
        // END of the optimized version
        if ( right-left+1 > 2 ) {

            int x = a[left],
            y = a[(left+right)/2],
            z = a[right];

            if ( x < y ) { int temp = x; x = y; y = temp; }
            if ( y < z ) { int temp = y; y = z; z = temp; }
            if ( x < y ) { int temp = x; x = y; y = temp; }

            int pivot = y;

            int i=left, j = right;
            while( i < j ) {
                while( i < j  &&  a[i] < pivot ) i++;
                while( i < j  &&  a[j] > pivot ) j--;

                if ( i < j ) {
                    swap( a, i, j );
                    i++;
                    j--;
                }
            }
            quickSort( a, left, j );
            quickSort( a, i, right );

        } else if ( left == right ) {
            // Nothing to do when the slice of the array is of size one
        } else {
            // If the thread reaches this line is because the sub-array contains two elements
            if ( a[right] < a[left] ) {
                swap( a, left, right );
            }
        }
    }
    /** Swap method.
     *  @param a int[], array of int
     *  @param i int, position of the array whose value will be interchanged with the other one
     *  @param j int, position of the array whose value will be interchanged with the other one
     */ 
    private static void swap( int [] a, int i, int j )
    {
        int temp = a[i]; a[i] = a[j]; a[j] = temp;
    }
    /** Insertion Sort. Version for working with a specific sub-array or slice.
     *  @param a int[], array of int
     *  @param left  int, leftmost position of the slice of the array to be sorted
     *  @param right int, rightmost position of the slice of the array to be sorted
     */ 
    private static void insertionSort( int[] a, int left, int right )
    {
        for( int i = left+1; i <= right; i++ ) {

            int j = i;
            int temp = a[i];

            while( j > left && temp < a[j-1] ) {
                a[j] = a[j-1];
                j--;
            }
            a[j] = temp;
        }
    }
}
