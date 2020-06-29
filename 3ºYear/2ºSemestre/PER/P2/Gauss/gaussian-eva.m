#!/usr/bin/octave -qf

if (nargin!=5)
printf("Usage: gaussian-eva.m <trdata> <trlabels> <tedata> <telabels> <as>\n")
exit(1);
end;

arg_list=argv();
trdata=arg_list{1};
trlabs=arg_list{2};
tedata=arg_list{3};
telabs=arg_list{4};
as=str2num(arg_list{5});

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

[etr edv]=gaussian(X,xl,Y,yl,as);

for i=1:columns(as)
    printf("Error con alpha (%.11f) = %f\n", as(i), edv(i)*100);
end