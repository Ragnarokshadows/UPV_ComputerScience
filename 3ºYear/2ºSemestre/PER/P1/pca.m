function [m,W]=pca(X)
    m=mean(X);
    Xm=X-m;
    covarianza=(Xm'*Xm)/rows(X);
    [eigvec, eigval] = eig(covarianza);
    [S I] = sort(diag(eigval),"descend");
    W = eigvec(:,I);
end