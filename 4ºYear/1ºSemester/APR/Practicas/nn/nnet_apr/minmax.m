## Copyright (C) 2012 Carnë Draug <carandraug+dev@gmail.com>
##
## This program is free software; you can redistribute it and/or modify it under
## the terms of the GNU General Public License as published by the Free Software
## Foundation; either version 3 of the License, or (at your option) any later
## version.
##
## This program is distributed in the hope that it will be useful, but WITHOUT
## ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
## FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
## details.
##
## You should have received a copy of the GNU General Public License along with
## this program; if not, see <http://www.gnu.org/licenses/>.

## -*- texinfo -*-
## @deftypefn {Function File} {@var{Pr} =} minmax (@var{Pp})
## Calculate maximum and mininum of rows.
##
## For each row of the matrix @var{Pp}, outputs its minimum and maximum on the
## first and second column of @var{Pr} respectively.  @var{Pr} will have the
## same number of rows as @var{Pp} and 2 columns.
##
## @group
## @example
## Pp = [5 7 9 2 5 0 6
##       5 3 6 2 7 9 3
##       8 3 2 3 5 6 8]
## minmax (Pp)
##   @result{} 0 9
##      2 9
##      0 8
## @end example
## @end group
##
## @var{Pp} can also be a cell array of matrices in wich case they all must have
## the same number of columns, and all matrices on each row of cells must have
## the same number of rows.  In this case, matrices of each row of @var{Pp} are
## concatenated horizontally for calculating the minimum ad maximum values.
## @var{Pr} will be a single column cell array with same number of rows as
## @var{Pp}. For example:
##
## @group
## @example
## Pp = @{[0 1; 1 2; 4 6] [2 3; 8 0; 3 1] [9 1; 5 2; 4 8];
##        [1 2; 9 7] [5 2; 3 1] [7 6; 0 3]@}
## minmax (Pp)
##   @result{} @{
##        [1,1] =
##           0   9
##           0   8
##           1   8
##        [2,1] =
##           1   7
##           0   9
##      @}
## @end example
## @end group
##
## If drawn on a table, it would look like:
##
## @verbatim
##   2x3 cell array      2x1 cell array
##
##  0 1   2 3   9 1   >      0 9
##  1 2   8 0   5 2   >      0 8
##  4 6   3 1   4 8   >      1 8
##
##  1 2   5 2   7 6   >      1 7
##  9 7   3 1   0 3   >      0 9
## @end verbatim
##
## Note how on this example: the number of columns (3) in the cell array is
## irrelevant but the output has the same number of rows (2); all matrices have
## the same number of columns (2).
##
## @seealso {cell2mat, max, min}
## @end deftypefn

function Pr = minmax (Pp)

  if (nargin != 1)
    print_usage;
  elseif (minmax_check (Pp))
    Pr = single_minmax (Pp);
  elseif (iscell (Pp) && ndims (Pp) == 2 && all (cellfun (@minmax_check, Pp(:))))
    Pr_rows = cellfun (@rows, Pp(:,1));
    if (!all (cellfun (@columns, Pp(:)) == columns (Pp{1})))
      error ("minmax: all matrices must have the same number of columns.");
    elseif (!all (bsxfun (@eq, cellfun (@rows, Pp), Pr_rows)(:)))
      error ("minmax: all matrices in a row of cells must have same number of rows.");
    endif
    Pr = mat2cell (single_minmax (cell2mat (Pp)), Pr_rows, 2);
  else
    error ("minmax: input must be one, or a 2D cell array of, 2D non-complex matrix.");
  endif

endfunction

function retval = minmax_check (val)
  retval = isnumeric (val) && !iscomplex (val) && ndims (val) == 2;
endfunction

function Pr = single_minmax (Pp)
  Pr = [min(Pp, [], 2) max(Pp, [], 2)];
endfunction

%!assert (minmax ([2 5 4; -2 6 5]), [2 5; -2 6]);                         # basic usage
%!assert (minmax ([2 5 4]), [2 5]);                                       # single row, basic usage
%!assert (minmax ({[0 1; -1 -2; 34 56] [2 3; 8 0; 21 23]; [1 -2; 9 7] [12 5; 13 11]}), ...
%!        {[0 3; -2 8; 21 56]; [-2 12; 7 13]});                           # basic usage with cell arrays
%!assert (minmax (1), [1 1]);                                             # matlab compatibility
%!fail ("minmax ([i 2; 3 4])");                                           # do not accept complex values
%!fail ("minmax (rand (2, 2, 2))");                                       # only 2D matrix
%!fail ("minmax ({[0 1; 1 2] [2 3 2; 8 0 2]; [1 2] [9 7 3]})");           # number of columns must be the same everywhere
%!fail ("minmax ({[0 1; 1 2] [2 3; 8 0; 5 5]; [1 2; 9 7] [1 5; 1 1]})");  # each row of cells must have matrices with same number of rows
