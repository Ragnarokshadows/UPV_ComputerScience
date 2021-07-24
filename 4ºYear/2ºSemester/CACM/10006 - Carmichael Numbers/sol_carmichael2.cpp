
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

bool fermat(long base, long exp, long modulus)
{
	long temp = base;
	long res = 1;
	while (exp > 0)
	{
		if (exp % 2 == 1)
		{
			res = (res * base) % modulus;
		}
		exp /= 2;
		base = (base * base) % modulus;
	}
	return res == temp;
}


bool isCarmichael(long num)
{
	int i;
	for (i = 2; i < num; i++)
	{
		if (!fermat(i,num,num))
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

