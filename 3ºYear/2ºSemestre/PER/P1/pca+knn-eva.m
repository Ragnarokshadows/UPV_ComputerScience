#!/usr/bin/octave -qf

if (nargin!=5)
printf("Usage: pca+knn-eva.m <trdata> <trlabels> <tedata> <telabels> <k>\n")
exit(1);
end;

arg_list=argv();
trdata=arg_list{1};
trlabs=arg_list{2};
tedata=arg_list{3};
telabs=arg_list{4};
k=str2num(arg_list{5});

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
 
XtrR = (X-m) * W(:,1:100);
XteR = (Y-m) * W(:,1:100);

err = knn(XtrR,xl,XteR,yl,ks);
printf("Error with PCA and k=%d: %f \n", k, err);

err = knn(X,xl,Y,yl,ks);
printf("Error without PCA and k=%d: %f \n", k, err);