//TEAM_AXIS

// Plan inicial
+flag (F): team(200) 
  <-
  +aux(0);
  +vueltas(0);
  .print("Estoy listo").

//////////////////////////////////////////////////////////////////////////////////I
//HAN ROBADO LA BANDERA : NOS CENTRAMOS EN UN OBJETIVO Y LO SEGUIMOS

//Si vemos a alguien, lo perseguimos
+!se_van_a_enterar: enemies_in_fov(ID,Type,Angle,Distance,Health,Position)
<-
  -routine;
  .print("Se van a enterar -> ",ID);
  .look_at(Position);
  .goto(Position);
  -+vueltas(0);
  !!persecucion(ID).
  
//Si no vemos a nadie, damos hasta 5 vueltas y si no hemos visto a nadie, vamos hacia nuestra posición inicial
+!se_van_a_enterar: not(enemies_in_fov(ID,Type,Angle,Distance,Health,Position))
<-
  -routine;
  ?vueltas(V);
  -+vueltas(V+1);
  .turn(1.5708);
  .wait(500);
  .print("No veo a nadie, me giro");
  if(V == 5){
    -+vueltas(0);
    ?patroll_point(G);
    -patroll_point(G);
    +patroll_point(G);
    +routine;
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
  ?patroll_point(G);
  -patroll_point(G);
  +routine;
  +patroll_point(G).

//ACABAN PLANES CONTRA ROBAR RABNDERA
//////////////////////////////////////////////////////////////////////////////////I

// Plan para cuando aceptan nuestra oferta, en la que dejamos de patrullar
// y vamos a la posición del soldado
+acceptproposal[source(A)]: curando(A, Pos)
<-
  .print("Me voy a ayudar al agente: ", A, "a la posicion: ", Pos);
  -routine;
  -acceptproposal;
  .goto(Pos).

// Plan para cuando rechazan nuestra oferta
+cancelproposal[source(A)]: curando(A, Pos)
<-
  .print("Me cancelan mi proposicion");
  -cancelproposal;
  -curando(_,_).

// Plan para cuando un soldado nos pide ayuda, momento
// en el que le enviamos nuestra distancia a él
+curadmeplease(Pos)[source(A)]
  <-
  if(not(curando(_,_))){
    ?position(MiPos);
    .distance(Pos, MiPos, D);
    .send(A,tell,medicBid(D));
    +curando(A,Pos);
    -curadmeplease(_);
    .print("Toma mi oferta");
  }.

// Plan para avisar al capitán de que sigue teniendo que vigilar la bandera
+captain(C)
<-
  -captain(C);
  .send(C, achieve, mirar_bandera).

// Plan para establecer los puntos de patrulla 
+set_routine(G)[source(A)]
  <-
	+control_points(G);
  .length(G,L);
  +total_points(L);
  +routine;
  +patroll_point(0).

//Si llegamos al objetivo y estamos buscando la bandera, volvemos al origen y avisamos al capitan, si estábamos buscando
//solo volvemos al origen
+target_reached(T): banderita
  <-
  if (banderita){
    -banderita;
    .wait(1000);
    .get_service("captain");
    };

  ?origin(G);
  .goto(G);
  +formacion;
  -target_reached(T).

// Plan para cuando se llega a un punto de la patrulla.
// En ese momento se genera un paquete de munición y
// se establece cuál es el siguiente punto
+target_reached(T): routine
  <-
  .print("HEALTHPACK!");
  .cure;
  ?patroll_point(P);
  -patroll_point(P);
  +patroll_point(P+1);
  -target_reached(T).

// Plan para cuando se llega a la posición del soldado, momento
// en que se generan tres paquetes de munción y se alarma al soldado
// en cuestión. Después se vuelve a la patrulla
+target_reached(T): curando(A,T)
<-
  .send(A,tell,buscaPaquetes);
  .print("Deberias buscar mis paquetes");
	.cure;
  .cure;
  .cure;
	.print("MEDPACK x3!");
  -target_reached(T);
  +routine;
  ?patroll_point(P);
  -patroll_point(P);
  +patroll_point(P);
	-curando(_,_).

// Plan para ir a los puntos de patrulla de forma circular
+patroll_point(P): total_points(T) & P<T 
  <-
  ?control_points(C);
  .nth(P,C,A);
  .goto(A).

// Plan para ir a los puntos de patrulla de forma circular
+patroll_point(P): total_points(T) & P==T
  <-
  -patroll_point(P);
  +patroll_point(0).

// Plan para disparar a los enemigos cuando no haya aliados en la línea
// de fuego
+enemies_in_fov(ID,Type,Angle,Distance,Health,Position)
  <- 
  ?position(Pos);
  .friend(Pos,Position, X);
  if(not(X)){
    .shoot(3,Position);
  }.
