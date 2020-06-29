+flag(Pos): team(100) 
  <-
   .superhealth;
   !!recarga; 
     .wait(2000);
//  .goto(Pos);
//    +objetivo(Pos).
  !masmunicion.

+flag(Pos): team(200) 
  <-
   .superhealth;
   !!recarga; 
     .wait(5000);
//  .goto(Pos);
//    +objetivo(Pos).
  !masmunicion.

/*
+position(Pos):  objetivo(Pos)
<-
  .reload;
   -objetivo(_);
    !masmunicion.
 */
 
 
+!recarga
<-
    .superhealth;
	.wait(1000);
	!recarga.
 
+!masmunicion
<-
 .wait(5000);
   .reload;
   !masmunicion.



