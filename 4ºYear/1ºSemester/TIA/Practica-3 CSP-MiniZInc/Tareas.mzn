% Variables
int: nt; % Número de tareas
var 0..24: T0A;
var 4..24: T0E;
var 0..24: TFA;
var 4..24: TFE;
var 0..24: holgura;
array [1 ..nt] of var 0..24: t_ini; % Vector de t ini
array [1 ..nt] of var 0..24: t_fin; % Vector de t fin
array [1 ..nt] of var 0..24: dur; % Vector de t dur

dur = [1,2,1,4,4,2];

include "alldifferent.mzn";

% Restricciones

% Tiempos tintorería
constraint ((t_ini[1] >= -2) /\ (t_fin[1] <= 7)) \/ ((t_ini[1] >= 14) /\ (t_fin[1] <= 21));
constraint t_fin[1] = t_ini[1] + dur[1];

% Tiempos peluquería
constraint ((t_ini[2] >= 4) /\ (t_ini[2] <= 9))
            \/ ((t_ini[2] >= 13) /\ (t_ini[2] <= 17));
constraint t_fin[2] = t_ini[2] + dur[2];

% Tiempos paquete
constraint ((t_ini[3] >= 8) /\ (t_ini[3] <= 14))
            \/ ((t_ini[3] >= 16) /\ (t_ini[3] <= 23));
constraint t_fin[3] = t_ini[3] + dur[3];

% Tiempos regalo
constraint ((t_ini[4] >= -2) /\ (t_ini[4] <= 22));
constraint t_fin[4] = t_ini[4] + dur[4];

% Tiempos farmacia
constraint if t_ini[5] <= 14 then t_fin[5] = t_ini[5] + dur[5] + 1
            else t_fin[5] = t_ini[5] + dur[5] endif;

% Tiempos amiga
constraint ((t_ini[6] >= 4) /\ (t_ini[6] <= 9 - dur[6]))
            \/ ((t_ini[6] >= 15) /\ (t_ini[6] <= 19 - dur[6]));
constraint t_fin[6] = t_ini[6] + dur[6];

% Trajectos
constraint forall(i in 1..4) (
      forall(j in 1..4) (
            if i != j then
                  (t_ini[i] >= t_fin[j] + 1) \/ 
                  (t_fin[i] + 1 <= t_ini[j])
            endif 
      )
);
constraint forall(i in 1..4) (
      T0A + 1 <= t_ini[i] /\
      t_fin[i] + 1 <= TFA
);
constraint forall(i in 4..6) (
      forall(j in 4..6) (
            if i != j then
                  (t_ini[i] >= t_fin[j] + 1) \/ 
                  (t_fin[i] + 1 <= t_ini[j])
            endif 
      )
);
constraint forall(i in 4..6) (
      T0E + 1 <= t_ini[i] /\
      t_fin[i] + 1 <= TFE
);

% Salida y llegada
constraint TFA < 20;
constraint T0E >= 4;

% Holgura
constraint holgura = 20-TFA; 

% Primera pregunta
%constraint t_fin[6] <= t_ini[2] - 8;

% Segunda pregunta
%constraint t_fin[6] <= t_ini[2] - 10;

% Resolver
solve satisfy;
%solve minimize TFE;
%solve maximize T0A;
%solve maximize holgura;

% Output
output[ "Tareas Adan: \n" ] ++ [show(t_ini[i])++"-"++show(t_fin[i]) ++"\n" | i in 1..4] ++ 
      [ "Salir de casa: " ++ show(T0A) ++ "\nAeropuerto: " ++ show(TFA) ++ "\n\n"] ++
      [ "Holgura: " ++ show(holgura) ++ "\n\n"] ++      
      [ "Tareas Eva: \n"  ] ++ [show(t_ini[i])++"-"++show(t_fin[i]) ++"\n" | i in 4..6] ++ 
      [ "Salir de casa: " ++ show(T0E) ++ "\nLlegar a casa: " ++ show(TFE)];