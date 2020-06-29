function[etr,edv]=bernoulli(X,xltr,Y,xldv,thresh,eps)
    %%Aquí el código necesario

    etr=[];
    edv=[];

    N=rows(X);

    for t=thresh

        [Xtr, Xdv]=binarization(X,Y,t);

        for e=eps
            pesos=[];
            pcs=[];
            priori=[];

            for i=1:10
                ind=find(xltr==(i-1));
                priori=[priori; log(N/rows(ind))];
                xc=Xtr(ind,:);
                pc=sum(xc) / rows(xc);
                ind=find(pc<e);
                pc(ind)=e;
                ind=find(pc>1-e);
                pc(ind)=1-e;
                pcs=[pcs pc'];
                wc=log(pc) - log(1 - pc);
                
                pesos=[pesos; wc];
            end

            w0=priori + sum(log(1 - pcs))';
            
            prob=pesos*Xtr' + w0;
            [value cstar]=max(prob);
            error=xltr!=(cstar'-1);
            etr=[etr; sum(error)/rows(error)];

            prob=pesos*Xdv' + w0;
            [value cstar]=max(prob);
            error=xldv!=(cstar'-1);
            edv=[edv; sum(error)/rows(error)];
            
        end
    end

endfunction

function[Xtr, Xdv]=binarization(X,Y,t)

    Xtr=X;
    Xdv=Y;

    maxX=max(max(X));
    minX=min(min(X));
    Xtr=(Xtr>(minX+(maxX-minX)*t));

    maxY=max(max(Y));
    minY=min(min(Y));
    Xdv=(Xdv>(minY+(maxY-minY)*t));

endfunction