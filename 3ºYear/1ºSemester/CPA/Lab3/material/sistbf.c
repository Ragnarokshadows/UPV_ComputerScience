#include <math.h>
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <mpi.h>

#define MAX_DIM 2048
#define EPSILON 1e-16
#define CHECK(rc, paso) (rc) ? printf("Error %d, paso %d\n", rc, paso) : 0
#define MIN(a, b) (((a) < (b)) ? (a) : (b))
//#define VERBOSE

/* Generador de matrices bien condicionadas           */
/* Entrada: Dimension del problema                    */
/* Salida: Puntero a la Matriz Generada               */
/* Notas: Reserva memoria, devuelve NULL ante errores */
double **genMat(int n, int m)
{
  int i;
  double **A = NULL;
  double *pdWorkArea = NULL;

  if ((n > 0) && (m > 0) && (m <= MAX_DIM)) {
    /* Reserva un area contigua para todas las filas */
    pdWorkArea = (double *)malloc(sizeof(double) * n * m);
    A = (double **)malloc(sizeof(double *) * n);
    if (pdWorkArea && A)
      for (i = 0; i < n; i++)
        A[i] = (pdWorkArea + m * i);
  }
  return A;
}

/* Rellena los elementos de la matriz                */
/* Es una matriz de Toeplitz diagonalmente dominante */
int rellenaMat(double **A, int n, int m)
{
  int i, j;

  if (A) {
    for (i = 0; i < n; i++)
      for (j = 0; j < m; j++)
        if (i == j)
          A[i][j] = n * n;
        else
          A[i][j] = n - abs(i - j);

    return 0;
  } else {
    return -1;
  }
}

/* Generador del vector de términos independientes    */
/* de forma que la solución del sistema de ecuaciones */
/* sea [1,1,...,1]'                                   */
double *genVec(int n)
{
  int i, j;
  double *pdVec = NULL;

  if ((n > 0) && (n <= MAX_DIM)) {
    pdVec = (double *)malloc(sizeof(double) * n);
    if (pdVec) {
      for (i = 0; i < n; i++) {
        pdVec[i] = n * n;
        for (j = 0; j < i; j++)
          pdVec[i] += n - j - 1;
        for (j = i + 1; j < n; j++)
          pdVec[i] += j;
      }
    }
  }
  return pdVec;
}

/* Impresion de un bloque local de una matriz             */
/* Entrada: Matriz a imprimir y sus dimensiones           */
/* Matriz almacenada como array 1D                        */
void printLocalMat1D(double *A, int n, int m)
{
  int i, j;

  for (i = 0; i < n; i++) {
    for (j = 0; j < m; j++)
      printf("%7.3f ", A[i*m+j]);
    printf("\n");
  }
}

/* Impresion de los bloques de una matriz de los          */
/* distintos procesos                                     */
/* Entrada: Matriz a imprimir y sus dimensiones           */
/* Matriz almacenada como array 1D                        */
int printMat1D(double *A, int mloc, int m, char *header)
{
  int i;
  int nelems, iproc, p;
  double *buf;
  MPI_Status status;

  if (A == NULL)
    return -2;

  if ((mloc < 0) || (mloc > MAX_DIM) || (m <= 0) || (m > MAX_DIM))
    return -3;

  MPI_Comm_rank(MPI_COMM_WORLD, &iproc);
  MPI_Comm_size(MPI_COMM_WORLD, &p);

  /* El proceso 0 escribe su propia matriz y las de los demas procesos, por orden */
  if (iproc == 0) {
    buf = (double *) malloc(mloc*m*sizeof(double));
    if (buf == NULL) return -4;

    printf("%s:\n", header);
    printf("---- proc. 0 ----\n");
    printLocalMat1D(A, mloc, m);
    /* Recibir matriz de cada proceso y escribirla */
    for (i=1; i<p; i++) {
       MPI_Recv(buf, mloc*m, MPI_DOUBLE, i, 0, MPI_COMM_WORLD, &status);
       MPI_Get_count(&status, MPI_DOUBLE, &nelems);
       printf("---- proc. %d ----\n", i);
       printLocalMat1D(buf, nelems/m, m);
    }

    free(buf);
  }
  else
    /* Enviar mi matriz al proceso 0 */
    MPI_Send(A, mloc*m, MPI_DOUBLE, 0, 0, MPI_COMM_WORLD);

  return 0;
}

/* Impresion de los bloques de una matriz de los          */
/* distintos procesos                                     */
/* Entrada: Matriz a imprimir y sus dimensiones locales   */
/* Salida: Diferentes codigos de error                    */
int printMat(double **A, int mloc, int m, char *header)
{
  return printMat1D(&A[0][0], mloc, m, header);
}

/* Libera una Matriz y el area apuntada */
int liberaMat(double **A)
{
  if (A) {
    if (A[0]) {
      free(A[0]);
      free(A);
      return 0;
    }
  }
  return -1;
}

/* Impresion de un vector                                 */
/* Entrada: Vector a imprimir y su dimension              */
/* Salida: Diferentes codigos de error                    */
int printVec(double *ppdVec, int n, char *header)
{
  return printMat1D(ppdVec, 1, n, header);
}

/* Returns the process that owns row i */
int owner(int i, int p, int mb) {
   /* Block distribution */
   return i/mb;
}

/* Returns the local index of row i in the local matrix of its owner process */
int localIndex(int i, int p, int mb) {
   /* Block distribution */
   return i%mb;
}

/* Returns the number of local rows in the process iproc */
int numLocalRows(int n, int mb, int iproc) {
   int mloc;
   /* Comment out the code for the distribution that is not wanted */

   /* Block distribution */
   mloc = MIN(mb, n - mb * iproc);
   if (mloc < 0) mloc = 0;
   return mloc;

   /* Cyclic distribution */
   //if (n%mb < iproc) mloc = mb-1;
   //else mloc = mb;
   //return mloc;
}

/* LU factorization
 * Computes the LU decomposition of matrix A, overwriting A.
 * At the end, U is stored in the upper triangle of A (including the diagonal), and L
 * in the lower triangle of A (excluding the diagonal). */
int lu(double **A, int n, int iproc, int p, int mb)
{
  int i, j, k;
  double *pdPivote;

  if (A == NULL)
    return -2;

  if ((n <= 0) || (n > MAX_DIM))
    return -3;

  pdPivote = (double *)malloc(sizeof(double) * n);

  for (k = 0; k < n - 1; k++) {
    if (owner(k,p,mb) == iproc) {
      if (fabs(A[localIndex(k,p,mb)][k]) < EPSILON) return -1;
      memcpy(pdPivote, A[localIndex(k,p,mb)], n * sizeof(double));
    }

    /* Broadcast row k (pivot) */
    MPI_Bcast(pdPivote, n, MPI_DOUBLE, owner(k,p,mb), MPI_COMM_WORLD);

    /* Modify rows k+1...n-1 */
    for (i = k + 1; i < n; i++) {
      if (owner(i,p,mb) == iproc) {
        /* Modify row i (elements at columns k...n-1), using the pivot row */
        A[localIndex(i,p,mb)][k] /= pdPivote[k];
        for (j = k + 1; j < n; j++)
          A[localIndex(i,p,mb)][j] -= A[localIndex(i,p,mb)][k] * pdPivote[j];
      }
    }
  }
  free(pdPivote);
  return 0;
}

/* Solves the unit lower triangular system L*x = b
 * Note: overwrites b with the solution x */
int triInf(double **L, double *b, int n, int iproc, int p, int mb)
{
  int i, j;

  if (L == NULL)
    return -2;

  if ((n <= 0) || (n > MAX_DIM))
    return -3;

  for (i = 0; i < n; i++) {
    /* Broadcast b[i] */
    MPI_Bcast(&b[i], 1, MPI_DOUBLE, owner(i,p,mb), MPI_COMM_WORLD);
    /* Modify elements at rows i+1...n-1 */
    for (j = i + 1; j < n; j++) {
      if (owner(j,p,mb) == iproc)
        b[j] -= L[localIndex(j,p,mb)][i] * b[i];
    }
  }
  return 0;
}

/* Solves the upper triangular system U*x = b
 * Note: overwrites b with the solution x */
int triSup(double **U, double *b, int n, int iproc, int p, int mb)
{
  int i, j;

  if (U == NULL)
    return -2;

  if ((n <= 0) || (n > MAX_DIM))
    return -3;

  for (i = n - 1; i >= 0; i--) {
    if (owner(i,p,mb) == iproc) {
      if (fabs(U[localIndex(i,p,mb)][i]) < EPSILON) return -1;
      b[i] = b[i] / U[localIndex(i,p,mb)][i];
    }
    /* Broadcast b[i] */
    MPI_Bcast(&b[i], 1, MPI_DOUBLE, owner(i,p,mb), MPI_COMM_WORLD);
    /* Modify elements at rows i-1...0 */
    for (j = i - 1; j >= 0; j--) {
      if (owner(j,p,mb) == iproc)
        b[j] -= U[localIndex(j,p,mb)][i] * b[i];
    }
  }
  return 0;
}

/* main                                                      */
/* Arguments: <system size>                                  */
int main(int argc, char *argv[])
{
  double **A, **Aloc;
  double *b;
  int n, i, rc;
  int mb;        /* block size = rows per process */
  int mloc;      /* Number of local rows (<=mb) */
  int iproc, p;  /* process index and number of processes */
  double dError;

  double t1, t2;

  MPI_Init(&argc, &argv);
  MPI_Comm_rank(MPI_COMM_WORLD, &iproc);
  MPI_Comm_size(MPI_COMM_WORLD, &p);

  if (argc!=2) {
      if (iproc==0) printf("Use: %s <system-size>\n", argv[0]);
      MPI_Finalize();
      return 0;
  }
  n = atoi(argv[1]);
  if ((n <= 0) || (n > MAX_DIM)) {
      if (iproc==0) printf("Error in the system size: %d\n", n);
      MPI_Finalize();
      return 0;
  }

  t1 = MPI_Wtime();

  /* Compute the block size and the number of local rows */
  mb = n / p;
  if (n % p != 0) mb++;
  mloc = numLocalRows(n,mb,iproc);

  /* STEP 1: Generate matrix A and vector b in process 0 */
  if (iproc == 0) {
    /* Use a number of rows multiple of the block size */
    A = genMat(p * mb, n);
    CHECK(A == NULL, 0);
    rc = rellenaMat(A, n, n);
    CHECK(rc, 1);
    Aloc = genMat(mb, n);
  } else {
    Aloc = genMat(mb, n);
    A = Aloc;
    CHECK(Aloc == NULL, 100);
  }

  if (iproc == 0) {
    b = genVec(n);
    CHECK(b == NULL, 2);
  } else {
    b = (double *)malloc(sizeof(double) * n);
    CHECK(b == NULL, 102);
  }

  /* STEP 2: Distribute data (A, b) */
  MPI_Bcast(b, n, MPI_DOUBLE, 0, MPI_COMM_WORLD);

  MPI_Scatter(A[0], mb * n, MPI_DOUBLE,
              Aloc[0], mb * n, MPI_DOUBLE, 0, MPI_COMM_WORLD);

#ifdef VERBOSE
  rc = printMat(Aloc, mloc, n, "\nMatrix A");
  CHECK(rc, 4);

  rc = printVec(b, n, "\nVector b");
  CHECK(rc, 5);
#endif

  /* STEP 3: Compute decomposition LU of A */
  rc = lu(Aloc, n, iproc, p, mb);
  CHECK(rc, 6);

#ifdef VERBOSE
  rc = printMat(Aloc, mloc, n, "\nMatrix LU");
  CHECK(rc, 7);
#endif

  /* STEP 4: Solve lower triangular system L*y=b */
  rc = triInf(Aloc, b, n, iproc, p, mb);
  CHECK(rc, 8);

#ifdef VERBOSE
  rc = printVec(b, n, "\nVector b after triInf");
#endif

  /* STEP 5: Solve upper triangular system U*x=y */
  rc = triSup(Aloc, b, n, iproc, p, mb);
  CHECK(rc, 9);

#ifdef VERBOSE
  rc = printVec(b, n, "\nVector b after triSup (system solution)");
  CHECK(rc, 10);
#endif

  if (iproc == 0) {
    dError = 0.0;
    for (i = 0; i < n; i++)
      dError += fabs(b[i] - 1.0);
    printf("\nTotal accumulated error: %f\n", dError);
  }

  free(b);
  rc = liberaMat(Aloc);
  CHECK(rc, 12);
  if (iproc == 0) {
    rc = liberaMat(A);
    CHECK(rc, 12);
  }

  t2 = MPI_Wtime();
  if(iproc==0){
     printf("Average elapsed time %f \n",(t2-t1));
  }

  MPI_Finalize();
  return 0;
}
