## Copyright (C) 2017, Francesco Faccio <francesco.faccio@mail.polimi.it>
## Copyright (C) 2015 Carnë Draug
##
## This program is free software; you can redistribute it and/or
## modify it under the terms of the GNU General Public License as
## published by the Free Software Foundation; either version 3 of the
## License, or (at your option) any later version.
##
## This program is distributed in the hope that it will be useful, but
## WITHOUT ANY WARRANTY; without even the implied warranty of
## MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
## General Public License for more details.
##
## You should have received a copy of the GNU General Public License
## along with this program; if not, see
## <http:##www.gnu.org/licenses/>.

## -*- texinfo -*-
## @deftypefn {} {@var{A} =} hardlims (@var{n}, @var{FP})
## @deftypefnx  {} {@var{A} =} hardlims (@var{n})
## @deftypefnx  {} {@var{info} =} hardlims (@var{'code'})
## Hard-limit symmetric transfer function.
##
## Return a matrix of size equal to @var{n}, with values of 1
## when @var{n} is larger than or equal to 0, and -1 otherwise.
## It is equivalent to:
##
## @example
##    / -1, @var{n} < 0
##    |
##    \  1, @var{n} >= 0
## @end example
##
## @var{n} is a S-by-Q matrix of input, while @var{FP} is a struct of function
## parameters, ignored by hardlims
##
## You can retrieve some information about the transfer function passing a
## string as first argument:
##
## @code{hardlims (@var{'name'})} returns the name of the transfer function
##
## @code{hardlims (@var{'output'})} returns the minimum and maximum of the
## output range
##
## @code{hardlims (@var{'active'})} returns the minimum and maximum of the
## active input range
##
## @seealso{hardlim, purelin, satlin, satlins, tansig}
## @end deftypefn

## Author: Carnë Draug <carandraug@octave.org>

function a = hardlims (n)

  if (nargin < 1)
    print_usage ();
  endif

  if (isnumeric (n))
    a = ifelse (n >= 0, 1, -1);
  elseif (isbool (n))
    a = ones (size (n));
  elseif (ischar (n))
    switch (tolower (n))
      case "name",       a = "Hard Limit Symmetric";
      case "output",     a = [-1 1];
      case "active",     a = [0 0];
      case "fullderiv",  a = 0;
      case "fpnames",    error (["hardlims: this feature has", ...
                                 " not been implemented yet."]);
      case "fpdefaults", error (["hardlims: this feature has", ...
                                 " not been implemented yet."]);
      otherwise
        error ("hardlims: unknown command '%s'", n);
    endswitch
  else
    print_usage ();
  endif
endfunction

%!error hardlims ()
%!error hardlims ('foo')
%!assert (hardlims (-2), -1)
%!assert (hardlims (2), 1)
%!assert (hardlims (0), 1)
%!assert (hardlims (true), 1)
%!assert (hardlims (false), 1)
%!assert (hardlims (1, 'foo', 1), 1)

%!assert (hardlims ([false true]), [1 1])
%!assert (hardlims ([-3 -2.5 0; 0 .5 -3]), [-1 -1 1; 1 1 -1])

%!assert (hardlims (single (4)), 1)

%!assert (hardlims ("name"), "Hard Limit Symmetric")
%!assert (hardlims ("output"), [-1 1])
%!assert (hardlims ("active"), [0 0])
%!assert (hardlims ("fullderiv"), 0)
