#include <stdio.h>
#include <stdlib.h>
#include <mpi.h>

#define N 10000
#define NITER 50

/* Valor double aleatorio entre 0 y 1 */
#define D01  (rand() / (double)RAND_MAX)

/* NOTE: We work with matrices stored by rows */
#define M(i,j) M[(i)*n + (j)]
#define Mloc(i,j) Mloc[(i)*n + (j)]

void inventa(int m, int n, double *A)
{
  for (m *= n; m > 0; m--)
    *A++ = D01;
}
/* Se asume que todos los elementos son no negativos,
 * lo que es cierto si se ha generado con la función inventa */
double inf_norma(int m, int n, double *A)
{
  double max, aux;
  int j;

  max = 0;
  for (; m > 0; m--) {
    aux = 0;
    for (j = 0; j < n; j++)
      aux += *A++;
    if (aux > max) max = aux;
  }
  return max;
}
void escala(int m, int n, double *A, double e)
{
  for (m *= n; m > 0; m--)
    *A++ *= e;
}

/*
 * Given an n-by-n matrix M and two vectors x, v, of lentgh n, compute:
 * Repeat num_iter times:
 *   x <- M * x + v
 * At the end, show the 1-norm of the final x
 * (The 1-norm of a vector is the sum of the absolute values of its elements)
 */
int main(int argc, char *argv[])
{
  int num_iter = NITER, semilla = 0,
      num_procs,  /* Number of processes */
      me,         /* Index of this process */
      n = N,      /* Number of rows, columns of  M */
      mb,         /* Block size (i.e. number of rows per process) */
      mLoc,       /* Number of local rows in this process (<= mb) */
      n2,         /* = mb*num_procs */
      tama, sz, proc;
  char opcion;
  double t;
  double *M, *Mloc, *x, *xloc, *v, *vloc, norma, aux;
  int i, j, iter;

  MPI_Init(&argc, &argv);
  MPI_Comm_rank(MPI_COMM_WORLD, &me);
  MPI_Comm_size(MPI_COMM_WORLD, &num_procs);

  while (argc > 1) {
    argc--; argv++;
    if (argv[0][0] != '-') {
      opcion = '?';
    } else {
      opcion = *++argv[0];
      if (opcion != '\0' && *++argv[0] == '\0' && argc > 1) {
        argc--; argv++;
      }
    }
    switch (opcion) {
    case 's':
      semilla = atoi(argv[0]);
      break;
    case 'n':
      n = atoi(argv[0]); if (n < 0) n = N;
      break;
    case 'i':
      num_iter = atoi(argv[0]); if (num_iter < 0) num_iter = NITER;
      break;
    default:
      fprintf(stderr, "Uso: mxv1 [-s seed] [-n size] [-i iterations]\n");
      return 1;
    }
  }

  /* Compute mb and n2 */
  mb = n / num_procs;
  if (num_procs * mb < n) mb++;
  n2 = num_procs * mb;

  /* Each process stores a block of rows of M (Mloc), of v (vloc), and of the
   * newly computed vector x (xloc), as well as the complete current vector x (x) */
  if (me == 0)   /* Process 0 stores the complete M and v */
    tama = n2 * n + n2;
  else
    tama = 0;
  tama += mb * n + 2 * mb + n2;   /* Mloc, vloc, xloc, x */

  M = malloc(tama * sizeof(double));
  if (M == NULL) {
    fprintf(stderr, "ERROR: Out of memory!\n");
    return 1;
  }

  sz = mb * n;
  if (me == 0) {
    v = M + n2 * n;
    Mloc = v + n2;
  } else {
    Mloc = M;
  }
  vloc = Mloc + sz;
  xloc = vloc + mb;
  x = xloc + mb;

  if (me == 0) {
    srand(semilla);
    inventa(n, n, M);
    inventa(n, 1, v);
    inventa(n, 1, x);
    /* Make the iterations convergent. (Norm of M must be less than 1) */
    escala(n, n, M, 0.9 / inf_norma(n, n, M));
  }

  t = MPI_Wtime();

  /* COMMUNICATIONS */
  /* Distribute M and v by blocks of rows (mb rows for each process)
   * and send the initial x to all */
  if (me == 0) {
    /* For process 0, copy the data */
    for (i = 0; i < mb; i++) {
      for (j = 0; j < n; j++)
        Mloc(i, j) = M(i, j);
      vloc[i] = v[i];
    }
    /* For other processes, send the data */
    for (proc = 1; proc < num_procs; proc++) {
      MPI_Send(&M[proc*sz], sz, MPI_DOUBLE, proc, 13, MPI_COMM_WORLD);
      MPI_Send(&v[proc*mb], mb, MPI_DOUBLE, proc, 89, MPI_COMM_WORLD);
      MPI_Send(x, n, MPI_DOUBLE, proc, 25, MPI_COMM_WORLD);
    }
  } else {
    MPI_Recv(Mloc, sz, MPI_DOUBLE, 0, 13, MPI_COMM_WORLD, MPI_STATUS_IGNORE);
    MPI_Recv(vloc, mb, MPI_DOUBLE, 0, 89, MPI_COMM_WORLD, MPI_STATUS_IGNORE);
    MPI_Recv(x, n, MPI_DOUBLE, 0, 25, MPI_COMM_WORLD, MPI_STATUS_IGNORE);
  }

  mLoc = n - me * mb;
  if (mLoc > mb)
    mLoc = mb;

  for (iter = 1; iter <= num_iter; iter++) {
    /* Compute local part of M*x + v into xloc */
    for (i = 0; i < mLoc; i++) {
      aux = vloc[i];
      for (j = 0; j < n; j++)
        aux += Mloc(i, j) * x[j];
      xloc[i] = aux;
    }

    /* COMMUNICATIONS */
    /* Prepare for next iteration. Form the complete vector x (x)
     * by assembling the fragments of x (xloc) in each process,
     * and replicate it in all the processes */
    if (me == 0) {
      /* Put all the fragments of x in their place */
      /* The first fragment is mine, so copy it */
      for (i = 0; i < mb; i++)
        x[i] = xloc[i];
      /* The rest of the fragments must be received from other processes */
      for (proc = 1; proc < num_procs; proc++)
        MPI_Recv(&x[proc*mb], mb, MPI_DOUBLE, proc, 49,
                 MPI_COMM_WORLD, MPI_STATUS_IGNORE);
      /* Send the complete x to everyone */
      for (proc = 1; proc < num_procs; proc++)
        MPI_Send(x, n, MPI_DOUBLE, proc, 53, MPI_COMM_WORLD);
    } else {
      /* Send my fragment of x */
      MPI_Send(xloc, mb, MPI_DOUBLE, 0, 49, MPI_COMM_WORLD);
      /* Receive the complete vector x */
      MPI_Recv(x, n, MPI_DOUBLE, 0, 53, MPI_COMM_WORLD, MPI_STATUS_IGNORE);
    }
  } /* end of the iter loop */

#define ABS(a) ((a) >= 0 ? (a) : -(a))
  /* Compute the 1-norm of the local part of x */
  norma = 0;
  for (i = 0; i < mLoc; i++)
    norma += ABS(xloc[i]);

  /* COMMUNICATIONS */
  /* Compute the 1-norm of the complete x from the 1-norm of each fragment,
   * i.e. compute the sum of the local norms, leaving the result in process 0 */
  if (me == 0) {
    for (proc = 1; proc < num_procs; proc++) {
      MPI_Recv(&aux, 1, MPI_DOUBLE, proc, 65, MPI_COMM_WORLD, MPI_STATUS_IGNORE);
      norma += aux;
    }
  } else {
    MPI_Send(&norma, 1, MPI_DOUBLE, 0, 65, MPI_COMM_WORLD);
  }

  t = MPI_Wtime() - t;

  if (me == 0) {
    printf("1-norm (%d iterations): %.10g\n", num_iter, norma);
    printf("Time using %d processes: %.2f\n", num_procs, t);
  }

  free(M);

  MPI_Finalize();

  return 0;
}
