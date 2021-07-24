#include <iostream>
#include <cstring>
#include <cstdio>
#include <vector>
#include <cmath>
#include <list>

using namespace std;

list<int> * primeFactorization(int n)
{
    list<int> *factors = new list<int>();
    int i, ii;

    while ((n % 2) == 0) {
        factors->push_back(2);
        n /= 2;
    }

    i = 3;
    ii = i * i;
    while (ii <= n) {

        if ((n % i) == 0) {
            factors->push_back(i);
            n /= i;
        } else {
            ii += (i << 2) + 4;
            i += 2;
        }
    }

    if (n > 1) factors->push_back(n);

    return factors;
}

int sum_digits(unsigned int n) {
    int sum = 0;
    unsigned int k;

    for (k = n; k > 0; k /= 10) {
        sum += k % 10;
    }

    return sum;
}
 
int main( int argc, char * argv[] )
{
    unsigned int n, k, f, suma_f, suma_d;
    int finish,  n_cases;
    list<int> * factors;
    
    scanf("%d\n", &n_cases);

    while(n_cases > 0) {
        scanf("%u\n", &n);
        finish = 0;
        k = n + 1;

        while(!finish) {
            factors = primeFactorization(k);

            if(factors->size() != 1) {
                suma_f = 0;
                suma_d = sum_digits(k);

                for (int f : *factors){
                    suma_f += sum_digits(f);               
                }

                if(suma_f == suma_d) {
                    finish = 1;
                } else k++;
            } else {
                k++;
            }
        }

        printf("%u\n", k);
        --n_cases;
    }

    return 0;
}