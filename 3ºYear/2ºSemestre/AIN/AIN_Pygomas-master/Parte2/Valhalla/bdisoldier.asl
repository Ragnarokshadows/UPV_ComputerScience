//TEAM_AXIS

+flag (F): team(200)
  <-
  .wait(2000);
  +asked(0);
  +following(0);
  +aux(0);
  +vueltas(0);
  +enemies_seen(0).




//////////////////////////////////////////////////////////////////////////////////I
//HAN ROBADO LA BANDERA : NOS CENTRAMOS EN UN OBJETIVO Y LO SEGUIMOS

//Si vemos a alguien, lo perseguimos
+!se_van_a_enterar: enemies_in_fov(ID,Type,Angle,Distance,Health,Position)
<-
  .print("Se van a enterar -> ",ID);
  .look_at(Position);
  .goto(Position);
  -+vueltas(0);
  !!persecucion(ID).
  
//Si no vemos a nadie, damos hasta 5 vueltas y si no hemos visto a nadie, vamos hacia nuestra posición inicial
+!se_van_a_enterar: not(enemies_in_fov(ID,Type,Angle,Distance,Health,Position))
<-
  ?vueltas(V);
  -+vueltas(V+1);
  .turn(1.5708);
  .wait(500);
  .print("No veo a nadie, me giro");
  if(V == 5){
    -+vueltas(0);
    ?origin(G);
    .goto(G);
    +volviedo;
  };
  
  if (V < 5){
    !se_van_a_enterar;
  }.
  
//Si el enemigo que vemos es el que tenemos que perseguir, vamos hacia él
+!persecucion(I): enemies_in_fov(ID,Type,Angle,Distance,Health,Position) & ID == I
<-
  .look_at(Position);
  
  !!persecucion(ID);
  .goto(Position).

//Si vemos a más de 4 enemigos y ninguno es el que tenemos que pereguir, nos centramos en uno nuevo
+!persecucion(I): enemies_in_fov(ID,Type,Angle,Distance,Health,Position) & not(ID == I)
<-
  ?aux(X);
  -+aux(X+1);
  if (X < 5){
    !!persecucion(I);
  };
  if (aux==5){
    .print("Cambio de objetio!");
    -+aux(0);
    !!persecucion(ID);
  }.

//Si estamos persiguiendo a alguien y no ve a nadie, si tiene la bandera delante, va a por ella, en caso contrario se vuelve al origen
+!persecucion(I): not(enemies_in_fov(ID,Type,Angle,Distance,Health,Position))
<-
  .flag(X);
  .length(X,L);
  if(not(L==0)){
    .goto(X);
    +banderita;
  };
  ?origin(G);
  .goto(G);
  +volviendo.

//ACABAN PLANES CONTRA ROBAR RABNDERA
//////////////////////////////////////////////////////////////////////////////////I




//////////////////////////////////////////////////////////////////////////////////I
//PLANES PARA COGER MUNICION Y VIDA

//Plan para coger paquetes de vida a la vista
+packs_in_fov(ID, TYPE, ANGLE, DIST, HEALTH, [X,Y,Z]): TYPE == 1001 & health(X) & X < 90 & not(to_pack)
  <-
  +to_pack;
  .print("Voy a por la sanacion");
  .goto([X,Y,Z]).

//Plan para coger paquetes de munición a la vista
+packs_in_fov(ID, TYPE, ANGLE, DIST, HEALTH, [X,Y,Z]): TYPE == 1002 & ammo(X) & X < 75 & not(to_pack)
  <-
  +to_pack;
  .print("Voy a por la municion");
  .goto([X,Y,Z]).

//Plan que se activa cuando un médico o fieldop le dice que busque los paquetes que ha dejado
+buscaPaquetes[source(A)]
  <-
  +buscando;
  ?flag(F);
  .look_at(F).

//ACABAN PLANES PARA COGER PAQUETES
//////////////////////////////////////////////////////////////////////////////////I




//////////////////////////////////////////////////////////////////////////////////I
//PLANES PARA PEDIR Y ELEGIR EL MEDICO QUE ESTE MAS CERCA

//Si nos baja la vida a menos de 75 y no estamos heridos ya, pedimos un médico
+health(X): X < 75 & not(herido)
  <-
  +herido;
  .print("Estoy herido");
  .get_medics.

//Si tenemos más de 75 de vida, ya no estamos heridos
+health(X): X >= 75 & herido
  <-
  -herido.

//Si obtenemos la lista de médicos y estamos heridos, pedimos a los médicos que nos envien sus propuestas y elegimos al mejor
+myMedics(M): herido & not(quitar(_))
<-
  .print("Voy a pedir ayuda");
  ?position(Pos);
  +mBids([]);
  +mAgents([]);
  .send(M, tell, curadmeplease(Pos));
  .wait(2000);
  !!elegirMedico;
  -myMedics(_).

//Plan para volver a pedir ayuda en caso de quede algún médico vivo
+myMedics(F): quitar(L)
<-
  -quitar;
  -myMedics(_);
  .length(F,Lf);

  if(L == 0 & not(Lf == 0)){
    -herido;
  }.


//Elige al médico más cercano y acepta su propuesta, al resto les envía la cancelación
+!elegirMedico: mBids(Bi) & mAgents(Ai)
<-
  .print("Selecciono el mejor: ", Bi, Ai);
  .length(Ai,L);
  .length(Bi,L2);
  if(L > 0 & L2 > 0 & L2==L){
    .minPos(Bi, Ind);
    .nth(Ind, Ai, A);
    .send(A, tell, acceptproposal);
    .delete(Ind, Ai, Ag1);
    .send(Ag1, tell, cancelproposal);
  };

  -mBids(Bi);
  +mBids([]);
  -mAgents(Ai);
  +mAgents([]);

  .get_medics;
  +quitar(L).

//Plan que se activa al recibir una propuesta de un médico, guardamos la distancia a él y su nombre
+medicBid(D)[source(A)]: herido
<-
  ?mBids(B);
  .concat(B, [D], B1);
  -mBids(B);
  +mBids(B1);

  ?mAgents(Ag);
  .concat(Ag, [A], Ag1);
  -mAgents(A);
  +mAgents(Ag1);
  -medicBid(D).


//ACABAN PLANES PARA PEDIR MEDICINA
//////////////////////////////////////////////////////////////////////////////////I




//////////////////////////////////////////////////////////////////////////////////I
//PLANES PARA PEDIR AYUDA A UN SOLDADO

//Si vemos a algun enemigo, pedimos ayuda a un soldado si no lo hemos hecho ya
+enemies_in_fov(ID,Type,Angle,Distance,Health,Position): asked(A) & (A == 0)
  <-
  ?asked(K);
  -asked(_);
  +asked(1);
  if (K==0){
    .print("Ayuda soldados!");
    +posEnem(Position);
    .get_backups;
  }.

//Si hemos pedido ayuda pero no tenemos a nadie delante, permitimos pedir ayuda de nuevo
+asked(A): A == 1 & not (enemies_in_fov(ID,Type,Angle,Distance,Health,Position))
  <-
  -asked(_);
  +asked(0);
  -myBackups(_).

//Si tenemos la lista de soldados y hemos pedido ayuda, pedimos a sus agentes su posición y elegimos al mejor
+myBackups(B): asked(A) & (A == 1)
  <-
  ?position(Pos);
  +bids([]);
  +agents([]);
  .send(B,tell,help_in(Pos));
  .wait(2000);
  !elegir_mejor;
  -myBackups(_).

//Plan para volver a pedir ayuda en caso de quede algún soldado vivo
+myBackups(B): pidiendo_ayuda(L)
<-
  -myBackups(_);
  -pidiendo_ayuda(_);
  .length(B,Lb);

  if(L == 0 & not(Lb==0)){ 
    -asked(_);
    +asked(0);
  }.

//Elegimos al agente que esté más cerca
+!elegir_mejor: bids(Bi) & agents (Ag)
  <-
  .length(Ag,L);
  .length(Bi,L2);
  if (L > 0 & L2 > 0 & L == L2){
    .minPos(Bi,Ind);
    .nth(Ind,Ag,Agen);
    .print("Ayudame, estas mas cerca! -> ", Agen);
    ?posEnem(Pu);
    -posEnem(_);
    .send(Agen,tell,acceptproposal(Pu));
    
    .delete(Ind,Ag,Oth);
    .send(Oth,tell,cancelproposal);


  };
  -asked(_);
  +asked(0);
  -bids(_);
  +bids([]);
  -agents(_);
  +agents([]);
  
  .get_backups;
  +pidiendo_ayuda(L).
  

//Si recibimos una petición de ayuda, solo contestamos si no vemos a ningún enemigo y no estamos ayudando a alguien ya
+help_in(Pos2)[source(A)]: not(enemies_in_fov(ID,Type,Angle,Distance,Health,Position)) & not(ayudando(X,Z))
  <-
  ?position(Pos);
  if (not (Pos == Pos2)){
    .distance(Pos,Pos2,D);
    .send(A,tell,my_dist(D));
    +ayudando(A,Pos2);
  }.

//Plan que se activa cuando recibe una propuesta de un soldado, guardamos su distancia y su nombre
+my_dist(D)[source(S)]
  <-
  ?agents(A);
  .concat(A,[S],A2);
  -agents(A);
  +agents(A2);
  ?bids(B);
  .concat(B,[D],B2);
  -bids(B);
  +bids(B2).

//Plan que se activa si un agente nos confirma que quiere nuestra ayuda
+acceptproposal(EP)[source(S)]
  <-
  ?ayudando(A2,Pos);
  .print("Voy a ayudarte!");
  ?flag(F);
  .optimal_point(F,Pos,EP,Punt);
  +enemyPos(EP);
  .goto(Punt).

//Plan que se activa cuando un agente nos cancela la solicitud de ayuda
+cancelproposal[source(A)]: ayudando(A,Pos)
  <-
  -ayudando(A,Pos).

//ACABAN PLANES PARA PEDIR AYUDA A UN SOLDADO
//////////////////////////////////////////////////////////////////////////////////I




//////////////////////////////////////////////////////////////////////////////////I
//PLANES PARA PEDIR MUNICION

//Si nos baja la munición, pedimos un fieldop
+ammo(X): X < 60 & not(sin_municion)
  <-
  +sin_municion;
  .print("Estoy sin municion");
  .get_fieldops.

//Si tenemos suficiente munición, elminamos esa creencia
+ammo(X): X >= 60 & sin_municion
  <-
  -sin_municion.

//Si tenemos la lista de fieldops y necesitamos munición, pedimos ayuda y elegimos al mejor
+myFieldops(F): sin_municion & not(quitar(_,_))
<-
  .print("Voy a pedir ayuda");
  ?position(Pos);
  +fBids([]);
  +fAgents([]);
  .send(F, tell, municionplease(Pos));
  .wait(2000);
  !!elegirField;
  -myFieldops(_).

//Plan para volver a pedir ayuda en caso de quede algún agente de campo vivo
+myFieldops(F): quitar(L)
<-
  -quitar;
  -myFieldops(_);
  .length(F,Lf);

  if(L == 0 & not(Lf == 0)){ 
    -sin_municion;
  }.


//Elegimos al fieldop que esté más cerca y cancelamos al resto
+!elegirField: fBids(Bi) & fAgents(Ai)
<-
  .print("Selecciono el mejor: ", Bi, Ai);
  .length(Ai,L);
  .length(Bi,L2);
  if(L > 0 & L2 > 0){
    .minPos(Bi, Ind);
    .nth(Ind, Ai, A);
    .send(A, tell, acceptproposal);
    .delete(Ind, Ai, Ag1);
    .send(Ag1, tell, cancelproposal);
  };

  -fBids(_);
  +fBids([]);
  -fAgents([]);
  +fAgents([]);

  .get_fieldops;
  +quitar(L).
  
 
//Plan que se activa al recibir una propuesta de un fieldop, guardamos su distancia y su nombre
+munBid(D)[source(A)]: sin_municion
<-
  ?fBids(B);
  .concat(B, [D], B1);
  -fBids(_);
  +fBids(B1);

  ?fAgents(Ag);
  .concat(Ag, [A], Ag1);
  -fAgents(_);
  +fAgents(Ag1);

  -munBid(D).

//ACABAN PLANES PARA PEDIR MUNICION
//////////////////////////////////////////////////////////////////////////////////I




//////////////////////////////////////////////////////////////////////////////////I
//PLAN PARA ESTABLECER NUESTRO PUNTO EN LA FORMACION
+set_origin(G)[source(A)]
  <-
	+origin(G);
  +formacion;
	.goto(G).
//////////////////////////////////////////////////////////////////////////////////I

// Plan para avisar al capitán de que sigue teniendo que vigilar la bandera
+captain(C)
<-
  -captain(C);
  .send(C, achieve, mirar_bandera).


//////////////////////////////////////////////////////////////////////////////////I
//METODOS DE TARGET_REACHED

//Si llegamos al objetivo y estamo haciendo la formación no giramos dejando la bandera atrás
+target_reached(T): formacion
  <-
  -formacion;
  -target_reached(T);
  ?flag(F);
  ?position(Pos);
  .faceOpposite(Pos,F,Punto);
  .look_at(Punto).

//Si llegamos al objetivo y estamos buscando la bandera, volvemos al origen y avisamos al capitan, si estábamos buscando
//solo volvemos al origen
+target_reached(T): banderita | buscando
  <-
  if (banderita){
    .print("La bandera es nuestra");
    .wait(1000);
    -banderita;
    .get_service("captain");
    };
  if(buscando){-buscando;};

  ?origin(G);
  .goto(G);
  +formacion;
  -target_reached(T).

//Si llegamos al objetivo y estabamos buscando un paquete, volvemos a la formacion
+target_reached(T): to_pack
  <-
  -to_pack;
  -target_reached(T);
  +formacion;
  ?origin(G);
  .goto(G).

//Si hemos llegado al objetivo y estabamos ayudando a alguien y vemos a un enemigo, nos centramos en él
+target_reached(T): ayudando(A,B) & enemies_in_fov(ID,Type,Angle,Distance,Health,Position)
  <-
  -enemyPos(_);
  -target_reached(T);
  .look_at(Position);
  .print("He llegado, veo al enemigo.");
  -ayudando(_,_).

//Si hemos llegado al objetivo y vemos al enemigo, nos centramos en él. Si no hay nadie volvemos a nuestro punto.
+target_reached(T): ayudando(A,B)
  <-
  ?enemyPos(P);
  -enemyPos(_);
  -target_reached(T);
  .look_at(P);
  if(not (enemies_in_fov(_,_,_,_,_,_))){
    ?origin(G);
    .goto(G);
    +formacion;
    .print("He llegado, no hay nadie, me vuelvo.");
  };
  -ayudando(_,_).

//ACABAN METODOS DE TARGET_REACHED
//////////////////////////////////////////////////////////////////////////////////I




//////////////////////////////////////////////////////////////////////////////////I
//METODO DE ATQUE

//Si vemos a un enemigo le disparamos si no es de nuestro equipo y nos centramos en uno de les enemigos que vemos
+enemies_in_fov(ID,Type,Angle,Distance,Health,Position)
  <- 
  ?position(Pos);
  .friend(Pos,Position, X);
  if(not(X)){
    ?enemies_seen(E);
    ?following(F);

    if (F==0 | F==ID | E > 5){
      -+following(ID);
      .look_at(Position);
      -+enemies_seen(0);
    };

    -+enemies_seen(E+1);
    .shoot(3,Position);
  }.
  //ACABA METODO DE ATAQUE
  //////////////////////////////////////////////////////////////////////////////////