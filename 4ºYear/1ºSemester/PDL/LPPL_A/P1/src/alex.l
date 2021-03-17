/*****************************************************************************/
/**  Ejemplo de FLEX: S E M - 1                       <jbenedi@dsic.upv.es> **/
/**  V. 20                                                                  **/
/*****************************************************************************/
%{
#include <stdio.h>
#include <string.h>
#include "header.h"
#include "asin.h"


#define  retornar(x) {if (verbosidad) ECHO; return x;}
%}
/*----------------------------------------------------------------------
  Dado que las funciones "input()" y "unput(c)" son costosas y no las
  utilizaremos, evitaremos que se generen automaticamente sus rutinas
  correspondientes desactivandolas mediante las siguientes opciones:
----------------------------------------------------------------------*/
%option noinput
%option nounput
%option yylineno

delimitador     [ \t\v]+
identificador   [_a-zA-Z][_a-zA-Z0-9]*
numero          [0-9]+("."[0-9]*)?|[0-9]*"."[0-9]+
comentario      \/\/.*\n 

%%

{delimitador}   {if (verbosidad) ECHO ; }
"\n"            {if (verbosidad) {ECHO; fprintf(stdout, "%3d.- ", yylineno); } } 
{comentario}    {if (verbosidad) {ECHO; fprintf(stdout, "%3d.- ", yylineno); } }  

bool            { retornar(BOOL_);  } 
int             { retornar(INT_);   } 
read            { retornar(READ_);  } 
print           { retornar(PRINT_); } 
if              { retornar(IF_);    } 
else            { retornar(ELSE_);  } 
for             { retornar(FOR_);   } 
return          { retornar(RETURN_);} 
false           { retornar(FALSE_); } 
true            { retornar(TRUE_);  } 

"="             { retornar(ASIG_);  } 
"&&"            { retornar(AND_);   } 
"||"            { retornar(OR_);    } 
"!"             { retornar(NOT_);   } 
"=="            { retornar(IGUAL_); } 
"!="            { retornar(DIST_);  } 
">"             { retornar(MY_);    } 
">="            { retornar(MYIG_);  } 
"<"             { retornar(MN_);    } 
"<="            { retornar(MNIG_);  } 
"+"             { retornar(MAS_);   } 
"-"             { retornar(MENOS_); } 
"*"             { retornar(POR_);   } 
"/"             { retornar(DIV_);   } 
"++"            { retornar(MAS2_);  } 
"--"            { retornar(MENOS2_);} 

"{"             { retornar(AL_);    }
"}"             { retornar(CL_);    }
"["             { retornar(AC_);    }
"]"             { retornar(CC_);    }
"("             { retornar(AP_);    }
")"             { retornar(CP_);    }
";"             { retornar(PCOMA_); }
","             { retornar(COMA_);  }

{numero}        { retornar(CTE_);   }
{identificador} { retornar(ID_);    } 

<<EOF>>         { if (verbosidad) { fprintf(stdout, "\n"); } return 0; } 
.               { if (verbosidad) { 
                  fprintf(stdout, "<"); ECHO; fprintf(stdout, "> "); } 
                  yyerror("Caracter desconocido");
                } 
%%