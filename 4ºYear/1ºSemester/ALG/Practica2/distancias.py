# Alumnos: Sergi Albiach Caro, Manel Angresola Navarro, Antonio Martínez Leal y Stéphane Díaz-Alejo León
# Distancias implementadas: Levenshtein, Damerau-Levenshtein restringida y Damerau-Levenshtein intermedia.

import numpy as np

################################################################
## LEVENSHTEIN PD hacia atrás versión eficiente espacialmente ##
################################################################
def dist_levenshtein(str1, str2):
    n = len(str1)
    m = len(str2)
    
    # Creamos un vector inicializado a [0 .. m+1]
    dl1 = np.fromiter((x for x in range(m + 1)), dtype=int)
    # Reservamos un vector para el cómputo de las siguientes columnas
    dl2 = np.zeros(m + 1, dtype=int)
    
    for i in range(1, n + 1):
        dl2[0] = i
        for j in range(1, m + 1):
            if str1[i-1] == str2[j-1]:
                dl2[j] = dl1[j-1]
            else:
                dl2[j] = 1 + min(dl2[j-1],   # Insertar
                                dl1[j],      # Eliminar
                                dl1[j-1])    # Reemplazar
        dl1 = np.copy(dl2)
    
    
    return dl1[-1]

####################################
## DAMERAU-LEVENSHTEIN RESTRINGIDA##
####################################
def dist_restringida(str1, str2):
    n = len(str1)
    m = len(str2)
    
    # Creamos un vector inicializado a [0 .. m+1]
    dl1 = np.fromiter((x for x in range(m + 1)), dtype=int)
    # Reservamos un vector para el cómputo de las siguientes columnas
    dl2 = np.zeros(m + 1, dtype=int)
    dl0 = np.zeros(m + 1, dtype=int)
    
    for i in range(1, n + 1):
        dl2[0] = i
        for j in range(1, m + 1):
            if str1[i-1] == str2[j-1]:
                dl2[j] = dl1[j-1]
            elif i > 1 and j > 1 and str1[i-2] == str2[j-1] and str1[i-1] == str2[j-2]:
                dl2[j] = 1 + min(dl2[j-1],   # Insertar
                                dl1[j],      # Eliminar
                                dl1[j-1],    # Reemplazar
                                dl0[j-2])    # Intercambiar
            else:
                dl2[j] = 1 + min(dl2[j-1],   # Insertar
                                dl1[j],      # Eliminar
                                dl1[j-1])    # Reemplazar
        
        dl0 = np.copy(dl1)
        dl1 = np.copy(dl2)
    
    return dl1[-1]

###################################
## DAMERAU-LEVENSHTEIN INTERMEDIA##
###################################
def dist_intermedia(str1, str2):
    cte = 1 #Implementacion pensada para cte = 1
    n = len(str1)
    m = len(str2)
    
    # Creamos un vector inicializado a [0 .. m+1]
    dl1 = np.fromiter((x for x in range(m + 1)), dtype=int)
    # Reservamos un vector para el cómputo de las siguientes columnas
    dl2 = np.zeros(m + 1, dtype=int)
    dl0 = np.zeros(m + 1, dtype=int)
    dl4 = np.zeros(m + 1, dtype=int)
    
    for i in range(1, n + 1):
        dl2[0] = i
        for j in range(1, m + 1):
            if str1[i-1] == str2[j-1]:
                dl2[j] = dl1[j-1]
            elif i > 1 and j > 1 and str1[i-2] == str2[j-1] and str1[i-1] == str2[j-2]:
                #print("ab-ba", i, j)
                dl2[j] = 1 + min(dl2[j-1],   # Insertar
                                dl1[j],      # Eliminar
                                dl1[j-1],    # Reemplazar
                                dl0[j-2])    # Intercambiar 

            elif (i > 1 and j > 1+cte) and (str1[i-2] == str2[j-1] and str1[i-1] == str2[j-2-cte]): #ab - bca
                #print("ab-bca", i, j)
                dl2[j] = 1 + min(dl2[j-1],   # Insertar
                                dl1[j],      # Eliminar
                                dl1[j-1],    # Reemplazar
                                dl0[j-3]+1)  # Intercambiar 

            elif (i > 1+cte and j > 1) and (str1[i-2-cte] == str2[j-1] and str1[i-1] == str2[j-2]): #bca - ab
                #print("bca-ab", i, j)
                dl2[j] = 1 + min(dl2[j-1],   # Insertar
                                dl1[j],      # Eliminar
                                dl1[j-1],    # Reemplazar
                                dl4[j-2]+1)  # Intercambiar 

            else:
                #print("else", i, j)
                dl2[j] = 1 + min(dl2[j-1],   # Insertar
                                dl1[j],      # Eliminar
                                dl1[j-1])    # Reemplazar

        dl4 = np.copy(dl0)
        dl0 = np.copy(dl1)
        dl1 = np.copy(dl2)

    return dl1[-1]

#########################
## LEVENSHTEIN General ##
#########################
def dist_levenshtein_general(str1, str2, cte):
    n = len(str1)
    m = len(str2)
    valCte = cte
    
    # Creamos una matriz donde guardar resultados
    dp = np.zeros((n+1, m+1), dtype=int) 
    # Inicializamos la primera fila
    dp[0] = np.fromiter((x for x in range(m + 1)), dtype=int)
    
    for i in range(1, n + 1):
        dp[i][0] = i #Inicializamos la primera columna
        for j in range(1, m + 1):
            if str1[i-1] == str2[j-1]:
                dp[i][j] = dp[i-1][j-1]
            else:
                c = 0
                buscando = True
                while(c <= valCte and buscando):
                    u = 0
                    while (u <= c and buscando):
                        v = 0
                        while (v <= c-u and buscando):
                            if(i > 1 and j > 1 and str1[i-2-u] == str2[j-1] and str1[i-1] == str2[j-2-v]):
                                #print("if" i, j)
                                dp[i][j] = 1 + min(dp[1][j-1],              # Insertar
                                                    dp[i-1][j],             # Eliminar
                                                    dp[i-1][j-1],           # Reemplazar
                                                    dp[i-2-u][j-2-v]+c)     # Intercambiar
                                buscando = False
                            v = v + 1
                        u = u + 1
                    c = c + 1
                if (buscando):
                    #print("else", i, j)
                    dp[i][j] = 1 + min(dp[i][j-1],   # Insertar
                                    dp[i-1][j],      # Eliminar
                                    dp[i-1][j-1])    # Reemplazar
    return dp[-1][-1]

if __name__ == '__main__':
    str1 = "algoritmo"
    str2 = "algortxximo"
    cte = 2
    print(str1, str2)
    print("Distancia Intermedia: ",dist_intermedia(str1,str2))
    print("Distancia General: ",dist_levenshtein_general(str1,str2,cte))
    

   