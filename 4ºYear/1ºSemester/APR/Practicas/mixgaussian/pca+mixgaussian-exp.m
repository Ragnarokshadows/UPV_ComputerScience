#!/usr/bin/octave -qf

if (nargin!=6)
printf("Usage: gaussian-exp.m <trdata> <trlabels> <ks> <%%trper> <%%dvper>\n")
exit(1);
end;

arg_list=argv();
trdata=arg_list{1};
trlabs=arg_list{2};
ks=str2num(arg_list{3});
pca_ks=str2num(arg_list{4});
trper=str2num(arg_list{5});
dvper=str2num(arg_list{6});

load(trdata);
load(trlabs);

N=rows(X);
seed=23; rand("seed",seed); permutation=randperm(N);
X=X(permutation,:); xl=xl(permutation,:);

Ntr=round(trper/100*N);
Ndv=round(dvper/100*N);
Xtr=X(1:Ntr,:); xltr=xl(1:Ntr);
Xdv=X(N-Ndv+1:N,:); xldv=xl(N-Ndv+1:N);

printf("\n------- --- ------\n");

[m W] = pca(Xtr);

Xtr = Xtr - m;
Xdv = Xdv - m;

edv = [];
n_k = length(ks) 
for k=1:length(pca_ks)
    pcaXtr=Xtr*W(:,1:pca_ks(k));
    pcaXdv=Xdv*W(:,1:pca_ks(k));
    for j=1:length(ks)
        edv = [edv mixgaussian(pcaXtr,xltr,pcaXdv,xldv,ks(j),1e-5)];
    end
end

for k=1:length(pca_ks)
    for j=1:length(ks)
        printf("%3d %3d %6.3f\n",pca_ks(k),ks(j),edv((k-1)*n_k + j));
    end
end

