#include <stdio.h>
#include <mpi.h>
int main (int argc, char *argv[])
{
    int k;
    int p;

    MPI_Init(&argc, &argv);

    MPI_Comm_rank(MPI_COMM_WORLD, &k);
    MPI_Comm_size(MPI_COMM_WORLD, &p);

    printf("Hello world\n");
    printf("I am %d and there are %d processes\n\n",k,p);
    MPI_Finalize();
    return 0;
}