/*****************************************************************************/
/**  Definiciones de constantes y estructuras usadas en la libreria, junto  **/
/**  con los perfiles de las funciones de manipulacion de la TDS.           **/
/**                     Jose Miguel Benedi, 2020-2019 <jbenedi@dsic.upv.es> **/
/*****************************************************************************/
/*****************************************************************************/
#ifndef _LIBTDS_H
#define _LIBTDS_H

/*************************************** Constantes para los tipos en la TdS */
#define T_VACIO       0
#define T_ENTERO      1
#define T_LOGICO      2
#define T_ARRAY       3
#define T_ERROR       4
/**************************************************** Categorias para la TdS */
#define NULO          0
#define VARIABLE      1
#define FUNCION       2
#define PARAMETRO     3

typedef struct simb /******************************** Estructura para la TdS */
{
  int   t;               /* Tipo del objeto                                  */
  int   n;               /* Nivel "global" o "local"                         */
  int   d;               /* Desplazamiento relativo en el segmento variables */
  int   ref;             /* Campo de referencia de usos multiples            */
} SIMB;
typedef struct dim  /* Estructura para la informacion obtenida de la TDArray */
{
  int   telem;                                      /* Tipo de los elementos */
  int   nelem;                                      /* Numero de elementos   */
} DIM;
typedef struct inf  /* Estructura para las funciones                         */
{
  char *nom;                             /* Nombre de la funcion             */
  int   tipo;                            /* Tipo del rango de la funcion     */
  int   tsp;                             /* Talla del segmento de parametros */
}INF;

/*************************** Variables globales de uso en todo el compilador */
int dvar;                     /* Desplazamiento en el Segmento de Variables  */
int niv;                      /* Nivel de anidamiento "global" o "local"     */

/************************************* Operaciones para la gestion de la TDS */
void cargaContexto (int n) ;
/* Crea el contexto necesario para los objetos globales y para los objetos 
   locales a las funciones                                                   */
void descargaContexto (int n) ;
/* Libera en la TDB y la TDS el contexto asociado con la funcion.            */

int insTdS(char *nom, int cat, int tipo, int n, int desp, int ref) ;
/* Inserta en la TDS toda la informacion asociada con una variable de nombre,
   "nom"; categoria, "cat"; tipo, "tipo"; nivel del bloque, "n"; desplaza-
   miento relativo en el segmento de variables, "desp" y referencia, "ref", 
   a posibles subtablas de vectores o dominios, siendo (-1) si es de tipo 
   simple). Si la variable ya existe devuelve el valor "FALSE=0" 
   ("TRUE=1" en caso contrario).                                             */
SIMB obtTdS(char *nom) ;
/* Obtiene toda la informacion asociada con un objeto de nombre, "nom", y la
   devuelve en una estructura de tipo "SIMB" (ver "libtds.h"). Si el objeto 
   no está declarado, devuelve "T_ERROR" en el campo "tipo".                 */

int insTdA(int telem, int nelem) ;
/* Inserta en la Tabla de Arrays la informacion de un array con elementos de 
   tipo, "telem"; y numero de elementos, "nelem". Devuelve su referencia en 
   la Tabla de Arrays.                                                       */
DIM obtTdA(int ref) ;
/* Devuelve toda la informacion asociada con un array referenciado por "ref" 
   en la Tabla de Arrays. En caso de error devuelve "T_ERROR" en el campo 
   "telem".                                                                  */

int insTdD (int refe, int tipo) ;
/* Para un dominio existente referenciado por "refe", inserta en la Tabla 
   de Dominios la informacion del "tipo" del parametro.  Si "ref= -1" entonces
   crea una nueva entrada en la tabla de dominios para el tipo de este 
   parametro y devuelve su referencia.  Si la funcion no tiene parametros, 
   debe crearse un dominio vacio con: "refe = -1" y "tipo = T_VACIO".       */
INF obtTdD (int refe) ;
/* Si "refe<0" entonces devuelve la informacion de la funcion actual, y si
   "refe>=0", devuelve la informacion de una función ya compilada con 
   referencia "refe". La informacion es: el nombre y el tipo del rango de la 
   funcion y la talla del segmento de parametros. Si "refe" no se corresponde
   con una funcion ya compilada, el campo "tipo = T_ERROR".                  */
int cmpDom (int refx, int refy) ;
/* Si los dominios referenciados por "refx" y "refy" no coinciden devuelve 
   "FALSE=0" ("TRUE=1" si son iguales).                                      */

void mostrarTdS () ;
/* Muestra toda la informacion de la TDS para objetos globales y locales.
   Se recomienda hacerlo (si "verTdS = true") al finalizar la compilación
   de la funciom, justo antes de ("descargarContexto").                      */

#endif  /* _LIBTDS_H */
/*****************************************************************************/
