// Ejemplo sintactico-semantico (absurdo) sin errores.
// Comprobad el resultado con la funcion "verTdS" 
//----------------------------------------------------
bool a;
int  b[27];

int F (int x, int y)
{ 
   bool a[27]; int b; 

  return y-x;
}

int c[27];
int d;

int main()
{
  int z[27];  int x; 

  read(x); read(d);

  if (x < d) print( F(x,d));
  else print( F(d,x));

  return 0;
}
