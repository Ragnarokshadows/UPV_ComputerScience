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
## @deftypefn {} {@var{A} =} hardlim (@var{n}, @var{FP})
## @deftypefnx  {} {@var{A} =} hardlim (@var{n})
## @deftypefnx  {} {@var{info} =} hardlim (@var{'code'})
## Hard-limit transfer function.
##
## Return a matrix of size equal to @var{n}, with values of 1
## when @var{n} is larger than or equal to 0, and 0 otherwise.
##
## @example
##    / 0, @var{n} < 0
##    |
##    \ 1, @var{n} >= 0
## @end example
##
## @var{n} is a S-by-Q matrix of input, while @var{FP} is a struct of function
## parameters, ignored by hardlim
##
## You can retrieve some information about the transfer function passing a
## string as first argument:
##
## @code{hardlim (@var{'name'})} returns the name of the transfer function
##
## @code{hardlim (@var{'output'})} returns the minimum and maximum of the
## output range
##
## @code{hardlim (@var{'active'})} returns the minimum and maximum of the
## active input range
##
## It is equivalent to:
##
## @example
## double (@var{n} >= 0)
## @end example
##
## @seealso{hardlims, purelin, satlin, satlins, tansig}
## @end deftypefn

## Author: Carnë Draug <carandraug@octave.org>

function a = hardlim (n)

  if (nargin < 1)
    print_usage ();
  endif

  if (isnumeric (n))
    a = double (n >= 0);
  elseif (isbool (n))
    a = ones (size (n));
  elseif (ischar (n))
    switch (tolower (n))
      case "name",       a = "Hard Limit Positive";
      case "output",     a = [0 1];
      case "active",     a = [0 0];
      case "fullderiv",  a = 0;
      case "fpnames",    error (["hardlim: this feature has", ...
                                   " not been implemented yet."]);
      case "fpdefaults", error (["hardlim: this feature has", ...
                                   " not been implemented yet."]);
      otherwise
        error ("hardlim: unknown command '%s'", n);
    endswitch
  else
    print_usage ();
  endif
endfunction

%!error hardlim ()
%!error hardlim ('foo')
%!assert (hardlim (-2), 0)
%!assert (hardlim (2), 1)
%!assert (hardlim (0), 1)
%!assert (hardlim (true), 1)
%!assert (hardlim (false), 1)
%!assert (hardlim (1, 'foo', 1), 1)

%!assert (hardlim ([false true]), [1 1])
%!assert (hardlim ([-3 -2.5 0; 0 .5 -3]), [0 0 1; 1 1 0])

%!assert (hardlim (single (4)), 1)

%!assert (hardlim ("name"), "Hard Limit Positive")
%!assert (hardlim ("active"), [0 0])
%!assert (hardlim ("output"), [0 1])
%!assert (hardlim ("fullderiv"), 0)
