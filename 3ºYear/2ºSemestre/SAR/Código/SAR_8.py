import numpy as np

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

    print(np.flip(tabla,0))