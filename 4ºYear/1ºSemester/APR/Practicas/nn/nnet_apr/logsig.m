## Copyright (C) 2017, Enrico Bertino <enrico.bertino@mail.polimi.it>
## Copyright (C) 2007 Michel D. Schmid <michael.schmid@plexso.com>
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
## @deftypefn  {} {@var{a} =} logsig (@var{n})
## @deftypefn  {} {@var{a} =} logsig (@var{n} @var{FP})
## @deftypefn  {} {@var{a} =} logsig (@var{'dn'}, @var{n})
## @deftypefn  {} {@var{a} =} logsig (@var{'dn'}, @var{n}, @var{A})
## @deftypefn  {} {@var{a} =} logsig (@var{'dn'}, @var{n}, @var{A}, @var{FP})
## @deftypefn  {} {@var{info} =} logsig (@var{'code'})
## 
## @code{logsig} is a non-linear transfer function used to train
## neural networks.
##
## Return the log-sigmoid of every element or the derivative of the log-sigmoid 
## if the input 'dn' is given.
##
## @var{n} is a S-by-Q matrix of input, while @var{FP} is a struct of function
## parameters, ignored by logsig
##
## You can retrieve some information about the transfer function passing a
## string as first argument:
##
## @code{logsig (@var{'name'})} returns the name of the transfer function
##
## @code{logsig (@var{'output'})} returns the minimum and maximum of the
## output range
##
## @code{logsig (@var{'active'})} returns the minimum and maximum of the
## active input range
##
## This function can be used in newff(...) to create a new feed forward
## multi-layer neural network.
##
## @end deftypefn

## @seealso{purelin,tansig}

## Author: Michel D. Schmid


function a = logsig(varargin)

  if (nargin < 1)
    print_usage ();
  endif


  if (ischar (varargin{1}))
    switch (tolower (varargin{1}))
      case "name",       a = "Sigmoid Positive";
      case "output",     a = [0 1];
      case "active",     a = [-4 4];
      case "fullderiv",  error (["logsig: this feature has", ...
                                 " not been implemented yet."]);
      case "fpnames",    error (["logsig: this feature has", ...
                                 " not been implemented yet."]);
      case "fpdefaults", error (["logsig: this feature has", ...
                                 " not been implemented yet."]);
      case "dn"
        if (nargin < 2 || nargout > 1)
            print_usage ();
        endif
        
        if (isnumeric (varargin{2}) || islogical (varargin{2}))
          n = varargin{2};
        else
          print_usage ();
        endif
        
        a =  exp (n) ./ ((1 + exp (n)) .^ 2);
        if(nargin == 3)
          if (isnumeric (varargin{3}))
            A = varargin{3};
	    assert (A, logsig(n), 1e-4); 
          endif
	endif

      otherwise
        error ("logsig: unknown command '%s'", varargin{1});
    endswitch
    
  elseif (isnumeric (varargin{1}) || islogical (varargin{1}))
  n = varargin{1};
  a = 1 ./ (1 + exp (-n));
  endif
endfunction 


%!assert (logsig (0), 0.5)
%!assert (logsig (inf), 1)
%!assert (logsig (-inf), 0)
%!assert (logsig ([1, 2]), [0.7311, 0.8808], 1e-3)
%!assert (logsig (true),0.7311, 1e-3)
%!assert (logsig ([false true]), [ 0.5000, 0.7311], 1e-3)
%!assert (logsig ([-3 -2.5 0; 2 .5 -3]), [0.0474, 0.0759, 0.5000; 0.8808, 0.6225, 0.0474], 1e-3)
%!assert (logsig(single (4)), 0.9829, 1e-3)
%!assert (logsig ("name"), "Sigmoid Positive")
%!assert (logsig ("output"), [0 1])
%!assert (logsig ("active"), [-4 4])
%!assert (logsig('dn',[1,2,3]), [0.1966, 0.1050, 0.0452], 1e-3)
