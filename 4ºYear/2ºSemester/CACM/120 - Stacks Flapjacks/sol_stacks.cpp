#include <string>
#include <map>
#include <vector>
#include <queue>
#include <algorithm>
#include <iostream>
#include <cstdio>
#include <sstream>
using namespace std;

int main( int argc, char *argv[] )
{
	vector<int> torre;
	vector<int> flips;
	string line;
    while(getline(cin, line)) {
        stringstream sinput(line);
		int n_tortitas = 0, i, j, k, aux, max_index;
				
		//PROCESAR EL INPUT
        while(sinput >> aux) torre.push_back(aux);
		int talla = torre.size();
        printf("%d", torre[0]);
        for(i = 1; i < talla; i++) printf(" %d", torre[i]);
        printf("\n");
	
		//REORDENAR
		int pendientes = talla;
		while(pendientes > 0){
		
			max_index = max_element(torre.begin(),torre.begin()+pendientes) - torre.begin();
			if (max_index+1 < pendientes){
				if (max_index > 0)
				{
					reverse(torre.begin(),torre.begin()+max_index+1);
					flips.push_back(talla-max_index);
				}
				reverse(torre.begin(),torre.begin()+pendientes);
				flips.push_back(talla-pendientes+1);
			}
			pendientes--;
			
		}
		for (int i = 0; i < flips.size(); i++) printf("%d ", flips[i] );
		printf("0\n");
		torre.clear();
		flips.clear();

	}
	return 0;
}
