#include <iostream>
#include <vector>
#include <map>
#include <string>
using namespace std;

int main() {
    int cases;
    cin >> cases;

    while(cases--) {
        int turtles, i, found = 0;
        string name;

        /* Usamos un diccionario para obtener rápido el índice original ya que comprobamos siguiendo el orden inverso del target */
        map<string, int> original;
        vector<string> target;

        cin >> turtles;
        cin.ignore();

        /* Diccionario con el nombre y la posición original */
        for(i = 0; i < turtles; i++) {
            getline(cin, name);
            original[name] = i;
        }
        /* Array con las posiciones deseadas */
        for(i = 0; i < turtles; i++) {
            getline(cin, name);
            target.push_back(name);
        }
        /* Comparamos hasta encontrar en el orden inverso de las posiciones deseadas, una posición mayor que la anterior */
        for(i = turtles - 1; i >= 1 && !found; i--) {
            if (original[target[i]] < original[target[i - 1]]) found = 1;
        }
        if (!found) i--;
        /* Sacamos la solución */
        while (i >= 0) {
            cout << target[i] << endl;
            i--;
        }

        cout << endl;
    }

    return 0;
}
