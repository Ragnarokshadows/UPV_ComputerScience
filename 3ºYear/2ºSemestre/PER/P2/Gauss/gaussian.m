function[etr,edv]=gaussian(Xtr,xltr,Xdv,xldv,alphas)

    mu=[];
    priori=[];
    
    sigma={};

    etr=[];
    edv=[];


    N=rows(Xtr);
    for i=1:10
        ind=find(xltr==(i-1));
        priori=[priori; log(rows(ind)/N)];
        xc=Xtr(ind,:);
        m = mean(xc);
        mu = [mu m'];
        Xm=xc-m;
        c=(Xm'*Xm)/rows(xc);
        sigma{i} = c;
    end


    for a=alphas
        probTr=[];
        probDv=[];
        
        for i=1:10
            c = sigma{i};
            c = a*c + (1 - a)*eye(size(c));
            probTr = [probTr; gc(priori(i,:),mu(:,i),c,Xtr)];
            probDv = [probDv; gc(priori(i,:),mu(:,i),c,Xdv)];
            
        end

        
        [value cstar]=max(probTr);
        error=xltr!=(cstar'-1);
        etr=[etr; sum(error)/rows(error)];

        [value cstar]=max(probDv);
        error=xldv!=(cstar'-1);
        edv=[edv; sum(error)/rows(error)];

    end

endfunction

function[ld]=logdet(X)

    eigval = eig(X);

    if(any(eigval<=0))
        ld = log(realmin);
    else
        ld=sum(log(eigval));
    endif

endfunction

function[prob]=gc(priori, m, c, X)
    
    inv = pinv(c);

    Wc = (-1/2)*inv;
    wc = inv*m;
    w0 = priori - (1/2)*logdet(c) - (1/2)*m'*inv*m;
    

    prob=(sum((X*Wc).*X,2))' + (wc'*X') + w0;

endfunction