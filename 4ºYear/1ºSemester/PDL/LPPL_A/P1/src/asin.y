/*****************************************************************************/
/**  Ejemplo de BISON-I: S E M - 2          2019-2020 <jbenedi@dsic.upv.es> **/
/*****************************************************************************/
%{
#include <stdio.h>
#include <string.h>
#include "header.h"
%}

%token
/** Palabras reservadas */
BOOL_ INT_ READ_ PRINT_ IF_ ELSE_ FOR_ RETURN_ FALSE_ TRUE_

/** Operadores */
ASIG_ AND_ OR_ NOT_ IGUAL_ DIST_ MY_ MYIG_ MN_ MNIG_ MAS_ MENOS_ POR_ DIV_ MAS2_ MENOS2_

/** Separadores */
AL_ CL_ AC_ CC_ AP_ CP_ PCOMA_ COMA_

/** Tokens con atributos */
%token ID_ 
%token CTE_ 

%%

programa  
        : listaDeclaraciones
        ;
listaDeclaraciones  
        : declaracion
        | listaDeclaraciones declaracion
        ;
declaracion
        : declaracionVariable
        | declaracionFuncion
        ;
declaracionVariable
        : tipoSimple ID_ PCOMA_
        | tipoSimple ID_ AC_ CTE_ CC_ PCOMA_
tipoSimple
        : INT_
        | BOOL_
        ;
declaracionFuncion
        : cabeceraFuncion bloque
        ;
cabeceraFuncion
        : tipoSimple ID_ AP_ parametrosFormales CP_
        ;
parametrosFormales
        : /* cadena vacia */
        | listaParametrosFormales 
        ;
listaParametrosFormales
        : tipoSimple ID_
        | tipoSimple ID_ COMA_ listaParametrosFormales
        ;
bloque
        : AL_ declaracionVariableLocal listaInstrucciones RETURN_ expresion PCOMA_ CL_
        ;
declaracionVariableLocal
        : /* cadena vacia */
        | declaracionVariableLocal declaracionVariable
        ;
listaInstrucciones
        : /* instruccion vacia */
        | listaInstrucciones instruccion
        ;
instruccion
        : AL_ listaInstrucciones CL_
        | instruccionAsignacion
        | instruccionSeleccion
        | instruccionEntradaSalida     
        | instruccionIteracion
        ;
instruccionAsignacion
        : ID_ ASIG_ expresion PCOMA_
        | ID_ AC_ expresion CC_ ASIG_ expresion PCOMA_
        ;
instruccionEntradaSalida
        : READ_ AP_ ID_ CP_ PCOMA_
        | PRINT_ AP_ expresion CP_ PCOMA_
        ;
instruccionSeleccion
        : IF_ AP_ expresion CP_ instruccion ELSE_ instruccion
        ;
instruccionIteracion
        : FOR_ AP_ expresionOpcional PCOMA_ expresion PCOMA_ expresionOpcional CP_ instruccion
        ;
expresionOpcional
        : /* expresion opcional vacia */
        | expresion
        | ID_ ASIG_ expresion
        ;
expresion
        : expresionIgualdad
        | expresion operadorLogico expresionIgualdad
        ;
expresionIgualdad
        : expresionRelacional
        | expresionIgualdad operadorIgualdad expresionRelacional
        ;
expresionRelacional
        : expresionAditiva
        | expresionRelacional operadorRelacional expresionAditiva
        ;
expresionAditiva
        : expresionMultiplicativa
        | expresionAditiva operadorAditivo expresionMultiplicativa
        ;
expresionMultiplicativa
        : expresionUnaria
        | expresionMultiplicativa operadorMultiplicativo expresionUnaria
expresionUnaria
        : expresionSufija
        | operadorUnario expresionUnaria
        | operadorIncremento ID_
        ;
expresionSufija
        : AP_ expresion CP_ 
        | ID_ operadorIncremento
        | ID_ AC_ expresion CC_
        | ID_ AP_ parametrosActuales CP_ 
        | ID_
        | constante
        ;
parametrosActuales
        : /* cadena vacia */
        | listaParametrosActuales
        ;
listaParametrosActuales
        : expresion
        | expresion COMA_ listaParametrosActuales
        ;
constante
        : CTE_
        | TRUE_
        | FALSE_
        ; 
operadorLogico
        : AND_
        | OR_
        ;
operadorIgualdad
        : IGUAL_
        | DIST_
        ;
operadorRelacional
        : MY_
        | MN_
        | MYIG_
        | MNIG_
        ;
operadorAditivo
        : MAS_
        | MENOS_
        ;
operadorMultiplicativo
        : POR_
        | DIV_
        ;
operadorUnario 
        : MAS_
        | MENOS_
        | NOT_
        ;
operadorIncremento
        : MAS2_
        | MENOS2_
        ;
%%