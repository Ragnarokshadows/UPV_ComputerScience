// Ejemplo de uso: operadores de incremento y decremento
// Debe devolver: 5, 5, 4, 5  y  3, 3, 4, 3
//------------------------------------------------------
int main ()
{ int x; int y;

  x = 4; y = ++x; print(y); print(x);
  x = 4; y = x++; print(y); print(x);

  x = 4; y = --x; print(y); print(x); 
  x = 4; y = x--; print(y); print(x); 

  return 0;
} 
