import re
import distancias as dist

from trie import Trie

class SpellSuggester:

    """
    Clase que implementa el método suggest para la búsqueda de términos.
    """

    def __init__(self, vocab_file_path):
        """Método constructor de la clase SpellSuggester

        Construye una lista de términos únicos (vocabulario),
        que además se utiliza para crear un trie.

        Args:
            vocab_file (str): ruta del fichero de texto para cargar el vocabulario.

        """

        self.vocabulary  = self.build_vocab(vocab_file_path, tokenizer=re.compile(r"\W+"))

    def build_vocab(self, vocab_file_path, tokenizer):
        """Método para crear el vocabulario.

        Se tokeniza por palabras el fichero de texto,
        se eliminan palabras duplicadas y se ordena
        lexicográficamente.

        Args:
            vocab_file (str): ruta del fichero de texto para cargar el vocabulario.
            tokenizer (re.Pattern): expresión regular para la tokenización.
        """
        with open(vocab_file_path, "r", encoding="utf-8") as fr:
            vocab = set(tokenizer.split(fr.read().lower()))
            vocab.discard('') # por si acaso
            return sorted(vocab)

    def suggest(self, term, distance="levenshtein", threshold=2**31):

        """Método para sugerir palabras similares siguiendo la tarea 3.

        A completar.

        Args:
            term (str): término de búsqueda.
            distance (str): algoritmo de búsqueda a utilizar
                {"levenshtein", "restringida", "intermedia"}.
            threshold (int): threshold para limitar la búsqueda
                puede utilizarse con los algoritmos de distancia mejorada de la tarea 2
                o filtrando la salida de las distancias de la tarea 2
        """
        assert distance in ["levenshtein_thres", "restringida_thres", "intermedia_thres", "levenshtein-trie"]

        results = {} # diccionario distancia:termino
        longAux = len(term)
        if(distance=="levenshtein_thres"):
            metDist = dist.dist_levenshtein_thres    
        elif(distance=="restringida_thres"):
            metDist = dist.dist_restringida_thres
        elif(distance=="intermedia_thres"):
            metDist = dist.dist_intermedia_thres
        else:
            metDist = dist.dist_levenshtein_trie
        
        if (distance == "levenshtein-trie"):
            return metDist(term, spellsuggester.trie, threshold)
        else:
            for word in self.vocabulary:
                if(abs(len(word)-longAux) <= threshold):
                    realDist = metDist(term, word, threshold)
                    if (realDist!=None and realDist<=threshold):
                        if(realDist in results):
                            results[realDist].append(word)
                        else:
                            results[realDist] = [word]        
                
        return results

class TrieSpellSuggester(SpellSuggester):
    """
    Clase que implementa el método suggest para la búsqueda de términos y añade el trie
    """
    def __init__(self, vocab_file_path):
        super().__init__(vocab_file_path)
        self.trie = Trie(self.vocabulary)
    
if __name__ == "__main__":
    spellsuggester = TrieSpellSuggester("./corpora/quijote.txt")
    words = ["casa", "senor", "jabón", "constitución", "ancho", "savaedra", "vicios", "quixot", "s3afg4ew"]
    
    #LEVENSHTEIN-TRIE
    fileL = open("results/our_result_levenshtein-trie.txt", "w", encoding="utf-8")
    for word in words:
        for threshold in range(1, 6):
            result = {}
            result2 = spellsuggester.suggest(word, "levenshtein-trie", threshold)
            for key in result2:
                result[result2[key]] = result.get(result2[key], []) + [key]
            for key in result:
                result[key].sort()
            resultArray = []
            for x in range(0, threshold+1):
                if(x in result):
                    for y in result[x]:
                        resultArray.append(str(x)+":"+str(y))
            outputString = (word + "\t" + str(threshold) + "\t" + str(len(resultArray)) + "\t" + (" ").join(resultArray) + "\n")
            fileL.write(outputString)
    fileL.close()

    #LEVENSHTEIN
    fileL = open("results/our_result_levenshtein_thres.txt", "w", encoding="utf-8")
    for word in words:
        for threshold in range(1, 6):
            result = spellsuggester.suggest(word, "levenshtein_thres", threshold)
            resultArray = []
            for x in range(0, threshold+1):
                if(x in result):
                    for y in result[x]:
                        resultArray.append(str(x)+":"+str(y))
            outputString = (word + "\t" + str(threshold) + "\t" + str(len(resultArray)) + "\t" + (" ").join(resultArray) + "\n") 
            fileL.write(outputString)
            # cuidado, la salida es enorme print(suggester.trie)
    fileL.close()

    #restringida
    fileR = open("results/our_result_restringida_thres.txt", "w", encoding="utf-8")
    for word in words:
        for threshold in range(1, 6):
            result = spellsuggester.suggest(word, "restringida_thres", threshold)
            resultArray = []
            for x in range(0, threshold+1):
                if(x in result):
                    for y in result[x]:
                        resultArray.append(str(x)+":"+str(y))
            outputString = (word + "\t" + str(threshold) + "\t" + str(len(resultArray)) + "\t" + (" ").join(resultArray) + "\n") 
            fileR.write(outputString)
            # cuidado, la salida es enorme print(suggester.trie)
    fileR.close()

    #intermedia
    fileI = open("results/our_result_intermedia_thres.txt", "w", encoding="utf-8")
    for word in words:
        for threshold in range(1, 6):
            result = spellsuggester.suggest(word, "intermedia_thres", threshold)
            resultArray = []
            for x in range(0, threshold+1):
                if(x in result):
                    for y in result[x]:
                        resultArray.append(str(x)+":"+str(y))
            outputString = (word + "\t" + str(threshold) + "\t" + str(len(resultArray)) + "\t" + (" ").join(resultArray) + "\n") 
            fileI.write(outputString)
            # cuidado, la salida es enorme print(suggester.trie)
    fileI.close()
