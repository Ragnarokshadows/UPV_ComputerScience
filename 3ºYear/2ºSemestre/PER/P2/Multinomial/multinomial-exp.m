#!/usr/bin/octave -qf
 
if (nargin!=5)
printf("Usage: multinomial-exp.m <trdata> <trlabels> <eps> <%%trper> <%%dvper>\n")
exit(1);
end;
 
arg_list=argv();
trdata=arg_list{1};
trlabs=arg_list{2};
eps=str2num(arg_list{3});
trper=str2num(arg_list{4});
dvper=str2num(arg_list{5});
 
load(trdata);
load(trlabs);
 
N=rows(X);
rand("seed",23); permutation=randperm(N);
X=X(permutation,:); xl=xl(permutation,:);
 
Ntr=round(trper/100*N);
Ndv=round(dvper/100*N);
Xtr=X(1:Ntr,:); xltr=xl(1:Ntr);
Xdv=X(N-Ndv+1:N,:); xldv=xl(N-Ndv+1:N);
 
[etr edv]=multinomial(Xtr,xltr,Xdv,xldv,eps);

for i=1:columns(eps)
   printf("Error con epsilon (%.11f) = %f\n", eps(i), edv(i)*100);
end