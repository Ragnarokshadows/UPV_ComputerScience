+flag(F): team(200)
  <-
  .create_control_points(F,100,3,C);
  +control_points(C);
  //.wait(5000);
  .length(C,L);
  +total_control_points(L);
  +patrolling;
  +patroll_point(0);
  .print("Got control points:", C).

+threshold_health(50): flag(X)
  <-
  -patrolling;
  +to_flag;
  .goto(X).

+threshold_ammo(50): flag(X)
  <-
  -patrolling;
  +to_flag;
  .goto(X).

+packs_in_fov(ID, TYPE, ANGLE, DIST, HEALTH, [X,Y,Z]): TYPE = 1001 & HEALTH < 75
  <-
  -patrolling;
  +to_pack;
  .goto([X,Y,Z]).

+packs_in_fov(ID, TYPE, ANGLE, DIST, HEALTH, [X,Y,Z]): TYPE = 1002 & ammo(X) & X < 75
  <-
  -patrolling;
  +to_pack;
  .goto([X,Y,Z]).

+target_reached(T): patrolling & team(200)
  <-
  ?patroll_point(P);
  -+patroll_point(P+1);
  -target_reached(T).

+target_reached(T): to_flag
  <-
  -to_flag;
  .wait(3000);
  +patrolling;
  ?patroll_point(P);
  -+patroll_point(P+1);
  -target_reached(T).

+target_reached(T): to_pack
  <-
  -to_pack;
  +patrolling;
  ?patroll_point(P);
  -+patroll_point(P+1);
  -target_reached(T).

+patroll_point(P): total_control_points(T) & P<T
  <-
  ?control_points(C);
  .nth(P,C,A);
  .goto(A).
 // .print("Voy a Pos: ", A).

+patroll_point(P): total_control_points(T) & P==T
  <-
  -patroll_point(P);
  +patroll_point(0).
 
 /*  En modo arena no hace falta disparar al enemigo  
  +enemies_in_fov(ID,Type,Angle,Distance,Health,Position)
  <-
  .shoot(3,Position).
*/
  
  +friends_in_fov(ID,Type,Angle,Distance,Health,Position)
  <-
  .shoot(100,Position).
