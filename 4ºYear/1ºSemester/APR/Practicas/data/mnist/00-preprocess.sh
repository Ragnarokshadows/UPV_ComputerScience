#!/bin/bash

# Corpus web: http://yann.lecun.com/exdb/mnist/

wget http://yann.lecun.com/exdb/mnist/train-images-idx3-ubyte.gz
wget http://yann.lecun.com/exdb/mnist/train-labels-idx1-ubyte.gz
wget http://yann.lecun.com/exdb/mnist/t10k-images-idx3-ubyte.gz
wget http://yann.lecun.com/exdb/mnist/t10k-labels-idx1-ubyte.gz

gunzip train-images-idx3-ubyte.gz
gunzip train-labels-idx1-ubyte.gz
gunzip t10k-images-idx3-ubyte.gz
gunzip t10k-labels-idx1-ubyte.gz

echo -e "# name: X\n# type: matrix\n# rows: 60000\n# columns: 784" > train-images-idx3-ubyte.mat
od -v -An -j16 -w784 -t u1 train-images-idx3-ubyte >> train-images-idx3-ubyte.mat
gzip train-images-idx3-ubyte.mat
gzip train-images-idx3-ubyte

echo -e "# name: xl\n# type: matrix\n# rows: 60000\n# columns: 1" > train-labels-idx1-ubyte.mat
od -v -An -j8  -w1   -t u1 train-labels-idx1-ubyte >> train-labels-idx1-ubyte.mat
gzip train-labels-idx1-ubyte.mat
gzip train-labels-idx1-ubyte

echo -e "# name: Y\n# type: matrix\n# rows: 10000\n# columns: 784" > t10k-images-idx3-ubyte.mat
od -v -An -j16 -w784 -t u1 t10k-images-idx3-ubyte >> t10k-images-idx3-ubyte.mat
gzip t10k-images-idx3-ubyte.mat
gzip t10k-images-idx3-ubyte

echo -e "# name: yl\n# type: matrix\n# rows: 10000\n# columns: 1" > t10k-labels-idx1-ubyte.mat
od -v -An -j8  -w1   -t u1 t10k-labels-idx1-ubyte >> t10k-labels-idx1-ubyte.mat
gzip t10k-labels-idx1-ubyte.mat
gzip t10k-labels-idx1-ubyte
