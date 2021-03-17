#!/usr/bin/env python
# -*- coding: utf-8 -*-
#
#  ALT_library.py
#  
#  Copyright 2020: Profesores de Algorítmica (UPV, GII/ETSINF)

import collections
import numpy as np

class Trie:
    """
    Clase que implementa un Trie para almacenar el vocabulario
    """

    def __init__(self, vocabulary):
        """Método constructor de la clase Trie.

        Llama al método build_trie para crear el trie a partir de un vocabulario de términos.

        Args:
            vocabulary (list of str): Vocabulario de términos.
                Ejemplo: ["aa","ac","bb","c","cab","cac", "aá", "aà"].
                Los términos deben estar ordenados lexicográficamente (sort() o sorted())

        Raises:
            Exception: si el vocabulario no es una lista de cadenas ordenada lexicográficamente.
        """
        
        if not (isinstance(vocabulary,list) and
                all(isinstance(w, str) and len(w) > 0 for w in vocabulary) and
                all(w1 < w2 for w1, w2 in zip(vocabulary, vocabulary[1:]))):
            raise Exception("vocabulario incorrecto")

        self.vocabulary = vocabulary # nos quedamos una referencia, esto podría dar problemas
                                     # si más adelante alguien cambia dicha lista, ¡cuidado!
                                     # una alternativa sería hacer copia local pero ocupa más espacio
                                     # si varios objetos van a compartir el vocabulario sin modificarlo
        self.build_trie() # construimos el Trie propiamente dicho

    def build_trie(self):
        """Método para construir el trie.

        Crea los atributos que representan el trie (trasparencias 34-38 del boletín de prácticas):

            label (np.array of unicode symbols): cada posición i del array contiene
                el símbolo/letra/carácter/unicode_rune asociado al nodo i del trie.

            firstchild (np.array of int): cada posición i del array contiene
                el índice del PRIMER hijo del nodo i del trie.
                La idea es que puedes recorrer todos los hijos de i haciendo un bucle
                en range(self.firstchild[i], self.firstchild[i+1])
                Se utiliza para programación dinámica hacia adelante (consultar a dónde vas)

            parent (np.array of int): cada posición i del array contiene
                el padre del nodo i. El nodo raiz y el centinela comparten otro nodo ficticio
                como padre (-1).
                Se utiliza para programación dinámica hacia atrás (consultar de dónde vienes)

            output es un diccionario que a los nodos finales les asocia la cadena que se emite
                la forma de saber si un nodo i es final es comprobar que i es una clave de este
                diccionario.
        """
        
        label = []     # lista python, después self.label un array numpy
        firstchild = [] # lista python, después self.firstchild un array numpy
        parent = []     # lista python, después self.parent array numpy
        self.output = {} # diccionario asocia a cada nodo final una palabra del vocabulario
        label.append(" ")   # asociado al nodo raíz
        firstchild.append(1) # asociado al nodo raíz
        parent.append(-1)   # asociado al nodo raíz
        
        # esto básicamente hace un recorrido por niveles del Trie, de
        # ahí que utilice una cola:
        Q = collections.deque()
        # cada elemento guardado en la cola es una tupla con estos campos:
        # first_index, last_index, triedepth, root = Q.popleft()
        # donde:
        # first_index y last_index: ambos referidos al vector self.vocabulary,
        #  el 2º apunta a después como range
        #  ambos delimitan la zona a tratar
        # triedepth se refiere a la prof. de root en el trie, donde
        # root es el estado raíz del subárbol que se va a generar
        Q.append((0, len(self.vocabulary), 1, 0))

        while len(Q) > 0:
            first_index, last_index, triedepth, root = Q.popleft()
            firstchild[root] = len(firstchild) # ver más abajo el (*)
            positions = []
            lastletter = ""
            for i in range(first_index, last_index):
                word = self.vocabulary[i]
                if len(word) >= triedepth:
                    letter = word[triedepth - 1]
                    if letter != lastletter:
                        lastletter = letter
                        label.append(letter)
                        parent.append(root)
                        firstchild.append(0) # se pone a 0 por poner algo, luego se modifica arriba en (*)
                        thischildpos = len(firstchild) - 1
                        if len(word) == triedepth:
                            self.output[thischildpos] = word
                        positions.append((i, thischildpos))
            positions.append((last_index, 0))
            for (frompos, root), (lastpos, dummy) in zip(positions, positions[1:]):
                Q.append((frompos, lastpos, triedepth + 1, root))

        # un nodo centinela al final para poder usar
        # range(self.firstchild[i], self.firstchild[i+1]) en el último nodo
        label.append(" ")
        firstchild.append(len(firstchild))
        parent.append(-1)
        # convertimos todo a np.array y lo guardamos como atributos de la clase:
        self.label = np.array(label, dtype=np.unicode_)
        self.firstchild = np.array(firstchild, dtype=np.int)
        self.parent = np.array(parent, dtype=np.int)

    def get_root(self):
        return 0

    def get_num_states(self):
        return len(self.firstchild)-1 # sentinel is not included
    
    def get_label(self, node):
        return self.label[node]

    def get_parent(self, node):
        return self.parent[node]

    def iter_children(self, node):
        return range(self.firstchild[node], self.firstchild[node + 1])

    def is_leaf(self, node):
        return self.firstchild[node] == self.firstchild[node+1]

    def is_final(self, node):
        return node in self.output

    def get_output(self, node):
        return self.output.get(node,"")
    
    def num_children(self, node):
        return self.firstchild[node + 1] - self.firstchild[node]

    def __str__(self):
        lines = ["vocabulary " + repr(self.vocabulary),
                 "pos label parent firstchild nºchild output"]
        num_nodes = len(self.label)-1 # sentinel is not included
        for node in range(num_nodes):
            etiqueta = '"'+str(self.label[node])+'"'
            num_children = self.firstchild[node + 1] - self.firstchild[node]
            out = '' if node not in self.output else '"'+self.output[node]+'"'
            line = f'{node:3} {etiqueta:4}  {self.parent[node]:3}    {self.firstchild[node]:3}        {num_children:2}      {out}'
            lines.append(line)
        return "\n".join(lines)

if __name__ == "__main__":
    vocabulario = ["aa","ac","bb","c","cab","cac"]
    trie = Trie(vocabulario)
    print(trie)
    print()
    vocabulario = ['a', 'ata', 'ato', 'cama', 'casa', 'caso', 'casó', 'caña']
    trie = Trie(vocabulario)
    print(trie)


