+flag(Pos): team(100) 
  <-
  .superhealth;
  !!recarga;
  .wait(2000);
 // .goto(Pos);
  //  +objetivo(Pos).
    !masmedicina.

+flag(Pos): team(200) 
  <-
  .superhealth;
  !!recarga;
    .wait(5000);   
//  .goto(Pos);
//    +objetivo(Pos).
  !masmedicina.

/*
+position(Pos):  objetivo(Pos)
<-
  .cure;
  //.print("In ASL, Fieldop reloaded at :",X,Y,Z);
    -objetivo(_);
    !masmedicina.
*/

+!recarga
<-
    .superhealth;
	.wait(1000);
	!recarga.

+!masmedicina
<-
 .wait(5000);
 .cure;
   !masmedicina.



