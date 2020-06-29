octave:2> X=[ 4 4 -2 2 -2 2 -4 -4; 4 4 2 -2 2 -2 -4 -4; 1 -1 -1 1 1 -1 -1 1]
X =

   4   4  -2   2  -2   2  -4  -4
   4   4   2  -2   2  -2  -4  -4
   1  -1  -1   1   1  -1  -1   1

octave:3> X=X'
X =

   4   4   1
   4   4  -1
  -2   2  -1
   2  -2   1
  -2   2   1
   2  -2  -1
  -4  -4  -1
  -4  -4   1

octave:4> media=sum(X)/rows(X)
media =

   0   0   0

octave:5> media=mean(X)
media =

   0   0   0

octave:6> help cov
'cov' is a function from the file /usr/share/octave/4.2.2/m/statistics/base/cov.m

 -- cov (X)
 -- cov (X, OPT)
 -- cov (X, Y)
 -- cov (X, Y, OPT)
     Compute the covariance matrix.

     If each row of X and Y is an observation, and each column is a
     variable, then the (I, J)-th entry of 'cov (X, Y)' is the
     covariance between the I-th variable in X and the J-th variable in
     Y.

          cov (X) = 1/(N-1) * SUM_i (X(i) - mean(X)) * (Y(i) - mean(Y))

     where N is the length of the X and Y vectors.

     If called with one argument, compute 'cov (X, X)', the covariance
     between the columns of X.

     The argument OPT determines the type of normalization to use.
     Valid values are

     0:
          normalize with N-1, provides the best unbiased estimator of
          the covariance [default]

     1:
          normalize with N, this provides the second moment around the
          mean

     Compatibility Note:: Octave always treats rows of X and Y as
     multivariate random variables.  For two inputs, however, MATLAB
     treats X and Y as two univariate distributions regardless of their
     shapes, and will calculate 'cov ([X(:), Y(:)])' whenever the number
     of elements in X and Y are equal.  This will result in a 2x2
     matrix.  Code relying on MATLAB's definition will need to be
     changed when running in Octave.

     See also: corr.

Additional help for built-in functions and operators is
available in the online version of the manual.  Use the command
'doc <topic>' to search the manual index.

Help and information about Octave is also available on the WWW
at http://www.octave.org and via the help@octave.org
mailing list.
octave:7> cov(X,1)
ans =

   10    6    0
    6   10    0
    0    0    1

octave:8> A=X-m
error: 'm' undefined near line 1 column 5
octave:8> A=X-media
A =

   4   4   1
   4   4  -1
  -2   2  -1
   2  -2   1
  -2   2   1
   2  -2  -1
  -4  -4  -1
  -4  -4   1

octave:9> covarianza=(A'*A)/rows(X)
covarianza =

   10    6    0
    6   10    0
    0    0    1

octave:10> help eig
'eig' is a built-in function from the file libinterp/corefcn/eig.cc

 -- LAMBDA = eig (A)
 -- LAMBDA = eig (A, B)
 -- [V, LAMBDA] = eig (A)
 -- [V, LAMBDA] = eig (A, B)
 -- [V, LAMBDA, W] = eig (A)
 -- [V, LAMBDA, W] = eig (A, B)
 -- [...] = eig (A, BALANCEOPTION)
 -- [...] = eig (A, B, ALGORITHM)
 -- [...] = eig (..., EIGVALOPTION)
     Compute the eigenvalues (LAMBDA) and optionally the right
     eigenvectors (V) and the left eigenvectors (W) of a matrix or a
     pair of matrices.

     The flag BALANCEOPTION can be one of:

     "balance"
          Preliminary balancing is on.  (default)

     "nobalance"
          Disables preliminary balancing.

     The flag EIGVALOPTION can be one of:

     "matrix"
          Return the eigenvalues in a diagonal matrix.  (default if 2 or
          3 outputs are specified)

     "vector"
          Return the eigenvalues in a column vector.  (default if 1
          output is specified, e.g.  LAMBDA = eig (A))

     The flag ALGORITHM can be one of:

     "chol"
          Uses the Cholesky factorization of B. (default if A is
          symmetric (Hermitian) and B is symmetric (Hermitian) positive
          definite)

     "qz"
          Uses the QZ algorithm.  (When A or B are not symmetric always
          the QZ algorithm will be used)

                            no flag           chol              qz
     -----------------------------------------------------------------------------
     both are symmetric     "chol"            "chol"            "qz"
     at least one is not    "qz"              "qz"              "qz"
     symmetric

     The eigenvalues returned by 'eig' are not ordered.

     See also: eigs, svd.

Additional help for built-in functions and operators is
available in the online version of the manual.  Use the command
'doc <topic>' to search the manual index.

Help and information about Octave is also available on the WWW
at http://www.octave.org and via the help@octave.org
mailing list.
octave:11> [eigvec, eigval] = eig(covarianza)
eigvec =

   0.00000  -0.70711   0.70711
   0.00000   0.70711   0.70711
   1.00000   0.00000   0.00000

eigval =

Diagonal Matrix

    1    0    0
    0    4    0
    0    0   16

octave:12> W=[ eigvec(:,3) eigvec(:,2) ]
W =

   0.70711  -0.70711
   0.70711   0.70711
   0.00000   0.00000

octave:13> help sort
'sort' is a built-in function from the file libinterp/corefcn/data.cc

 -- [S, I] = sort (X)
 -- [S, I] = sort (X, DIM)
 -- [S, I] = sort (X, MODE)
 -- [S, I] = sort (X, DIM, MODE)
     Return a copy of X with the elements arranged in increasing order.

     For matrices, 'sort' orders the elements within columns

     For example:

          sort ([1, 2; 2, 3; 3, 1])
             =>  1  1
                 2  2
                 3  3

     If the optional argument DIM is given, then the matrix is sorted
     along the dimension defined by DIM.  The optional argument 'mode'
     defines the order in which the values will be sorted.  Valid values
     of 'mode' are "ascend" or "descend".

     The 'sort' function may also be used to produce a matrix containing
     the original row indices of the elements in the sorted matrix.  For
     example:

          [s, i] = sort ([1, 2; 2, 3; 3, 1])
            => s = 1  1
                   2  2
                   3  3
            => i = 1  3
                   2  1
                   3  2

     For equal elements, the indices are such that equal elements are
     listed in the order in which they appeared in the original list.

     Sorting of complex entries is done first by magnitude ('abs (Z)')
     and for any ties by phase angle ('angle (z)').  For example:

          sort ([1+i; 1; 1-i])
              => 1 + 0i
                 1 - 1i
                 1 + 1i

     NaN values are treated as being greater than any other value and
     are sorted to the end of the list.

     The 'sort' function may also be used to sort strings and cell
     arrays of strings, in which case ASCII dictionary order (uppercase
     'A' precedes lowercase 'a') of the strings is used.

     The algorithm used in 'sort' is optimized for the sorting of
     partially ordered lists.

     See also: sortrows, issorted.

Additional help for built-in functions and operators is
available in the online version of the manual.  Use the command
'doc <topic>' to search the manual index.

Help and information about Octave is also available on the WWW
at http://www.octave.org and via the help@octave.org
mailing list.
octave:14> [eigvec, eigval] = eig(covarianza)
eigvec =

   0.00000  -0.70711   0.70711
   0.00000   0.70711   0.70711
   1.00000   0.00000   0.00000

eigval =

Diagonal Matrix

    1    0    0
    0    4    0
    0    0   16

octave:15> diag(eigval)
ans =

    1
    4
   16

octave:16> [S I] = sort(diag(eigval),"descend")
S =

   16
    4
    1

I =

   3
   2
   1

octave:17> eigval=diag(eigval)
eigval =

    1
    4
   16

octave:18> eigval(I)
ans =

   16
    4
    1

octave:19> eigvec(:,I)
ans =

   0.70711  -0.70711   0.00000
   0.70711   0.70711   0.00000
   0.00000   0.00000   1.00000

octave:20> eigvec
eigvec =

   0.00000  -0.70711   0.70711
   0.00000   0.70711   0.70711
   1.00000   0.00000   0.00000

octave:21> eigvec=eigvec(:,I)
eigvec =

   0.70711  -0.70711   0.00000
   0.70711   0.70711   0.00000
   0.00000   0.00000   1.00000

octave:22> W=eigvec(:,1:2)
W =

   0.70711  -0.70711
   0.70711   0.70711
   0.00000   0.00000

octave:23> x=[4 4 1]
x =

   4   4   1

octave:24> x*W
ans =

   5.65685   0.00000

octave:25> X
X =

   4   4   1
   4   4  -1
  -2   2  -1
   2  -2   1
  -2   2   1
   2  -2  -1
  -4  -4  -1
  -4  -4   1

octave:26> X*W
ans =

   5.65685   0.00000
   5.65685   0.00000
   0.00000   2.82843
   0.00000  -2.82843
   0.00000   2.82843
   0.00000  -2.82843
  -5.65685   0.00000
  -5.65685   0.00000

octave:27> xl = [1 2 4 1 3 2 4 3]'
xl =

   1
   2
   4
   1
   3
   2
   4
   3

octave:28> [X*W xl]
ans =

   5.65685   0.00000   1.00000
   5.65685   0.00000   2.00000
   0.00000   2.82843   4.00000
   0.00000  -2.82843   1.00000
   0.00000   2.82843   3.00000
   0.00000  -2.82843   2.00000
  -5.65685   0.00000   4.00000
  -5.65685   0.00000   3.00000

octave:29> X
X =

   4   4   1
   4   4  -1
  -2   2  -1
   2  -2   1
  -2   2   1
   2  -2  -1
  -4  -4  -1
  -4  -4   1

octave:30> Walt=[0 0 1; 1 0 0]'
Walt =

   0   1
   0   0
   1   0

octave:31> X*Walt
ans =

   1   4
  -1   4
  -1  -2
   1   2
   1  -2
  -1   2
  -1  -4
   1  -4

octave:32> [X*Walt xl]
ans =

   1   4   1
  -1   4   2
  -1  -2   4
   1   2   1
   1  -2   3
  -1   2   2
  -1  -4   4
   1  -4   3

octave:33> [X*W xl]
ans =

   5.65685   0.00000   1.00000
   5.65685   0.00000   2.00000
   0.00000   2.82843   4.00000
   0.00000  -2.82843   1.00000
   0.00000   2.82843   3.00000
   0.00000  -2.82843   2.00000
  -5.65685   0.00000   4.00000
  -5.65685   0.00000   3.00000

octave:34> diary off
