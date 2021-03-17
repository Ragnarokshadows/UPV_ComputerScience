% Computes the error rate on the evaluation set Y of the 
% gaussian classifier trained on X
% X  is a n x d training data matrix 
% xl is a n x 1 training label vector 
% Y is a m x d evaluation data matrix
% yl is a m x 1 evaluation label vector 
% alphas are the smoothing weights of the full covariance matrix
function [errY] = gaussian(X,xl,Y,yl,alphas)

classes=unique(xl);
N=rows(X);
M=rows(Y);
D=columns(X);

% Parameter estimation
for c=classes'
  ic=find(c==classes);
  idx=find(xl==c);
  Xc=X(idx,:);
  Nc=rows(Xc);
  pc(ic)=Nc/N;
  muc=sum(Xc)/Nc;
  mu(:,ic)=muc';
  sigma{ic}=((Xc-muc)'*(Xc-muc))/Nc;
end

for i=1:length(alphas)

  % Smoothing with identity matrix
  for c=classes'
    ic=find(c==classes);
    ssigma{ic}=alphas(i)*sigma{ic}+(1-alphas(i))*eye(D);
  end

  % Compute g for each sample in the evaluation set
  for c=classes'
    ic=find(c==classes);
    gY(:,ic)=log(pc(ic))+compute_pxGc(mu(:,ic),ssigma{ic},Y);
  end

  [~,idY]=max(gY');
  errY(i)=mean(classes(idY)!=yl)*100;
end

end

% Computes component-and-class conditional gaussian prob
function [pxGc] = compute_pxGc(mu,sigma,X)
  qua=-0.5*sum((X*pinv(sigma)).*X,2);
  lin=X*(mu'*pinv(sigma))';
  cons=-0.5*logdet(sigma);
  cons=cons-0.5*mu'*pinv(sigma)*mu;
  pxGc=qua+lin+cons;
end

% Robust computation of the logarithm of the determinant of the covariance matrix X
% https://www.adelaide.edu.au/mathslearning/play/seminars/evalue-magic-tricks-handout.pdf
% The det of X is the product of its eigenvalues, 
% then the log of the det is the sum of the log of the eigenvalues
% If there are eigenvalues that are zero (or negative), 
% then the log of the det is the smallest value
function v = logdet(X)
  lambda = eig(X);
  if any(lambda<=0)
    v=log(realmin);
  else
    v=sum(log(lambda));
  end
end
