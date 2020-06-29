//Uso de operaciones asíncronas, aquí modeladas con la función setTimeout.
//Note los valores de indice e i en la ejecución.


for(var i=0; i<10; i++) 
  setTimeout(function(índice){return function(){
		           console.log("índice:  ",índice,"  i:  ",i)}
		      }(i),i*1000);


console.log("Terminado codigo script","   valor actual de i: ",i);

