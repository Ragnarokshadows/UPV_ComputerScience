#include <stdio.h>
#include <stdlib.h>
#include <mpi.h>

#define N 10000
#define NITER 50

/* Valor double aleatorio entre 0 y 1 */
#define D01  (rand() / (double)RAND_MAX)

/* NOTE: We work with matrices stored by columns */
#define M(i,j) M[(i) + (j)*n]
#define Mloc(i,j) Mloc[(i) + (j)*n]

void inventa(int m, int n, double *A)
{
  for (m *= n; m > 0; m--)
    *A++ = D01;
}
/* Se asume que todos los elementos son no negativos,
 * lo que es cierto si se ha generado con la función inventa */
double uno_norma(int m, int n, double *A)
{
  double max, aux;
  int i;

  max = 0;
  for (; n > 0; n--) {
    aux = 0;
    for (i = 0; i < m; i++)
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
      nb,         /* Block size (i.e. number of columns per process) */
      nLoc,       /* Number of local columns in this process (<= nb) */
      n2,         /* = nb*num_procs */
      tama, sz, proc;
  char opcion;
  double t;
  double *M, *Mloc, *x, *xloc1, *xloc2, *v, norma, aux;
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
      fprintf(stderr, "Use: mxv2 [-s seed] [-n size] [-i iterations]\n");
      return 1;
    }
  }

  /* Compute nb and n2 */
  nb = n / num_procs;
  if (num_procs * nb < n) nb++;
  n2 = num_procs * nb;

  /* Each process stores a block of columns of M (Mloc), a block of rows of the
   * current vector x (xloc1) and the local contribution to the new x (xloc2)
   * (the new global x is formed by summing xloc2 of all the processes plus v */
  if (me == 0)   /* Process 0 stores the complete M, x and v */
    tama = n * n2 + n2 + n;
  else
    tama = 0;
  tama += n * nb + nb + n; /* Mloc, xloc1, xloc2 */

  M = malloc(tama*sizeof(double));
  if (M == NULL) {
    fprintf(stderr, "ERROR: Out of memory!\n");
    return 1;
  }

  sz = n * nb;
  if (me == 0) {
    x = M + n * n2;
    v = x + n2;
    Mloc = v + n;
  } else {
    Mloc = M;
  }
  xloc1 = Mloc + sz;
  xloc2 = xloc1 + nb;

  if (me == 0) {
    srand(semilla);
    inventa(n, n, M);
    inventa(n, 1, v);
    inventa(n, 1, x);
    /* Make the iterations convergent. (Norm of M must be less than 1) */
    escala(n, n, M, 0.9 / uno_norma(n, n, M));
  }

  t = MPI_Wtime();

  /* COMMUNICATIONS */
  /* Distribute M by blocks of columns and x by blocks of rows (nb columns/rows for each
   * process ) */
  if (me == 0) {
    /* For process 0, copy the data */
    for (j = 0; j < nb; j++) {
      for (i = 0; i < n; i++)
        Mloc(i, j) = M(i, j);
      xloc1[j] = x[j];
    }
    /* For other processes, send the data */
    for (proc = 1; proc < num_procs; proc++) {
      MPI_Send(&M[proc*sz], sz, MPI_DOUBLE, proc, 13, MPI_COMM_WORLD);
      MPI_Send(&x[proc*nb], nb, MPI_DOUBLE, proc, 25, MPI_COMM_WORLD);
    }
  } else {
    MPI_Recv(Mloc, sz, MPI_DOUBLE, 0, 13, MPI_COMM_WORLD, MPI_STATUS_IGNORE);
    MPI_Recv(xloc1, nb, MPI_DOUBLE, 0, 25, MPI_COMM_WORLD, MPI_STATUS_IGNORE);
  }

  nLoc = n - me * nb;
  if (nLoc > nb)
    nLoc = nb;

  for (iter = 1; iter <= num_iter; iter++) {
    /* Compute Mloc*xloc1 into xloc2 */
    for (i = 0; i < n; i++) {
      aux = 0;
      for (j = 0; j < nLoc; j++)
        aux += Mloc(i, j) * xloc1[j];
      xloc2[i] = aux;
    }

    /* COMMUNICATIONS */
    /* Finish the computation of the new x: sum all the local vectors xloc2
     * and add v, leaving the result in process 0.
     * Then, distribute the vector among the processes, for the next iteration */
    if (me == 0) {
      /* Operate with my own xloc2 */
      for (i = 0; i < n; i++)
        x[i] = xloc2[i] + v[i];
      /* Receive xloc2 from other processes and add it to x. */
      for (proc = 1; proc < num_procs; proc++) {
        MPI_Recv(xloc2, n, MPI_DOUBLE, proc, 49, MPI_COMM_WORLD, MPI_STATUS_IGNORE);
        for (i = 0; i < n; i++)
          x[i] += xloc2[i];
      }
      /* For process 0, copy the data */
      for (j = 0; j < nb; j++)
        xloc1[j] = x[j];
      /* For other processes, send the data */
      for (proc = 1; proc < num_procs; proc++)
        MPI_Send(&x[proc*nb], nb, MPI_DOUBLE, proc, 53, MPI_COMM_WORLD);
    } else {
      /* Send xloc2, receive xloc1 */
      MPI_Send(xloc2, n, MPI_DOUBLE, 0, 49, MPI_COMM_WORLD);
      MPI_Recv(xloc1, nb, MPI_DOUBLE, 0, 53, MPI_COMM_WORLD, MPI_STATUS_IGNORE);
    }
  } /* end of the iter loop */

#define ABS(a) ((a) >= 0 ? (a) : -(a))
  /* Compute the 1-norm of the local part of x (xloc1) */
  norma = 0;
  for (i = 0; i < nLoc; i++)
    norma += ABS(xloc1[i]);

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
