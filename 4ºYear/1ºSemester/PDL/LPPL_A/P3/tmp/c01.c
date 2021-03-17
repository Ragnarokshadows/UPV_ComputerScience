// Ejemplo de uso: operadores logicos y relacionales
// Lee un par de numeros "x" e "y", si (x != y) y (x, y >= 0)
// entonces devuelve el valor absoluto de (x-y), si no, repite
// la lectura hasta que se cumplan las restricciones.
//------------------------------------------------------------
int main ()
{ int x; int y; bool z;

  z= true;
  for (; z ;) {
    read(x); read(y);
    if ((x != y) || false)          
      if (!(x == 0) && true)                      
        if ((y > 0) && (x >= 0))
          if (y-x < 0) {
	    print(x-y); z = false;
	  }
          else {
	    print(y-x); z = false;
	  }
        else {}
      else {}
    else {}
  }

  return 0;
} 
