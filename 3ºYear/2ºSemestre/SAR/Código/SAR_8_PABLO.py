import numpy as np
import sys

def boyer_moore(T: str, P: str) -> int:
    # cálculo de d
    d = dict((c, len(P) - P.rfind(c) - 1) for c in set(P))
    # búsqueda de P en T
    j = len(P) - 1
    for key in d.keys():
        print("{}    ".format(key), end='')
    print("")
    for key in d.keys():
        val = d[key]
        spaces = 6 - len(str(val))
        print(str(val), end='')
        for i in range(1, spaces):
            print(" ", end='')
    print("")
    print("----------------------")
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

def busqpar_rec(taula, fila, pos):
    if (fila == len(taula) - 2):
        return [pos]
    bottom = taula[fila+1,pos]
    bottomleft = taula[fila+1,pos-1] if (pos > 0) else float("inf")
    if (bottom < bottomleft):
        return busqpar_rec(taula, fila+1, pos)
    elif (bottom > bottomleft):
        return busqpar_rec(taula, fila+1, pos - 1)
    else:
        return busqpar_rec(taula, fila+1, pos) + busqpar_rec(taula, fila+1, pos - 1)

def busqueda_PD(T: str, P: str, V: int):
    tabla = np.zeros((len(P)+1, len(T)+1))
    for j in range(len(P) + 1):
        tabla[j, 0] = j
    for i in range(len(T)):
        tabla[0, i + 1] = 0 # cualquier posición de inicio
        for j in range(len(P)):   
            tabla[j + 1, i + 1] = min(tabla[j, i + 1] + 1, tabla[j + 1, i] + 1, tabla[j, i] + sust(T[i], P[j]))

    taula = np.flip(tabla,0)
    print(taula)
    print("----------------------")
    casos = np.where(taula[0]<=V)
    for p in casos[0]:
        init = busqpar_rec(taula, 0, p)
        init = list(dict.fromkeys(init))
        for s in init:
            print("{} [{}, {}]".format(T[s-1:p], s, p))
    

if __name__ == "__main__":
    args = sys.argv[1:]

    if len(args)==0:
        print("'python ./SAR_8.py -bm <text> <patro>' per a executar Boyer-Moore \n'python ./SAR_8.py -bu <text> <patro>' per a executar búsqueda amb errors")

    elif args[0] == "-bm":
        boyer_moore(args[1], args[2])
    elif args[0] == "-bu":
        busqueda_PD(args[1], args[2], int(args[3]))
    else:
        print("'python ./SAR_8.py -bm <text> <patro>' per a executar Boyer-Moore \n'python ./SAR_8.py -bu <text> <patro> <min>' per a executar búsqueda amb errors")
   