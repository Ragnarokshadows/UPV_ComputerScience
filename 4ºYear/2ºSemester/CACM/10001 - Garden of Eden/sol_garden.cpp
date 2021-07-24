#include <iostream>
#include <string>
using namespace std;

bool find_ancestor(int n_cells, int lat_index, unsigned int lattice, int identifier, int fcr, int plc)
{
    int id, i;
    /*Si el bit de lat_index es 1, id = identificador, sino id = -identificador - 1 (complemento en 1) */
    id = (lattice & (((unsigned int) 1) << lat_index)) ? identifier : ~identifier;
    for (i = 0; i < 4; i++, id >>= 2) {
        if (id & 3) {
            int lc_0 = -1, lc_1 = -1;

            if (id & 1) lc_0 = i * 2;
            if (id & 2) lc_1 = i * 2 + 1;
            if (!lat_index) { 
                if (id & 1 && find_ancestor(n_cells, lat_index + 1, lattice, identifier, lc_0 & 3, i) ||
                    id & 2 && find_ancestor(n_cells, lat_index + 1, lattice, identifier, lc_1 & 3, i)) {
                    
                    return true;
                }
            } else if (lat_index == n_cells - 1) { 
                if (lc_0 != -1 && (lc_0 >> 1) == fcr && (lc_0 & 3) == plc ||
                    lc_1 != -1 && (lc_1 >> 1) == fcr && (lc_1 & 3) == plc) {
                    
                    return true;
                }
            } else if (lc_0 != -1 && (lc_0 & 3) == plc || lc_1 != -1 && (lc_1 & 3) == plc) {
                if (find_ancestor(n_cells, lat_index + 1, lattice, identifier, fcr, i)) return true;
            }
        }
    }

    return false;
}

int main()
{
    int identifier, n_cells;
    string state;
    bool res;

    while (cin >> identifier >> n_cells >> state) {
        unsigned int lattice = 0;

        for (int i = 0; i < n_cells; i++) {
            /* Obtenemos el estado cómo un número */
            if (state[i] == '1') lattice |= ((unsigned int) 1) << (n_cells - i - 1);
        }
        res = find_ancestor(n_cells, 0, lattice, identifier, -1, -1);

        if(res) {
            cout << "REACHABLE\n" ;
        } else {
            cout << "GARDEN OF EDEN\n";
        }
    }

    return 0;
}