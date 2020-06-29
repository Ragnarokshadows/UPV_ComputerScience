#!/usr/bin/octave -qf

if (nargin!=6)
printf("Usage: multinomial-eva.m <trdata> <trlabels> <tedata> <telabels> <thresh> <eps>\n")
exit(1);
end;

arg_list=argv();
trdata=arg_list{1};
trlabs=arg_list{2};
tedata=arg_list{3};
telabs=arg_list{4};
thresh=str2num(arg_list{5});
eps=str2num(arg_list{6});

load(trdata);
load(trlabs);
load(tedata);
load(telabs);

%
% HERE YOUR CODE
%

N=rows(X);
rand("seed",23); permutation=randperm(N);
X=X(permutation,:); xl=xl(permutation,:);

[etr edv]=bernoulli(X,xl,Y,yl,thresh,eps);

for i=1:columns(thresh)
   for j=1:columns(eps)
      printf("Error con threshold (%d) and epsilon (%d) = %f\n", thresh(i), eps(j), edv(i*j)*100);
   end
end