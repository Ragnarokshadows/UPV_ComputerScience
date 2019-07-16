% Exercise 1.
countTo(1,[1]).
countTo(2,[1,2]).
countTo(3,[1,2,3]).
countTo(4,[1,2,3,4]).


% Exercise 4. Wrong version of mymember, to correct:
mymember(E,[E|_]).
mymember(E,[H|L]) :- mymember(H,L).


% Exercise 5. Wrong version of myappend, to correct:
myappend([],L,L).
myappend([E|L1],L2,[X|L3]) :- X = E, myappend(L1,L2,L1).


flatten([],[]).
flatten([X|L],[X|P]) :- atomic(X), flatten(L,P).
flatten([X|L],P) :- not(atomic(X)), flatten(X,P_X), flatten(L,P_L), append(P_X,P_L,P).        


prefix(P,L) :- append(P,_,L).
suffix(P,L) :- append(_,P,L).
sublist(S,L) :- suffix(L1,L),prefix(S,L1).

% Another version of sublist:
sublist2(S,L) :- append(_,L1,L),append(S,_,L1).


inverse([],[]).
inverse([H|T],L) :- inverse(T,Z), append(Z,[H],L).

% Another version of inverse:
inverse2(L,I) :- inv(L,[],I).
inv([],I,I).
inv([X|L],A,I) :- inv(L,[X|A],I).
 

% Exercise 7. Incomplete version of swap:           
swap([],[]).
swap([X],[X]).
swap([X,Y|Z],?????).


% Exercise 8. 
mistery([],0).
mistery([_|T],N) :- mistery(T,M), N is M + 1.


% Exercise 9. Incomplete version of subset: 
subset([],_).
subset([A|X],Y) :- member(A,Y), ?????.


% Exercise 10. Incomplete version of sorted: 
sorted([X]).
??????? :- X =< Y, sorted([Y|Ys]).


% Exercise 11. Incomplete version of remove: 
remove(_,[],[]).
remove(C,[X|R],L) :- X == C, remove(C,R,L).
remove(C,[X|R],W) :- X \== C, ????? , ??????? .
