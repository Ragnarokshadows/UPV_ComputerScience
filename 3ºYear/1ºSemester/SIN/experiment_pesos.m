#!/usr/bin/octave -qf
if (nargin!=4)
printf("Usage: ./experiment_pesos.m <data> <alphas> <bes> <reps>\n");
exit(1);
end
arg_list=argv();
data=arg_list{1};
as=str2num(arg_list{2});
bs=str2num(arg_list{3});
ks=str2num(arg_list{4});
load("OCR_14x14"); [N,L]=size(data); D=L-1;

ll=unique(data(:,L)); C=numel(ll);
rand("seed",23); data = data(randperm(N),:);

NTr = round(.7*N);
tr=data(1:NTr,:); #Training set
M = N-NTr; 
te=data(NTr+1:N,:); #Testing set

printf("#------- -------- --- --- --- ------- ----------\n");

[w,E,k]=perceptron(tr,bs,as,ks);
save_precision(4); save("percep_w","w");

printf("#------- -------- --- --- --- ------- ----------\n");