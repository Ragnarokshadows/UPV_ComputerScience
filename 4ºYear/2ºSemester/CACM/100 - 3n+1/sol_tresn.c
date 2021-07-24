#include <stdio.h>
#include <string.h>

#define N 1000000

static int cache [N];

int cycle_length(int n)
{
    int count=1;

    while(n != 1) {
        if (n < N && cache[n] > 0) return count + cache[n] - 1;

        /* Impar */
        if ((n % 2) == 1) {
            n = 3 * n + 1;
        } 
        /* Par */
        else {
            n >>= 1; /* Shift de un bit a la derecha es igual a dividir entre dos */
        }
        ++count;
    }

    return count;
}

int max_cycle_length(int i, int j)
{
    int max_cycle=0;
    int k, cycle;

    if (j < i) {
        k = j; j = i; i = k;
    }

    for(k=i; k <= j; k++) {
        cycle = cycle_length(k);
        cache[k] = cycle;
        if (cycle > max_cycle) max_cycle = cycle;
    }

    return max_cycle;
}

int main(int argc, char *argv[])
{
    int i, j;

    /* Caso trivial */
    cache[1]=1;

    /* Leemos los dos número y sacamos la solución */
    while(scanf("%d %d", &i, &j) == 2) {
        printf("%d %d %d\n", i, j, max_cycle_length(i,j));
    }

    return 0;
}
