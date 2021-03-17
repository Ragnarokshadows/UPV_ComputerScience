#!/usr/bin/octave -qf

if (nargin!=7)
printf("Usage: pca+mlp-exp.m <trdata> <trlabels> <tsdata> <tslabels> <nHiddens> <%%trper> <%%dvper>\n")
exit(1);
end;

arg_list=argv();
trdata=arg_list{1};
trlabs=arg_list{2};
trdata=arg_list{3};
trlabs=arg_list{4};
nHiddens=str2num(arg_list{5});
trper=str2num(arg_list{6});
dvper=str2num(arg_list{7});

load(trdata);
load(trlabs);
load(tsdata);
load(tslabs);

addpath("nnet_apr");

trper = 40;
dvper = 10;
nHiddens = [40 60];

N=rows(X);
seed=23; rand("seed",seed); permutation=randperm(N);
X=X(permutation,:); xl=xl(permutation,:);

Ntr=round(trper/100*N);
Ndv=round(dvper/100*N);
Xtr=X(1:Ntr,:); xltr=xl(1:Ntr);
Xdv=X(N-Ndv+1:N,:); xldv=xl(N-Ndv+1:N);

printf("\npca nH dv-err");
printf("\n--- -- ------\n");

[m W]=pca(X);

Xtr=Xtr-m;
Xdv=Xdv-m;

Y=Y-m;

pcaKs=[20];
show=10;
epochs=300;

for k=1:length(pcaKs)
    pcaXtr= Xtr * W(:,1:pcaKs(k));
    pcaXdv= Xdv * W(:,1:pcaKs(k));
    pcaY= Y * W(:,1:pcaKs(k));
    for i=1:length(nHiddens)
        edv = mlp(pcaXtr,xltr,pcaXdv,xldv,pcaY,yl,nHiddens(i),epochs,show,seed);
        edv = edv * 100;
       pause(10);
        printf("%3d %3d %6.3f\n",pcaKs(k),nHiddens(i),edv);
    end
end