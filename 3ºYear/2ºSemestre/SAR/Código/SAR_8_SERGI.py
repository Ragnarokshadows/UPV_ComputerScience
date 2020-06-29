import numpy as np
import sys

def boyer_moore(T: str, P: str) -> int:
    # cálculo de d
    d = dict((c, len(P) - P.rfind(c) - 1) for c in set(P))
    # búsqueda de P en T
    j = len(P) - 1
    print(T)
    print(P)
    while j < len(T):
        aux = P
        i= len(P) - 1
        while i >= 0 and P[i] == T[j]:
            i, j = i - 1, j - 1
        if i == -1:
            print("Solución encontrada")
            return j + 1 # encontrado
        elif len(P) - i > d.get(T[j], len(P)):
            j = j + len(P) - i
        else:
            j = j + d.get(T[j], len(P))

        for k in range((j+1)-len(P)):
            aux = "-" + aux

        print(aux)

    

    print("No hay solución")
    return -1 # no encontrado

def sust(a: str, b: str) -> int:
    return 1 if a != b else 0

def aux(posicions,paraula,paraules,tabla,T,definit=[],prim=True):
    if not posicions: return definit

    (xi,xj) = posicions.pop()
    esq = tabla[xi,xj-1]
    diag = tabla[xi-1,xj-1]
    baix = tabla[xi-1,xj]



    if xi > 0 and xj > 0 and esq <= diag and esq <= baix:
        posicions.append((xi,xj-1))
        par = aux(posicions,paraula,paraules,tabla,T,definit,False)
        if par : paraules.append(par)

    if xi > 0 and xj > 0 and baix <= esq and baix <= diag:
        posicions.append((xi-1, xj))
        par = aux(posicions,paraula,paraules,tabla,T,definit,False)
        if par: paraules.append(par)

    if xi > 0 and xj > 0 and diag <= esq and diag <= baix: 
        paraula = T[xj-1]+paraula
        if xi == 1: definit.append(paraula)

        if (xi > 1):
            posicions.append((xi-1, xj-1))
            par = aux(posicions,paraula,paraules,tabla,T,definit,False)
            if par: definit.append(par)
    
    res = []
    if prim : res = definit
    return res

def busqueda_PD(T: str, P: str):
    tabla = np.zeros((len(P)+1, len(T)+1))
    for j in range(len(P) + 1):
        tabla[j, 0] = j
    for i in range(len(T)):
        tabla[0, i + 1] = 0 # cualquier posición de inicio
        for j in range(len(P)):   
            tabla[j + 1, i + 1] = min(tabla[j, i + 1] + 1, tabla[j + 1, i] + 1, tabla[j, i] + sust(T[i], P[j]))

    taula = np.flip(tabla,0)
    print(taula)

    fila = taula[0,:]

    pos = np.where(fila==min(fila))
    pos = pos[0]
    llista = list(pos)
    paraules = []
    for i in range(0,len(llista)):

        xj = llista[i]
        xi = len(P)

        posicions = [(xi,xj)]
        paraula = ""
        paraules=[]
        paraules = aux(posicions,paraula,paraules,tabla,T,[],True)

        for paraula in paraules:
            print(paraula)

if __name__ == "__main__":
    args = sys.argv[1:]

    if len(args)==0:
        print("'python ./SAR_8.py -bm <text> <patro>' per a executar Boyer-Moore \n'python ./SAR_8.py -bu <text> <patro>' per a executar búsqueda amb errors")

    elif args[0] == "-bm":
        boyer_moore(args[1], args[2])
    elif args[0] == "-bu":
        busqueda_PD(args[1], args[2])
    else:
        print("'python ./SAR_8.py -bm <text> <patro>' per a executar Boyer-Moore \n'python ./SAR_8.py -bu <text> <patro>' per a executar búsqueda amb errors")


