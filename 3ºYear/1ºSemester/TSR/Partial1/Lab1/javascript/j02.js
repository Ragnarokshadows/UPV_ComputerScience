
//Clausuras de variables y funciones.

function f(y){
	var x=100;
	function g(){
		x++;
		console.log("incremento de x:  "+x);
		return x;
	}
	return function(){
			y++;
			console.log("y: " + y);
			console.log("y+g(): ",y+g());
			return y;
	}

}

var z=f(-100);
z();
z();
z();
