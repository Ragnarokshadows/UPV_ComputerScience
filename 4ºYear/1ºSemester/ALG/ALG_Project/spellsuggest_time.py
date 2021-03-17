import re
import distancias as dist
import math
import collections
import random
import numpy as np
from time import process_time 

from trie import Trie

## Funcion que se pasa por defecto en measure_time()
def dummy_function():
    pass

def measure_time(function, arguments, prepare=dummy_function, prepare_args=()):
    count, accum = 0, 0

    while accum < 0.1:
        prepare(*prepare_args)
        t_ini = process_time()
        returned_value = function(*arguments)
        accum += process_time()-t_ini
        count += 1

    return accum/count, returned_value


class SpellSuggesterTimes:

    """
    Clase que implementa el método suggest para la búsqueda de términos.
    """

    def __init__(self, vocab_file_path, n=10**1):
        """Método constructor de la clase SpellSuggester

        Construye una lista de términos únicos (vocabulario),
        que además se utiliza para crear un trie.

        Args:
            vocab_file (str): ruta del fichero de texto para cargar el vocabulario.

        """
        #build_vocab()
        tokenizer = re.compile(r"\W+")
        with open(vocab_file_path, "r", encoding='utf-8') as fr:
            c = collections.Counter(tokenizer.split(fr.read().lower()))
            if '' in c:
                del c['']
            reversed_c = [(freq, word) for (word,freq) in c.items()]
            sorted_reversed = sorted(reversed_c, reverse=True)
            sorted_vocab = [word for (freq,word) in sorted_reversed]
            
            self.full_vocab = sorted_vocab
            self.vocabulary = sorted_vocab[0:n]
        
        self.trie = Trie(sorted(self.vocabulary))
    


    def get_random_terms(self, n):
        custom_terms = random.choices(self.full_vocab, k=n)
        case = 0
        for j in range(6):
            for i in range(len(custom_terms)):
                case = random.randint(0,3)
                word = list(custom_terms[i])
                if case==0 or len(word)==1: #insertar
                    word.insert(random.randint(0,len(word)),"z")
                    
                elif case==1:#borrar
                    del word[random.randint(0,len(word)-1)]

                elif case==2:#intercambiar
                    index = random.randint(0,len(word)-2)
                    word[index], word[index+1] =  word[index+1],  word[index]

                custom_terms[i] = "".join(word)
        
        return custom_terms

    def suggest(self, term, distance="levenshtein", threshold=2**31):

        """Método para sugerir palabras similares siguiendo la tarea 3.

        A completar.

        Args:
            term (str): término de búsqueda.
            distance (str): algoritmo de búsqueda a utilizar
                {"levenshtein_thres", "restringida_thres", "intermedia_thres"}.
            threshold (int): threshold para limitar la búsqueda
                puede utilizarse con los algoritmos de distancia mejorada de la tarea 2
                o filtrando la salida de las distancias de la tarea 2
        """
        assert distance in ["levenshtein", "restringida", "intermedia","levenshtein_thres", "restringida_thres", "intermedia_thres", "levenshtein-trie"]
        
        no_t = False

        results = {} # diccionario distancia:termino
        longAux = len(term)
        if(distance=="levenshtein_thres"):
            metDist = dist.dist_levenshtein_thres    
        elif(distance=="restringida_thres"):
            metDist = dist.dist_restringida_thres
        elif(distance=="intermedia_thres"):
            metDist = dist.dist_intermedia_thres
        elif(distance=="levenshtein"):
            metDist = dist.dist_levenshtein  
            no_t = True  
        elif(distance=="restringida"):
            metDist = dist.dist_restringida
            no_t = True 
        elif(distance=="intermedia"):
            metDist = dist.dist_intermedia
            no_t = True 
        else:
            metDist = dist.dist_levenshtein_trie
        
        if (distance == "levenshtein-trie"):
            return metDist(term, spellsuggester.trie, threshold)
        else:
            for word in self.vocabulary:
                if(abs(len(word)-longAux) <= threshold):
                    if no_t:
                        realDist = metDist(term, word)
                    else:
                        realDist = metDist(term, word, threshold)
                    if (realDist!=None and realDist<=threshold):
                        if(realDist in results):
                            results[realDist].append(word)
                        else:
                            results[realDist] = [word]        
                
        return results

    
if __name__ == "__main__":
    thres = [1, 2, 3, 4, 5]
    spellsuggester = SpellSuggesterTimes("./corpora/quijote.txt")
    words = spellsuggester.get_random_terms(10)

    print(words)

    tallas = [6, 6**2, 6**3, 6**4, 6**5, 6**6]

    mediciones = {
        "levenshtein": {},
        "restringida": {},
        "intermedia": {},
        "levenshtein_thres": {},
        "restringida_thres": {},
        "intermedia_thres": {},
        "levenshtein-trie": {}
    }

    # MEDIR TIEMPOS
    for i in tallas:
        mediciones["levenshtein"][i] = []
        mediciones["restringida"][i] = []
        mediciones["intermedia"][i] = []
        mediciones["levenshtein_thres"][i] = {}
        mediciones["restringida_thres"][i] = {}
        mediciones["intermedia_thres"][i] = {}
        mediciones["levenshtein-trie"][i] = {}

        for t in thres:
            mediciones["levenshtein_thres"][i][t] = []
            mediciones["restringida_thres"][i][t] = []
            mediciones["intermedia_thres"][i][t] = []
            mediciones["levenshtein-trie"][i][t] = []

        spellsuggester = SpellSuggesterTimes("./corpora/quijote.txt", i)
        for w in words:
            # ORIGINALES           
            tiempo, resultado = measure_time(spellsuggester.suggest,[w,"levenshtein"])
            mediciones["levenshtein"][i].append(tiempo)

            tiempo, resultado = measure_time(spellsuggester.suggest,[w,"restringida"])
            mediciones["restringida"][i].append(tiempo)

            tiempo, resultado = measure_time(spellsuggester.suggest,[w,"intermedia"])
            mediciones["intermedia"][i].append(tiempo)
            
            # THRESHOLD Y TRIE
            for t in thres:
                tiempo, resultado = measure_time(spellsuggester.suggest,[w,"levenshtein_thres", t])
                mediciones["levenshtein_thres"][i][t].append(tiempo)

                tiempo, resultado = measure_time(spellsuggester.suggest,[w,"restringida_thres", t])
                mediciones["restringida_thres"][i][t].append(tiempo)

                tiempo, resultado = measure_time(spellsuggester.suggest,[w,"intermedia_thres", t])
                mediciones["intermedia_thres"][i][t].append(tiempo)

                tiempo, resultado = measure_time(spellsuggester.suggest,[w,"levenshtein-trie", t])
                mediciones["levenshtein-trie"][i][t].append(tiempo)

    # CALCULOS (media, mediana y desviación típica)
    for metodo in mediciones:
        for keyTalla in mediciones[metodo]:
            if metodo not in ["levenshtein_thres", "restringida_thres", "intermedia_thres", "levenshtein-trie"]:
                listaTiempos = mediciones[metodo][keyTalla]
                media = np.mean(listaTiempos)
                mediana = np.median(listaTiempos)
                desvT = np.std(listaTiempos)
                print("Método: " + metodo + " Talla: " + str(keyTalla) + " Media: " + str(media) + " Mediana: " + str(mediana) + " Desviación Típica: " + str(desvT))

            else:
                for keyThres in mediciones[metodo][keyTalla]:
                    listaTiempos = mediciones[metodo][keyTalla][keyThres]
                    media = np.mean(listaTiempos)
                    mediana = np.median(listaTiempos)
                    desvT = np.std(listaTiempos) 
                    print("Método: " + metodo + " Talla: " + str(keyTalla) + " Threshold: " + str(keyThres) + " Media: " + str(media) + " Mediana: " + str(mediana) + " Desviación Típica: " + str(desvT))