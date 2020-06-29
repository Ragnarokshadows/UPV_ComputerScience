% This function computes LDA on X providing C-1 
% projection vectors in W.
% function [W]=lda(X,xl)
function [W]=lda(X,xl)

% X is nxd (n samples and d dimensions)
[n d]=size(X);

% Number of classes
C=rows(unique(xl));
% Class vector
classes=unique(xl);

%
% Computing Sw
%
Sw=zeros(d);
for c=1:C

  % Select samples from class c
  Xc=X(find(xl==classes(c)),:);
  N(c)=rows(Xc);

  % Compute mean for class c
  mu(c,:)=mean(Xc);

  % Compute covariance matrix for class 
  Xmu=Xc-mu(c,:);
  Sw=Sw+(Xmu'*Xmu)/N(c);

end

Sw

%
% Computing Sb
%

% Compute global mean
MU=mean(X);

Sb=zeros(d);
for c=1:C
  Sb=Sb+N(c)*(mu(c,:)-MU)'*(mu(c,:)-MU);
end

Sb

% Computing generalized eigenvectors and eigenvalues
[vec val]=eig(Sb,Sw);
% Sorting eigenvectors according to eigenvalues
[val perm]=sort(-diag(val));
W=vec(:,perm(1:C-1));

% Vector normalization
W=W./sqrt(sum(W.*W));

end
