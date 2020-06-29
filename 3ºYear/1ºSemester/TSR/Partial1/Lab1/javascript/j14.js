//Ejecución de varias operaciones asíncronas serializadas.

console.log("Inicio de la operación: ",
serieAsinc(10,function(){console.log("\n\n\tFIN de secuenciaAsin");}),"\n\n");


function serieAsinc(numeroInstancias,callback){
  var continuar=parseInt(numeroInstancias)||-1;
  if(continuar<0)return false;

  const num = numeroInstancias;
  var contador = num;
  
//INICIO PROCESAMIENTO SERIE---------------------
  principal();
  return true;
  
//             - - - - - - - - - -

  function principal(){
	var xx=num-contador;
	var retardo=intRandom(0,10000);
	setTimeout(g,retardo);
	
	function g(){
		console.log("---------------->  retardo:  "+retardo);
		contador--;
		if(contador == 0) {
			console.log("soy el:  "+xx + 
				 "  - - - - - - - - - - - - - - ->> Y SOY EL ÚLTIMO");
//FINAL PROCESAMIENTO SERIE-------------------------------
    
            callback();         
		} else { 
			console.log("soy el:  "+xx + " quedan: " + contador +
								   	  "  - ->> y no soy el último");
		    principal();
		}
	}
  }

  function intRandom(min,max) {
    return Math.floor(Math.random() * (max - min)) + min;
  }
}



