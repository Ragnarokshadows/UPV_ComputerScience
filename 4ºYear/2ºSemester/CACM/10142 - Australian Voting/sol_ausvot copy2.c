
#include <stdio.h>
#include <string.h>

#define MAX_CANDIDATE 20
#define MAX_CHAR 80
#define MAX_BALLOT 1000

static char candidates[MAX_CANDIDATE][MAX_CHAR];
static int ballots[MAX_BALLOT][MAX_CANDIDATE];

int active_candidates[MAX_CANDIDATE];
int votes[MAX_CANDIDATE];

int main( int argc, char * argv[] )
{
    int n_cases, n_candidates, n_ballots, c, i, b, min_v, max_v;
    int finish, found;
    char ch;

    // Leemos el número de casos
    scanf("%d", &n_cases);

    while(n_cases > 0){

        n_ballots = 0;

        // Leemos el número de candidatos
        scanf("%d", &n_candidates);

        // Leemos los nombres de los candidatos
        for(c=0; c < n_candidates; c++) scanf(" %[^\n]%*c", candidates[c]);

        // Leemos las papeletas con los votos
        while( (ch=getchar()) != '\n' && ch != EOF ) {
            ballots[n_ballots][0] = ch - '0' - 1;

            for (i=1; i < n_candidates; i++) {
                scanf("%d", &ballots[n_ballots][i]);
                ballots[n_ballots][i]--;
            }
            getchar();
            n_ballots++;
        }

        // Acabamos si no hay candidatos y tampoco papeletas
        finish = !(n_candidates > 0 && n_ballots > 0);

        // Activamos todos los candidatos
        for(c=0; c < n_candidates; c++) active_candidates[c] = 1;
        
        // Mientras no haya acabado la elección
        while(!finish) {
            min_v = 1000; // +Infinito (máximo número de papeletas)
            max_v = -1;   // -Infinito (número negativo)

            // Reiniciamos la cuenta de votos de cada candidato
            for(c=0; c < n_candidates; c++) votes[c]=0;
            
            //Contamos los votos de los candidatos activos en orden de preferencia
            for(b=0; b < n_ballots; b++) {
                found = 0;

                for(i=0; i < n_candidates && !found; i++) {
                    c = ballots[b][i];

                    if (active_candidates[c]) {
                        votes[c]++;
                        found=1;
                    }
                }
            }

            // Guardamos el mínimo y máximo número de votos
            for(c=0; c < n_candidates; c++) {
                if (active_candidates[c]) {
                    if (votes[c] < min_v) min_v = votes[c];
                    if (votes[c] > max_v) max_v = votes[c];
                }
            }

            // Si el max y min es igual es que estamos en el caso de que todos tienen los mismos votos
            if (min_v == max_v) {
                for(c=0; c < n_candidates; c++) {
                    if (active_candidates[c]) printf("%s\n", candidates[c]);
                }
                
                finish = 1;
            } 
            
            else {
                for(c=0; c < n_candidates && !finish; c++) {
                    // Si el número de votos es más del 50% o el doble de los votos es mayor que el número de papeletas
                    if (2 * votes[c] >= n_ballots) {
                        printf("%s\n", candidates[c]);
                        finish = 1;
                    }

                    // Desactivamos los candidatos con el mínimo número de votos
                    if (votes[c] == min_v) active_candidates[c] = 0;
                }
            }
        }

        if (n_cases > 0) printf("\n");
        n_cases--;
    }
    
    return 0;
}
