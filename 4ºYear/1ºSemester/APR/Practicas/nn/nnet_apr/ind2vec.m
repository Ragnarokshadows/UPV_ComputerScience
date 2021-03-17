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
## @deftypefn {} {@var{vec} =} ind2vec (@var{ind}, @var{n})
## @deftypefnx  {} {@var{vec} =} ind2vec (@var{ind})
##
## Convert indices to sparse vector representation
##
## @var{ind} can be a row vector or a cell array of row vectors (not
## supported yet) of indices. @code{ind2vec (@var{ind})} returns a sparse
## matrix of column vectors, with 1 in each column corresponding with the
## index in @var{ind}.
##
## @var{n} is an integer greater or equal than the maximum index provided in
## @var{ind}. If you provide the second argument to the function,
## @code{ind2vec (@var{ind}, @var{n})} will return a N x M matrix, with zeros
## in the last N-M rows.
##
## @example
##
## vec = ind2vec ([1 4 3 2], 6);
## full (vec);
##
## @end example
##
## @end deftypefn

## @seealso{vec2ind}

function vector = ind2vec (ind, varargin)

  #FIXME: ind can be a cell array of row vectors

  if (nargin > 2 || nargin < 1 || nargout > 1)
    print_usage ();
  endif

  #Validate input parameters
  validateattributes (ind,
                      {"numeric", "logical"},
                      {"integer", "positive", "vector", "row"},
                      "ind2vec",
                      "ind");

  if (nargin == 2)

    # Convert second argument to double
    nrow = double (varargin{1});
    validateattributes (nrow,
                        {"numeric", "logical"},
                        {"integer", "positive", ">=", max(ind)},
                        "ind2vec",
                        "nrow");
  else
    nrow = max (ind);    
  endif

  ncol = numel (ind);
  vector = sparse (ind, 1:ncol, ones (1, ncol), nrow, ncol);

endfunction

## Test input validation
%!error ind2vec ()
%!error ind2vec (1,1,0)
%!error ind2vec (1,1,1,1)

%!error <ind2vec: ind must be of class:> ind2vec ('foo')
%!error <ind2vec: ind must be integer> ind2vec (1.2)
%!error <ind2vec: ind must be positive> ind2vec (0)
%!error <ind2vec: ind must be positive> ind2vec (-1)
%!error <ind2vec: ind must be vector> ind2vec (ones(3))
%!error <ind2vec: ind must be positive> ind2vec ([1 2 0])
%!error <ind2vec: ind must be positive> ind2vec ([1 -1])
%!error <ind2vec: ind must be row> ind2vec ([1 4]')

%!error <ind2vec: nrow must be integer> ind2vec (1,1.2)
%!error <ind2vec: nrow must be greater than or equal to 3> ind2vec (3,2)
%!error <ind2vec: nrow must be greater than or equal to 4> ind2vec ([1 4], 3)
%!error <ind2vec: nrow must be positive> ind2vec (1, -1)

%!test # one argument
%! vec = ind2vec ([1 4]);
%! assert ([vec], [sparse([1 0 0 0; 0 0 0 1]')]);

%!test # one argument logical
%! vec = ind2vec ([true true 2]);
%! assert ([vec], [sparse([1 0; 1 0; 0 1]')]);

%!test # add empty rows if nrow > ncol
%! vec = ind2vec ([1 4], 6);
%! assert ([vec], [sparse([1 0 0 0 0 0; 0 0 0 1 0 0]')]);

%!test # don't add rows if nrow == ncol
%! assert ([ind2vec([1 4])], [ind2vec([1 4], 4)]);

%!test # Convert second argument if it's not a double (Matlab compatibility)
%! vec = ind2vec ([1 2], 'Hi Mario');
%! assert ([size(vec)], [double('H'),2]);

%!test # second argument logical
%! vec = ind2vec ([true 1], true);
%! assert ([vec], [sparse([1 1])]);


