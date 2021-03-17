%
% Mixture of gaussians
%
% X  is a n x d training data matrix 
% xl is a n x 1 training label vector 
% Y is a m x d evaluation data matrix
% yl is a m x 1 evaluation label vector 
% K is the number of components per mixture
% alpha is the weight of the full variance matrix (1-alpha identity matrix)
function [errY] = mixgaussian(X,xl,Y,yl,K,alpha)

seed=23; rand('seed',seed);
classes=unique(xl);
C=rows(classes);
N=rows(X);
M=rows(Y);
D=columns(X);

% Estimation of class priors as histogram counts
pc=histc(xl,classes)/N;

% Initialization of mixture of gaussians based on parameter initialization
sigma=cell(C,K);
for c=classes'
  ic=find(c==classes);
  % Initialization of component priors p(k|c) as uniform distro
  pkGc{ic}(1:K)=1/K;
  % Initialization of K component means mu_kc as K random samples from class c
  idc=find(xl==c);
  Nc=rows(idc);
  mu{ic}=X(idc(randperm(Nc,K)),:)';
  % Initialization of K component covariance sigma_kc 
  % as K class covariance matrix divided by the number of components K
  sigma(ic,1:K)=alpha*cov(X(idc,:),1)/K+(1-alpha)*eye(D); 
end

% Convergence condition to stop EM
% While likelihood relative increase greater than epsilon, keep iterating
epsilon = 1e-4;
L=-inf;
it=0;
printf(" It          oL           L  errX  errY\n");
printf("--- ----------- ----------- ----- -----\n");
do 
  oL=L;L=0;

  % For each class	  
  for c=classes'
    % E step: Estimate znk
    ic=find(c==classes);
    idc=find(xl==c);
    Nc=rows(idc);
    Xc=X(idc,:);
    z=[];
    for k=1:K
      z(:,k)=compute_zk(ic,k,pkGc,mu,sigma,Xc);
    end
    % Robust computation of znk and log-likelihood
    maxz=max(z,[],2);
    z=exp(z-maxz);
    sumz=sum(z,2);
    z=z./sumz;
    L=L+Nc*log(pc(ic))+sum(maxz+log(sumz));

    % M step: parameter update ****************************************************************
    % HERE YOUR CODE FOR PARAMETER ESTIMATION *************************************************
    sumz = sum(z);
    pkGc{ic} = sumz/Nc; % Primer parámetro
    mu{ic} = (Xc'*z)./sumz; % Segundo parámetro
    for k=1:K
      aux = ((Xc-mu{ic}(:,k)')' * ((Xc-mu{ic}(:,k)').*z(:,k)))/sumz(k);
      sigma(ic,k) = alpha * aux + (1-alpha)*eye(D);
    end
  end

  % Likelihood divided by the number of training samples
  L=L/N;

  % Compute g for training and evaluation sets
  for c=classes'
    ic=find(c==classes);
    % Training set
    z=[];
    for k=1:K
      z(:,k)=compute_zk(ic,k,pkGc,mu,sigma,X);
    end
    % Robust computation of znk
    maxz=max(z,[],2);
    z=exp(z-maxz);
    sumz=sum(z,2);
    gX(:,ic)=log(pc(ic))+maxz+log(sumz);

    % Evaluation set
    z=[];
    for k=1:K
      z(:,k)=compute_zk(ic,k,pkGc,mu,sigma,Y);
    end
    % Robust computation of znk
    maxz=max(z,[],2);
    z=exp(z-maxz);
    sumz=sum(z,2);
    gY(:,ic)=log(pc(ic))+maxz+log(sumz);
  end

  % Classification of training and evaluation sets and error estimation
  [~,idX]=max(gX');
  errX=mean(classes(idX)!=xl)*100;
  [~,idY]=max(gY');
  errY=mean(classes(idY)!=yl)*100;
  it=it+1;
  printf("%3d %11.5f %11.5f %5.2f %5.2f\n",it,oL,L,errX,errY);
  
until ((L-oL)/abs(oL) < epsilon)
end

% Computes component-and-class conditional gaussian prob
function [zk] = compute_zk(ic,k,pkGc,mu,sigma,X)
  D=columns(X);
  cons=log(pkGc{ic}(k));
  cons=cons-0.5*D*log(2*pi);
  cons=cons-0.5*logdet(sigma{ic,k});
  cons=cons-0.5*mu{ic}(:,k)'*pinv(sigma{ic,k})*mu{ic}(:,k);
  lin=X*(mu{ic}(:,k)'*pinv(sigma{ic,k}))';
  qua=-0.5*sum((X*pinv(sigma{ic,k})).*X,2);
  zk=qua+lin+cons;
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
    v=log(eps);
  else
    v=sum(log(lambda));
  end
end
