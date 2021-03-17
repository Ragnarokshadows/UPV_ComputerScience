/*****************************************************************************/
/**   Ejemplo de un posible fichero de cabeceras donde situar las           **/
/** definiciones de constantes, variables y estructuras para MenosC.        **/
/** Los alumos deberan adaptarlo al desarrollo de su propio compilador.     **/
/*****************************************************************************/
#ifndef _HEADER_H
#define _HEADER_H

typedef struct lpf /****** Estructura para las listaParametros formales */
{              
  int talla;        
  int ref;         
}LPF;

typedef struct exp { /****** Estructura para las expresiones */ 
    int valor;
    int tipo;
    int valid;
} EXP;

/****************************************************** Constantes generales */
#define TRUE  1
#define FALSE 0

/***************************************************** Constantes simbólicas */
#define TALLA_TIPO_SIMPLE 1 /* Talla asociada a los tipos simples */
#define TALLA_SEGENLACES 2 /* Talla del segmento de Enlaces de Control */

/***************************************************** Constantes operadores */
/* Operador Logico*/
#define OP_AND 0
#define OP_OR  1
/* Operador igualdad */
#define OP_IGUAL    0
#define OP_DIST     1
/* Operador relacional */
#define OP_MY   0
#define OP_MYIG 1
#define OP_MN   2
#define OP_MNIG 3
/* Operador aditivo */
#define OP_MAS      0
#define OP_MENOS    1
/* Operador multiplicativo */
#define OP_POR  0
#define OP_DIV  1
/* Operador unario */
#define OP_MAS   0
#define OP_MENOS 1
#define OP_NOT   2
/* Operador incremento */
#define OP_MAS2     0
#define OP_MENOS2   1
/************************************************************ Error messages */
/* Variables */
#define E_UNDECLARED            "La variable no ha sido declarada"
#define E_REPEATED_DECLARATION  "La variable no puede ser declarada dos veces"
#define E_ARRAY_SIZE_INVALID    "La talla del array no es valida"
#define E_ARRAY_INDEX_INVALID   "El indice es invalido"
#define E_ARRAY_INDEX_TYPE      "El indice debe ser entero"
#define E_ARRAY_WO_INDEX        "El array solo puede ser accedido con indices"
#define E_VAR_WITH_INDEX        "La variable no es un array, no puede ser accedida con indices"
#define E_VAR_WITH_CALL					"La variable no es una funcion, no puede ser llamada con parentesis"

/* Estructuras de control y loops */
#define E_IF_LOGICAL            "La expresion del if debe ser logica"
#define E_WHILE_LOGICAL         "La expresion del while debe ser logica"
#define E_FOR_LOGICAL           "La expresion del for debe ser logica"

/* Tipos */
#define E_TYPES_ASIGNACION      "Tipos no coinciden en asignacion a variable"
#define E_TYPES_LOGICA          "Tipos no coinciden en operacion logica"
#define E_TYPE_MISMATCH         "Los tipos no coinciden"
#define E_TYPE_RETURN_NS        "El return debe ser de tipo simple"

/************************************* Variables externas definidas en el AL */
extern int yylex();
extern int yyparse();

extern FILE *yyin;                           /* Fichero de entrada           */
extern int   yylineno;                       /* Contador del numero de linea */
extern char *yytext;                         /* Patron detectado             */
/********* Funciones y variables externas definidas en el Programa Principal */
extern void yyerror(const char * msg) ;   /* Tratamiento de errores          */

extern int verbosidad;                   /* Flag si se desea una traza       */
extern int numErrores;              /* Contador del numero de errores        */

/************************ Variables externas definidas en Programa Principal */
extern int verTdS; /* Flag para saber si mostrar la TdS */

/***************************** Variables externas definidas en las librerías */
extern int dvar; /* Desplazamiento en el Segmento de Variables */
extern int niv; /* Nivel de anidamiento "global" o "local" */

#endif  /* _HEADER_H */
/*****************************************************************************/
