#include <stdio.h>
#include <string.h>

#define N 10000000

static int cache [N];

int cycle_length( int n )
{
    int counter=1;

    while( n != 1 ) {

        if ( n < N && cache[n] > 0 ) return counter+cache[n]-1;

        if ( (n%2) == 1 ) {
            n = 3*n+1;
        } else {
            n >>= 1;
        }

        ++counter;
    }

    return counter;
}

int max_cycle_length( int i, int j )
{
    int max_cl=0;
    int k;

    if ( j < i ) {
        k = j; j = i; i = k;
    }

    for( k=i; k <= j; k++ ) {
        int cl = cycle_length(k);
        cache[k] = cl;
        if ( cl > max_cl ) max_cl = cl;
    }

    return max_cl;
}

int main( int argc, char *argv[] )
{
    int i,j;

    /* memset( cache, 0, N*sizeof(int) ); */
    cache[1]=1;

    while( scanf( "%d%d", &i, &j ) == 2 ) {

        printf( "%d %d %d\n", i, j, max_cycle_length(i,j) );
    }

    return 0;
}
