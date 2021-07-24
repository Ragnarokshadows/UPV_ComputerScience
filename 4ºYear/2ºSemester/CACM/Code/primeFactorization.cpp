
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
/*
(i+2)*(i+2) = i*i + 2*i + 2*i + 4 = i*i + 4*i + 4
3*3 = 9
5*5 = 25 = 9 + 4*3 + 4 = 9+12+4 = 25
7*7 = 49 = 25 + 4*5 + 4 = 25+20+4 = 49

c += i+i+i+i+4;
c += i<<2 + 4;
*/

typedef pair<uint64_t, int> prime_factor;

list<prime_factor> * primeFactorization_v2(uint64_t n)
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
