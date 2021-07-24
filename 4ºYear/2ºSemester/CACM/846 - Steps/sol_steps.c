#include <math.h>
#include <stdio.h>

int main( int argc, char * argv[] )
{
    int n_cases;
    int x, y, steps, l, n;

    /* Leemos el número de casos */
    scanf("%d\n", &n_cases);

    while(n_cases > 0){
        scanf("%d %d\n", &x, &y);
		l = y - x;
		steps = 0;
		n = (int) (sqrt(l));
		steps = n;
		l -= n * (n + 1) / 2;

		while(l > 0) {
			while(n * (n + 1) / 2 > l) {
				n -= 1;
			}
			if(n * (n  + 1) / 2 == l) {
				l = 0;
				steps += n;
			}
			else {
				l -= n;
				steps += 1;
			}
		}
		printf("%d\n", steps);

        n_cases--;
    }
    
    return 0;
}
