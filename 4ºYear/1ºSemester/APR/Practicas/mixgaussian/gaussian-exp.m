#!/usr/bin/octave -qf

if (nargin!=5)
printf("Usage: gaussian-exp.m <trdata> <trlabels> <alphas> <%%trper> <%%dvper>\n")
exit(1);
end;

arg_list=argv();
trdata=arg_list{1};
trlabs=arg_list{2};
alphas=str2num(arg_list{3});
trper=str2num(arg_list{4});
dvper=str2num(arg_list{5});

load(trdata);
load(trlabs);

N=rows(X);
seed=23; rand("seed",seed); permutation=randperm(N);
X=X(permutation,:); xl=xl(permutation,:);

Ntr=round(trper/100*N);
Ndv=round(dvper/100*N);
Xtr=X(1:Ntr,:); xltr=xl(1:Ntr);
Xdv=X(N-Ndv+1:N,:); xldv=xl(N-Ndv+1:N);

[edv] = gaussian(Xtr,xltr,Xdv,xldv,alphas);

printf("\n  alpha dv-err");
printf("\n------- ------\n");

for i=1:length(alphas)
  printf("%.1e %6.3f\n",alphas(i),edv(i));
end
