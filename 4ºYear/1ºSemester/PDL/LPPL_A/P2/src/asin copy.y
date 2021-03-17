/*****************************************************************************/
/**  Ejemplo de BISON-I: S E M - 2          2019-2020 <jbenedi@dsic.upv.es> **/
/*****************************************************************************/
%{
#include <stdio.h>
#include <string.h>
#include "header.h"
#include "libtds.h"
%}

%union{ 
    char* ident; 
    int cent; 
    LPF lpf;
    EXP exp;
}

%token
/** Palabras reservadas */
BOOL_ INT_ READ_ PRINT_ IF_ ELSE_ FOR_ RETURN_ FALSE_ TRUE_

/** Operadores */
ASIG_ AND_ OR_ NOT_ IGUAL_ DIST_ MY_ MYIG_ MN_ MNIG_ MAS_ MENOS_ POR_ DIV_ MAS2_ MENOS2_

/** Separadores */
AL_ CL_ AC_ CC_ AP_ CP_ PCOMA_ COMA_

/** Tokens con atributos */
%token<cent> CTE_ 
%token<ident> ID_

%type<cent> tipoSimple operadorUnario operadorIncremento operadorMultiplicativo constante
%type<cent> operadorAditivo operadorRelacional operadorIgualdad cabeceraFuncion
%type<cent> operadorLogico parametrosFormales declaracionVariable declaracionFuncion declaracion
%type<cent> instruccionAsignacion listaDeclaraciones

%type<lpf> listaParametrosFormales

%type <exp> expresionMultiplicativa expresionAditiva expresionSufija expresionUnaria 
%type <exp> expresion expresionRelacional expresionIgualdad expresionOpcional

%%
/** Hecho */
programa  
        : 
        {
            dvar = 0; 
            niv = 0;
            cargaContexto(niv);
        } 
        listaDeclaraciones
        {
                if($2 == 0){yyerror("Se debe declarar al menos una funcion main()");}
        }
        ;
/** Hecho */
listaDeclaraciones  
        : declaracion{$$ = $1;} 
        | listaDeclaraciones declaracion{$$ = $1 + $2;} 
        ;
declaracion
        : declaracionVariable   { $$ = 0; } 
        | declaracionFuncion    { $$ = $1; } 
        ;
/** Hecho */
declaracionVariable
        : tipoSimple ID_ PCOMA_
            {
                if(! insTdS($2, VARIABLE, $1, niv, dvar, -1))
                    yyerror(E_REPEATED_DECLARATION);
                else 
                    dvar += TALLA_TIPO_SIMPLE;
            } 
        | tipoSimple ID_ AC_ CTE_ CC_ PCOMA_
        { 
            int numelem = $4; int ref;
            if (numelem <= 0) {
                yyerror(E_ARRAY_SIZE_INVALID);
                numelem = 0;
            }
            ref = insTdA($1, numelem);
            if (!insTdS($2, VARIABLE, T_ARRAY, niv, dvar, ref))
                yyerror(E_REPEATED_DECLARATION);
            else
                dvar += numelem * TALLA_TIPO_SIMPLE; 
        }
        ;
/** Hecho **/      
tipoSimple
        : INT_      { $$ = T_ENTERO; }
        | BOOL_     { $$ = T_LOGICO; }
        ;
/** Hecho **/  
declaracionFuncion
        : cabeceraFuncion {$<cent>$=dvar; dvar = 0;} bloque { if(verTdS) mostrarTdS(); descargaContexto(niv); niv=0; dvar=$<cent>2;}
        {
                $$=$1;

        }         ;
/** Hecho **/  
cabeceraFuncion
        : tipoSimple ID_ {niv=1; cargaContexto(niv);} AP_ parametrosFormales CP_ 
        {
            if(!insTdS($2, FUNCION, $1, 0, -1, $5)){
                yyerror(E_REPEATED_DECLARATION);
            }
            if(strcmp($2, "main\0")==0) $$=-1; else $$=0;
            
        } 
        ;
/** Hecho **/ 
parametrosFormales
        : /* cadena vacia */            { $$ = insTdD(-1, T_VACIO); } 
        | listaParametrosFormales       { $$ = $1.ref; } 
        ;
/** Hecho **/ 
listaParametrosFormales
        : tipoSimple ID_
        {
            $$.ref = insTdD(-1, $1);
            int talla = TALLA_TIPO_SIMPLE + TALLA_SEGENLACES;
            $$.talla = talla;
            if(!insTdS($2, PARAMETRO, $1, niv, -talla, -1))
                yyerror(E_REPEATED_DECLARATION);
        } 
        | tipoSimple ID_ COMA_ listaParametrosFormales 
        {
            int talla = $4.talla + TALLA_TIPO_SIMPLE;
            $$.talla = talla;
            $$.ref = insTdD($4.ref, $1);
            if(!insTdS($2, PARAMETRO, $1, niv, -talla, -1))
                yyerror(E_REPEATED_DECLARATION);
            
        } 
        ;
bloque
        : AL_ declaracionVariableLocal listaInstrucciones RETURN_ expresion PCOMA_ CL_
        {
          INF inf = obtTdD(-1);
          if (inf.tipo != T_ERROR){
                if (inf.tipo != $5.tipo){ yyerror(E_TYPE_RETURN_NS); }
          }
        }
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
       {
         SIMB sim = obtTdS($1);
         $$ = T_ERROR;
         if ($3.tipo != T_ERROR)
        {
          if (sim.t == T_ERROR){ yyerror(E_UNDECLARED); }
          else if (sim.t == T_ARRAY){ yyerror(E_ARRAY_WO_INDEX); }
          else if (sim.t != $3.tipo){ yyerror(E_TYPES_ASIGNACION); }
          else
         {
           $$ = $3.tipo;
         }  
        } 
       } 
        | ID_ AC_ expresion CC_ ASIG_ expresion PCOMA_
       {
         SIMB sim = obtTdS($1);
         $$ = T_ERROR;
         if ($3.tipo != T_ERROR && $6.tipo != T_ERROR)
        {
          if (sim.t == T_ERROR){ yyerror(E_UNDECLARED); }
          else if (sim.t != T_ARRAY){ yyerror(E_VAR_WITH_INDEX); }
          else if ($3.tipo != T_ENTERO){ yyerror(E_ARRAY_INDEX_TYPE); }
          else
         {
           DIM dim = obtTdA(sim.ref);
           if (dim.telem != $6.tipo){ yyerror(E_TYPES_ASIGNACION); }
           else
          {
            $$ = $3.tipo;
          } 
         }  
        } 
       } 
        ;
instruccionEntradaSalida
        : READ_ AP_ ID_ CP_ PCOMA_
       {
         SIMB sim = obtTdS($3);
         if (sim.t == T_ERROR){ yyerror(E_UNDECLARED); }
         else if (sim.t != T_ENTERO){ yyerror("Read requiere una entrada entera"); }
       } 
        | PRINT_ AP_ expresion CP_ PCOMA_
       {
         if ($3.tipo != T_ENTERO){ yyerror("Print requiere una entrada entera"); } 
       } 
        ;
instruccionSeleccion
        : IF_ AP_ expresion CP_
       {
         if ($3.tipo != T_ERROR && $3.tipo != T_LOGICO){ yyerror(E_IF_LOGICAL); } 
       }
        instruccion ELSE_ instruccion
        ;
instruccionIteracion
        : FOR_ AP_ expresionOpcional PCOMA_ expresion PCOMA_ expresionOpcional CP_
       {
         if ($5.tipo != T_ERROR && $5.tipo != T_LOGICO){ yyerror(E_FOR_LOGICAL); }
       } 
        instruccion
        ;

expresionOpcional
        : /* expresion opcional vacia */ { $$.tipo = T_VACIO; }   
        | expresion { $$.tipo = $1.tipo; } 
        | ID_ ASIG_ expresion
        {
            SIMB sim = obtTdS($1);
            $$.tipo = T_ERROR;
            if ($3.tipo != T_ERROR)
            {
                if (sim.t == T_ERROR){ yyerror(E_UNDECLARED); }
                else if (sim.t == T_ARRAY){ yyerror(E_ARRAY_WO_INDEX); }
                else if (sim.t != $3.tipo){ yyerror(E_TYPES_ASIGNACION); }
                else { $$.tipo = $3.tipo; } 
            } 
        } 
        ;
expresion
        : expresionIgualdad { $$.tipo = $1.tipo; }  
        | expresion operadorLogico expresionIgualdad
        {   $$.tipo = T_ERROR;
            if ($1.tipo != T_ERROR && $3.tipo != T_ERROR) 
            {
                if ($1.tipo != $3.tipo) {
                    yyerror(E_TYPES_LOGICA);
                } else if ($1.tipo != T_LOGICO) {
                    yyerror("Operacion logica invalida para no booleanos");
                } else {
                    $$.tipo = T_LOGICO;
                }
            } 
        }
        ;
expresionIgualdad
        : expresionRelacional { $$.tipo = $1.tipo; } 
        | expresionIgualdad operadorIgualdad expresionRelacional
        {   $$.tipo = T_ERROR;
            if ($1.tipo != T_ERROR && $3.tipo != T_ERROR) {
                if ($1.tipo != $3.tipo) {
                    yyerror(E_TYPE_MISMATCH);
                } else if ($1.tipo == T_ARRAY) {
                    yyerror("Operacion de igualdad no existe para arrays");
                } else {
                    $$.tipo = T_LOGICO;
                }
            } 
        }
        ;
expresionRelacional
        : expresionAditiva { $$.tipo = $1.tipo; } 
        | expresionRelacional operadorRelacional expresionAditiva
        {   $$.tipo = T_ERROR;
            if ($1.tipo != T_ERROR && $3.tipo != T_ERROR) {
                if ($1.tipo != $3.tipo) {
                    yyerror(E_TYPE_MISMATCH);
                } else if ($1.tipo == T_LOGICO) {
                    yyerror("Operacion relacional solo acepta argumentos lógicos");
                } else {
                    $$.tipo = T_LOGICO;
                }
            } 
        }
        ;
expresionAditiva
        : expresionMultiplicativa{ $$.tipo = $1.tipo; }  
        | expresionAditiva operadorAditivo expresionMultiplicativa
       {
        $$.tipo=T_ERROR;
        if ($1.tipo!=T_ERROR && $3.tipo != T_ERROR){
            if ($1.tipo != $3.tipo){
                yyerror(E_TYPE_MISMATCH);
            }else if ($1.tipo != T_ENTERO){
                yyerror("Operacion aditiva solo acepta argumentos enteros");
            } else {
                $$.tipo = T_ENTERO;
            } 
        }
       } 
        ;
expresionMultiplicativa 
        : expresionUnaria   {$$.tipo = $1.tipo;}    
        | expresionMultiplicativa operadorMultiplicativo expresionUnaria
        {
        $$.tipo=T_ERROR;
        if ($1.tipo!=T_ERROR && $3.tipo != T_ERROR){
            if ($1.tipo != $3.tipo){
                yyerror(E_TYPE_MISMATCH);
            }else if ($1.tipo != T_ENTERO){
                yyerror("Operacion multiplicativa solo acepta argumentos enteros");
            } else {
                $$.tipo = T_ENTERO;
                if ($2 == OP_DIV) {
                        if ($3.valor == 0) {
                            $$.tipo = T_ERROR;
                            yyerror("Division entre 0");
                        }
                } 
            } 
        }
        }
expresionUnaria
        : expresionSufija   {$$.tipo = $1.tipo;}  
        | operadorUnario expresionUnaria
        {
        $$.tipo=T_ERROR;
        if ($2.tipo!=T_ERROR){
            if ($2.tipo == T_ENTERO){
                if ($1 == OP_NOT) {
                    yyerror("Operacion \"!\" invalida en expresion entera");
                } else {
                    $$.tipo = T_ENTERO;
                }
            }  

        }else if ($2.tipo == T_LOGICO) {
                if ($1 == OP_NOT) {
                    $$.tipo = T_LOGICO;
                } else {
                    yyerror("Operacion entera invalida en expresion logica");
                }
        }
        }  
        | operadorIncremento ID_
        {
        SIMB simb = obtTdS($2);
        $$.tipo = T_ERROR;

        if (simb.t == T_ERROR)
            yyerror(E_UNDECLARED);
        else if (simb.t == T_ARRAY)
            yyerror(E_ARRAY_WO_INDEX);
        else
            $$.tipo = simb.t;
        } 
        ;
expresionSufija
        : AP_ expresion CP_                 { $$.tipo = $2.tipo;  }     
        | ID_ operadorIncremento
       {
          SIMB sim = obtTdS($1); 
          $$.tipo = T_ERROR;
          if(sim.t == T_ERROR) {   yyerror(E_UNDECLARED); }
          else if (sim.t == T_ARRAY){ yyerror(E_ARRAY_WO_INDEX); }
          else{ $$.tipo = sim.t; }
       } 
        | ID_ AC_ expresion CC_
       {
          SIMB sim = obtTdS($1); 
          $$.tipo = T_ERROR;
			    if(sim.t == T_ERROR) {   yyerror(E_UNDECLARED); }
          else if (sim.t != T_ARRAY)  { yyerror(E_VAR_WITH_INDEX); } 
			    else {
            DIM dim = obtTdA(sim.ref);
            $$.tipo = dim.telem;
          } 
       } 
        | ID_ AP_ parametrosActuales CP_ 
       {
          SIMB sim = obtTdS($1); 
          $$.tipo = T_ERROR;
          INF inf = obtTdD(sim.ref);
          if (sim.t == T_ERROR){ yyerror(E_UNDECLARED); }
          else if (inf.tipo == T_ERROR){ yyerror(E_VAR_WITH_CALL); }
          else {
            $$.tipo = inf.tipo;
          } 
       } 
        | ID_                               
        { 
            SIMB sim = obtTdS($1); 
            $$.tipo = T_ERROR;
			      if(sim.t == T_ERROR) {   yyerror(E_UNDECLARED); }
            else if (sim.t == T_ARRAY)  { yyerror(E_ARRAY_WO_INDEX); } 
			      else {  $$.tipo = sim.t;  } 
        } 
        | constante                         { $$.tipo = $1;  }
        ;
parametrosActuales
        : /* cadena vacia */
        | listaParametrosActuales
        ;
listaParametrosActuales
        : expresion
        | expresion COMA_ listaParametrosActuales
        ;
/** HECHO **/
constante
        : CTE_      { $$ = T_ENTERO;    }  
        | TRUE_     { $$ = T_LOGICO;    }  
        | FALSE_    { $$ = T_LOGICO;    }  
        ; 
operadorLogico
        : AND_      { $$ = OP_AND;      } 
        | OR_       { $$ = OP_OR;       } 
        ;
operadorIgualdad
        : IGUAL_    { $$ = OP_IGUAL;    } 
        | DIST_     { $$ = OP_DIST;     } 
        ;
operadorRelacional
        : MY_       { $$ = OP_MY;       }   
        | MN_       { $$ = OP_MN;       } 
        | MYIG_     { $$ = OP_MYIG;     } 
        | MNIG_     { $$ = OP_MNIG;     } 
        ;
operadorAditivo
        : MAS_      { $$ = OP_MAS;      } 
        | MENOS_    { $$ = OP_MENOS;    } 
        ;
operadorMultiplicativo
        : POR_      { $$ = OP_POR;      } 
        | DIV_      { $$ = OP_DIV;      } 
        ;
operadorUnario 
        : MAS_      { $$ = OP_MAS;      } 
        | MENOS_    { $$ = OP_MENOS;    } 
        | NOT_      { $$ = OP_NOT;      } 
        ;
operadorIncremento
        : MAS2_     { $$ = OP_MAS2;     } 
        | MENOS2_   { $$ = OP_MENOS2;   } 
        ;
%%
