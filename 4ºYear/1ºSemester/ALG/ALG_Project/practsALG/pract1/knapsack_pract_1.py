import numpy as np
import time
import random

def knapsack_backward(W, v, w):
    """
    Knapsack con PD hacia detrás.
    Devuelve:
            1) El máximo beneficio
            2) El número de estados de la matriz de PD.
    """
    V = np.zeros((len(v) + 1, W + 1))
    n_states = (len(v) + 1) * (W + 1)
    for i in range(1, len(v) + 1):
        for c in range(1, W + 1):
            if w[i-1] <= c:
                V[i, c] = max(V[i-1, c], V[i-1][c-w[i-1]] + v[i-1])
            else:
                V[i, c] = V[i-1, c]

    return V[-1, -1], n_states


def knapsack_forward(W, v, w):
    """
    Knapsack, PD hacia delante con lista de estados activos en forma de diccionario Python.
    Devuelve:
            1) El máximo beneficio
            2) El número de estados activos que ha explorado hasta acabar (TODO).
    """
    Vcurr = {0:0.}
    n_states = 1 # CAMBIAR Y COMPLETAR DONDE CORRESPONDA

    for vi, wi in zip(v, w):
        Vnext = {}
        for peso, benef in Vcurr.items():
            # Considerar no coger el objeto
            Vnext[peso] = max(benef, Vnext.get(peso, 0))

            # Considerar coger el objeto
            if peso + wi <= W:
                Vnext[peso+wi] = max(benef+vi, Vnext.get(peso+wi, 0))

        Vcurr = Vnext
        n_states += len(Vnext) 

    return max(Vcurr.values()), n_states


def sparse_knapsack(W, v, w):
    """
    Knapsack PD hacia delante pero en lugar de un diccionario Python se utiliza
    una lista de tuplas (peso,beneficio) ordenada de menor a mayor peso.
    Se basa en que si suma un mismo peso a todos los pesos de la lista ordenada
    sigue quedando ordenada. Juntar dos listas ordenadas se hace con la funcion
    auxiliar "merge"
    Devuelve:
            1) El máximo beneficio
            2) El número de estados activos que ha explorado hasta acabar (TODO).
    """
    def merge(list1, list2):
        """
        Función auxiliar, asume que list1 y list2 son listas de tuplas (peso,benef)
        ordenadas por peso, devuelve una lista ordenadas por (peso,benef), en caso
        de encontrar pesos repetidos se queda con el mayor beneficio
        """
        resul = []
        i, j, len1, len2 = 0, 0, len(list1), len(list2)
        while i < len1 and j < len2:
            if list1[i][0] == list2[j][0]:
                resul.append((list1[i][0], max(list1[i][1], list2[j][1])))
                i += 1
                j += 1
            elif list1[i][0] < list2[j][0]:
                resul.append(list1[i])
                i += 1
            else:
                resul.append(list2[j])
                j += 1
        resul += list1[i:] + list2[j:]
        return resul

    col = [(0,0)]
    n_states = 1 # CAMBIAR ESTO Y COMPLETAR DONDE CORRESPONDA

    for vi,wi in zip(v,w):
        # cada iteración es una etapa o columna de PD
        col = merge(col, [(weight+wi,value+vi) for (weight,value) in col if weight+wi<=W])

        n_states += len(col)

    return max(v for (w,v) in col), n_states

def generate_knapsack(N,W):
    """
    Genera una instancia del problema de la mochila discreta
    """
    v = [ random.randrange(1, 1000) for i in range(N) ]
    w = [ random.randrange(1, W) for i in range(N) ]
    return W,v,w

def generate_instances(n_ins, W, n_obj):
    """
    Genera n_ins instancias del problema de la mochila discreta para un peso máximo
    de W y n_obj objetos
    Devuelve una lista con tantas tuplas como instancias: [(W, v, w), ...]
    """
    return [generate_knapsack(n_obj, W) for _ in range(n_ins)]


def dummy_function():
    pass

def measure_time(function, arguments,
                 prepare=dummy_function, prepare_args=()):
    """
    mide el tiempo de ejecutar function(*arguments)

    IMPORTANTE: como se puede ejecutar varias veces puede que sea
    necesario pasarle una función que establezca las condiciones
    necesarias para medir adecuadamente (ej: si mides el tiempo de
    ordenar algo y lo deja ordenado, la próxima vez que ordenes no
    estará desordenado)

    DEVUELVE: tiempo y el valor devuelto por la función
    """
    count, accum = 0, 0
    while accum < 0.1:
        prepare(*prepare_args)
        t_ini = time.process_time()
        returned_value = function(*arguments)
        accum += time.process_time()-t_ini
        count += 1
    return accum/count, returned_value

def measure_knapsack_functions():
    """TODO:

    Aumentar la talla del problema aumentando el número de objetos a
    meter en la mochila. Ej: tallas de 50 a 1000 de 50 en 50.

    Para una misma talla generar varias instancias (ej: 5).

    IMPORTANTE: utilizar LAS MISMAS instancias en todas las versiones
    de mochila (análisis de datos APAREADOS).

    Reportar, por salida estándar, para cada talla:

     - El tiempo medio de ejecución de cada algoritmo.
     - El porcenaje de estados activos visitados respecto de la versión PD hacia atrás.

    """

    W = 20000

    functions = [(knapsack_backward, "backward"),
                 (knapsack_forward,  "forward"),
                 (sparse_knapsack, "sparse")]

    measures = {}
    for function,functname in functions:
        measures[functname] = {} # asocia a cada talla una lista de (tiempo, estados)

    for n_obj in range(50,1001,50):
        instances = generate_instances(5, W, n_obj)
        for function,functname in functions:
            val = [True, True, True, True, True]
            measures[functname][n_obj] = []
            
            t_m = 0
            n_m = 0
            for i,instance in enumerate(instances):
                t, (maxval, nstates) = measure_time(function, instance)
                measures[functname][n_obj].append((t,nstates))
                # muestra para cada ejecución una serie de valores:
                print(n_obj, i, functname, t, maxval, nstates)

                t_m += t
                n_m += nstates     
            
            t_m = t_m / 5

            if(functname == "backward"):
                n_back = n_m
                m_back = maxval
            else:
                val[i] = val[i] and (maxval == m_back)

            n_m = n_m / n_back * 100

            print("\nTiempo medio de %s: %f\nPorcentaje de estados activos: %.2f\n" %(functname, t_m, n_m))

        for i in range(5):
            print("Instancia %d tiene el mismo valor: %s" %(i,val[i]))

        print()

    # COMPLETAR:
    # - comprueba que para un mismo n_obj e instancia (valor de i) el valor maxval
    #   es el mismo en todos los algoritmos
    #
    # - comenta el print anterior y añade código para mostrar los datos que se piden

    
if __name__ == "__main__":
    measure_knapsack_functions()
