
#include <cmath>
#include <list>

using namespace std;

list<int> * primeFactorization(int n)
{
    list<int> *factors = new list<int>();
    int i, ii; //, s;

    while((n % 2) == 0) {
        factors->push_back(2);
        n /= 2;
    }

    i = 3; // s = sqrt(n)+1;
    ii = i*i;
    while(ii <= n /* i <= s */) {

        if ((n % i) == 0) {
            factors->push_back(i);
            n /= i;
            // s = sqrt(n)+1;
        } else {
            ii += i << 2 + 4;
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
