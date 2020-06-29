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
    fila1 = tabla[1,:]

    pos = np.where(fila==min(fila))
    pos = pos[0]
    llista = list(pos)

    for i in range(0,len(llista)):
        init = llista[i]-len(P)
        if init < 0 : init = 0
        
        part = fila1[init:llista[i]]
        pos2 = np.where(part==min(part))
        llista2 = list(pos2[0])


        for j in llista2:

            print(T[j+init-1:llista[i]])

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
   