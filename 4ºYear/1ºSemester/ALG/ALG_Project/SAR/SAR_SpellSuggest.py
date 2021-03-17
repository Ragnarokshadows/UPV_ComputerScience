import re
import distancias as dist

from trie import Trie

class SpellSuggester:

    """
    Clase que implementa el método suggest para la búsqueda de términos.
    """

    def __init__(self, vocab_list):
        """Método constructor de la clase SpellSuggester

        Construye una lista de términos únicos (vocabulario),
        que además se utiliza para crear un trie.

        Args:
            vocab_file (str): ruta del fichero de texto para cargar el vocabulario.

        """

        self.vocabulary = self.build_vocab(vocab_list)

    def build_vocab(self, vocab_list):
        """Método para crear el vocabulario.

        Se eliminan palabras duplicadas y se ordena
        lexicográficamente.

        Args:
            vocab_list (list): lista de palabras para crear el vocabulario.
        """
        vocab = set(vocab_list)
        vocab.discard('')
        return sorted(vocab)

    def suggest(self, term, distance="levenshtein_thres", threshold=2**31):

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
        assert distance in ["levenshtein_thres", "levenshtein-trie"]

        results = {} # Almacenamos los resultados como un diccionario Distancia: Término
        longAux = len(term)
        if(distance=="levenshtein_thres"):
            metDist = dist.dist_levenshtein_thres
        else:
            metDist = dist.dist_levenshtein_trie
        
        if (distance == "levenshtein-trie"):
            # Calculamos las distintas distancias del término con el trie
            # En este caso, almacenamos los resultados como un diccionario Término: Distancia
            # Siempre usamos TrieSpellSuggester por lo que podemos acceder directamente a esta variable
            return metDist(term, self.trie, threshold)
        else:
            # Calculamos la distancias del términco con las distintas palabras
            for word in self.vocabulary:
                # Si la longitud de una palabra es mayor que la otra + threshold, no es necesario compararla
                if(abs(len(word)-longAux) <= threshold):
                    realDist = metDist(term, word, threshold)
                    # Si la distancia es menor o igual al threshold, añadimos la palabra a los resultados
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
    def __init__(self, vocab_list):
        super().__init__(vocab_list)
        self.trie = Trie(self.vocabulary)