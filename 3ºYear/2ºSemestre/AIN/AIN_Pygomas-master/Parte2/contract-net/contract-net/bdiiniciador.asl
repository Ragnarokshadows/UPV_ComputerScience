// Agente que se limita a ir a una posicion y pedir ayuda
//TEAM_ALLIED

+flag (F): team(100)
  <-
    .goto([100, 0, 100]);
    +initpos.



+target_reached(T): team(100) & not (pedidaayuda)
  <-
  .print("He llegado al punto donde solicito ayuda");
  -initpos;
  +pedidaayuda;
   .get_medics.


+myMedics(M): pedidaayuda
<-
 .print("Pido ayuda");
  ?position(Pos);
   +bids([]);
   +agents([]);
   .send(M, tell, savemeproposal(Pos));  
   .wait(1000); 
   !!elegirmejor;
  -myMedics(_) .

+mybid(Pos)[source(A)]:  pedidaayuda
<- 
    .print("Recibo propuesta");
    ?bids(B);
    .concat(B, [Pos], B1);  -+bids(B1);  
    ?agents(Ag); 
    .concat(Ag, [A], Ag1);  -+agents(Ag1); 
     -mybid(Pos).
    
      
+!elegirmejor: bids(Bi) & agents(Ag)
<-
    .print("Selecciono el mejor: ", Bi, Ag);
   .nth(0, Bi, Pos);  // no elijo el mejor, me quedo con el primero
   .nth(0, Ag, A);
    .print("el mejor es: ", A);
   .send(A, tell, acceptproposal);
   .delete(0, Ag, Ag1);
   .send(Ag1, tell, cancelproposal);
   -+bids([]);
   -+agents([]).
   
+!elegirmejor: not (bids(Bi))
<-  
  .print("Nadie me puede ayudar");
  -pedidaayuda. 





