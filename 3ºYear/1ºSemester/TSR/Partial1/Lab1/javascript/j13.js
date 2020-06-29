//Ejecución de varias operaciones asíncronas paralelizadas al estilo fork-join.

console.log("Inicio de la operación: ",
forkJoinAsinc(10,function(){console.log("\n\n\tFIN de forkJoinAsin");}),"\n\n");

function forkJoinAsinc(numeroInstancias,callback){
	
  var continuar=parseInt(numeroInstancias)||-1;
  if(continuar<0)return false;

  const num = numeroInstancias;
  var join = num;
  
//FORK -----------------------------
  for(var i=0; i<num; i++) {
	let retardo=intRandom(0,10000);
    setTimeout(function(indice){
		return function(retardo){
			console.log("                   retardo:  "+retardo);
			join--;
			if(join == 0) {
				console.log("soy el:  "+indice + 
			       "  - - - - - - - - - - - - - - ->> Y SOY EL ÚLTIMO");
//JOIN -----------------------------
			    callback();
			}
			   else  console.log("soy el:  "+indice + " quedan: " + join +
			                              "  - ->> y no soy el último");
			}
		}(i),retardo,retardo);
  }
  return true;

  function intRandom(min, max) {
    return Math.floor(Math.random() * (max - min)) + min;
  }
}


