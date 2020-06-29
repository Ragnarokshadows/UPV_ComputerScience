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

XtrR = (X-m) * W(:,1:k);
XteR = (Y-m) * W(:,1:k);

[XtrR, xlR] = wilson(XtrR,xl);

err = knn(XtrR,xlR,XteR,yl,1);
printf("Error with PCA and then Wilson: %f \n", err);

[XtrR, xlR] = wilson(X,xl);

[m,W] = pca(XtrR);

XtrR = (XtrR-m) * W(:,1:k);

err = knn(XtrR,xlR,XteR,yl,1);
printf("Error with Wilson and then PCA: %f \n", err);