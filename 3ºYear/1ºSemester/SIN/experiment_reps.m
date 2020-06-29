#!/usr/bin/octave -qf
if (nargin!=4)
printf("Usage: ./experiment_reps.m <data> <alphas> <bes> <reps>\n");
exit(1);
end
arg_list=argv();
data=arg_list{1};
as=str2num(arg_list{2});
bs=str2num(arg_list{3});
reps=str2num(arg_list{4});
load("OCR_14x14"); [N,L]=size(data); D=L-1;

ll=unique(data(:,L)); C=numel(ll);
rand("seed",23); data = data(randperm(N),:);

NTr = round(.7*N);
tr=data(1:NTr,:); #Training set
M = N-NTr; 
te=data(NTr+1:N,:); #Testing set

printf("#      a        b reps   E   k Ete Ete (%%)    Ite (%%)\n");
printf("#------- -------- ---- --- --- --- ------- ----------\n");

for a=as
  for b=bs
    for rep=reps
        [w,E,k]=perceptron(tr,b,a,rep); rl=zeros(M,1);
        for n=1:M rl(n)=ll(linmach(w,[1 te(n,1:D)]'));end
        [nerr m]=confus(te(:,L),rl);
        m=nerr/M;
        s=sqrt(m*(1-m)/M);
        r=1.96*s;
        printf("%8.1f %8.1f %4d %3d %3d %3d %7.1f [%.1f, %.1f]\n",a,b,rep,E,k,nerr,m*100,(m-r)*100,(m+r)*100);
    end
  end
end