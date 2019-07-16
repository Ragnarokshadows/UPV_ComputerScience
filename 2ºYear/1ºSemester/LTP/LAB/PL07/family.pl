%%
%% declaraciones
%%
isparent('juan', 'maria'). % juan es padre de maria
isparent('pablo', 'juan'). % pablo es padre de juan
isparent('pablo', 'marcela').
isparent('carlos', 'debora').
isparent('luisa', 'debora').
 
% A es hijo de B si B es padre de A
ischild(A,B) :- isparent(B,A).

% A es abuelo de B si A es padre de C y C es padre B
isgrandparent(A,B) :- 
   isparent(A,C), 
   isparent(C,B).

% A y B son hermanos si el padre de A es tambien el padre de B y si A y B no son lo mismo
issibling(A,B) :- 
   isparent(C,A), 
   isparent(C,B), 
   A \== B.        
 
% A y B son familiares si A es padre de B o A es hijo de B o A es hermano de B
isrelative(A,B) :- 
   isparent(A,B).
isrelative(A,B) :-
   ischild(A,B). 
isrelative(A,B) :- 
   issibling(A,B).
isrelative(A,B) :- 
   isgrandparent(A,B).
isrelative(A,B) :- isgrandchild(A,B).

% A es el ñet@ de B si B es abuel@ de A
isgrandchild(A,B) :- isgrandparent(B,A).
