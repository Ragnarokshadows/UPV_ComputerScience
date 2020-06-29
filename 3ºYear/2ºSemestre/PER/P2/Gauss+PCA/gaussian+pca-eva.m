#!/usr/bin/octave -qf

if (nargin!=6)
printf("Usage: gaussian-eva.m <trdata> <trlabels> <tedata> <telabels> <as> <ks>\n")
exit(1);
end;

arg_list=argv();
trdata=arg_list{1};
trlabs=arg_list{2};
tedata=arg_list{3};
telabs=arg_list{4};
as=str2num(arg_list{5});
ks=str2num(arg_list{6});

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

[m,W] = pca(X);
 
for k=ks
    XtrR = (X-m) * W(:,1:k);
    XdvR = (Y-m) * W(:,1:k);
    [etr edv]=gaussian(XtrR,xl,XdvR,yl,as);

    for i=1:columns(as)
        printf("Error con alfa (%.11f) y dim (%d) = %f\n", as(i), k, edv(i)*100);
    end
end
