function xloh = onehot(xl)
    classes = unique(xl);
    C = length(classes);
    for i=1:C
        xloh(i,:) = (xl == classes(i));
    end
end