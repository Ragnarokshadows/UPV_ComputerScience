
//Uso de operaciones asíncronas, aquí modeladas con la función setTimeout.
//Note el valor de índice asociado a las ejecuciones de las temporizaciones.


for(var i=0; i<10; i++) 
  setTimeout(function(índice){
	  return function(){console.log("índice: ",índice)}}(i),i*1000);


console.log("Terminado codigo script  valor actual de i: ",i);

