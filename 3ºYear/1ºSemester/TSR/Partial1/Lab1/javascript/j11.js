//Uso de operaciones asíncronas, aquí modeladas con la función setTimeout.
//Note los valores de índice e i en la ejecución.


for(var i=0; i<10; i++) tempo(i);
  

console.log("Terminado codigo script","   valor actual de i: ",i);


function tempo(índice){
	setTimeout(function(){console.log("índice:  ",índice,"  i:  ",i);},índice*1000);
}

