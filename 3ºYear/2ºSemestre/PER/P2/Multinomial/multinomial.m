function[etr,edv]=multinomial(Xtr,xltr,Xdv,xldv,epsilons)
    %%Aquí el código necesario

    pesos_ori=[];
    pesos_ep=[];
    priori=[];

    etr=[];
    edv=[];

    N=rows(Xtr);
    for i=1:10
        ind=find(xltr==(i-1));
        priori=[priori; log(N/rows(ind))];
        xc=Xtr(ind,:);
        norm=sum(xc);
        norm=norm/sum(norm,2);
        pesos_ori=[pesos_ori; norm];
    end

    pesos_ep=pesos_ori;

    for e=epsilons
        for i=1:10
            epsi=pesos_ori(i,:) + e;
            pesos_ep(i,:)=log(epsi/sum(epsi));
        end

        prob=pesos_ep*Xtr'+priori;
        [value cstar]=max(prob);
        error=xltr!=(cstar'-1);
        etr=[etr; sum(error)/rows(error)];

        prob=pesos_ep*Xdv'+priori;
        [value cstar]=max(prob);
        error=xldv!=(cstar'-1);
        edv=[edv; sum(error)/rows(error)];

    end

endfunction