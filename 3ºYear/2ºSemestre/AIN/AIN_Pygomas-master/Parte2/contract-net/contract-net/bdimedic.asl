// agente que se limita a estar quieto e ir a curar
//TEAM_ALLIED 

+flag (F): team(100) 
  <-
   ?position([X,Y,Z]);
   +miposicion([X+30,0,Y+30]);
   .goto([X+30,0,Y+30]).
 

+savemeproposal(Pos)[source(A)]: not (ayudando(_,_))
<-
 ?position(MiPos);
  .send(A, tell, mybid(MiPos));
  +ayudando(A, Pos);
  -savemeproposal(_);
 .print("enviada propuesta de ayuda") .
  
+cancelproposal[source(A)]: ayudando(A, Pos)
<-
  .print("Me cancelan mi proposicion");
  -ayudando(A, Pos).
  
+acceptproposal[source(A)]: ayudando(A, Pos)
<- 
  .print("Me voy a ayudar al agente: ", A, "a la posicion: ", Pos);
  .goto(Pos).
  
+target_reached(T): ayudando(A, T)
  <-
    .print("MEDPACK! para el agente:", A);
    .cure;
    ?miposicion(P);
    .goto(P);
    -ayudando(A, Pos).
    
    
+target_reached(T): not (ayudando(_,_))
  <- 
  .print("estoy en mi posicion").

