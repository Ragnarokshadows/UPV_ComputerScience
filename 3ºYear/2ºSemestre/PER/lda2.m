function [W]=lda(X, xl)
    D = rows(X); # dimensiones
    n = columns(X); # nÃºmero de muestras
    printf("Media de los datos \n")
    m = sum(X')' / columns(X) # valor medio para cada dimensiÃ³n

    # CÃ¡lculo de Sb y Sw
    Sb = zeros(D, D);
    Sw = zeros(D, D);
    for c=unique(xl)
        indc = find(xl == c);
        nc = columns(indc);
        printf("Media para la clase ")
        c
        printf("\n")
        xc = sum(X(:,indc)')' / nc
        Sw += (X(:,indc) - xc) * (X(:,indc) - xc)' / nc;
        Sb += nc * (xc - m) * (xc - m)';
    endfor

    # Encontrar eigenvalues Sb y Sw y devolver los primeros k eigenvectors
    # ordenados por eigenvalue ascendente
    [V, lambda] = eig(Sb, Sw);
    [eigval, order] = sort(-diag(lambda)');
    W = V(:,order)(:,1:2);
    %W = V(:,order)(:,:);
    W=W./sqrt(sum(W.*W));
    printf("Sw es: \n")
    Sw
    printf("Sb es: \n")
    Sb
    printf("Vectores propios \n")
    V
    printf("Vectores ordenados por valores propios\n")
    order
    printf("W ordenada es: \n")
    W
    %W = [0.6 -0.1; 0.4 -0.4; 0.4 -0.2; -0.2 0.3; 0.5 0.9];
    printf("Y la proyección sería: \n")
    Xr = W'*(X);
    Xr'
    
endfunction
