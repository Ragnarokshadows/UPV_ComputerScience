// Ejemplo de uso: operadores logicos y relacionales
//--------------------------------------------------
int main ()
{ int x; int y; bool z;

  z= false;
  for (; z ;) {
    read(x); read(y);
    if ((x != y) || false)          
      if (!(x == 0) && true)                      
        if ((y >= 0) && (x > 0))
          if (y-x < 0) z = true;
          else z = false;
        else {}
      else {}
    else {}
  }

  return 0;
} 
