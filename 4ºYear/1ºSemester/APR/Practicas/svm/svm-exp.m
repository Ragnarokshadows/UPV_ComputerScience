#!/usr/bin/octave -qf

if (nargin!=4)
printf("Usage: svm-exp.m <trdata> <trlabels> <%%trper> <%%dvper>\n")
exit(1);
end;

addpath("svm_apr");

arg_list=argv();
trdata=arg_list{1};
trlabs=arg_list{2};
trper=str2num(arg_list{3});
dvper=str2num(arg_list{4});

load(trdata);
load(trlabs);

cs = [1 10 100 1000 10000]; 
ts = [0 1 2 3];
ds = [1 2 3 4 5];

N=rows(X);
seed=23; rand("seed",seed); permutation=randperm(N);
X=X(permutation,:); xl=xl(permutation,:);

Ntr=round(trper/100*N);
Ndv=round(dvper/100*N);
Xtr=X(1:Ntr,:); xltr=xl(1:Ntr);
Xdv=X(N-Ndv+1:N,:); xldv=xl(N-Ndv+1:N);

printf("\n------- --- ------\n");

N=rows(Xdv);


for k=1:length(cs)
    for j=1:length(ts)
        if ts(j) == 1
            for i=1:length(ds)
                printf("Entrenando \n");
                res = svmtrain(xltr, Xtr, ["-q -t ", num2str(ts(j)), " -c ", num2str(cs(k)), " -d ", num2str(ds(i))]);
                printf("Prediciendo \n");
                [pred, accuracy, d] = svmpredict(xldv, Xdv, res, '-q');
                p = accuracy(1) / 100;
	            intervalo = 1.96* sqrt((p * (1-p))/N);
                printf("%d \t %d \t %d \t %3f \t %3f   \n",ts(j),cs(k),ds(i),p,intervalo);
            end
        else
            printf("Entrenando \n");
            res = svmtrain(xltr, Xtr, ["-q -t ", num2str(ts(j)), " -c ", num2str(cs(k))]);
            printf("Prediciendo \n");
            [pred, accuracy, d] = svmpredict(xldv, Xdv, res, '-q');
            p = accuracy(1) / 100;
	        intervalo = 1.96* sqrt((p * (1-p))/N);
            printf("%d \t %d \t %3f \t %3f   \n",ts(j),cs(k),p,intervalo);
        endif
        printf("Cambiando kernel \n");
    end
end