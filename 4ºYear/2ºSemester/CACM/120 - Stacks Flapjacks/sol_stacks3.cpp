#include <iostream>
#include <cstdio>
#include <sstream>
using namespace std;

int main() {
    string line;
    while(getline(cin, line)) {
        stringstream sinput(line);
        int torre[1000], n_tortitas = 0;
        int lista_flips[1000], num_flips = 0;
        int i,j,k,aux;

        //PROCESAR EL INPUT
        while(sinput >> torre[n_tortitas]) n_tortitas++;
        printf("%d", torre[0]);
        for(i = 1; i < n_tortitas; i++) printf(" %d", torre[i]);
        printf("\n");

        //ARREGLAR LA TORRE DE TORTITAS
        //Por cada una de las tortitas, colocamos la más grande al final.
        for(i = 0; i < n_tortitas; i++) {
            //obtenemos la tortita mas grande en el rango [0,ultima_tortita]
            int ultima_tortita =  n_tortitas-i-1;
            int index = ultima_tortita;
            for(j = 0; j < n_tortitas-i; j++) {
                if(torre[index] < torre[j])
                    index = j;
            }
            //si la tortita esta ya bien colocada, continuamos con la siguiente iteracion
            if(index != ultima_tortita)
            {
                //hacemos el flip para colocar la tortita arriba (si no esta ya)
                if(index > 0) {
                    lista_flips[num_flips++] = n_tortitas-index;
                    for(j = 0, k = index; j < k; j++, k--)
                        aux = torre[j], torre[j] = torre[k], torre[k] = aux;
                }
                //hacemos el flip para colocar la tortita en la posición que nos interesa
                lista_flips[num_flips++] = i+1;
                for(j = 0, k = ultima_tortita; j < k; j++, k--)
                    aux = torre[j], torre[j] = torre[k], torre[k] = aux;
            }
        }
        for(i = 0; i < num_flips; i++) printf("%d ", lista_flips[i]);
        printf("0\n");
    }
    return 0;
}

