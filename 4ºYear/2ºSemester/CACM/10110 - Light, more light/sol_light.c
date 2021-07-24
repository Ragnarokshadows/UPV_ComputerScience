#include <math.h>
#include <stdio.h>

int main( int argc, char * argv[] )
{
    int finish = 0;
	unsigned long int n, aux;

    while(!finish){
		scanf("%lu\n", &n);
		
		if(n > 0){
			aux = (int) (sqrt(n));

			if(aux * aux == n) printf("yes\n");
			else printf("no\n");
		} else {
			finish = 1;
		}
    }
    
    return 0;
}
