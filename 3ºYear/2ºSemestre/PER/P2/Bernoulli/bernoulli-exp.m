#!/usr/bin/octave -qf
 
if (nargin!=6)
printf("Usage: bernoulli-exp.m <trdata> <trlabels> <thresh> <eps> <%%trper> <%%dvper>\n")
exit(1);
end;
 
arg_list=argv();
trdata=arg_list{1};
trlabs=arg_list{2};
thresh=str2num(arg_list{3});
eps=str2num(arg_list{4});
trper=str2num(arg_list{5});
dvper=str2num(arg_list{6});
 
load(trdata);
load(trlabs);
 
N=rows(X);
rand("seed",23); permutation=randperm(N);
X=X(permutation,:); xl=xl(permutation,:);
 
Ntr=round(trper/100*N);
Ndv=round(dvper/100*N);
Xtr=X(1:Ntr,:); xltr=xl(1:Ntr);
Xdv=X(N-Ndv+1:N,:); xldv=xl(N-Ndv+1:N);
 
[etr edv]=bernoulli(Xtr,xltr,Xdv,xldv,thresh,eps);

for i=1:columns(thresh)
   for j=1:columns(eps)
      printf("Error con threshold (%d) and epsilon (%d) = %f\n", thresh(i), eps(j), edv(i*j)*100);
   end
end