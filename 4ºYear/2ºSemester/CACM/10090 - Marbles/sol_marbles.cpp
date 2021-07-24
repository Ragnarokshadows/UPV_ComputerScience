#include <iostream>
#include <cmath>
using namespace std;

typedef long long ll;

ll gcd(ll a, ll b, ll *x, ll *y)
{
    ll g, x1, y1;

    if (b > a) return gcd(b, a, y, x);

    if (b == 0) {
        *x = 1;
        *y = 0;
        return a;
    }

    g = gcd(b, a%b, &x1, &y1);

    *x = y1;
    *y = (x1 - std::floor(a / b) * y1);

    return g;
}

int main(){
    ll n, n1, n2, c1, c2;
    ll x, y, gcd_n, t1, t2, t, k1, k2, cost1, cost2;

    while(cin >> n >> c1 >> n1 >> c2 >> n2) {
        gcd_n = gcd(n1, n2, &x, &y);

        if(n % gcd_n != 0) {
            cout << "failed\n";
        } else {
            t1 = (ll) ceil(-(double) x * n / n2);
            t2 = (ll) floor((double) y * n / n1);
            
            if(t2 < t1) cout << "failed\n";
            else {
                cost1 = (n / gcd_n) * (c1 * x + c2 * y) + t1 * (c1 * n2 - c2 * n1) / gcd_n;
                cost2 = (n / gcd_n) * (c1 * x + c2 * y) + t2 * (c1 * n2 - c2 * n1) / gcd_n;
                
                if(cost1 < cost2) t = t1;
                else t = t2;
                
                k1 = (x * n + n2 * t) / gcd_n;
                k2 = (y * n - n1 * t) / gcd_n;
                cout << k1 << " " << k2 << "\n";
            }
        }
    }
    
    return 0;
}