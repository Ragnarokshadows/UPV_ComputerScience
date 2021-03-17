function [errY] = mlp(Xtr,xltr,Xdv,xldv,Y,yl,nHidden,epochs,show,seed)
    Xtr=Xtr'; xltr=xltr'; Xdv=Xdv'; xldv=xldv'; Y=Y'; yl=yl';
    [Xtrnorm,Xtrmean,Xtrstd] = prestd(Xtr);
    XdvNN.P = trastd(Xdv,Xtrmean,Xtrstd);
    XdvNN.T = onehot(xldv);
    nOutput = length(unique(xltr));
    initNN = newff(minmax(Xtrnorm),[nHidden nOutput],{"tansig","logsig"},"trainlm","","mse");
    initNN.trainParam.show = show;
    initNN.trainParam.epochs = epochs;
    rand("seed",seed);
    NN = train(initNN,Xtrnorm,onehot(xltr),[],[],XdvNN);

    Ynorm = trastd(Y,Xtrmean,Xtrstd);
    Yout = sim(NN,Ynorm);
    [val ind] = max(Yout);
    label = ind - 1;
    %label = ind;
    errY = 1 - sum(label==yl)/length(yl);
end