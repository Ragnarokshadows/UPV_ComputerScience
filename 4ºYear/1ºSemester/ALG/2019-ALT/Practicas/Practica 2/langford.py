[InternetShortcut]
URL=# -*- coding: utf-8 -*-
import sys

def langford_directo(N, allsolutions):
    N2   = 2*N
    seq  = [0]*N2
    
    def backtracking(num):
        if num<=0:
            yield "-".join(map(str, seq))
        else:
	    # COMPLETAR

    if N%4 not in (0,3):
        yield "no hay solucion"
    else:
        count = 0
        for s in backtracking(N):
            count += 1
            yield "solution %04d -> %s" % (count, s)
            if not allsolutions:
                break

# http://www.cs.mcgill.ca/~aassaf9/python/algorithm_x.html
def select(X, Y, r):
    cols = []
    for j in Y[r]:
        for i in X[j]:
            for k in Y[i]:
                if k != j:
                    X[k].remove(i)
        cols.append(X.pop(j))
    return cols

def deselect(X, Y, r, cols):
    for j in reversed(Y[r]):
        X[j] = cols.pop()
        for i in X[j]:
            for k in Y[i]:
                if k != j:
                    X[k].add(i)

def solve(X, Y, solution=[]):
    if not X:
        yield list(solution)
    else:
        c = min(X, key=lambda c: len(X[c]))
        for r in list(X[c]):
            solution.append(r)
            cols = select(X, Y, r)
            for s in solve(X, Y, solution):
                yield s
            deselect(X, Y, r, cols)
            solution.pop()

def langford_data_structure(N):
    # n1,n2,... means that the value has been used
    # p1,p2,... means that the position has been used
    def value(i):
        return sys.intern('n%d' % (i,))
    def position(i):
        return sys.intern('p%d' % (i,))

    # COMPLETAR

    X = {j: set() for j in X}
    for i in Y:
        for j in Y[i]:
            X[j].add(i)

    return X, Y

def langford_exact_cover(N, allsolutions):
    if N%4 not in (0, 3):
        yield "no hay solucion"
    else:
        X,Y = langford_data_structure(N)
        sol = [None]*2*N
        count = 0
        for coversol in solve(X, Y):
            for item in coversol:
                n,p= map(int,item[1:].split('p'))
                sol[p]=n
                sol[p+n+1]=n
            count += 1
            yield "solution %04d -> %s" % (count,"-".join(map(str, sol)))
            if not allsolutions:
                break

if __name__ == "__main__":
    if len(sys.argv) not in (2, 3, 4):
        print('\nUsage: %s N [TODAS] [EXACT_COVER] \n' % (sys.argv[0],))
        sys.exit()
    try:
        N = int(sys.argv[1])
    except ValueError:
        print('First argument must be an integer')
        sys.exit()
    allSolutions = False
    langford_function = langford_directo
    for param in sys.argv[2:]:
        if param == 'TODAS':
            allSolutions = True
        elif param == 'EXACT_COVER':
            langford_function = langford_exact_cover
    for sol in langford_function(N, allSolutions):
        print(sol)

