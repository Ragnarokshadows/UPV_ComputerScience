
//Ejercicio de recapitulación (operaciones asíncronas y clausuras):
//     - ¿Cuál es el resultado de la ejecución de este código?
//     - Una vez contestada la cuestión anterior ejecute el código 
//       compruebe y la validez de la respuesta.



for (var i=0;i<5;i++) 
   (function(){
       setTimeout( function(){console.log("---> ",i);}, i*1000 );})();
         //5
         
for (var k=0;k<3;k++) 
   (function(k){
       setTimeout( 
         function(){console.log("==========> ",k);}, k*4010);})(k);
         //0,1,2
	   
for (var x=0;x<3;x++){ 
   (function(){
	   var mm=x;
       setTimeout( function(){
		   var m=x;
         console.log("::::::::::::::> ",m,"   ",mm);}, x*8030);})();
         //3 0, 3 1, 3 2
}




