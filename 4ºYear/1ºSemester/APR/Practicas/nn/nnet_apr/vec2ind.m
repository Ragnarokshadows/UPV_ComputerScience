## Copyright (C) 2017, Francesco Faccio <francesco.faccio.93@gmail.com>
## Copyright (C) 2009 Luiz Angelo Daros de Luca <luizluca@gmail.com>
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
## @deftypefn {} {[@var{ind},@var{n}] =} vec2ind (@var{vec})
## @deftypefnx  {} {@var{ind} =} vec2ind (@var{vec})
##
## Convert a matrix of vectors into indices.
##
## @var{vec} is a matrix with a 1 on each column. 
##
## @code{vec2ind (@var{ind}, @var{n})} will return 2 arguments:
## @var{ind} is a row vector containing for each column of @var{vec} the index
## where the 1 is. 
## @var{n} is the number of rows in @var{vec}.
##
## @example
##
## [ind, n] = vec2ind ([1 0 1; 0 1 0; 0 0 0; 0 0 0]);
##
## @end example
##
## @end deftypefn

## @seealso{ind2vec}

function varargout = vec2ind (vector)

  if (nargin != 1 || nargout > 2)
    print_usage ();
  endif

  # Input validation
  validateattributes (vector,
                      {"numeric", "logical"},
                      {"integer", ">=", 0, "<=", 1},
                      "vec2ind",
                      "vector");

  if (! (all (sum (vector (:, 1:end)) == 1)))
    error ("vec2ind: vector must contain a single 1 in each column");
  endif

  [ind, col] = find (vector);
  ind = ind';
  n = size (vector, 1);

  varargout{1} = ind;

  if (nargout == 2)
    varargout{2} = n;
  endif

endfunction

## Test input validation
%!error vec2ind ()
%!error vec2ind (1,1)

%!error <vec2ind: vector must be of class:> vec2ind ('foo')
%!error <vec2ind: vector must be integer> vec2ind (1.2)
%!error <vec2ind: vector must be greater than or equal to 0> vec2ind (-1)
%!error <vec2ind: vector must be less than or equal to 1> vec2ind ([1 2 0])
%!error <vec2ind: vector must be greater than or equal to 0> vec2ind ([1 -1])
%!error <vec2ind: vector must contain a single 1 in each column> vec2ind ([1 1; 1 0])

%!test 
%! [ind n] = vec2ind ([0 0 1 1 0; 0 1 0 0 1; 1 0 0 0 0; 0 0 0 0 0]);
%! assert ([ind n], [[3 2 1 1 2], 4]);




