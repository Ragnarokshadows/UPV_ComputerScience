[InternetShortcut]
URL=
##################################################
##                                              ##
##            RECUPERADOR DE NOTICIAS           ##
##                   Version -1                 ##
##                                              ##
##              NO OPTIMIZADO !!                ##
## NO se puede utilizar PARA el proyecto de SAR ##
##                                              ##
##################################################

import pickle
import sys


def load_object(indexfile):
    with open(indexfile, 'rb') as fh:
        object = pickle.load(fh)
    return object


def solve_query(index, N, query):
    """
      FUNCION NO ACEPTABLE PARA EL PROYECTO DE SAR !!!
    """

    spt = query.split()
    i = 0
    if spt[i] == 'not':
        neg, i = True, i + 1
    else:
        neg = False
    term = spt[i]
    l1 = index[term]
    if neg:
        l1 = sorted(set(range(N)).difference(l1))
    i += 1
    while i < len(spt):
        conn = spt[i]
        neg, i = False, i + 1
        if spt[i] == 'not':
            neg, i = True, i + 1
        term = spt[i]
        l2 = index[term]
        if neg:
            l2 = sorted(set(range(N)).difference(l2))
        l1 = solve_conn(conn, l1, l2)
        i += 1
    return l1


def solve_conn(conn, l1, l2):
    """
      FUNCION NO ACEPTABLE PARA EL PROYECTO DE SAR !!!
    """
    r = set(l1)
    if conn == 'and':
        # AND sin negaciones
        r = r.intersection(l2)
    elif conn == 'or':
        # OR sin negaciones
        r = r.union(l1, l2)
    return sorted(r)


def solve_query_list(index, N, fname):
    with open(fname) as fh:
        ql = fh.read().split('\n')
    for query in ql[:-1]:
        if len(query) > 0:
            r = solve_query(index, N, query.lower())
            print("%s\t%d" % (query, len(r)))


def syntax():
    print("python %s indexfile query_list" % sys.argv[0])
    print(sys.argv)
    sys.exit()


if __name__ == "__main__":
    if len(sys.argv) != 3:
        syntax()
    indexfile = sys.argv[1]
    ql_file = sys.argv[2]
    (index, nnews) = load_object(indexfile)
    solve_query_list(index, nnews, ql_file)
