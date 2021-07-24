
#include <stdio.h>
#include <string.h>

#define MAX_LINES 100

static char game[MAX_LINES][MAX_LINES];
static char field[MAX_LINES][MAX_LINES];

int main( int argc, char * argv[] )
{
    int n, m, i, j, finish = 1, n_case = 1;
    int k, h;
    char c;
    
    while(finish) {
        scanf("%d", &n);
        scanf("%d", &m);
        getchar();
        if(n || m) {
            if(n_case > 1) {
                printf("\n");
            }

            for(i=0; i < n; i++) {
                for(j=0; j < m; j++) {
                    field[i][j] = '0';
                }
            }

            printf("Field #%d:\n", n_case);
            for(i=0; i < n; i++) {
                for(j=0; j < m; j++) {
                    scanf("%c", &game[i][j]);
                    if(game[i][j] == '*'){
                        field[i][j] = '*';
                                                
                        for(h = i - 1; h <= i + 1; h++){
                            for(k = j - 1; k <= j + 1; k++) {
                                if((h >= 0 && h < n) && (k >= 0 && k < m)){
                                    if(field[h][k] != '*'){
                                        field[h][k] += 1;
                                    }
                                }
                            }
                        }
                    }
                }
                getchar();
            }
            for(i=0; i < n; i++) {
                for(j=0; j < m; j++) {
                    printf("%c", field[i][j]);
                }
                printf("\n");
            }

            
        }
        else {
            finish = 0;
        } 
        ++n_case;
    }

    return 0;
}
