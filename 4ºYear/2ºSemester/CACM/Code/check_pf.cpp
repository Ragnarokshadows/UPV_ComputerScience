
#include <cmath>
#include <list>
#include <iostream>

using namespace std;


typedef pair<uint64_t, int> prime_factor;

list<prime_factor> * primeFactorization_v2(uint64_t n);

int main(int argc, char * argv[])
{
    int n;

    while (cin >> n) {
        cout << n << endl;

        list<prime_factor> * l = primeFactorization_v2(n);

        for (prime_factor pf : *l)
            cout << "      " << pf.first << "  " << pf.second << endl;

        cout << endl;
    }

    return EXIT_SUCCESS;
}
