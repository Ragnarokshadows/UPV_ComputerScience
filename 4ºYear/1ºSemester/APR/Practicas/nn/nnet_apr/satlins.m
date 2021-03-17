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
## @deftypefn {} {@var{A} =} satlins (@var{n}, @var{FP})
## @deftypefnx  {} {@var{A} =} satlins (@var{n})
## @deftypefnx  {} {@var{info} =} satlins (@var{'code'})
## Symmetric saturating linear transfer function.
##
## Return a matrix of size equal to @var{n}, with values of -1 or 1
## when @var{n} is less than -1 or larger than 1, and same values of @var{n}
## when @var{n} is between -1 and 1.
##
## @example
##    / -1, if @var{n} < -1
##    |  n, if -1 < @var{n} < 1
##    \  1, if @var{n} > 1
## @end example
##
## @var{n} is a S-by-Q matrix of input, while @var{FP} is a struct of function
## parameters, ignored by satlins
##
## You can retrieve some information about the transfer function passing a
## string as first argument:
##
## @code{satlins (@var{'name'})} returns the name of the transfer function
##
## @code{satlins (@var{'output'})} returns the minimum and maximum of the
## output range
##
## @code{satlins (@var{'active'})} returns the minimum and maximum of the
## active input range
##
## @seealso{hardlim, hardlims, logsig, poslin, purelin, tansig}
## @end deftypefn

## Author: Carnë Draug <carandraug@octave.org>

function a = satlins (n)

  if (nargin < 1)
    print_usage ();
  endif

  if (isnumeric (n))
    a = double (n);
    a(n > 1) = 1;
    a(n < 0) = -1;
  elseif (isbool (n))
    a = double (n);
  elseif (ischar (n))
    switch (tolower (n))
      case "name",      a = "Linear Saturating Symmetric";
      case "output",    a = [-1 1];
      case "active",    a = [-1 1];
      case "fullderiv", a = 0;
      case "fpnames",    error (["satlins: this feature has", ...
                                 " not been implemented yet."]);
      case "fpdefaults", error (["satlins: this feature has", ...
                                 " not been implemented yet."]);
      otherwise
        error ("satlins: unknown command '%s'", n);
    endswitch
  else
    print_usage ();
  endif

endfunction

%!assert (satlins (-2), -1)
%!assert (satlins (2), 1)
%!assert (satlins (0.1), .1)
%!assert (satlins (0.9), 0.9)
%!assert (satlins (0), 0)
%!assert (satlins (true), 1)
%!assert (satlins (false), 0)
%!assert (satlins (1, 'foo', 0), 1)

%!assert (satlins ([false true]), [0 1])
%!assert (satlins ([3 -2.5 0; 1 .5 -3]), [1 -1 0; 1 .5 -1])

%!assert (satlins (single (4)), 1)

%!assert (satlins ("name"), "Linear Saturating Symmetric")
%!assert (satlins ("output"), [-1 1])
%!assert (satlins ("active"), [-1 1])
%!assert (satlins ("fullderiv"), 0)

%!error satlins ()
%!error satlins ('foo')




