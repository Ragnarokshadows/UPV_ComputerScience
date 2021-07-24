
#include <stdio.h>
#include <string.h>
#include <iostream>
#include <sstream>
#include <vector>
#include <map>
#include <algorithm>

using namespace std;

int fact(int n)
{
    int res = 1;
    for (int i = 2; i <= n; i++)
        res = res * i;
    return res;
}

int nCr(int n, int r)
{
    return fact(n) / (fact(r) * fact(n - r));
}

int queue(int n, int p, int r)
{
	
	int res=0, aux;
	if (r > p)
		{
			aux = p;
			p = r;
			r = aux;
		}
		
	if (n==p && r==1) return 1;
	if (r==1 && p >= 1)
	{
		for(int k = p-1; k <= n-1; k++)
		{
			res += queue(k,p-1,1)*nCr(n-2, k-1)*fact(n-k-1);
		}
	} else if (p >= r && r > 1){
		for(int k = p; k <= n-r+1; k++)
		{
			res += queue(k,p,1)*nCr(n-1, k-1)*queue(n-k+1,r,1);
		}
	}
	return res;
}

int main() {
	int cases, N, P, R, aux, k, res;
	cin >> cases;

	while(cases--)
	{
		cin >> N;
		cin >> P;
		cin >> R;
		//Working with P >= R
		if (R > P)
		{
			aux = P;
			P = R;
			R = aux;
		}
		
		res = queue(N,P,R);
		printf("%d\n", res);
	}

	return (0);
}