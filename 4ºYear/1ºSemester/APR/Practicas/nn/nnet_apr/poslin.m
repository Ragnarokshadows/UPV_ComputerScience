## Copyright (C) 2017, Francesco Faccio <francesco.faccio@mail.polimi.it>
## Copyright (C) 2007 Michel D. Schmid  <michael.schmid@plexso.com>
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
## @deftypefn {} {@var{A} =} poslin (@var{n}, @var{FP})
## @deftypefnx  {} {@var{A} =} poslin (@var{n})
## @deftypefnx  {} {@var{info} =} poslin (@var{'code'})
## Linear Positive transfer function.
##
## Return a matrix of size equal to @var{n}, with values equal to @var{n}
## when @var{n} is larger than or equal to 0, and 0 otherwise.
## It is equivalent to:
##
## @example
##    / 0, @var{n} < 0
##    |
##    \ n, @var{n} >= 0
## @end example
##
## @var{n} is a S-by-Q matrix of input, while @var{FP} is a struct of function
## parameters, ignored by poslin
##
## You can retrieve some information about the transfer function passing a
## string as first argument:
##
## @code{poslin (@var{'name'})} returns the name of the transfer function
##
## @code{poslin (@var{'output'})} returns the minimum and maximum of the
## output range
##
## @code{poslin (@var{'active'})} returns the minimum and maximum of the
## active input range
##
## @seealso{hardlim, poslin, purelin, satlin, satlins, tansig}
## @end deftypefn

## Author: Michel D. Schmid

function a = poslin (n)

  if (nargin < 1)
    print_usage ();
  endif
  
  if (isnumeric (n))
    a = ifelse (n > 0, double (n), 0);
  elseif (isbool (n))
    a = double (n);
  elseif (ischar (n))
    switch (tolower (n))
      case "name",       a = "Linear Positive";
      case "output",     a = [0 Inf];
      case "active",     a = [0 Inf];
      case "fullderiv",  error (["poslin: this feature has", ...
                                 " not been implemented yet."]);
      case "fpnames",    error (["poslin: this feature has", ...
                                 " not been implemented yet."]);
      case "fpdefaults", error (["poslin: this feature has", ...
                                 " not been implemented yet."]);
      otherwise
        error ("poslin: unknown command '%s'", n);
    endswitch
  else
    print_usage ();
  endif

endfunction

%!error poslin ()
%!error poslin ('foo')
%!assert (poslin (-2), 0)
%!assert (poslin (2), 2)
%!assert (poslin (0), 0)
%!assert (poslin (true), 1)
%!assert (poslin (false), 0)
%!assert (poslin (1, 'foo', 1), 1)

%!assert (poslin ([false true]), [0 1])
%!assert (poslin ([-3 -2.5 0; 0 .5 -3]), [0 0 0; 0 .5 0])

%!assert (poslin (single (4)), 4)

%!assert (poslin ("name"), "Linear Positive")
%!assert (poslin ("output"), [0 Inf])
%!assert (poslin ("active"), [0 Inf])






