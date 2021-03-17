include "data.dzn";
include "alldifferent.mzn";

par int: desplazamiento; % Duración del tiempo de desplazamiento. 
par int: N_Adan; % Numero de tareas de Adán.
par int: N_Eva; % Numero de tareas de Eva.
par 0..24: TI_Adan;
par 0..24: TF_Adan;
par 0..24: TI_Eva;

var 0..24: TF_Eva;

var 0..24: TA_Adan;
var 0..24: Tout_Eva;
var 0..24: Tout_Adan;

array [1..N_Adan,1..6] of par int: Horarios_Adan;
array [1..N_Eva,1..6] of par int: Horarios_Eva;

array [1..N_Adan,1..3] of var int: Tareas_Adan;
array [1..N_Eva,1..3] of var int: Tareas_Eva;

constraint forall(i in 1..N_Adan) (
  if i = 1 then
    ((Tareas_Adan[i,1] >= Horarios_Adan[i,2] /\ Tareas_Adan[i,2] <= Horarios_Adan[i,3]) \/ 
    (Tareas_Adan[i,1] >= Horarios_Adan[i,4] /\ Tareas_Adan[i,2] <= Horarios_Adan[i,5])) /\ Tareas_Adan[i,3] = Horarios_Adan[i,6]
  else
    ((Tareas_Adan[i,1] >= Horarios_Adan[i,2] /\ Tareas_Adan[i,1] <= Horarios_Adan[i,3]) \/ 
    (Tareas_Adan[i,1] >= Horarios_Adan[i,4] /\ Tareas_Adan[i,1] <= Horarios_Adan[i,5])) /\ Tareas_Adan[i,3] = Horarios_Adan[i,6]
  endif
);

constraint forall(i in 1..N_Adan) (
  Tareas_Adan[i,1] + Tareas_Adan[i,3] = Tareas_Adan[i,2] /\ Tareas_Adan[i,1] >= TI_Adan + desplazamiento /\
  forall(j in 1..N_Adan) (
    if i != j then
      (Tareas_Adan[i,1] >= Tareas_Adan[j,2] + desplazamiento) \/ 
      (Tareas_Adan[i,2] + desplazamiento <= Tareas_Adan[j,1])
    endif
  )
);

constraint forall(i in 1..N_Adan) (TA_Adan >= Tareas_Adan[i,2] + desplazamiento) /\ TA_Adan <= TF_Adan;

constraint Tareas_Eva[1,1] <= Horarios_Eva[1,4] -> Tareas_Eva[1,3] >= Horarios_Eva[1,2];
constraint Tareas_Eva[1,1] > Horarios_Eva[1,4] -> Tareas_Eva[1,3] >= Horarios_Eva[1,5];

constraint forall(i in 2..N_Eva) (
  if i = N_Eva then
    ((Tareas_Eva[i,1] >= Horarios_Eva[i,2] /\ Tareas_Eva[i,1] <= Horarios_Eva[i,3]) \/ 
    (Tareas_Eva[i,1] >= Horarios_Eva[i,4] /\ Tareas_Eva[i,1] <= Horarios_Eva[i,5])) /\ Tareas_Eva[i,3] = Horarios_Eva[i,6]
  else
    ((Tareas_Eva[i,1] >= Horarios_Eva[i,2] /\ Tareas_Eva[i,2] <= Horarios_Eva[i,3]) \/
    (Tareas_Eva[i,1] >= Horarios_Eva[i,4] /\ Tareas_Eva[i,2] <= Horarios_Eva[i,5])) /\ Tareas_Eva[i,3] = Horarios_Eva[i,6]
  endif
);

constraint forall(i in 1..N_Eva) (
  Tareas_Eva[i,1] + Tareas_Eva[i,3] = Tareas_Eva[i,2] /\ Tareas_Eva[i,1] >= TI_Eva + desplazamiento /\
  forall(j in 1..N_Eva) (
    if i != j then
      (Tareas_Eva[i,1] >= Tareas_Eva[j,2] + desplazamiento) \/ 
      (Tareas_Eva[i,2] + desplazamiento <= Tareas_Eva[j,1])
    endif
  )
);

constraint forall(i in 1..N_Eva) (TF_Eva >= Tareas_Eva[i,2] /\ Tareas_Eva[i,2] <= 24) /\ exists(i in 1..N_Eva) (TF_Eva = Tareas_Eva[i,2]);

constraint forall(i in 1..N_Eva) (Tout_Eva <= Tareas_Eva[i,1] - desplazamiento) /\ Tout_Eva >= TI_Eva;

constraint (forall(i in 1..N_Adan) (Tout_Adan <= Tareas_Adan[i,1] - desplazamiento)) /\ Tout_Adan >= TI_Adan;

constraint Tareas_Adan[N_Adan,1] = Tareas_Eva[N_Eva,1];

%constraint Tareas_Eva[2,2] + 5 <= Tareas_Adan[2,1];
%constraint Tareas_Eva[2,2] + 4 <= Tareas_Adan[2,1];

%solve satisfy;
solve minimize (TA_Adan - TF_Adan);
%solve minimize TF_Eva;
%solve maximize Tout_Adan;

output[ ("Tarea Adan " ++ show(i) ++ " :\n" ++ "\tInicio: " ++ show(Tareas_Adan[i,1]/2+10) ++ "\n\tFinal: " ++ show(Tareas_Adan[i,2]/2+10) ++ "\n\tDuración: " ++ show(Tareas_Adan[i,3]/2)) ++ "\n"| i in 1..N_Adan] 
++ ["\n"] ++
[ ("Tarea Eva " ++ show(i) ++ " :\n" ++ "\tInicio: " ++ show(Tareas_Eva[i,1]/2+10) ++ "\n\tFinal: " ++ show(Tareas_Eva[i,2]/2+10) ++ "\n\tDuración: " ++ show(Tareas_Eva[i,3]/2)) ++ "\n"| i in 1..N_Eva ]
++ ["\n"]
++ ["Tiempo de salida de casa de Adan: " ++ show((Tout_Adan)/2 + 10) ++ "\n"] 
++ ["Tiempo de llegada al aeropuerto de Adan: " ++ show((TA_Adan)/2+10) ++ "\n"]
++ ["Tiempo de salida de casa de Eva: " ++ show((Tout_Eva)/2+10) ++ "\n"]
++ ["Tiempo de llegada de casa de Eva: " ++ show((TF_Eva + desplazamiento)/2+10) ++ "\n"];



