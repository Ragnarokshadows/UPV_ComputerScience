
#include <stdio.h>
#include <string.h>
#include <stdbool.h> 

#define MAX_NUM 65000

bool* calculateNotPrimes(bool *arr)
{
	arr[0] = arr[1] = true;
	long i = 0;
	for (i = 2; i*i < MAX_NUM + 1; i++)
	{
		if (!arr[i])
		{
			long j;
			for(j = i; i*j < MAX_NUM + 1; j++)
			{
				arr[i*j] = true;
			}
		}
	}
	return arr;
}

long fermat2(long base, long exp, long modulus)
{
	long res = 1;
	long aux;

	if (exp > 0)
	{
		aux = base % modulus;
		if (exp % 2 != 0){ /*exponent is odd*/
			res = aux;
		}
		aux = fermat2(aux,exp/2,modulus); /* ( (x mod n)^(y/2) ) mod n */
		res = (aux * aux * res) % modulus;
	}

	return res;
}

bool fermat(long a, long n)
{
	long aux = fermat2(a, n, n); /*(a^n) mod n*/
	return (aux == a);
}

bool isCarmichael(long num)
{
	int a;
	for (a = 2; a < num; a++)
	{
		if (!fermat(a,num))
		{
			return false;
		}
	}
	return true;
}

int main() {

	bool prime_list[MAX_NUM + 1] = { false };
	
	bool* not_primes = calculateNotPrimes(prime_list);

	long n;
	while(scanf("%d\n", &n)==1)
	{
		if (n != 0)
		{
			if(not_primes[n] && isCarmichael(n))
			{
				printf("The number %d is a Carmichael number.\n",n);
			} else {
				printf("%d is normal.\n",n);
			}
		}
		
	}

	return (0);
}

