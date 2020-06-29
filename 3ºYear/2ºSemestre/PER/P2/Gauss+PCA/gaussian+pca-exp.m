#!/usr/bin/octave -qf
 
if (nargin!=6)
printf("Usage: gaussian-exp.m <trdata> <trlabels> <as> <ks> <%%trper> <%%dvper>\n")
exit(1);
end;
 
arg_list=argv();
trdata=arg_list{1};
trlabs=arg_list{2};
as=str2num(arg_list{3});
ks=str2num(arg_list{4});
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
 
[m,W] = pca(Xtr);


for k=ks

    XtrR = (Xtr-m) * W(:,1:k);
    XdvR = (Xdv-m) * W(:,1:k);
    [etr edv]=gaussian(XtrR,xltr,XdvR,xldv,as);

    for i=1:columns(as)
        printf("Error con alfa (%.11f) y dim (%d) = %f\n", as(i), k, edv(i)*100);
    end

end






