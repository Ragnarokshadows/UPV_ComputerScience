% This function computes PCA on X providing the projection vectors
% in W and the mean vector mu for X.
% function [mu W]=pca(X)
function [m W]=pca(X)

% Compute mean
m=mean(X);

% Compute covariance matrix
%sigma=cov(X,1)
Xm=X-m;
sigma=Xm'*Xm/rows(X);


% Compute eigenvectors and eigenvalues
[vec val]=eig(sigma);
[val perm]=sort(-diag(val));
W=vec(:,perm);

end
