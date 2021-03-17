import json
from nltk.stem.snowball import SnowballStemmer
import os
import re
import math
from SAR_SpellSuggest import TrieSpellSuggester

# Alumnos: Sergi Albiach Caro, Manel Angresola Navarro, Antonio Martínez Leal y Stéphane Díaz-Alejo León
# Ampliaciones implementadas: parentesis + multiples indices + stemming + permuterm + ranking de resultado
# Ampliación implementada de ALG: distancias y SpellSuggester

class SAR_Project:
    """
    Prototipo de la clase para realizar la indexacion y la recuperacion de noticias
        
        Preparada para todas las ampliaciones:
          parentesis + multiples indices + posicionales + stemming + permuterm + ranking de resultado

    Se deben completar los metodos que se indica.
    Se pueden añadir nuevas variables y nuevos metodos
    Los metodos que se añadan se deberan documentar en el codigo y explicar en la memoria
    """

    # lista de campos, el booleano indica si se debe tokenizar el campo
    # NECESARIO PARA LA AMPLIACION MULTIFIELD
    fields = [("title", True), ("date", False),
              ("keywords", True), ("article", True),
              ("summary", True)]
    
    
    # numero maximo de documento a mostrar cuando self.show_all es False
    SHOW_MAX = 10


    def __init__(self):
        """
        Constructor de la classe SAR_Indexer.
        NECESARIO PARA LA VERSION MINIMA

        Incluye todas las variables necesaria para todas las ampliaciones.
        Puedes añadir más variables si las necesitas 

        """
        self.index = {} # hash para el indice invertido de terminos --> clave: termino, valor: posting list.
                        # Si se hace la implementacion multifield, se pude hacer un segundo nivel de hashing de tal forma que:
                        # self.index['title'] seria el indice invertido del campo 'title'.
        self.sindex = {} # hash para el indice invertido de stems --> clave: stem, valor: posting list
        self.ptindex = {} # hash para el indice permuterm --> clave: permuterm, valor: posting list
        self.docs = {} # diccionario de terminos --> clave: entero(docid),  valor: ruta del fichero.
        self.weight = {} # hash de terminos para el pesado, ranking de resultados. puede no utilizarse
        self.weight_doc = {} # hash de documentos para el pesado
        self.news = {} # hash de noticias --> clave entero (newid), valor: la info necesaria para diferencia la noticia dentro de su fichero
        self.tokenizer = re.compile("\W+") # expresion regular para hacer la tokenizacion
        self.stemmer = SnowballStemmer('spanish') # stemmer en castellano
        self.show_all = False # valor por defecto, se cambia con self.set_showall()
        self.show_snippet = False # valor por defecto, se cambia con self.set_snippet()
        self.use_stemming = False # valor por defecto, se cambia con self.set_stemming()
        self.use_ranking = False  # valor por defecto, se cambia con self.set_ranking()
        self.busq = None # valor por defecto, se cambia con self.set_busq()
        self.trie_spell_suggest = None # trie spell suggester, si se crea
        self.pterms = {} # hash para el indice invertido permuterm --> clave: permuterm, valor: lista con los terminos que tienen ese permuterm
        self.sterms = {} # hash para el indice invertido de stems --> clave: stem, valor: lista con los terminos que tienen ese stem
        self.N = 0 # Número de documentos en la colección
        self.term_field = {} # términos en la query y aque campo pertenecen --> clave: término, valor: campo (field)


    ###############################
    ###                         ###
    ###      CONFIGURACION      ###
    ###                         ###
    ###############################


    def set_showall(self, v):
        """

        Cambia el modo de mostrar los resultados.
        
        input: "v" booleano.

        UTIL PARA TODAS LAS VERSIONES

        si self.show_all es True se mostraran todos los resultados el lugar de un maximo de self.SHOW_MAX, no aplicable a la opcion -C

        """
        self.show_all = v


    def set_snippet(self, v):
        """

        Cambia el modo de mostrar snippet.
        
        input: "v" booleano.

        UTIL PARA TODAS LAS VERSIONES

        si self.show_snippet es True se mostrara un snippet de cada noticia, no aplicable a la opcion -C

        """
        self.show_snippet = v

    def set_busq(self, v):
        """

        Cambia al modo de búsqueda aproximada.
        
        input: "v" string.

        UTIL PARA TODAS LAS VERSIONES

        si se asigna self.busq se buscarán palabras similares con este método de distancia en caso de no encontrarlas

        """
        self.busq = v
        assert v in [None, "levenshtein_thres", "levenshtein-trie"]


    def set_stemming(self, v):
        """

        Cambia el modo de stemming por defecto.
        
        input: "v" booleano.

        UTIL PARA LA VERSION CON STEMMING

        si self.use_stemming es True las consultas se resolveran aplicando stemming por defecto.

        """
        self.use_stemming = v


    def set_ranking(self, v):
        """

        Cambia el modo de ranking por defecto.
        
        input: "v" booleano.

        UTIL PARA LA VERSION CON RANKING DE NOTICIAS

        si self.use_ranking es True las consultas se mostraran ordenadas, no aplicable a la opcion -C

        """
        self.use_ranking = v




    ###############################
    ###                         ###
    ###   PARTE 1: INDEXACION   ###
    ###                         ###
    ###############################

    # Manel Angresola Navarro
    def index_dir(self, root, **args):
        """
        NECESARIO PARA TODAS LAS VERSIONES
        
        Recorre recursivamente el directorio "root"  y indexa su contenido
        los argumentos adicionales "**args" solo son necesarios para las funcionalidades ampliadas

        """

        self.multifield = args['multifield']
        self.positional = args['positional']
        self.stemming = args['stem']
        self.permuterm = args['permuterm']
        self.suggest = args['suggest']

        if self.multifield:
            # Indexar diversos campos
            self.index = {
                'title': {}, 'date': {}, 'keywords': {}, 'article': {}, 'summary': {}
            }
            self.weight = {
                'title': {}, 'date': {}, 'keywords': {}, 'article': {}, 'summary': {}
            }
            if self.stemming:
                self.sindex = {
                    'title': {}, 'date': {}, 'keywords': {}, 'article': {}, 'summary': {}
                }
            if self.permuterm:
                self.ptindex = {
                    'title': {}, 'date': {}, 'keywords': {}, 'article': {}, 'summary': {}
                }
        else:
            self.index = {
                'article': {}
            }
            self.weight = {
                'article': {}
            }
            if self.stemming:
                self.sindex = {
                    'article': {}
                }
            if self.permuterm:
                self.ptindex = {
                    'article': {}
                }

        for dir, subdirs, files in os.walk(root):
            for filename in files:
                if filename.endswith('.json'):
                    fullname = os.path.join(dir, filename)
                    self.index_file(fullname)

        ##########################################
        ## COMPLETAR PARA FUNCIONALIDADES EXTRA ##
        ##########################################
        
    # Manel Angresola Navarro, ampliaciones: Stéphane Díaz-Alejo León y Antonio Martínez Leal
    def index_file(self, filename):
        """
        NECESARIO PARA TODAS LAS VERSIONES

        Indexa el contenido de un fichero.

        Para tokenizar la noticia se debe llamar a "self.tokenize"

        Dependiendo del valor de "self.multifield" y "self.positional" se debe ampliar el indexado.
        En estos casos, se recomienda crear nuevos metodos para hacer mas sencilla la implementacion

        input: "filename" es el nombre de un fichero en formato JSON Arrays (https://www.w3schools.com/js/js_json_arrays.asp).
                Una vez parseado con json.load tendremos una lista de diccionarios, cada diccionario se corresponde a una noticia

        """

        with open(filename) as fh:
            jlist = json.load(fh)

        #
        # "jlist" es una lista con tantos elementos como noticias hay en el fichero,
        # cada noticia es un diccionario con los campos:
        #      "title", "date", "keywords", "article", "summary"
        #
        # En la version basica solo se debe indexar el contenido "article"
        #
        #
        #
        docid = len(self.docs)
        self.docs[docid] = filename # Fijar entrada del diccionario docs
        newsindex = len(self.news)
        newsposition = 0

        # Por cada noticia que encontremos en el fichero json
        for doc in jlist:  
            
            # Fijar entrada del diccionario news
            self.news[newsindex] = {
                'docid': docid,
                'position': newsposition
            }

            # Por cada campo de la noticia
            for field in self.index.keys():
                
                #Diccionario para stemming
                stems = {}

                terms = {}
                if self.multifield:
                    if [item for item in self.fields if item[0] == field][0][1]:
                        termList = self.tokenize(doc[field])
                    else:
                        termList = [doc[field]]
                else:
                    termList = self.tokenize(doc[field])
                # Por cada término del campo de la noticia    
                for term in termList:

                    # Versión eficiente de stemming
                    # Si está activada la opción y el término no lo hemos añadido todavís continuamos
                    if self.stemming and term not in terms:
                        stem = self.stemmer.stem(term)

                        # Si el stem aún no está en el diccionario, lo añadimos
                        if stem not in self.sterms:
                            self.sterms[stem] = []

                        # Si el término aún no está en la lista de términos asociaodos, lo añadimos
                        if term not in self.sterms[stem]:
                            self.sterms[stem] = self.sterms.get(stem, []) + [term]

                        if stem not in stems:
                            # Añadimos el stem si no lo hemos añadido todavía
                            self.sindex[field][stem] = self.sindex[field].get(stem, []) + [newsindex]
                            stems[stem] = True
                    #-------------------------------
                    
                    # Versión eficiente de permuterm
                    # Si está activada la opción y el término no lo hemos añadido todavís continuamos
                    if self.permuterm and term not in terms:
                        auxterm = term + "$"

                        # Generamos los términos permuterm y actualizamos sus posting lists
                        for i in range(len(auxterm)):
                            self.ptindex[field][auxterm] = self.ptindex[field].get(auxterm, []) + [newsindex]

                            # Si el permuterm aún no está en el diccionario, lo añadimos
                            if auxterm not in self.pterms:
                                self.pterms[auxterm] = []

                            # Si el término aún no está en la lista de términos asociaodos, lo añadimos
                            if term not in self.pterms[auxterm]:
                                self.pterms[auxterm] = self.pterms.get(auxterm, []) + [term]
                            auxterm = auxterm[1:] + auxterm[0]
                    #-------------------------------

                    if term not in terms:
                        # Añadir término a la posting list si no lo hemos añadido todavía
                        self.index[field][term] = self.index[field].get(term, []) + [newsindex]
                        terms[term] = True

                        self.weight[field][term] = self.weight[field].get(term,{})
                    
                    # Se añade la frecuencia del término en el documento y campo en concreto
                    self.weight[field][term][newsindex] = self.weight[field][term].get(newsindex,0) + 1

            
            # Incrementar índice de la notícia
            newsindex += 1
            newsposition += 1
        
        # Número de noticias en la colección
        self.N = newsindex - 1

        if self.suggest:
            # Construir TrieSpellSuggester
            build_this = []
            for field in self.index:
                build_this += list(self.index[field].keys())
            self.trie_spell_suggest = TrieSpellSuggester(build_this)


    def tokenize(self, text):
        """
        NECESARIO PARA TODAS LAS VERSIONES

        Tokeniza la cadena "texto" eliminando simbolos no alfanumericos y dividientola por espacios.
        Puedes utilizar la expresion regular 'self.tokenizer'.

        params: 'text': texto a tokenizar

        return: lista de tokens

        """
        return self.tokenizer.sub(' ', text.lower()).split()


    # Stéphane Díaz-Alejo León
    def make_stemming(self):
        """
        NECESARIO PARA LA AMPLIACION DE STEMMING.

        Crea el indice de stemming (self.sindex) para los terminos de todos los indices.

        self.stemmer.stem(token) devuelve el stem del token

        """
        
        # Recorremos todos los campos del índice de términos
        for field in self.index:

            # Recorremos todos los términos del campo
            for term in self.index[field]:
                    
                # Generamos el stem solo si no hemos hecho el stemming del término con anterioridad
                stem = self.stemmer.stem(term)
                
                # Añadimos el stem si no lo hemos añadido todavía
                self.sindex[field][stem] = self.or_posting(self.sindex[field].get(stem, []),self.index[field][term])

        ####################################################
        ## COMPLETAR PARA FUNCIONALIDAD EXTRA DE STEMMING ##
        ####################################################


    # Antonio Martínez Leal
    def make_permuterm(self):
        """
        NECESARIO PARA LA AMPLIACION DE PERMUTERM

        Crea el indice permuterm (self.ptindex) para los terminos de todos los indices.

        """
        # Recorremos todos los campos del índice de términos
        for field in self.index:

            # Recorremos todos los términos del campo
            for term in self.index[field]:
                    auxterm = term + "$"
                    i=0

                    # Generamos los términos permuterm y actualizamos sus posting lists
                    for l in auxterm:
                        pterm = auxterm[i:] + auxterm[0:i]
                        i=i+1
                        self.ptindex[field][pterm] = self.or_posting(self.ptindex[field].get(pterm, []),self.index[field][term])
                        self.pterms[pterm] = self.pterms.get(pterm, []) + [term]

        ####################################################
        ## COMPLETAR PARA FUNCIONALIDAD EXTRA DE STEMMING ##
        ####################################################



    # Manel Angresola Navarro
    def show_stats(self):
        """
        NECESARIO PARA TODAS LAS VERSIONES
        
        Muestra estadisticas de los indices
        
        """
        print('========================================')
        print('Number of indexed days: {}'.format(len(self.docs)))
        print('----------------------------------------')
        print('Number of indexed news: {}'.format(len(self.news)))
        print('----------------------------------------')
        print('TOKENS:')
        for field in self.index.keys():
            print("\t# of tokens in '{}': {}".format(field, len(self.index[field])))
        print('----------------------------------------')
        if (self.permuterm):
            print('PERMUTERMS:')
            for field in self.ptindex.keys():
                 print("\t# of tokens in '{}': {}".format(field, len(self.ptindex[field])))
            print('----------------------------------------')
        if (self.stemming):
            print('STEMS:')
            for field in self.sindex.keys():
                 print("\t# of tokens in '{}': {}".format(field, len(self.sindex[field])))
            print('----------------------------------------')
        print('Positional queries are NOT allowed')
        print('========================================')

        








    ###################################
    ###                             ###
    ###   PARTE 2.1: RECUPERACION   ###
    ###                             ###
    ###################################

    # Sergi Albiach Caro
    def solve_query(self, query, prev={}):
        """
        NECESARIO PARA TODAS LAS VERSIONES

        Resuelve una query.
        Debe realizar el parsing de consulta que sera mas o menos complicado en funcion de la ampliacion que se implementen


        param:  "query": cadena con la query
                "prev": incluido por si se quiere hacer una version recursiva. No es necesario utilizarlo.
                "first": indica si llamamos al método por primera vez


        return: posting list con el resultado de la query

        """
        #Normalitzem la query per a no distingir majúscules
        query = query.lower()
        
        #Separem els paréntesis de la resta de les paraules amb un espai
        separada = ""
        for i in  range(0,len(query)):
            item = query[i]
            if  item == '(' or item == ')':
                separada = separada + " " + item + " "
            else:
                separada = separada + item

        #Separem els diferents items de la query: '(' ')' 'AND' 'OR' 'NOT' 'termX' 
        query = separada.split()

        #Insertem per a fer que el primer terme faja un OR amb una llista buida i no hi haja problemes
        query.insert(0,'or')

        #Tornem el resultat cridant a un mètode auxiliar que resol la query de forma recursiva
        return self.meu_solve(query,{})

        ########################################
        ## COMPLETAR PARA TODAS LAS VERSIONES ##
        ########################################

    # Manel Angresola Navarro
    def colonSplit(self,term):
        """
        Calcula el nou field i recalcula el nou terme
        
        param:  "term": terme a consultar per traure el field

        return: una tupla (field,term) amb el field i el terme corresponent
        """

        field = 'article'
        colonSplit = term.split(':')
        if len(colonSplit) == 2:
            field = colonSplit[0]
            term = colonSplit[1]
            
        return field,term

    # Sergi Albiach Caro
    def betweenParenthesis(self,query):
        """
        Obté la subconsulta continguda entre dos paréntesis
        
        param:  "query": consulta sobre la que s'obtindrà la subconsulta entre paréntesis

        return: la subconsulta compresa entre els dos paréntesis més exteriors
        """

        j = 0 #contador de subqueries obertes -> s'obre un paréntesis

        #el primer item de query es '(', calculem on está el paréntesis que tanca ')'
        for i in range (0,len(query)):
            if query[i] == '(': j = j+1 #s'obri una subconsulta
            if query[i] == ')': j = j-1 #es tanca una subconsulta
            if(j==0): break #s'ha tancat el paréntesis corresponent al primer paréntesis

        return query[1:i]

    # Sergi Albiach Caro
    def meu_solve(self,query,prev):
        """
        Resol una consulta de manera recursiva
        
        param:  "query": consulta a resoldre
                "prev": parámetre recursiu que almatzenarà la posting list t1 en (t1 AND/OR t2)

        return: la posting list asociada a la consulta
        """
        
        new_query = ""      #la nova query a processar
        i=1                 #ens indicarà a partir d'on comença la nova query
        field = 'article'   #el camp sobre el que s'efectua la consulta

        #Si no hi ha cap consulta a processar, tornem prev
        if query is None or len(query) == 0:
            return prev
        
        else:

            #Obtenim les posting lists per a fer "t1 AND/OR t2"
            t1 = prev

            #Analitzem el que va després del AND/OR per si cal fer la negació del següent terme
            if query[1] != 'not' and query[1] != '(': #es una paraula qualsevol
                term = query[1]
                field,term = self.colonSplit(term)

                t2 = self.get_posting(term, field)
                i=2

            elif query[1] != '(': #després del AND/OR hi ha un 'NOT'
                term = query[2] #item després del 'NOT'

                if term == '(': #hem de negar una consulta entre paréntesis
                    
                    #obtenim la consulta entre paréntesis i la resolem
                    new_query = self.betweenParenthesis(query[2:len(query)])
                    new_query.insert(0,'or')
                    t2_sense_negar = self.meu_solve(new_query,{})

                    #neguem la consulta
                    t2 = self.reverse_posting(t2_sense_negar)
                    
                    #comencem en llargària de la subquery, més els 2 paréntesis, més el 'NOT', més 1 (comptat en la subquery per 'OR')
                    i = len(new_query)+3 
                

                else: #hem de negar soles un terme
                    field,term = self.colonSplit(term)

                    t2 = self.reverse_posting( self.get_posting(term, field) )
                    i=3

            else: #es un '('

                #obtenim la consulta entre paréntesis
                new_query = self.betweenParenthesis(query[1:len(query)])
                new_query.insert(0,'or')
                t2 = self.meu_solve(new_query,{})

                #comencem en la llargària de la subquery, més els paréntesis, més 1 (comptat per 'OR' en len(new_query))
                i = len(new_query)+2
                

            
            #Actualitzem la llista segons l'operador
            if query[0] == 'and':
                prev = self.and_posting(t1,t2)
            elif query[0] == 'or':
                prev = self.or_posting(t1,t2)


        #Actualitzem la posició des d'on començarà la següent part de la query
        new_query = query[i:len(query)]
        
        #Cridem recursivament
        return self.meu_solve(new_query,prev)

    # Obtiene posting list similares a un término
    # Sergi Albiach Caro, Manel Angresola Navarro, Stéphane Díaz-Alejo León, Antonio Martínez Leal
    def get_posting_similares(self, term, field):
        # Buscar palabras similares
        terminos_encontrados = []
        posting_list = []
        # Usamos como máximo un valor de threshold de 5 para que devuelva resultados con sentido
        if self.busq == 'levenshtein-trie':
            for thres in range(1, 5):
                for termino in list(self.trie_spell_suggest.suggest(term, self.busq, thres).keys()):
                    terminos_encontrados.append(termino)
                if terminos_encontrados:
                    # Una vez hayamos encontrado términos similares, finalizamos
                    break
        else:
            for thres in range(1, 5):
                for lista_terminos in list(self.trie_spell_suggest.suggest(term, self.busq, thres).values()):
                    for termino in lista_terminos:
                        terminos_encontrados.append(termino)
                if terminos_encontrados:
                    # Una vez hayamos encontrado términos similares, finalizamos
                    break
        
        # Añadimos las posting list de los términos encontrados
        for termino in terminos_encontrados:
            posting_list += self.get_posting(termino, field, False)

        # Devolvemos las posting list ordenada sin duplicados
        return sorted(list(set(posting_list)))

    # Antonio Martínez Leal, ampliación: Stéphane Díaz-Alejo León
    def get_posting(self, term, field='article', busca_mas=True):
        """
        NECESARIO PARA TODAS LAS VERSIONES

        Devuelve la posting list asociada a un termino. 
        Dependiendo de las ampliaciones implementadas "get_posting" puede llamar a:
            - self.get_positionals: para la ampliacion de posicionales
            - self.get_permuterm: para la ampliacion de permuterms
            - self.get_stemming: para la ampliacion de stemming


        param:  "term": termino del que se debe recuperar la posting list.
                "field": campo sobre el que se debe recuperar la posting list, solo necesario si se hace la ampliacion de multiples indices

        return: posting list

        """
        termAux = term

        # Se añade el término y campo de la consulta para el ránking
        self.term_field[(termAux, field)] = True

        res = []

        #Comprobamos si se debe realizar permuterms
        if ("*" in termAux or "?" in termAux):
            res = self.get_permuterm(termAux,field)


        #Comprobamos si se debe realizar stemming
        elif (self.use_stemming):
            res = self.get_stemming(term, field)

        #Caso estándar
        elif (termAux in self.index[field]):
            res = self.index[field][termAux]

        # Si el término a buscar no se encuentra en el diccionario
        if (self.busq) and (busca_mas) and (not res):
            res = self.get_posting_similares(term, field)

        return res

        ########################################
        ## COMPLETAR PARA TODAS LAS VERSIONES ##
        ########################################

    # Antonio Martínez Leal
    def get_pterms(self, term, field):
        """
        Devuelve la lista de los permuterms asociados a un termino. 

        param:  "term": termino del que se debe recuperar la lista de permuterms.
                "field": campo sobre el que se debe recuperar la lista de permuterms, necesario para ampliacion de multiples indices

        return: lista de permuterms

        """
        res = []
        pterm = term + "$"

        # Comprobamos que comodin se incluye
        if "*" in pterm:
            s = "*"
        else:
            s = "?"

        # Realizamos permutaciones hasta que el caracter comodin se encuentra en la ultima posicion
        while pterm[len(pterm)-1]!=s:
            pterm = pterm[1:] + pterm[0]

        # Llegados a este punto ya tenemos la palabra que debemos buscar en ptindex
        # Si s=="*"
        if(s == "*"):
            for element in self.ptindex[field].keys():
                if(element[0:len(pterm)-1] == pterm[0:len(pterm)-1]):
                    res.append(element)
        # Si s=="?"
        else:
            for element in self.ptindex[field].keys():
                if(element[0:len(pterm)-1] == pterm[0:len(pterm)-1] and len(element) <= (len(pterm)-1)):
                    res.append(element)
        return res

    # No se ha implementado
    def get_positionals(self, terms, field='article'):
        """
        NECESARIO PARA LA AMPLIACION DE POSICIONALES

        Devuelve la posting list asociada a una secuencia de terminos consecutivos.

        param:  "terms": lista con los terminos consecutivos para recuperar la posting list.
                "field": campo sobre el que se debe recuperar la posting list, solo necesario se se hace la ampliacion de multiples indices

        return: posting list

        """
        pass
        ########################################################
        ## COMPLETAR PARA FUNCIONALIDAD EXTRA DE POSICIONALES ##
        ########################################################

    # Stéphane Díaz-Alejo León
    def get_stemming(self, term, field='article'):
        """
        NECESARIO PARA LA AMPLIACION DE STEMMING

        Devuelve la posting list asociada al stem de un termino.

        param:  "term": termino para recuperar la posting list de su stem.
                "field": campo sobre el que se debe recuperar la posting list, solo necesario se se hace la ampliacion de multiples indices

        return: posting list

        """
        
        # Generamos el stem del término
        stem = self.stemmer.stem(term)
        res = []

        # Búscamos si el stem está indexado
        if (stem in self.sindex[field]):

            # Devolvemos la posting list asociada al stem
            res = self.sindex[field][stem]

        return res

        ####################################################
        ## COMPLETAR PARA FUNCIONALIDAD EXTRA DE STEMMING ##
        ####################################################

    # Antonio Martínez Leal
    def get_permuterm(self, term, field='article'):
        """
        NECESARIO PARA LA AMPLIACION DE PERMUTERM

        Devuelve la posting list asociada a un termino utilizando el indice permuterm.

        param:  "term": termino para recuperar la posting list, "term" incluye un comodin (* o ?).
                "field": campo sobre el que se debe recuperar la posting list, solo necesario se se hace la ampliacion de multiples indices

        return: posting list

        """
        res = []
        
        #Comprobamos que se incluye la palabra comodín y cuál es
        if("*" in term or "?" in term):
            pterm = term + "$"
            if "*" in pterm:
                s = "*"
            else:
                s = "?"

            #Realizamos permutaciones hasta que el carácter comodín se encuentra en la última posición
            while pterm[len(pterm)-1]!=s:
                pterm = pterm[1:] + pterm[0]
            
            #Llegados a este punto ya tenemos la palabra que debemos buscar en ptindex
            #Si s=="*"
            if(s == "*"):
                for element in self.ptindex[field].keys():
                    if(element[0:len(pterm)-1] == pterm[0:len(pterm)-1]):
                        res = self.or_posting(res,self.ptindex[field][element])

            #Si s=="?"
            else:
                for element in self.ptindex[field].keys():
                    if(element[0:len(pterm)-1] == pterm[0:len(pterm)-1] and len(element) <= (len(pterm)-1)):
                        res = self.or_posting(res,self.ptindex[field][element])

        return res

        ##################################################
        ## COMPLETAR PARA FUNCIONALIDAD EXTRA PERMUTERM ##
        ##################################################



    # Antonio Martínez Leal
    def reverse_posting(self, p):
        """
        NECESARIO PARA TODAS LAS VERSIONES

        Devuelve una posting list con todas las noticias excepto las contenidas en p.
        Util para resolver las queries con NOT.


        param:  "p": posting list


        return: posting list con todos los newid exceptos los contenidos en p

        """

        #Obtenemos una posting list con todas las noticias diferentes
        res = list(self.news.keys())

        #Procedemos a quitar aquellas que están incluidas en p
        return self.minus_posting(res, p)

        ########################################
        ## COMPLETAR PARA TODAS LAS VERSIONES ##
        ########################################


    # Stéphane Díaz-Alejo León
    def and_posting(self, p1, p2):
        """
        NECESARIO PARA TODAS LAS VERSIONES

        Calcula el AND de dos posting list de forma EFICIENTE

        param:  "p1", "p2": posting lists sobre las que calcular


        return: posting list con los newid incluidos en p1 y p2

        """
        res=[]
        i=0
        j=0

        # Mientras no nos pasemos de la longitud de las posting lists
        while i < len(p1) and j < len(p2):

            # Si es el mismo id de documento en las dos listas lo añadimos y pasamos al siguiente en las dos listas
            if p1[i] == p2[j]:
                res.append(p1[i])
                i += 1
                j += 1
            
            # Si el id de la primera lista es menor, entonces solo avanzamos en esta
            elif p1[i] < p2[j]:
                i += 1

            # Si el id de la segunda lista es menor, entonces solo avanzamos en esta
            else:
                j += 1
        
        return res
        ########################################
        ## COMPLETAR PARA TODAS LAS VERSIONES ##
        ########################################


    # Stéphane Díaz-Alejo León
    def or_posting(self, p1, p2):
        """
        NECESARIO PARA TODAS LAS VERSIONES

        Calcula el OR de dos posting list de forma EFICIENTE

        param:  "p1", "p2": posting lists sobre las que calcular


        return: posting list con los newid incluidos de p1 o p2

        """
        res=[]
        i=0
        j=0

        # Mientras no nos pasemos de la longitud de las posting lists
        while i < len(p1) and j < len(p2):

            # Si es el mismo id de documento en las dos listas lo añadimos y pasamos al siguiente en las dos listas
            if p1[i] == p2[j]:
                res.append(p1[i])
                i += 1
                j += 1

            # Si el id de la primera lista es menor, lo añadimos y entonces avanzamos en esta
            elif p1[i] < p2[j]:
                res.append(p1[i])
                i += 1

            # Si el id de la segunda lista es menor, lo añadimos y entonces avanzamos en esta
            else:
                res.append(p2[j])
                j += 1

        # Si aún quedan documentos en alguna de las dos listas, los añadimos
        while i < len(p1):
            res.append(p1[i])
            i += 1

        while j < len(p2):
            res.append(p2[j])
            j += 1
        
        return res
        ########################################
        ## COMPLETAR PARA TODAS LAS VERSIONES ##
        ########################################

    # Stéphane Díaz-Alejo León
    def minus_posting(self, p1, p2):
        """
        OPCIONAL PARA TODAS LAS VERSIONES

        Calcula el except de dos posting list de forma EFICIENTE.
        Esta funcion se propone por si os es util, no es necesario utilizarla.

        param:  "p1", "p2": posting lists sobre las que calcular


        return: posting list con los newid incluidos de p1 y no en p2

        """
        res=[]
        i=0
        j=0

        # Mientras no nos pasemos de la longitud de las posting lists
        while i < len(p1) and j < len(p2):

            # Si es el mismo id de documento en las dos listas, pasamos al siguiente en las dos listas
            if p1[i] == p2[j]:
                i += 1
                j += 1

            # Si el id de la primera lista es menor, lo añadimos y entonces avanzamos en esta
            elif p1[i] < p2[j]:
                res.append(p1[i])
                i += 1

            # Si el id de la segunda lista es menor, entonces solo avanzamos en esta
            else:
                j += 1

        # Si aún quedan documentos en la primera lista, los añadimos
        while i < len(p1):
            res.append(p1[i])
            i += 1
        
        return res
        ########################################################
        ## COMPLETAR PARA TODAS LAS VERSIONES SI ES NECESARIO ##
        ########################################################





    #####################################
    ###                               ###
    ### PARTE 2.2: MOSTRAR RESULTADOS ###
    ###                               ###
    #####################################

    
    def solve_and_count(self, query):
        """
        NECESARIO PARA TODAS LAS VERSIONES

        Resuelve una consulta y la muestra junto al numero de resultados 

        param:  "query": query que se debe resolver.

        return: el numero de noticias recuperadas, para la opcion -T

        """
        result = self.solve_query(query)
        print("%s\t%d" % (query, len(result)))
        return len(result)  # para verificar los resultados (op: -T)

    # Sergi Albiach Caro
    def solve_and_show(self, query):
        """
        NECESARIO PARA TODAS LAS VERSIONES

        Resuelve una consulta y la muestra informacion de las noticias recuperadas.
        Consideraciones:

        - En funcion del valor de "self.show_snippet" se mostrara una informacion u otra.
        - Si se implementa la opcion de ranking y en funcion del valor de self.use_ranking debera llamar a self.rank_result

        param:  "query": query que se debe resolver.

        return: el numero de noticias recuperadas, para la opcion -T
        
        """
        #Resolem la query
        result = self.solve_query(query)
        if not result: result = []
        
        numero = 0
        n_score = 0
        
        #Si utilitzem el ranking, ordenem les notícies
        if self.use_ranking:
            result = self.rank_result(result, self.term_field)
        
        #Mostrem la query i la quantiat de resultats que hem trobat
        print("Query: {}\nNumber of results: {}".format(query,len(result)))
        
        query = query.lower()
        #Per cada noticia (newsID) en la posting list resultant de la query
        for newsID in result:
            
            #Si utilitzem el ranking, obtenim l'score de la notícia, si no, l'score = 0
            if self.use_ranking: n_score = round(self.weight_doc[newsID],2)
            else: n_score = 0 

            docid = self.news[newsID]['docid'] #document en está la notícia
            docPos = self.news[newsID]['position'] #la posició dins del document
            docPath = self.docs[docid] #el path on está el document

            with open(docPath) as fh:
                jlist = json.load(fh)

            noticia = jlist[docPos]

            n_id = newsID #l'ID de la notícia
            n_data = noticia['date'] #la data
            n_titol = noticia['title'] #el títol
            n_keywords = noticia['keywords'] #les keywords

            numero = numero + 1 #el número de notícia que estem processant [0,len(result)-1]

            
            #Mostrem el número de la notícia, puntuació del ranking, identificador, data, títol, keywords amb format segons opció -N
            if(self.show_snippet):
                print("#{}\nScore: {:.2f}\nNews id: {}\nDate: {}\nTitle: {}\nKeywords: {}".format(numero, n_score, n_id, n_data, n_titol, n_keywords))
            else:
                print("#{}      ({:.2f})  ({})  ({})  {}      ({})".format(numero,n_score,n_id,n_data,n_titol,n_keywords))
            
            #Si volem l'snippet, el calculem i el mostrem per pantalla
            if(self.show_snippet):
                idx = []
                body = noticia['article']
                body = self.tokenize(body)

                #separem els paréntesis de la query
                separada = ""
                for j in  range(0,len(query)):
                    item = query[j]
                    if  item == '(' or item == ')':
                        separada = separada + " " + item + " "
                    else:
                        separada = separada + item
                
                #fem split pels espais
                query_s = separada.split()

                #afegim els index de cada paraula de la query al text
                for term in query_s:
                    for j in range(0,len(body)):
                        if (term != "and" and term != "not" and term != "or" and term != "(" and term != ")" and term == body[j]):
                           idx.append(j)
                           break
                
                idx.sort() #Ordenem els index de menor a major
                n_snippet = ""
                fet = False #Variable que ens indica si hem processat el segon terme (p2)

                for ix in range(0,len(idx)):
                    if (ix < len(idx)-1): # Si no es l'últim índex
                        fet = False
                        p1 = idx[ix]
                        p2 = idx[ix+1]

                        dretaH = 4
                        esquerraH = 3

                        if p2-p1 <= 3: #Si entre els dos termes hi ha menys de 3 paraules
                            if (p1 < 3) : esquerraH = p1
                            if (p2 > len(body)-3) : dretaH = p2

                            fet = True

                            if n_snippet == "":
                                n_snippet = " ".join(body[p1-esquerraH:p2+dretaH])
                            else:
                                n_snippet = n_snippet + " [...] " + " ".join(body[p1-esquerraH:p2+dretaH])
                        
                        else: #Si hi ha més de 3 paraules entre dos termes

                            if (p1 < 3) : esquerraH = p1
                            if (p1 > len(body)-3) : dretaH = p1

                            if n_snippet == "":
                                n_snippet = " ".join(body[p1-esquerraH:p1+dretaH])
                            else:
                                n_snippet = n_snippet + " [...] " + " ".join(body[p1-esquerraH:p1+dretaH])
                    
                    else: #Si processem l'últim índex
                        p1 = idx[len(idx)-1]

                        dretaH = 4
                        esquerraH = 3

                        if (p1 < 3) : esquerraH = p1
                        if (p1 > len(body)-3) : dretaH = p1

                        if (not fet): #Si no l'hem processat ja (en l'if de la línia 1023)
                            if n_snippet == "":
                                n_snippet = " ".join(body[p1-esquerraH:p1+dretaH])
                            else:
                                n_snippet = n_snippet + " [...] " + " ".join(body[p1-esquerraH:p1+dretaH])


                print("Snippet: {}".format(n_snippet))

            print ("------------------------------")

        return len(result)
        ########################################
        ## COMPLETAR PARA TODAS LAS VERSIONES ##
        ########################################



    # Stéphane Díaz-Alejo León
    def rank_result(self, result, query):
        """
        NECESARIO PARA LA AMPLIACION DE RANKING

        Ordena los resultados de una query.

        param:  "result": lista de resultados sin ordenar
                "query": query, puede ser la query original, la query procesada o una lista de terminos


        return: la lista de resultados ordenada

        """
        
        
        # Lista de términos usados en la query
        terms = {}

        for tupla in query.keys():

            # Cogemos el término y el campo
            term = tupla[0]
            field = tupla[1]

            # Si es una consulta permuterm, cogemos los permuterms relacionados y luego los términos
            if ("*" in term or "?" in term):
                pterms = self.get_pterms(term, field)

                for element in pterms:
                    terminos = self.pterms[element]
                    for termino in terminos:
                        terms[(termino, field)] = True

            # Si hemos usado stemming, debemos considerar los términos asociados al stem
            elif self.use_stemming:
                terminos = self.sterms[self.stemmer.stem(term)]
                for termino in terminos:
                    terms[(termino, field)] = True

            # En cualquier otro caso solo añadimos el término y campo
            else:
                terms[(term, field)] = True             

        pesado = []

        # Por cada noticia en el resultado
        for doc in result:
            peso_doc = 0

            # Por cada término y campo
            for tupla in terms:
                term = tupla[0]
                field = tupla[1]
                
                # Calculamos el pesado del término
                tf = 0
                ft = self.weight[field][term].get(doc,0)

                if ft > 0:
                    tf = 1 + math.log10(ft)
                
                # Calculamos la función global idf
                df = len(self.weight[field][term])
                idf = math.log10(self.N/df)

                # El peso de l noticia es la suma del producto de Tf*idf de cada término
                peso_doc = peso_doc + tf*idf
            
            # Añadimos los pesados de cada noticia para luego tomarlos
            self.weight_doc[doc] = self.weight_doc.get(doc, 0) + peso_doc
            pesado.append(peso_doc)
            
        # Ordenamos la lista de noticias en base al pesado
        res = [x for _,x in sorted(zip(pesado,result), reverse=True)]

        return res

        
        ###################################################
        ## COMPLETAR PARA FUNCIONALIDAD EXTRA DE RANKING ##
        ###################################################
