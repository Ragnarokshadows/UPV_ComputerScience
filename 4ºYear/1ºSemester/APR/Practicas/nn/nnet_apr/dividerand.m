## Copyright (C) 2016, Francesco Faccio <francesco.faccio@mail.polimi.it>
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
## @deftypefn  {} {[@var{itr},@var{ival},@var{itest}] =} dividerand (@var{n},@var{trainratio},@var{valratio},@var{testratio})
## @deftypefnx  {} {[@var{itr},@var{ival},@var{itest}] =} dividerand (@var{alltargets})
## @deftypefnx  {} {[@var{itr},@var{ival},@var{itest}] =} dividerand (@var{alltargets},@var{trainratio})
## @deftypefnx  {} {[@var{itr},@var{ival},@var{itest}] =} dividerand (@var{alltargets},@var{trainratio},@var{valratio})
## @deftypefnx  {} {[@var{itr}] =} dividerand (@dots{})
## @deftypefnx  {} {[@var{itr},@var{ival}] =} dividerand (@dots{})
##
## Divide indices in training, validation and test group according to the
## informed ratios.
##
## If @var{n} is a number, it represents the number of indices you want to
## divide randomly. The function returns three sets of indices according to
## the ratio coefficients provided.
##
## If @var{n} is a matrix, the function divides the columns of the matrix in
## the three sets and it returns them.
##
## @var{trainratio},@var{valratio},@var{testratio} are the ratio coefficients
## provided by the user. If not specified, the default value
## is @var{trainratio} = 0.7, @var{valratio} = 0.15, @var{testratio} = 0.15.
## If the value of the ratios are not probabilities, they are normalized.
##
## Example:
##
## @example
##
## [indextrain,indexvalidation,indextest] = dividerand (30);
## [indextrain,indexvalidation,indextest] = dividerand (eye(100),56,12,14);
##
## @end example
##
## @end deftypefn

function varargout = dividerand (alltargets, varargin)

  if (nargin > 4 || nargin < 1 || nargout > 3)
    print_usage ();
  endif

  #Set default values
  trainratio = 0.7;
  valratio   = 0.15;
  testratio  = 0.15;

  if (nargin > 1)
    trainratio = varargin{1};
    if (nargin > 2)
      valratio = varargin{2};
      if (nargin > 3)
        testratio = varargin{3};
      endif
    endif
  endif

  #Validate input parameters
  validateattributes (alltargets, {"numeric"},
                      {"integer"}, "dividerand",
                      "alltargets");
  validateattributes (trainratio, {"numeric"},
                      {"positive"}, "dividerand",
                      "trainratio");
  validateattributes (valratio, {"numeric"},
                      {"nonnegative"}, "dividerand",
                      "valratio");
  validateattributes (testratio, {"numeric"},
                      {"nonnegative"}, "dividerand",
                      "testratio");

  #Normalize ratio coefficients
  total      = trainratio + valratio + testratio;
  trainratio = trainratio / total;
  valratio   = valratio   / total;
  testratio  = testratio  / total;

  #Calculate the number of cases for each type

  if (alltargets > 0 || size (alltargets, 2) > 1)
 
    if (size (alltargets, 2) > 1);
      numtargets = size (alltargets, 2);
    else
      numtargets = alltargets;
    endif

    numval   = round (valratio * numtargets);
    numtest  = round (testratio * numtargets);
    numtrain = numtargets - numval - numtest;

    #Find their indexes
    indtargets = randperm (numtargets);
    indval    = sort (indtargets (1:numval));
    indtest   = sort (indtargets (numval + (1:numtest)));
    indtrain  = sort (indtargets (numtest + numval + (1:numtrain)));
  else
    indtrain = zeros (1, 0);
    indval   = zeros (1, 0);
    indtest  = zeros (1, 0);
  endif

  #Output
  if (size (alltargets, 2) == 1)
    varargout{1} = indtrain;
    if (nargout > 1)
      varargout{2} = indval;
      if (nargout > 2)
        varargout{3} = indtest;
      endif
    endif
  else
    varargout{1} = alltargets (:, indtrain);
    if (nargout > 1)
      varargout{2} = alltargets (:, indval);
      if (nargout > 2)
        varargout{3} = alltargets (:, indtest);
      endif
    endif

  endif

endfunction

## Test input validation
%!error dividerand ()
%!error dividerand (1,1,1,1,1)
%!error dividerand (1,-2)
%!error dividerand (10,0,1,1)
%!error dividerand (-1,0,1,1)
%!error <dividerand: alltargets must be of class:> dividerand ('foo', 1, 1, 2)
%!error <dividerand: trainratio must be of class:> dividerand (10, 'foo', 1, 2)
%!error <dividerand: trainratio must be positive> dividerand (10, -2, 1, 2)
%!test # Allow to set zero ratio to valratio and testratio
%! [a,b,c] = dividerand (10, 1, 0, 0);
%! assert ([a,size(b),size(c)], [[1:10],[1 0],[1 0]]);
%!test # If alltargets is a negative number, return empty structure
%! [a,b,c] = dividerand (-1);
%! assert ([size(a),size(b),size(c)], [[1 0],[1 0],[1 0]]);
%!test # If alltargets is a negative number, return empty structure
%! [a,b,c] = dividerand (-1,1,0,0);
%! assert ([size(a),size(b),size(c)], [[1 0],[1 0],[1 0]]);
%!test # If alltargets is a matrix, divide the matrix
%! [a,b,c] = dividerand (eye(20));
%! assert ([size(a),size(b),size(c)], [[20 14],[20 3],[20 3]]);
%! [a,b,c] = dividerand (eye(20), 10, 15, 10);
%! assert ([size(a),size(b),size(c)], [[20 5],[20 9],[20 6]]);
%!test # empty structure if ratio is too small
%! [a,b,c] = dividerand (10,1e2,1,1);
%! assert ([size(a),size(b),size(c)], [[1 10],[1 0],[1 0]]);
%!test # no error if alltargets is a negative matrix
%! [a,b,c] = dividerand (-1.*ones(20));
%! assert ([size(a),size(b),size(c)], [[20 14],[20 3],[20 3]]);


