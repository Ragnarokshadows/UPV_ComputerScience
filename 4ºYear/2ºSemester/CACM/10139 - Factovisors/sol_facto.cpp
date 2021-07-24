#include <string>
#include <iostream>
#include <cmath>
#include <list>
using namespace std; 


typedef pair<uint64_t, int> prime_factor;

list<prime_factor> * primeFactorization(uint64_t n)
{
    list<prime_factor> *factors = new list<prime_factor>();
    int i, ii;

    prime_factor pf(2, 0);
    while ((n % 2) == 0) {
        n /= 2;
        pf.second ++;
    }
    if (pf.second > 0) factors->push_back(pf);

    i = 3;
    ii = i * i;
    while (ii <= n) {

        pf.first = i;
        pf.second = 0;

        while ((n % i) == 0) {
            pf.second ++;
            n /= i;
        }
        if (pf.second > 0) factors->push_back(pf);

        ii += (i << 2) + 4;
        i += 2;
    }

    if (n > 1) factors->push_back(prime_factor(n, 1));

    return factors;
}

int main( int argc, char * argv[] )
{
    unsigned long long n, m;
    unsigned long long p; 
    int c, res, t_freq;
    unsigned long long temp;
    list<prime_factor> *factors;

    while(scanf("%llu %llu\n", &n, &m) == 2) {
        res = 1;
        if(m == 0) {
			printf("0 does not divide %llu!\n",n);
		} 
        else if(m <= n) {
            printf("%llu divides %llu!\n", m, n);
        }
        else {
            factors = primeFactorization(m);
            std::list<prime_factor>::iterator it;
            for (it = factors->begin(); res && it != factors->end(); ++it){
                p = it->first;
                c = it->second;

                t_freq = 0;
                temp = p;
                while(n >= temp) {
                    t_freq += n / temp;
                    temp = temp * p;
                }
                if(t_freq < c) {
                    res = 0;
                }
            }

            if(res) {
                printf("%llu divides %llu!\n", m, n);
            } else {
                printf("%llu does not divide %llu!\n", m, n);
            }
        }
    }

    return 0;
}
