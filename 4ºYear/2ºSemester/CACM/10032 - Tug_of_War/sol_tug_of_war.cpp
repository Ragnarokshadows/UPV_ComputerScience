
#include <stdio.h>
#include <string.h>
#include <iostream>
#include <sstream>
#include <vector>
#include <map>
#include <algorithm>

using namespace std;

bool stop = false;

void recursion(int nA, int nB, int wA, int wB, int k, int *solA, int *solB, int N, vector <int> pesos)
{
	if (k >= N)
	{	
		
		if (abs(nA - nB) <= 1){
			if (abs(wA - wB) < abs(*solA - *solB))
			{
				*solA = wA;
				*solB = wB;
				if (abs(*solA - *solB) <= 1) stop = true;
			}
		}
	} else if (!stop)
	{
		if (abs(wA - wB) < abs(wA - wB + 2*pesos[k]))
		{
			recursion(nA, nB + 1,wA, wB+pesos[k], k+1, solA, solB, N, pesos);
			recursion(nA + 1, nB,wA+pesos[k], wB, k+1, solA, solB, N, pesos);
		} else {
			recursion(nA + 1, nB, wA+pesos[k], wB, k+1, solA, solB, N, pesos);
			recursion(nA, nB + 1, wA, wB+pesos[k], k+1, solA, solB, N, pesos);
			
		}
	}

}



int main() {
	string str;
	int cases, n, num_players, aux;

	cin >> cases;

	while (cases--)
	{
		vector <int> pesos;
		cin >> num_players;
		aux=num_players;
		while(aux--)
    	{
			cin >> n;
			pesos.push_back(n);
		}

		sort(pesos.begin(), pesos.end(), greater<int>());
		
		int solA = 450 * 100;
		int solB = 0;
		recursion(0, 0, 0, 0, 0, &solA, &solB, num_players, pesos);
		printf("%d %d\n",min(solA, solB),max(solA, solB));
		if(cases > 0) printf("\n");
		stop = false;
	}
	




	return (0);
}