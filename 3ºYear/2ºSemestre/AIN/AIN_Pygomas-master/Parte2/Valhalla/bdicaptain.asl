//TEAM_AXIS

// Plan inicial:
// -Registrar el servicio de capitán
// -Establecer el objetivo de crear la formación
+flag (F): team(200)
  <-
    .register_service("captain");
	.print("El capitan se ha registrado");
	!!form_circle. 

// Plan que alarma al resto de tropas de que han robado la bandera
+!se_van_a_enterar
<-
	.get_backups;
	.get_fieldops;
	.get_medics;
	.wait(1000);
	?myBackups(S);
	?myFieldops(Am);
	?myMedics(Me);

	.send(S, achieve, se_van_a_enterar);
	.send(Am, achieve, se_van_a_enterar);
	.send(Me, achieve, se_van_a_enterar).

// Plan para crear la foramción de las tropas cuando estén todas disponibles
+!form_circle: flag(F)
	<-
	.get_backups;
	.get_fieldops;
	.get_medics;
	.wait(1000);
	?myBackups(S);
	?myFieldops(Am);
	?myMedics(Me);

	.length(S,Ls);
	.length(Am,Lam);
	.length(Me,Lme);

	if (Ls==5 & Lam==2 & Lme==2) {

		.circleFlag(12,5,F,Ou);
		.reverse(Ou, Uo);

		//Enviar soldados al circulo
		.nth(0,S,A1);
		.nth(0,Ou,G1);
		.send(A1,tell,set_origin(G1));
		.print("Primer envio soldado");
		.print(G1);
		
		.nth(1,S,A2);
		.nth(1,Ou,G2);
		.send(A2,tell,set_origin(G2));
		.print("Segundo envio soldado");
		.print(G2);
		
		.nth(2,S,A3);
		.nth(2,Ou,G3);
		.send(A3,tell,set_origin(G3));
		.print("Tercer envio soldado");
		.print(G3);
		
		.nth(3,S,A4);
		.nth(3,Ou,G4);
		.send(A4,tell,set_origin(G4));
		.print("Cuarto envio soldado");
		.print(G4);

		.nth(4,S,A5);
		.nth(4,Ou,G5);
		.send(A5,tell,set_origin(G5));
		.print("Quinto envio soldado");
		.print(G5);
 
		//Enviar medicos y agentes de campo a patrullar el cículo
		.nth(0,Am,Aam1); //carguero Aam1
		.send(Aam1,tell,set_routine(Ou));
		.print("Primer envio ammo");

		.nth(1,Am,Aam2); //carguero Aam2
		.send(Aam2,tell,set_routine(Uo));
		.print("Segundo envio ammo");

		.nth(0,Me,Ame1); //medico Ame1
		.send(Ame1,tell,set_routine(Ou));
		.print("Primer envio medic");

		.nth(1,Me,Ame2); //medico Ame2
		.send(Ame2,tell,set_routine(Uo));
		.print("Segundo envio medico");

		//Capitán cerca de la bandera
		?flag([X,Y,Z]);
		+formacion;
		.goto([X-2,Y,Z-2]);
	};
	if(not(Lme==2) | not(Lam==2) | not(Ls == 5)){
		!!form_circle;
	}.

// Plan para cuando se llega al punto cercano de la bandera,
// momento en que se mira a ésta y se establece como objetivo
+target_reached(T): formacion
  <-
  -formacion;
  ?flag(F);
  .look_at(F);
  .wait(3000);
  !!mirar_bandera.
  
// Plan para observar si han robado la bandera o sigue segura
+!mirar_bandera
<-
	.flag(X);
	if(X){
		.print("Bandera asegurada");
		.wait(1000);
		!!mirar_bandera;
	};
	if(not(X)){
		.print("F, nos birlaron la bandera");
		!!se_van_a_enterar;
	}.

// Plan para disparar a los enemigos cuando no haya aliados en la línea
// de fuego
+enemies_in_fov(ID,Type,Angle,Distance,Health,Position)
  <- 
  ?position(Pos);
  .friend(Pos,Position, X);
  if(not(X)){
    .shoot(3,Position);
  }.