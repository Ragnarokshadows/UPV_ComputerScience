
#include <cmath>

/*
    Euclides algorithm for computing gcd(a,b) and x,y such that a*x + b*y = gcd(a, b)
*/
#define LOCAL_DEBUG

#if defined( LOCAL_DEBUG )
#include <cstdio>
#endif

int gcd(int a, int b, int *x, int *y)
{
    int g, x1, y1;

#if defined( LOCAL_DEBUG )
printf("=> gcd( %d, %d )\n", a, b);
#endif

    if (b > a) return gcd(b, a, y, x);

    if (b == 0) {
        *x = 1;
        *y = 0;
#if defined( LOCAL_DEBUG )
printf("<= x = %d  y = %d \n", *x, *y);
#endif
        return a;
    }

    g = gcd(b, a%b, &x1, &y1);

    *x = y1;
    *y = (x1 - std::floor(a / b) * y1);

#if defined( LOCAL_DEBUG )
printf("<= x = %d  y = %d \n", *x, *y);
#endif

    return g;
}

/*
    a * x  + b  * y  = gcd(a, b)
    b * x1 + a' * y1 = gcd(a, b)

    a * x + b * y  =  b * x1 + a' * y1 

- De la llamada recursiva tenemos calculados x1 e y1
- Sabemos que a' = a mod b, 
- Sabemos que x = y1, entonces

    a * y1 + b * y = b * x1 + (a - b * floor(a / b) * y1

- Dividimos por b

    a / b * y1 + y = x1 + (a / b - floor(a / b)) * y1
    y = x1 + (a/b - floor(a / b)) * y1 - a / b * y1
    y = x1 - floor(a / b) * y1
*/
