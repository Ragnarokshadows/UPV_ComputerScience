/*****************************************************************************/
/**  Ejemplo de BISON-I: S E M - 2          2019-2020 <jbenedi@dsic.upv.es> **/
/*****************************************************************************/
%{
#include <stdio.h>
#include <string.h>
#include "header.h"
#include "libtds.h"
#include "libgci.h"

int ref_main[2];
int talla_global = 0; 
%}

%union{ 
    char* ident; 
    int cent; 
    LPF lpf;
    EXP exp;
    FOR_INST instfor;
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

%type<cent> tipoSimple operadorUnario operadorIncremento operadorMultiplicativo 
%type<cent> operadorAditivo operadorRelacional operadorIgualdad cabeceraFuncion
%type<cent> operadorLogico parametrosFormales parametrosActuales listaParametrosActuales declaracionVariable declaracionFuncion declaracion
%type<cent> instruccionAsignacion listaDeclaraciones

%type<lpf> listaParametrosFormales

%type <exp> expresionMultiplicativa expresionAditiva expresionSufija expresionUnaria 
%type <exp> expresion expresionRelacional expresionIgualdad expresionOpcional constante

%%
/** HECHO2 **/
programa  
        : 
        {
            dvar = 0; 
            niv = 0;
            si = 0;
            cargaContexto(niv);
            ref_main[0] = creaLans(si);
            emite(INCTOP, crArgNul(), crArgNul(), crArgEnt(-1));
            ref_main[1] = creaLans(si);
            emite(GOTOS, crArgNul(), crArgNul(), crArgEtq(-1));
        } 
        listaDeclaraciones
        {
            if($2 == 0){yyerror("Se debe declarar al menos una funcion main()");}
        }
        ;
/** HECHO2 **/
listaDeclaraciones  
        : declaracion{$$ = $1;} 
        | listaDeclaraciones declaracion{$$ = $1 + $2;} 
        ;
/** HECHO2 **/
declaracion
        : declaracionVariable   { $$ = 0; } 
        | declaracionFuncion    { $$ = $1; } 
        ;
/** HECHO2 **/
declaracionVariable
        : tipoSimple ID_ PCOMA_
            {
                if(! insTdS($2, VARIABLE, $1, niv, dvar, -1))
                    yyerror(E_REPEATED_DECLARATION);
                else 
                {
                    dvar += TALLA_TIPO_SIMPLE;
                    if(!niv) talla_global += TALLA_TIPO_SIMPLE;
                }
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
            {
                dvar += numelem * TALLA_TIPO_SIMPLE; 
                if(!niv) talla_global +=numelem * TALLA_TIPO_SIMPLE;
            }
        }
        ;
/** HECHO2 **/   
tipoSimple
        : INT_      { $$ = T_ENTERO; }
        | BOOL_     { $$ = T_LOGICO; }
        ;
/** HECHO2 **/  
declaracionFuncion
        : cabeceraFuncion 
        {
            $<cent>$=dvar; 
            dvar = 0;
        }
        bloque 
        {
            if(verTdS) mostrarTdS(); 
            descargaContexto(niv); 
            niv=0; 
            dvar=$<cent>2;
            $$=$1;
        }         
        ;
/** HECHO2 **/ 
cabeceraFuncion
        : tipoSimple ID_ {niv=1; cargaContexto(niv);} AP_ parametrosFormales CP_ 
        {
            if(!insTdS($2, FUNCION, $1, 0, si, $5)){
                yyerror(E_REPEATED_DECLARATION);
            }
            if(strcmp($2, "main\0")==0)
            {
                $$=-1; 
                completaLans(ref_main[0], crArgEnt(talla_global));
                completaLans(ref_main[1], crArgEtq(si));
            }  
            else 
            {
                $$=0;
            }    
        } 
        ;
/** HECHO2 **/ 
parametrosFormales
        : /* cadena vacia */            { $$ = insTdD(-1, T_VACIO); } 
        | listaParametrosFormales       { $$ = $1.ref; } 
        ;
/** HECHO2 **/ 
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
/** HECHO2 **/
bloque
        : 
        {
            emite(PUSHFP, crArgNul(), crArgNul(), crArgNul());
            emite(FPTOP, crArgNul(), crArgNul(), crArgNul());
            $<cent>$ = creaLans(si);
            emite(INCTOP, crArgNul(), crArgNul(), crArgEnt(-1));
        } 
        AL_ declaracionVariableLocal listaInstrucciones RETURN_ expresion PCOMA_ CL_
        {
            INF inf = obtTdD(-1);

            int dir_return = TALLA_SEGENLACES + inf.tsp + TALLA_TIPO_SIMPLE;
		
            completaLans($<cent>1, crArgEnt(dvar)); 
            emite(EASIG, crArgPos(niv, $6.pos), crArgNul(), crArgPos(niv, -dir_return));
            emite(TOPFP, crArgNul(), crArgNul(), crArgNul());
            emite(FPPOP, crArgNul(), crArgNul(), crArgNul());

            if (inf.tipo != T_ERROR)
            {
                    if (inf.tipo != $6.tipo){ yyerror(E_TYPE_RETURN_NS); }
            }

            if (strcmp(inf.nom, "main")== 0)
            { 
                emite(FIN, crArgNul(), crArgNul(), crArgNul());
            } 
            else 
            {
                emite(RET, crArgNul(), crArgNul(), crArgNul());
            } 
        }
        ;
/** HECHO2 **/
declaracionVariableLocal
        : /* cadena vacia */
        | declaracionVariableLocal declaracionVariable
        ;
/** HECHO2 **/
listaInstrucciones
        : /* instruccion vacia */
        | listaInstrucciones instruccion
        ;
/** HECHO2 **/
instruccion
        : AL_ listaInstrucciones CL_
        | instruccionAsignacion
        | instruccionSeleccion
        | instruccionEntradaSalida     
        | instruccionIteracion
        ;
/** HECHO2 **/
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
            emite(EASIG, crArgPos(niv, $3.pos), crArgNul(), crArgPos(sim.n, sim.d));
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
                        $$ = $6.tipo;
                    } 
                }  
            } 
            //emite(EASIG, crArgPos(niv, $3.pos * TALLA_TIPO_SIMPLE), crArgNul(), crArgPos(niv, $3.pos));
            emite(EVA, crArgPos(sim.n, sim.d), crArgPos(niv, $3.pos), crArgPos(niv, $6.pos)); 
        } 
        ;
/** HECHO2 **/
instruccionEntradaSalida
        : READ_ AP_ ID_ CP_ PCOMA_
        {
            SIMB sim = obtTdS($3);
            if (sim.t == T_ERROR){ yyerror(E_UNDECLARED); }
            else if (sim.t != T_ENTERO){ yyerror("Read requiere una entrada entera"); }
            emite(EREAD, crArgNul(), crArgNul(), crArgPos(sim.n, sim.d));
        } 
        | PRINT_ AP_ expresion CP_ PCOMA_
        {
            if ($3.tipo != T_ENTERO){ yyerror("Print requiere una entrada entera"); } 
            emite(EWRITE, crArgNul(), crArgNul(), crArgPos(niv, $3.pos));
        } 
        ;
/** HECHO2 **/
instruccionSeleccion
        : IF_ AP_ expresion CP_
        {
            if ($3.tipo != T_ERROR && $3.tipo != T_LOGICO){ yyerror(E_IF_LOGICAL); } 
            $<cent>$ = creaLans(si);
            emite(EIGUAL, crArgPos(niv, $3.pos), crArgEnt(0), crArgEtq(-1));
        }
        instruccion 
        {
            $<cent>$ = creaLans(si);
            emite(GOTOS, crArgNul(), crArgNul(), crArgEtq(-1));
            completaLans($<cent>5, crArgEtq(si));
        } 
        ELSE_ instruccion
        {
            completaLans($<cent>7, crArgEnt(si));
        } 
        ;
/** HECHO2 **/
instruccionIteracion
        : FOR_ AP_ expresionOpcional PCOMA_ 
        {
            $<cent>$ = si;
        } 
        expresion PCOMA_ 
        {
            if ($6.tipo != T_ERROR && $6.tipo != T_LOGICO){ yyerror(E_FOR_LOGICAL); }
            $<instfor>$.lv = creaLans(si); emite(EIGUAL, crArgPos(niv, $6.pos), crArgEnt(1), crArgEtq(-1));
            $<instfor>$.lf = creaLans(si); emite(GOTOS, crArgNul(), crArgNul(), crArgEtq(-1));
            $<instfor>$.aux = si;
        } 
        expresionOpcional CP_
        {
            emite(GOTOS, crArgNul(), crArgNul(), crArgEtq($<cent>5));
            completaLans($<instfor>8.lv, crArgEnt(si));
        } 
        instruccion
        {
            emite(GOTOS, crArgNul(), crArgNul(), crArgEtq($<instfor>8.aux));
            completaLans($<instfor>8.lf, crArgEnt(si));
        } 
        ;

/** HECHO2 **/ 
expresionOpcional
        : /* expresion opcional vacia */ { $$.tipo = T_VACIO; }   
        | expresion { $$.tipo = $1.tipo; $$.pos = $1.pos; } 
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
            $$.pos = creaVarTemp();
            //emite(EASIG, crArgPos(niv, $3.pos), crArgNul(), crArgPos(niv, $$.pos));
            emite(EASIG, crArgPos(niv, $3.pos), crArgNul(), crArgPos(sim.n, sim.d));
        } 
        ;
/** HECHO2 **/
expresion
        : expresionIgualdad { $$.tipo = $1.tipo; $$.pos = $1.pos;}  
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
            $$.pos = creaVarTemp();
            if ($2 == EMULT) {
                emite(EMULT, crArgPos(niv, $1.pos), crArgPos(niv, $3.pos), crArgPos(niv, $$.pos));
            } else {
                emite(ESUM, crArgPos(niv, $1.pos), crArgPos(niv, $3.pos), crArgPos(niv, $$.pos));
                emite(EMENEQ, crArgPos(niv, $$.pos), crArgEnt(1), crArgEtq(si+2));
                emite(EASIG, crArgEnt(1), crArgNul(), crArgPos(niv, $$.pos));
            }
        }
        ;
/** HECHO2 **/
expresionIgualdad
        : expresionRelacional { $$.tipo = $1.tipo; $$.pos = $1.pos; } 
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
            $$.pos = creaVarTemp();
            emite(EASIG, crArgEnt(1), crArgNul(), crArgPos(niv, $$.pos));
            emite($2, crArgPos(niv, $1.pos), crArgPos(niv, $3.pos), crArgEtq(si+2));
            emite(EASIG, crArgEnt(0), crArgNul(), crArgPos(niv, $$.pos));
        }
        ;
/** HECHO2 **/
expresionRelacional
        : expresionAditiva { $$.tipo = $1.tipo; $$.pos = $1.pos; } 
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
            $$.pos = creaVarTemp();
            emite(EASIG, crArgEnt(1), crArgNul(), crArgPos(niv, $$.pos));
            emite($2, crArgPos(niv, $1.pos), crArgPos(niv, $3.pos), crArgEtq(si+2));
            emite(EASIG, crArgEnt(0), crArgNul(), crArgPos(niv, $$.pos));
        }
        ;
/** HECHO2 **/
expresionAditiva
        : expresionMultiplicativa{ $$.tipo = $1.tipo; $$.pos = $1.pos; }  
        | expresionAditiva operadorAditivo expresionMultiplicativa
        {
            $$.tipo = T_ERROR;
            if ($1.tipo != T_ERROR && $3.tipo != T_ERROR){
                if ($1.tipo != $3.tipo){
                    yyerror(E_TYPE_MISMATCH);
                } else if ($1.tipo != T_ENTERO){
                    yyerror("Operacion aditiva solo acepta argumentos enteros");
                } else {
                    $$.tipo = T_ENTERO;
                } 
            }
            $$.pos = creaVarTemp();
            emite($2, crArgPos(niv, $1.pos), crArgPos(niv, $3.pos), crArgPos(niv, $$.pos));
        } 
        ;
/** HECHO2 **/
expresionMultiplicativa 
        : expresionUnaria   { $$.tipo = $1.tipo; $$.pos = $1.pos; }    
        | expresionMultiplicativa operadorMultiplicativo expresionUnaria
        {
            $$.tipo = T_ERROR;
            if ($1.tipo != T_ERROR && $3.tipo != T_ERROR){
                if ($1.tipo != $3.tipo){
                    yyerror(E_TYPE_MISMATCH);
                }else if ($1.tipo != T_ENTERO){
                    yyerror("Operacion multiplicativa solo acepta argumentos enteros");
                } else {
                    $$.tipo = T_ENTERO; 
                } 
            }
            $$.pos = creaVarTemp();
            emite($2, crArgPos(niv, $1.pos), crArgPos(niv, $3.pos), crArgPos(niv, $$.pos));
        }
        ;
/** HECHO2 **/      
expresionUnaria
        : expresionSufija   { $$.tipo = $1.tipo; $$.pos = $1.pos; }  
        | operadorUnario expresionUnaria
        {
            $$.tipo = T_ERROR;
            if ($2.tipo != T_ERROR){
                if ($2.tipo == T_ENTERO){
                    if ($1 == ESIG) {
                        yyerror("Operacion \"!\" invalida en expresion entera");
                    } else {
                        $$.tipo = T_ENTERO;
                    }
                }  

            } else if ($2.tipo == T_LOGICO) {
                if ($1 == ESIG) {
                    $$.tipo = T_LOGICO;
                } else {
                    yyerror("Operacion entera invalida en expresion logica");
                }
            }
            $$.pos = creaVarTemp();
            if ($1 == ESIG) {
                emite(EDIF, crArgEnt(1), crArgPos(niv, $2.pos), crArgPos(niv, $$.pos));    
            } else {
                emite($1, crArgEnt(0), crArgPos(niv, $2.pos), crArgPos(niv, $$.pos));
            }
        }  
        | operadorIncremento ID_
        {
            SIMB sim = obtTdS($2);
            $$.tipo = T_ERROR;

            if (sim.t == T_ERROR)
                yyerror(E_UNDECLARED);
            else if (sim.t == T_ARRAY)
                yyerror(E_ARRAY_WO_INDEX);
            else
                $$.tipo = sim.t;
            
            $$.pos = creaVarTemp();
            emite($1, crArgPos(sim.n, sim.d), crArgEnt(1), crArgPos(sim.n, sim.d));
            emite(EASIG, crArgPos(sim.n, sim.d), crArgNul(), crArgPos(niv, $$.pos));
        } 
        ;
/** HECHO2 **/
expresionSufija
        : AP_ expresion CP_    { $$.tipo = $2.tipo; $$.pos = $2.pos; }     
        | ID_ operadorIncremento
        {
            SIMB sim = obtTdS($1); 
            $$.tipo = T_ERROR;
            if(sim.t == T_ERROR) {   yyerror(E_UNDECLARED); }
            else if (sim.t == T_ARRAY){ yyerror(E_ARRAY_WO_INDEX); }
            else{ $$.tipo = sim.t; }

            $$.pos = creaVarTemp();
            emite(EASIG, crArgPos(sim.n, sim.d), crArgNul(), crArgPos(niv, $$.pos)); 
            emite($2, crArgPos(sim.n, sim.d), crArgEnt(1), crArgPos(sim.n, sim.d));
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
            $$.pos = creaVarTemp();
            emite(EAV, crArgPos(sim.n, sim.d), crArgPos(niv, $3.pos), crArgPos(niv, $$.pos));
        } 
        | ID_ AP_ 
        {
            //TEST
            emite(EPUSH, crArgNul(), crArgNul(), crArgEnt(0));
        } 
        parametrosActuales CP_ 
        {
            SIMB sim = obtTdS($1); 
            $$.tipo = T_ERROR;
            INF inf = obtTdD(sim.ref);
            if (sim.t == T_ERROR){ yyerror(E_UNDECLARED); }
            else if (inf.tipo == T_ERROR){ yyerror(E_VAR_WITH_CALL); }
            else {
                if (!cmpDom(sim.ref, $4)) { yyerror(E_TYPE_MISMATCH); }
                else 
                { 
                    $$.tipo = inf.tipo; 
                    emite(CALL,crArgNul(),crArgNul(),crArgEtq(sim.d));
                    emite(DECTOP,crArgNul(),crArgNul(),crArgEnt(inf.tsp));
                    $$.pos = creaVarTemp();
                    emite(EPOP,crArgNul(),crArgNul(),crArgPos(niv, $$.pos));
                }
            } 
        } 
        | ID_                               
        { 
            SIMB sim = obtTdS($1); 
            $$.tipo = T_ERROR;
			if(sim.t == T_ERROR) {   yyerror(E_UNDECLARED); }
            else if (sim.t == T_ARRAY)  { yyerror(E_ARRAY_WO_INDEX); } 
			else {  $$.tipo = sim.t;  } 

            $$.pos = creaVarTemp();
            emite(EASIG, crArgPos(sim.n, sim.d), crArgNul(), crArgPos(niv, $$.pos));
        } 
        | constante   
        { 
            $$.tipo = $1.tipo; 
            $$.pos = creaVarTemp();
            emite(EASIG, crArgEnt($1.pos), crArgNul(), crArgPos(niv, $$.pos)); 
        }
        ;
/** HECHO2 **/
parametrosActuales
        : /* cadena vacia */ { $$ = insTdD(-1, T_VACIO); }
        | listaParametrosActuales { $$ = $1; }
        ;
/** HECHO2 **/
listaParametrosActuales
        : expresion
		{
			$$ = insTdD(-1, $1.tipo);
            emite(EPUSH,crArgNul(),crArgNul(),crArgPos(niv, $1.pos));
		}
        | expresion 
        {
            emite(EPUSH,crArgNul(),crArgNul(),crArgPos(niv, $1.pos));
        } 
        COMA_ listaParametrosActuales
		{
			$$ = insTdD($4, $1.tipo);
		}
        ;
/** HECHO2 **/
constante
        : CTE_      { $$.tipo = T_ENTERO; $$.pos = $1; }  
        | TRUE_     { $$.tipo = T_LOGICO; $$.pos = 1;  }  
        | FALSE_    { $$.tipo = T_LOGICO; $$.pos = 0;  }  
        ; 
operadorLogico
        : AND_      { $$ = EMULT;      } 
        | OR_       { $$ = ESUM;       } 
        ;
operadorIgualdad
        : IGUAL_    { $$ = EIGUAL;    } 
        | DIST_     { $$ = EDIST;     } 
        ;
operadorRelacional
        : MY_       { $$ = EMAY;       }   
        | MN_       { $$ = EMEN;       } 
        | MYIG_     { $$ = EMAYEQ;     } 
        | MNIG_     { $$ = EMENEQ;     } 
        ;
operadorAditivo
        : MAS_      { $$ = ESUM;      } 
        | MENOS_    { $$ = EDIF;       } 
        ;
operadorMultiplicativo
        : POR_      { $$ = EMULT;      } 
        | DIV_      { $$ = EDIVI;      } 
        ;
operadorUnario 
        : MAS_      { $$ = ESUM;      } 
        | MENOS_    { $$ = EDIF;    } 
        | NOT_      { $$ = ESIG;      } 
        ;
operadorIncremento
        : MAS2_     { $$ = ESUM;     } 
        | MENOS2_   { $$ = EDIF;   } 
        ;
%%
