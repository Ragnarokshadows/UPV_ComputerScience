+flag(F): team(200)
  <-
  +vida(85);
  +ir_a;
  +count(4);
  +count2(10);
  .print("Hola, vais a morir").


+target_reached(T): to_pack_mun
  <-
  -centro; 
  +ir_a;
  -to_pack_mun.

+target_reached(T): to_pack
  <-
  -centro;
  +ir_a;
  -to_pack.

+ammo(A): A <= 20 & esquina
  <-
  .goto([[128,0,128]]);
  +centro.

+packs_in_fov(ID, TYPE, ANGLE, DIST, HEALTH, [X,Y,Z]): TYPE = 1002 & ammo(X) & X <= 50 & centro & not(to_pack_mun)
  <-
  +to_pack_mun;
  -to_pack;
  .goto([X,Y,Z]).

+packs_in_fov(ID, TYPE, ANGLE, DIST, HEALTH, [X,Y,Z]): TYPE = 1001 & health(X) & X <= 50 & centro & not(to_pack)
  <-
  +to_pack;
  .goto([X,Y,Z]).

+look_for: not(friends_in_fov(ID,Type,Angle,Distance,Health,Position)) & count(X) & X = 0
  <-
  -look_for;
  -count(X);
  +ir_a;
  +count(4);
  +look_for.

+look_for: not(friends_in_fov(ID,Type,Angle,Distance,Health,Position)) & count(X) & X > 0
  <-
  -look_for;
  .turn(0.785398);
  -count(X);
  +count(X-1);
  .print("LookFor 1");
  .wait(500);
  +look_for.

+look_for: friends_in_fov(ID,Type,Angle,Distance,Health,Position)
  <-
  -look_for;
  .print("LookFor 2");
  +attack.

+attack: not(friends_in_fov(ID,Type,Angle,Distance,Health,Position))
  <-
  .print("Attack 1");
  -attack;
  +ir_a.

+health(X): vida(Y) & Y > X & X <= 45 
  <-
  -vida(Y);
  +vida(X);
  .print("Herido");
  +ir_a.

+health(X): vida(Y) & Y > X & X > 45
  <-
  -vida(Y);
  +vida(X);
  .stop;
  .print("Herido");
  +look_for.

+ir_a: position([X,Y,Z]) & X >= 128 & Z >= 128 & not(centro)
  <-
  -ir_a;
  +esquina;
  .goto([240,0,240]).

+ir_a: position([X,Y,Z]) & X >= 128 & Z <= 128 & not(centro)
  <-
  -ir_a;
  +esquina;
  .goto([240,0,15]).

+ir_a: position([X,Y,Z]) & X <= 128 & Z >= 128 & not(centro)
  <-
  -ir_a;
  +esquina;
  .goto([15,0,240]).

+ir_a: position([X,Y,Z]) & X <= 128 & Z <= 128 & not(centro)
  <-
  -ir_a;
  +esquina;
  .goto([15,0,15]).

+target_reached(T): centro
  <-
  .wait(1000).

+target_reached(T): esquina
  <-
  .look_at([128,0,128]).

+friends_in_fov(ID,Type,Angle,Distance,Health,Position)
  <-
  .shoot(10,Position).

+attack: friends_in_fov(ID,Type,Angle,Distance,Health,Position) & count2(X) & X > 0
  <-
  -attack;
  -count2(X);
  +count2(X-1);
  .print("Attack 2: ",Position);
  +attack.

+attack: friends_in_fov(ID,Type,Angle,Distance,Health,Position) & count2(X) & X = 0
  <-
  -attack;
  -count2(X);
  +count2(10);
  +ir_a.
