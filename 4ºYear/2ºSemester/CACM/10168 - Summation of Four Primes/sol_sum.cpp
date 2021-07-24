#include <iostream>
#include <cstring>
#include <cstdio>
#include <vector>
using namespace std;
 
const int maxn=10000010;
bool isPrime[maxn];
vector <int> v;
int tol;
 
void fill_primes(){
    long long i, j;
    int finish;
    memset(isPrime,true,sizeof(isPrime));

    for(i = 2; i < maxn; i++) {
        if(isPrime[i]) {
            tol++;
            v.push_back(i);
        }

        finish = 0;

        for(j = 0; j < tol && i * v[j] < maxn && !finish; j++) {
            isPrime[i * v[j]] = false;
            if(i % v[j] == 0) finish = 1;
        }
    }
}
 
int main( int argc, char * argv[] )
{
    fill_primes();
    int n, i;
    int finish;

    while(scanf("%d", &n) > 0) {
        finish = 0;

        if(n < 8) {
            printf("Impossible.\n");
        } 
        else{
            if(n & 1) {
                printf("2 3");
                n -= 5;
            }
            else{
                printf("2 2");
                n -= 4;
            }

            for(i = 0; i < tol && v[i] <= n && !finish; i++) {
                if(isPrime[v[i]] && isPrime[n - v[i]]) {
                    printf(" %d %d\n", v[i], n - v[i]);
                    finish = 1;
                }
            }
        }
    }
    return 0;
}