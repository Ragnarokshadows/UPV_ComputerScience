[InternetShortcut]
URL=
##################################################
##                                              ##
##            INDEXADOR DE NOTICIAS             ##
##                   Version -1                 ##
##                                              ##
##              NO OPTIMIZADO !!                ##
## NO se puede utilizar PARA el proyecto de SAR ##
##                                              ##
##################################################

import json
import os
import pickle
import re
import sys
import time


def tokenize(text):
    """
      FUNCION NO ACEPTABLE PARA EL PROYECTO DE SAR !!!
    """
    return re.sub("\W+", ' ', text.lower()).split()


def index_dir(root):
    """
      FUNCION NO ACEPTABLE PARA EL PROYECTO DE SAR !!!
    """
    index, next_id = {}, 0
    for dir, subdirs, files in os.walk(root):
        for filename in files:
            if filename.endswith('.json'):
                fullname = os.path.join(dir, filename)
                next_id = index_file(fullname, index, next_id)
    return index, next_id


def index_file(filename, index, newid):
    """
      FUNCION NO ACEPTABLE PARA EL PROYECTO DE SAR !!!
    """
    print(filename)
    with open(filename) as fh:
        jlist = json.load(fh)
    for i, j in enumerate(jlist):
        txt = tokenize(j["article"])
        index_list(txt, index, newid)
        newid += 1
    return newid


def index_list(txt, index, nid):
    for token in txt:
        if token not in index:
            index[token] = [nid]
        elif index[token][-1] != nid:
            index[token].append(nid)


def save_object(object, filename):
    with open(filename, 'wb') as fh:
        pickle.dump(object, fh)


if __name__ == "__main__":
    newsdir = sys.argv[1]
    indexfile = sys.argv[2]

    t0 = time.time()
    index, nnews = index_dir(newsdir)
    t1 = time.time()
    save_object((index, nnews), indexfile)
    t2 = time.time()
    print()
    print("=" * 40)
    print("Number of indexed news:", nnews)
    print("-" * 40)
    print("Number of tokens :", len(index))
    print("-" * 40)
    print("Time indexing: %2.2fs." % (t1 - t0))
    print("Time saving: %2.2fs." % (t2 - t1))
    print("=" * 40)
    print()
