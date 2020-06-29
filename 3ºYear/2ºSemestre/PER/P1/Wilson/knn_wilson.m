% Computes the error rate of k nearest neighbors 
% of the test set Y with respect to training set X
% X  is a n x d training data matrix 
% xl is a n x 1 training label vector 
% Y is a m x d test data matrix
% yl is a m x 1 test label vector 
% k is the number of nearest neigbors
function [err]=knn_wilson(X,xl,Y,yl,k)

N=rows(X);
M=rows(Y);
numbatches=ceil(N*M*4/1024^3)*4;
if (numbatches<1) numbatches=1; end

batchsize=ceil(M/numbatches);

classification=[];

% The classification of the test samples is split 
% into batches to make sure that the distance matrix D 
% computed in the distance function fits into memory
for i=1:numbatches

% Building batches of test samples of batchsize
Ybatch=Y((i-1)*batchsize+1:min(i*batchsize,M),:);

% D is a distance matrix where training samples are by rows 
% and test sample by columns
D = L2dist(X,Ybatch);

% Sorting descend per column from closest to farthest
[D,idx] = sort(D,'ascend');

% indexes in the training set of k nearest neighbors of each test sample
idx = idx(1,1);

% Classification of the test samples in the majority class among the nearest neighbor
classification = xl(idx,:);

end

% true if missclassified, false otherwise
err = yl!=classification;

end
