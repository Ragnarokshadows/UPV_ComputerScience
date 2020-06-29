function [alpha]=perceptron(alpha,K,lab)

do
    printf("ITERACION\n\n");
    m = 0;
    n = rows(alpha);

    for i=1:n
        
        if(lab(i,:)*g(i,n,alpha,lab,K) <= 0)

            printf("ERROR\n");
            alpha(i,:) = alpha(i,:) + 1

        else

            printf("CORRECTO\n")
            m = m + 1;

        endif

    end

until(m>=n)

end

function [res]=g(x,n,alpha,lab,K)

res = 0;

    for i=1:n

        res = res + alpha(i,:) * lab(i,:) * K(i,x) + alpha(i,:) * lab(i,:);

    end

end
