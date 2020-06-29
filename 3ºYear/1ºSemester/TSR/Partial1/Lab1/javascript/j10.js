//Uso de operaciones asíncronas, aquí modeladas con la función setTimeout.
//Uso de la función setTimeout con pase de argumentos a la función listener.
//Note los valores de índice e i en la ejecución.


for(var i=0; i<10; i++) 
	setTimeout(function(índice){console.log("índice:  ",índice,"  i:  ",i);},
	             i*1000,i);

  

console.log("Terminado codigo script","   valor actual de i: ",i);



	

