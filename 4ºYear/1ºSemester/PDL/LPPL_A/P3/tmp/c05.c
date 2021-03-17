// Calcula el factorial de un mumero > 0 y < 13
//---------------------------------------------
int factorial(int n)
{ int f;

  if (n <= 1) f=1;
  else f= n * factorial(n-1);
  
  return f;
}

int main()
{ int x;

  read(x);
  if (x > 0) 
    if (x < 13) print(factorial(x)); 
    else {}
  else {}
  
  return 0;
}
