// Ejemplo con vectores: 8 errores
int main() 
{
  int  a[20];
  int  b;
  bool c; 
  bool d[0];         // Talla inapropiada del array
  int  a;            // Identificador repetido
  
  e = 27;            // Objeto no declarado
  b = c;             // Error de tipos en la "asignacion"
  c = a[2];          // Error de tipos en la "asignacion"
  a[4] = c;          // Error de tipos en la "asignacion"
  
  a[c] = 1 ;         // El indice del "array" debe ser entero
  b[14] = 27;        // El identificador debe ser de tipo "array"

  return 0;
}
