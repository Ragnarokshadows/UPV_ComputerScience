// Ejemplo de funciones y variables globales y locales
// Lee dos numeros (0 <= x, y < 10) y obtiene (x+y/2)
//----------------------------------------------------
int a[10];

bool inicializa (int x)
{ int i;
  for (i=0; i < x; i++)  a[i] = i;

  return true;
}

int suma(int x, int y)
{ int a;
  a = x + y;
  return a;
}

int division(int x, int y)
{
  return x/y;
}

int main()
{ int i; bool ok; int x; int y;

  for (ok = !inicializa(10); ! ok ;) {
    read(x); read(y);
    if (((x>=0)&&(x<10))&&((y>=0)&&(y<10))) ok = true; else {}
  }
  
  print(a[0]);
  print(a[1]);
  print(a[2]);
  print(a[3]);
  print(a[4]);
  print(a[5]);
  print(a[6]);
  print(a[7]);
  print(a[8]);
  print(a[9]);
  print(division(suma(a[x],y),2));
  
  return 0;
}
