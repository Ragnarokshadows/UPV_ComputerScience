/*****************************************************************************/
/**  Definiciones de constantes y estructuras usadas en la libreria, junto  **/
/**  con los perfiles de las funciones de ayuda para la GCI.                **/
/**                     Jose Miguel Benedi, 2019-2020 <jbenedi@dsic.upv.es> **/
/*****************************************************************************/
/*****************************************************************************/
#ifndef _LIBGCI_H
#define _LIBGCI_H

/********* Constantes para el tipo de los argumentos de las instrucciones 3D */
#define ARG_ENTERO    0
#define ARG_POSICION  1
#define ARG_ETIQUETA  2
#define ARG_NULO      3
/********************************* Instrucciones del Codigo Tres Direcciones */
#define ESUM          0
#define EDIF          1
#define EMULT         2
#define EDIVI         3
#define RESTO         4
#define ESIG          5
#define EASIG         6
#define GOTOS         7
#define EIGUAL        8
#define EDIST         9
#define EMEN         10
#define EMAY         11
#define EMAYEQ       12
#define EMENEQ       13
#define EAV          14
#define EVA          15
#define EREAD        16
#define EWRITE       17
#define FIN          18
#define RET          19
#define CALL         20
#define EPUSH        21
#define EPOP         22
#define PUSHFP       23
#define FPPOP        24
#define FPTOP        25
#define TOPFP        26
#define INCTOP       27
#define DECTOP       28
/*************************** Variables globales de uso en todo el compilador */
int si;                       /* Desplazamiento en el Segmento de Codigo     */

/*****************************************************************************/
typedef struct tipo_arg /****** Estructura para los argumentos del codigo 3D */
{              
  int tipo;      /* Tipo del argumento: entero, posicion, etiqueta o nulo    */
  int nivel;     /* Nivel (local o global) en caso de tipo posicion          */
  int val;       /* Valor argumento: entero, desplazamiento, etiqueta o nulo */
}TIPO_ARG;

/*************** Funciones para crear los argumentos de las instrucciones 3D */
TIPO_ARG crArgNul () ;
/* Crea el argumento de una instruccion tres direcciones de tipo nulo.       */
TIPO_ARG crArgEnt (int valor) ;
/* Crea el argumento de una instruccion tres direcciones de tipo entero 
   con la informacion de la constante entera dada en "valor".                */
TIPO_ARG crArgEtq (int valor) ;
/*  Crea el argumento de una instruccion tres direcciones de tipo etiqueta 
    con la informacion de la direccion dada en "valor".                      */
TIPO_ARG crArgPos (int n, int valor) ;
/*  Crea el argumento de una instruccion tres direcciones de tipo posicion 
    con la informacion del nivel "n" y del desplazamiento en "valor".        */

/******************************** Funciones para la manipulacion de las LANS */
int creaLans (int d);
/* Crea una lista de argumentos no satisfechos para una instruccion
   incompleta cuya direccion es "d" y devuelve su referencia.                */
int fusionaLans (int x, int y);
/* Fusiona dos listas de argumentos no satisfechos cuyas referencias
   son "x" e "y" y devuelve la referencia de la lista fusionada.             */
void completaLans (int x, TIPO_ARG arg);
/* Completa con el argumento "arg" el campo "res" de todas las instrucciones 
   incompletas de la lista "x".                                              */

void emite (int cop, TIPO_ARG arg1, TIPO_ARG arg2, TIPO_ARG res);
/* Crea una instruccion tres direcciones con el codigo de operacion "cod" y 
   los argumentos "arg1", "arg2" y "res", y la pone en la siguiente posicion 
   libre (indicada por "si") del Segmento de Codigo. A continuacion, 
   incrementa "si".                                                          */

int creaVarTemp ();
/*  Crea una variable temporal de tipo simple (TALLA_TIPO_SIMPLE = 1), en el 
    segmento de variables (indicado por "dvar") y devuelve su desplazamiento 
    relativo. A continuacón, incrementa "dvar".                              */

void volcarCodigo(char *nom) ;
/* Vuelca (en modo texto) el codigo generado en un fichero cuyo nombre es el 
   del fichero de entrada con la extension ".c3d".                           */

#endif  /* _LIBGCI_H */
/*****************************************************************************/
