#!/usr/bin/octave -qf

if (nargin!=4)
printf("Usage: svm-eva.m <trdata> <trlabels> <tedata> <telabels>\n")
exit(1);
end;

addpath("svm_apr");

arg_list=argv();
trdata=arg_list{1};
trlabs=arg_list{2};
tedata=arg_list{3};
telabs=arg_list{4};

load(trdata);
load(trlabs);
load(tedata);
load(telabs);

cs = [1 10 100 1000 10000]; 
ts = [1];
ds = [2];

N = rows(Y);

printf("\n------- --- ------\n");


for k=1:length(cs)
    for j=1:length(ts)
        if ts(j) == 1
            for i=1:length(ds)
                printf("Entrenando \n");
                res = svmtrain(xl, X, ["-q -t ", num2str(ts(j)), " -c ", num2str(cs(k)), " -d ", num2str(ds(i))]);
                printf("Prediciendo \n");
                [pred, accuracy, d] = svmpredict(yl, Y, res, '-q');
                p = accuracy(1) / 100;
	            intervalo = 1.96* sqrt((p * (1-p))/N);
                printf("%d \t %d \t %d \t %6.3f \t [%.3f  %.3f] \n",ts(j),cs(k),ds(i),p * 100,(p-intervalo)*100,(p+intervalo)*100);
            end
        else
            printf("Entrenando \n");
            res = svmtrain(xl, X, ["-q -t ", num2str(ts(j)), " -c ", num2str(cs(k))]);
            printf("Prediciendo \n");
            [pred, accuracy, d] = svmpredict(yl, Y, res, '-q');
            p = accuracy(1) / 100;
	        intervalo = 1.96* sqrt((p * (1-p))/N);
            printf("%d \t %d \t %6.3f \t [%.3f  %.3f] \n",ts(j),cs(k),p * 100,(p-intervalo)*100,(p+intervalo)*100);
        endif
        printf("Cambiando kernel \n");
    end
end