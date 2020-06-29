function [XR, xlR]=wilson(X,xl)
    error = true; XR=X; xlR=xl;
    while(error)
        error = false;
        N=rows(XR)
        i = 1;
        j = 1;
        while(i <= N && rows(XR)>1)
            test = XR;
            test(i,:) = [];
            test_l = xlR;
            test_l(i,:) = [];
            
            err = knn_wilson(test,test_l,XR(i,:),xlR(i,:),1);

            if (rem(j,1000) == 0)
                printf("Iteración = %d NºFilas = %d \n", j, N);
            end

            if (err == 1)
                XR(i,:) = [];
                xlR(i,:) = [];
                error = true;
                N = N - 1;
                i = i - 1;
            end
            i = i + 1;
            j = j + 1;
        endwhile
    endwhile
end