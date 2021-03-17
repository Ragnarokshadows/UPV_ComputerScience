## Copyright (C) 2017, Francesco Faccio <francesco.faccio@mail.polimi.it>
## Copyright (C) 2005 Michel D. Schmid  <michael.schmid@plexso.com>
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
## @deftypefn {} {@var{A} =} purelin (@var{n}, @var{FP})
## @deftypefnx  {} {@var{A} =} purelin (@var{n})
## @deftypefnx  {} {@var{info} =} purelin (@var{'code'})
## Linear transfer function.
##
## Return a matrix of size equal to @var{n}, with same values of @var{n} 
##
## @var{n} is a S-by-Q matrix of input, while @var{FP} is a struct of function
## parameters, ignored by purelin
##
## You can retrieve some information about the transfer function passing a
## string as first argument:
##
## @code{purelin (@var{'name'})} returns the name of the transfer function
##
## @code{purelin (@var{'output'})} returns the minimum and maximum of the
## output range
##
## @code{purelin (@var{'active'})} returns the minimum and maximum of the
## active input range
##
## @seealso{hardlim, hardlims, satlin, satlins, tansig}
## @end deftypefn

## Author: Michel D. Schmid

function a = purelin (n)

  if (nargin < 1)
    print_usage ();
  endif

  if (isnumeric (n) || isbool (n))
    a = double (n);
  elseif (ischar (n))
    switch (tolower (n))
      case "name",       a = "Linear";
      case "output",     a = [-Inf Inf];
      case "active",     a = [-Inf Inf];
      case "fullderiv",  error (["purelin: this feature has", ...
                                 " not been implemented yet."]);
      case "fpnames",    error (["purelin: this feature has", ...
                                 " not been implemented yet."]);
      case "fpdefaults", error (["purelin: this feature has", ...
                                 " not been implemented yet."]);
      otherwise
        error ("purelin: unknown command '%s'", n);
    endswitch
  else
    print_usage ();
  endif
endfunction

%!assert (purelin (2), 2);
%!assert (purelin (-2), -2);
%!assert (purelin (0), 0);
%!assert (purelin (ones (2)), ones (2));
%!assert (purelin ("name"), "Linear")
%!assert (purelin ("output"), [-Inf Inf])
%!assert (purelin ("active"), [-Inf Inf])
%!assert (purelin (true), 1)
%!assert (purelin (false), 0)
%!assert (purelin (10, 'foo', 1), 10)

%!assert (purelin (single (4)), 4)
%!assert (purelin ([true false]), [1 0])

%!error assert (purelin (2), 1);
%!error purelin ()
%!error purelin ('foo')






