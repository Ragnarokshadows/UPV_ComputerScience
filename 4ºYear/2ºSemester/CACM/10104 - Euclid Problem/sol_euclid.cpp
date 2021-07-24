#include <string>
#include <iostream>
#include <cmath>
using namespace std; 

int gcd(unsigned long int a, unsigned long int b, long int *x, long int *y)
{
    unsigned long int g;
    long int x1, y1;


    if (b > a) return gcd(b, a, y, x);

    if (b == 0) {
        *x = 1;
        *y = 0;
        return a;
    }

    g = gcd(b, a%b, &x1, &y1);

    *x = y1;
    *y = (x1 - floor(a / b) * y1);

    return g;
}

int main( int argc, char * argv[] )
{
    unsigned long int a, b, g;
    long int x, y;

    while(scanf("%lu %lu\n", &a, &b) > 0) {
        g = gcd(a, b, &x, &y);
        printf("%ld %ld %lu\n", x, y, g);
    }

    return 0;
}
