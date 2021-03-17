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
%type<cent> operadorLogico parametrosFormales parametrosActuales listaParametrosActuales declaracionVariable declaracionFuncion declaracion
%type<cent> instruccionAsignacion listaDeclaraciones

%type<lpf> listaParametrosFormales

%type <cent> expresionMultiplicativa expresionAditiva expresionSufija expresionUnaria 
%type <cent> expresion expresionRelacional expresionIgualdad expresionOpcional

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
                if (inf.tipo != $5){ yyerror(E_TYPE_RETURN_NS); }
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
         if ($3 != T_ERROR)
        {
          if (sim.t == T_ERROR){ yyerror(E_UNDECLARED); }
          else if (sim.t == T_ARRAY){ yyerror(E_ARRAY_WO_INDEX); }
          else if (sim.t != $3){ yyerror(E_TYPES_ASIGNACION); }
          else
         {
           $$ = $3;
         }  
        } 
       } 
        | ID_ AC_ expresion CC_ ASIG_ expresion PCOMA_
       {
         SIMB sim = obtTdS($1);
         $$ = T_ERROR;
         if ($3 != T_ERROR && $6 != T_ERROR)
        {
          if (sim.t == T_ERROR){ yyerror(E_UNDECLARED); }
          else if (sim.t != T_ARRAY){ yyerror(E_VAR_WITH_INDEX); }
          else if ($3 != T_ENTERO){ yyerror(E_ARRAY_INDEX_TYPE); }
          else
         {
           DIM dim = obtTdA(sim.ref);
           if (dim.telem != $6){ yyerror(E_TYPES_ASIGNACION); }
           else
          {
            $$ = $3;
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
         if ($3 != T_ENTERO){ yyerror("Print requiere una entrada entera"); } 
       } 
        ;
instruccionSeleccion
        : IF_ AP_ expresion CP_
       {
         if ($3 != T_ERROR && $3 != T_LOGICO){ yyerror(E_IF_LOGICAL); } 
       }
        instruccion ELSE_ instruccion
        ;
instruccionIteracion
        : FOR_ AP_ expresionOpcional PCOMA_ expresion PCOMA_ expresionOpcional CP_
       {
         if ($5 != T_ERROR && $5 != T_LOGICO){ yyerror(E_FOR_LOGICAL); }
       } 
        instruccion
        ;

expresionOpcional
        : /* expresion opcional vacia */ { $$ = T_VACIO; }   
        | expresion { $$ = $1; } 
        | ID_ ASIG_ expresion
        {
            SIMB sim = obtTdS($1);
            $$ = T_ERROR;
            if ($3 != T_ERROR)
            {
                if (sim.t == T_ERROR){ yyerror(E_UNDECLARED); }
                else if (sim.t == T_ARRAY){ yyerror(E_ARRAY_WO_INDEX); }
                else if (sim.t != $3){ yyerror(E_TYPES_ASIGNACION); }
                else { $$ = $3; } 
            } 
        } 
        ;
expresion
        : expresionIgualdad { $$ = $1; }  
        | expresion operadorLogico expresionIgualdad
        {   $$ = T_ERROR;
            if ($1 != T_ERROR && $3 != T_ERROR) 
            {
                if ($1 != $3) {
                    yyerror(E_TYPES_LOGICA);
                } else if ($1 != T_LOGICO) {
                    yyerror("Operacion logica invalida para no booleanos");
                } else {
                    $$ = T_LOGICO;
                }
            } 
        }
        ;
expresionIgualdad
        : expresionRelacional { $$ = $1; } 
        | expresionIgualdad operadorIgualdad expresionRelacional
        {   $$ = T_ERROR;
            if ($1 != T_ERROR && $3 != T_ERROR) {
                if ($1 != $3) {
                    yyerror(E_TYPE_MISMATCH);
                } else if ($1 == T_ARRAY) {
                    yyerror("Operacion de igualdad no existe para arrays");
                } else {
                    $$ = T_LOGICO;
                }
            } 
        }
        ;
expresionRelacional
        : expresionAditiva { $$ = $1; } 
        | expresionRelacional operadorRelacional expresionAditiva
        {   $$ = T_ERROR;
            if ($1 != T_ERROR && $3 != T_ERROR) {
                if ($1 != $3) {
                    yyerror(E_TYPE_MISMATCH);
                } else if ($1 == T_LOGICO) {
                    yyerror("Operacion relacional solo acepta argumentos lógicos");
                } else {
                    $$ = T_LOGICO;
                }
            } 
        }
        ;
expresionAditiva
        : expresionMultiplicativa{ $$ = $1; }  
        | expresionAditiva operadorAditivo expresionMultiplicativa
       {
        $$=T_ERROR;
        if ($1!=T_ERROR && $3 != T_ERROR){
            if ($1 != $3){
                yyerror(E_TYPE_MISMATCH);
            }else if ($1 != T_ENTERO){
                yyerror("Operacion aditiva solo acepta argumentos enteros");
            } else {
                $$ = T_ENTERO;
            } 
        }
       } 
        ;
expresionMultiplicativa 
        : expresionUnaria   {$$ = $1;}    
        | expresionMultiplicativa operadorMultiplicativo expresionUnaria
        {
        $$=T_ERROR;
        if ($1!=T_ERROR && $3 != T_ERROR){
            if ($1 != $3){
                yyerror(E_TYPE_MISMATCH);
            }else if ($1 != T_ENTERO){
                yyerror("Operacion multiplicativa solo acepta argumentos enteros");
            } else {
                $$ = T_ENTERO; 
            } 
        }
        }
expresionUnaria
        : expresionSufija   {$$ = $1;}  
        | operadorUnario expresionUnaria
        {
        $$=T_ERROR;
        if ($2!=T_ERROR){
            if ($2 == T_ENTERO){
                if ($1 == OP_NOT) {
                    yyerror("Operacion \"!\" invalida en expresion entera");
                } else {
                    $$ = T_ENTERO;
                }
            }  

        }else if ($2 == T_LOGICO) {
                if ($1 == OP_NOT) {
                    $$ = T_LOGICO;
                } else {
                    yyerror("Operacion entera invalida en expresion logica");
                }
        }
        }  
        | operadorIncremento ID_
        {
        SIMB simb = obtTdS($2);
        $$ = T_ERROR;

        if (simb.t == T_ERROR)
            yyerror(E_UNDECLARED);
        else if (simb.t == T_ARRAY)
            yyerror(E_ARRAY_WO_INDEX);
        else
            $$ = simb.t;
        } 
        ;
expresionSufija
        : AP_ expresion CP_                 { $$ = $2;  }     
        | ID_ operadorIncremento
       {
          SIMB sim = obtTdS($1); 
          $$ = T_ERROR;
          if(sim.t == T_ERROR) {   yyerror(E_UNDECLARED); }
          else if (sim.t == T_ARRAY){ yyerror(E_ARRAY_WO_INDEX); }
          else{ $$ = sim.t; }
       } 
        | ID_ AC_ expresion CC_
       {
          SIMB sim = obtTdS($1); 
          $$ = T_ERROR;
			    if(sim.t == T_ERROR) {   yyerror(E_UNDECLARED); }
          else if (sim.t != T_ARRAY)  { yyerror(E_VAR_WITH_INDEX); } 
			    else {
            DIM dim = obtTdA(sim.ref);
            $$ = dim.telem;
          } 
       } 
        | ID_ AP_ parametrosActuales CP_ 
       {
          SIMB sim = obtTdS($1); 
          $$ = T_ERROR;
          INF inf = obtTdD(sim.ref);
          if (sim.t == T_ERROR){ yyerror(E_UNDECLARED); }
          else if (inf.tipo == T_ERROR){ yyerror(E_VAR_WITH_CALL); }
          else {
						if (!cmpDom(sim.ref, $3)) { yyerror(E_TYPE_MISMATCH); }
						else { $$ = inf.tipo; }
          } 
       } 
        | ID_                               
        { 
            SIMB sim = obtTdS($1); 
            $$ = T_ERROR;
			      if(sim.t == T_ERROR) {   yyerror(E_UNDECLARED); }
            else if (sim.t == T_ARRAY)  { yyerror(E_ARRAY_WO_INDEX); } 
			      else {  $$ = sim.t;  } 
        } 
        | constante                         { $$ = $1;  }
        ;
parametrosActuales
        : /* cadena vacia */ { $$ = insTdD(-1, T_VACIO); }
        | listaParametrosActuales { $$ = $1; }
        ;
listaParametrosActuales
        : expresion
				{
					$$ = insTdD(-1, $1);
				}
        | expresion COMA_ listaParametrosActuales
				{
					$$ = insTdD($3, $1);
				}
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
