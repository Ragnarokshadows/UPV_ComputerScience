/*****************************************************************************/
/*  Programa principal y tratamiento de errores.                             */
/*                       Jose Miguel Benedi, 2020-2021 <jbenedi@dsic.upv.es> */
/*****************************************************************************/
#include <stdio.h>
#include <string.h>
#include "header.h"
#include "libgci.h"

int verTdS=FALSE;                        /* Flag para mostrar la TDS         */
int verbosidad=FALSE;                    /* Flag si se desea una traza       */
int numErrores=0;                   /* Contador del numero de errores        */
/*****************************************************************************/
void yyerror(const char * msg)
/*  Tratamiento de errores.                                                  */
{
  numErrores++;  fflush(stdout);
  fprintf(stdout, "\nError en %d: %s\n", yylineno, msg);
}
/*****************************************************************************/
int main (int argc, char **argv) 
/* Gestiona la linea de comandos e invoca al analizador sintactico-semantico.*/
{ int i, n = 1; char *nom_fich;

  for (i=1; i<argc; ++i) { 
    if (strcmp(argv[i], "-v")==0) { verbosidad = TRUE; n++; }
    else if (strcmp(argv[i], "-t")==0) { verTdS = TRUE; n++; }
  }
  if (argc == n+1) {
    if ((yyin = fopen (argv[n], "r")) == NULL) {
      fprintf (stderr, "El fichero '%s' no es valido\n", argv[n]);
      fprintf (stderr, "Uso: cmc [-v] fichero\n");
    }
    else {        
      if (verbosidad == TRUE) fprintf(stdout,"%3d.- ", yylineno);
      nom_fich = argv[n];
      yyparse ();
      if (numErrores == 0) volcarCodigo(nom_fich);
      else fprintf(stderr,"\nNumero de errores:      %d\n", numErrores);
    }   
  }
  else fprintf (stderr, "Uso: cmc [-v] [-t] fichero\n");

  return (0);
} 
/*****************************************************************************/
