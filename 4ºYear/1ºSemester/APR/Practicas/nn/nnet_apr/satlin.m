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
## @deftypefn {} {@var{A} =} satlin (@var{n}, @var{FP})
## @deftypefnx  {} {@var{A} =} satlin (@var{n})
## @deftypefnx  {} {@var{info} =} satlin (@var{'code'})
## Saturating linear transfer function.
##
## Return a matrix of size equal to @var{n}, with values of 0 or 1
## when @var{n} is less than 0 or larger than 1, and same values of @var{n}
## when @var{n} is between 0 and 1.
##
## @example
##    / 0, if @var{n} < 0
##    | n, if 0 < @var{n} < 1
##    \ 1, if @var{n} > 1
## @end example
##
## @var{n} is a S-by-Q matrix of input, while @var{FP} is a struct of function
## parameters, ignored by satlin
##
## You can retrieve some information about the transfer function passing a
## string as first argument:
##
## @code{satlin (@var{'name'})} returns the name of the transfer function
##
## @code{satlin (@var{'output'})} returns the minimum and maximum of the
## output range
##
## @code{satlin (@var{'active'})} returns the minimum and maximum of the
## active input range
##
## @seealso{hardlim, hardlims, logsig, purelin, satlins, tansig}
## @end deftypefn

## Author: Carnë Draug <carandraug@octave.org>

function a = satlin (n)

  if (nargin < 1)
    print_usage ();
  endif

  if (isnumeric (n))
    a = double (n);
    a(n > 1) = 1;
    a(n < 0) = 0;
  elseif (isbool (n))
    a = double (n);
  elseif (ischar (n))
    switch (tolower (n))
      case "name",       a = "Linear Saturating Positive";
      case "output",     a = [0 1];
      case "active",     a = [0 1];
      case "fullderiv",  a = 0;
      case "fpnames",    error (["satlin: this feature has", ...
                                 " not been implemented yet."]);
      case "fpdefaults", error (["satlin: this feature has", ...
                                 " not been implemented yet."]);
      otherwise
        error ("satlin: unknown command '%s'", n);
    endswitch
  else
    print_usage ();
  endif

endfunction

%!assert (satlin (-2), 0)
%!assert (satlin (2), 1)
%!assert (satlin (0.1), .1)
%!assert (satlin (0.9), 0.9)
%!assert (satlin (0), 0)
%!assert (satlin (true), 1)
%!assert (satlin (false), 0)
%!assert (satlin (1, 'foo', 0), 1)

%!assert (satlin ([false true]), [0 1])
%!assert (satlin ([-3 -2.5 0; 2 .5 -3]), [0 0 0; 1 .5 0])

%!assert (satlin (single (4)), 1)

%!assert (satlin ("name"), "Linear Saturating Positive")
%!assert (satlin ("output"), [0 1])
%!assert (satlin ("active"), [0 1])
%!assert (satlin ("fullderiv"), 0)

%!error satlin ()
%!error satlin ('foo')
