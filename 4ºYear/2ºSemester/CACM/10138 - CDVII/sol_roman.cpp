#include <string>
#include <map>
#include <iostream>
#include <sstream>
#include<map>
#include <iomanip>
using namespace std; 

map<string, map<int, int>> m;
map<string, map<int, int>>::iterator it;
map<int, int>::iterator it2, it3;
int fee [24];

/* Método que calcula el tiempo en base al string con la fecha */
int calculate_time(const string& buf){
	int mon, day, hr, min;
	sscanf( buf.c_str(), "%d:%d:%d:%d", &mon, &day, &hr, &min );
	return day * 24 * 60 + hr * 60 + min;
}

/* Método sencillo para tener el valor absoluto */
int absolute(int x){ return x < 0 ? -x : x; }

/* Método que calcula el precio */
double calculate_price(map<int, int>& n){
	int ans = 0;
	for(it2 = n.begin(); it2 != n.end(); ++it2){
		/* Caso en que la entrada pertenece a una exit */
		if(it2->second >= 0){
			/* Y no es la primera entrada, que entonces obviamos */
			if(it2 != n.begin()) {
				it3 = it2;
				--it3;
				/* Cogemos la parte del enter si existe, que tendría que ser la anterior y calculamos el precio*/
				if(it3->second < 0){
					int prev = -it3->second - 1;
					int km = it2->second - prev;
					int pr = fee[(it3->first/60)%24];
					ans += 100 + absolute(km * pr);
				}
			}
		}
	}
	/* Le sumamos dos dólares (200) y pasamos de centavos a dólares */
	return ans == 0 ? 0.00 : (double)(ans+200)/100.0;
}

int main(int argc, char * argv[])
{
    int n_cases, i, f, n_photo;
	double price;
    
    string line;

	cout << setprecision(2) << fixed;

    cin >> n_cases;

    while(n_cases--) {
		m.clear();
        /* Leemos los precios de peaje de cada hora */
        for(i = 0; i < 24; i++) {
            cin >> fee[i];
        }
        cin.ignore();
        /* Guardamos las photos en un diccionario */
        while(getline(cin, line) && !line.empty()) {
            istringstream in(line);
			in >> line;
            /* Cogemos el diccionario relacionado con cada matrícula*/
			map<int, int>& n = m[line];
			in >> line;
            /* Calculamos el tiempo */
			int time = calculate_time(line);
            /* Cogemos el enter o exit */
			in >> line;
			int x;
            /* Cogemos los km */
			in >> x;
            /* Diccionario tiempo -> km acorde a eXit o eNter (km o -km) */
			n[time] = (line[1] == 'n' ? -x -1 : x);
        }

        for(it = m.begin(); it != m.end(); ++it) {
			/* it-> second valor del dic */
			price = calculate_price(it->second);
			if(price != 0.00) {
				/* it-> first clave del dic */
			    cout << it->first << " $" << price << endl;
            }
		}

		if(n_cases) cout << endl;
    }

    return 0;
}
