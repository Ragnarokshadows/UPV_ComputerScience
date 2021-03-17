// Ejemplo de manipulacion de vectores
// Devuelve la cuenta inversa de 9 a 0 (inclusive)
//------------------------------------------------
int a[10];

int main()
{ int i;

  for (i = 0; i < 10; i++) a[i] = i;
  for (i = 9; i >= 0; i--) print(a[i]);
 
  return 0;
}
