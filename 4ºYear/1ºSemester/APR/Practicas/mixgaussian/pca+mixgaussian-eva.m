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
load(tedata);
load(telabs);

Xtr=X;
xltr=xl;
Xdv=Y;
xldv=yl;


[m W] = pca(Xtr);

Xtr = Xtr - m;
Xdv = Xdv - m;

M = rows(Xdv);

edv = [];
n_k = length(ks) 
for k=1:length(pca_ks)
    pcaXtr=Xtr*W(:,1:pca_ks(k));
    pcaXdv=Xdv*W(:,1:pca_ks(k));
    for j=1:length(ks)
        edv = [edv mixgaussian(pcaXtr,xltr,pcaXdv,xldv,ks(j),1e-4)];
    end
end

for k=1:length(pca_ks)
    for j=1:length(ks)
        m=(edv((k-1)*n_k + j))/ 100;
        s=sqrt(m*(1-m)/M);
        r=1.96*s;
        printf("%3d %3d %6.3f [%.3f,%.3f]\n",pca_ks(k),ks(j),m*100,(m-r)*100,(m+r)*100);
    end
end

