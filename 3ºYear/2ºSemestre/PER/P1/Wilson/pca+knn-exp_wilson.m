#!/usr/bin/octave -qf
 
if (nargin!=5)
printf("Usage: pca+knn-exp.m <trdata> <trlabels> <ks> <%%trper> <%%dvper>\n")
exit(1);
end;
 
arg_list=argv();
trdata=arg_list{1};
trlabs=arg_list{2};
ks=str2num(arg_list{3});
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
 
[m,W] = pca(Xtr);
[XtrR2, xlR2] = wilson(Xtr,xl);

err = knn(XtrR2,xlR2,Xdv,xldv,1);
printf("Error Wilson: %d \n", err);

[m2,W2] = pca(XtrR2);
 
for i=ks
   XtrR = (Xtr-m) * W(:,1:i);
   XdvR = (Xdv-m) * W(:,1:i);

   [XtrR, xlR] = wilson(XtrR,xl);
   err = knn(XtrR,xlR,XdvR,xldv,1);
   printf("dim = %d -> error PCA+Wilson: %d \n", i, err);

   XtrR3 = (XtrR2-m2) * W2(:,1:i);
   XdvR3 = (Xdv-m2) * W2(:,1:i);
   err = knn(XtrR3,xlR2,XdvR3,xldv,1);
   printf("dim = %d -> error Wilson+PCA: %d \n", i, err);
end
